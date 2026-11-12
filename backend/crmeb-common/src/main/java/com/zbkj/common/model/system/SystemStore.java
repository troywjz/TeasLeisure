package com.zbkj.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

/**
 * 门店自提
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
@TableName("eb_system_store")
@Schema(name = "SystemStore对象", description = "门店自提")
public class SystemStore implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "门店名称")
    private String name;

    @Schema(description = "简介")
    private String introduction;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "省市区")
    private String address;

    @Schema(description = "详细地址")
    private String detailedAddress;

    @Schema(description = "门店logo")
    private String image;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "核销有效日期")
    private String validTime;

    @Schema(description = "每日营业开关时间")
    private String dayTime;

    @Schema(description = "是否显示")
    private Boolean isShow;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
