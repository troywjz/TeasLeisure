package com.zbkj.common.model.log;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 敏感操作日志表
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_sensitive_method_log")
@Schema(name = "SensitiveMethodLog对象", description = "敏感操作日志表")
public class SensitiveMethodLog implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "管理员id")
    private Integer adminId;

    @Schema(description = "管理员账号")
    private String adminAccount;

    @Schema(description = "接口描述")
    private String description;

    @Schema(description = "请求方式")
    private String methodType;

    @Schema(description = "方法名称")
    private String method;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求URL")
    private String url;

    @Schema(description = "主机地址")
    private String ip;

    @Schema(description = "请求参数")
    private String requestParam;

    @Schema(description = "返回参数")
    private String result;

    @Schema(description = "操作状态（0正常 1异常）")
    private Integer status;

    @Schema(description = "错误消息")
    private String errorMsg;

    @Schema(description = "创建时间")
    private Date createTime;


}
