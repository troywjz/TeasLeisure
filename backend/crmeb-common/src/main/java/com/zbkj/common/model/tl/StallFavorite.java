package com.zbkj.common.model.tl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("eb_tl_stall_favorite")
public class StallFavorite implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO) private Long id;
    private Long stallId;
    private Integer userId;
    private Date createTime;
}
