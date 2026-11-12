package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 门店店员表
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
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_store_staff")
@Schema(name = "SystemStoreStaff对象", description = "门店店员表")
public class SystemStoreStaffRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "移动端用户 id")
    @NotNull(message = "请选择移动端用户")
    @Min(value = 1, message = "请选择移动端用户")
    private Integer uid;

    @Schema(description = "管理员头像")
    private String avatar;

    @Schema(description = "提货点id，开启订单核销时必填")
    private Integer storeId;

    @Schema(description = "移动端管理员/核销员名称 [昵称]")
    @NotBlank(message = "管理员名称不能为空")
    @Length(max = 64,message = "管理员名称不能超过64个字符")
    private String staffName;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "核销开关：0=关闭，1=开启")
    private Integer verifyStatus;

    @Schema(description = "商家管理状态：0=禁用，1=启用")
    private Integer status;


}
