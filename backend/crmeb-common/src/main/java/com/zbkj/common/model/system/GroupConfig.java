package com.zbkj.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_group_config")
@Schema(name = "GroupConfig对象", description = "组合配置表")
public class GroupConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "标签:1-首页banner,2-首页推荐，3-经营理念，4-友情链接, 5-快捷入口，8-首页底部二维码，9-首页广告,10-首页导航,11-付费会员权益,12-积分区间,13-开屏广告")
    private Integer tag;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "链接地址")
    private String linkUrl;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "值")
    private String value;

    @Schema(description = "描述")
    private String message;

    @Schema(description = "状态:是否显示")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "拓展字段")
    private String expand;

}
