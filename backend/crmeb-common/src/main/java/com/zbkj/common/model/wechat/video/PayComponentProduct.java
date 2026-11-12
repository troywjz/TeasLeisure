package com.zbkj.common.model.wechat.video;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组件商品表
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
@TableName("eb_pay_component_product")
@Schema(name = "PayComponentProduct对象", description = "组件商品表")
public class PayComponentProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键ID，商家自定义商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "主商品ID")
    private Integer primaryProductId;

    @Schema(description = "交易组件平台内部商品ID")
    private Integer componentProductId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "绑定的小程序商品路径")
    private String path;

    @Schema(description = "轮播图,多张")
    private String headImg;

    @Schema(description = "商品资质图片,多张")
    private String qualificationPics;

    @Schema(description = "第三级类目ID")
    private Integer thirdCatId;

    @Schema(description = "第三级类目名称")
    @TableField(exist = false)
    private String thirdCatName;

    @Schema(description = "品牌id")
    private Integer brandId;

    @Schema(description = "预留字段，用于版本控制")
    private String infoVersion;

    @Schema(description = "线上状态：0-初始值，5-上架，11-自主下架，13-违规下架/风控系统下架")
    private Integer status;

    @Schema(description = "商品草稿状态:1-未审核，2-审核中，3-审核失败，4-审核成功")
    private Integer editStatus;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "反多少积分")
    private Integer giveIntegral;

    @Schema(description = "规格 0单 1多")
    private Boolean specType;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "是否删除 0未删除 1删除")
    private Boolean isDel;

    @Schema(description = "是否单独分佣")
    private Boolean isSub;
}
