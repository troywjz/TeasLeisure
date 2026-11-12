package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("eb_tl_category")
public class StallCategory {
    @TableId(type = IdType.AUTO) private Integer id;
    private String name;
    private String icon;
    private Integer sort;
    private Boolean enabled;
}
