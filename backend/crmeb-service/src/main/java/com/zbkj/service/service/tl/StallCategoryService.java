package com.zbkj.service.service.tl;

import com.zbkj.common.model.tl.StallCategory;
import java.util.List;

public interface StallCategoryService {
    List<StallCategory> enabled();
    List<StallCategory> all();
    Integer save(StallCategory item);
    void delete(Integer id);
}
