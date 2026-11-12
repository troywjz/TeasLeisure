package com.zbkj.service.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zbkj.common.config.CrmebConfig;
import com.zbkj.common.constants.Constants;
import com.zbkj.common.exception.CrmebException;
import com.zbkj.common.model.system.SystemConfig;
import com.zbkj.common.utils.RedisUtil;
import com.zbkj.service.dao.SystemConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Reads system configuration values for services that need them during initialization.
 * Keeping this reader independent of SystemConfigService avoids a service dependency cycle.
 */
@Component
public class SystemConfigValueReader {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigValueReader.class);

    private final SystemConfigDao dao;
    private final RedisUtil redisUtil;
    private final CrmebConfig crmebConfig;

    public SystemConfigValueReader(SystemConfigDao dao, RedisUtil redisUtil, CrmebConfig crmebConfig) {
        this.dao = dao;
        this.redisUtil = redisUtil;
        this.crmebConfig = crmebConfig;
    }

    public String get(String name) {
        if (!crmebConfig.isAsyncConfig()) {
            SystemConfig systemConfig = getByName(name);
            if (ObjectUtil.isNull(systemConfig) || StrUtil.isBlank(systemConfig.getValue())) {
                return "";
            }
            return systemConfig.getValue();
        }

        Long size = redisUtil.getHashSize(Constants.CONFIG_LIST);
        if (size <= 0) {
            return loadAndCache(name);
        }
        Object data = redisUtil.hget(Constants.CONFIG_LIST, name);
        if (ObjectUtil.isNotNull(data)) {
            return data.toString();
        }
        return loadAndCache(name);
    }

    public String getRequired(String name) {
        String value = get(name);
        if (StrUtil.isBlank(value)) {
            throw new CrmebException("没有找到或配置：" + name + "数据");
        }
        return value;
    }

    private String loadAndCache(String name) {
        SystemConfig systemConfig = getByName(name);
        if (ObjectUtil.isNull(systemConfig) || StrUtil.isBlank(systemConfig.getValue())) {
            syncConfigCache(name, "");
            return "";
        }
        syncConfigCache(name, systemConfig.getValue());
        return systemConfig.getValue();
    }

    private SystemConfig getByName(String name) {
        LambdaQueryWrapper<SystemConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SystemConfig::getId, SystemConfig::getName, SystemConfig::getValue);
        wrapper.eq(SystemConfig::getStatus, false);
        wrapper.eq(SystemConfig::getName, name);
        wrapper.orderByDesc(SystemConfig::getId);
        wrapper.last(" limit 1");
        return dao.selectOne(wrapper);
    }

    private void syncConfigCache(String name, String value) {
        if (!redisUtil.hset(Constants.CONFIG_LIST, name, value)) {
            logger.error("同步系统配置缓存失败，name={}", name);
        }
    }
}
