package com.zbkj.common.response.tl;

import com.zbkj.common.model.tl.StallCategory;
import com.zbkj.common.vo.SystemCityTreeVo;
import lombok.Data;

import java.util.List;

@Data
public class StallOperatorCreateOptionsResponse {
    private List<StallCategory> categories;
    private List<SystemCityTreeVo> cityTree;
}
