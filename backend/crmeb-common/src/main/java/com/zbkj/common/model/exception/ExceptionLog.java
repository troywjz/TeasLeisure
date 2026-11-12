package com.zbkj.common.model.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 异常信息表
 * </p>
 *
 * @author HZW
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_exception_log")
@Schema(name = "ExceptionLog对象", description = "异常信息表")
public class ExceptionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "异常日志ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "异常的url")
    @TableField("exp_url")
    private String expUrl;

    @Schema(description = "异常的参数")
    @TableField("exp_params")
    private String expParams;

    @Schema(description = "异常的类型")
    @TableField("exp_type")
    private String expType;

    @Schema(description = "异常的类名")
    @TableField("exp_controller")
    private String expController;

    @Schema(description = "异常的方法名")
    @TableField("exp_method")
    private String expMethod;

    @Schema(description = "异常详细信息")
    @TableField("exp_detail")
    private String expDetail;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;


}
