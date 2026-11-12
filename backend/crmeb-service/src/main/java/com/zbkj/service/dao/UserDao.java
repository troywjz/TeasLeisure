package com.zbkj.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.common.model.user.User;
import com.zbkj.common.response.UserSpreadPeopleItemResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 用户表 Mapper 接口
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
public interface UserDao extends BaseMapper<User> {

    /** Locks only the user key row for serialized per-user messaging counters. */
    @Select("SELECT uid FROM eb_user WHERE uid = #{userId} FOR UPDATE")
    Integer lockUserRowById(@Param("userId") Integer userId);

    List<UserSpreadPeopleItemResponse> getSpreadPeopleList(Map<String, Object> map);

    List<User> findAdminList(Map<String, Object> map);

    List<User> findRetailPeopleList(Map<String, Object> map);
}
