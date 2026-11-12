package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zbkj.common.model.tl.StallCategory;
import com.zbkj.service.dao.tl.StallCategoryDao;
import com.zbkj.service.service.tl.StallCategoryService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

@Service
public class StallCategoryServiceImpl implements StallCategoryService {
    @Resource private StallCategoryDao dao;
    public List<StallCategory> enabled() { return dao.selectList(Wrappers.<StallCategory>lambdaQuery().eq(StallCategory::getEnabled,true).orderByAsc(StallCategory::getSort).orderByAsc(StallCategory::getId)); }
    public List<StallCategory> all() { return dao.selectList(Wrappers.<StallCategory>lambdaQuery().orderByAsc(StallCategory::getSort).orderByAsc(StallCategory::getId)); }
    public Integer save(StallCategory item) { if(item.getId()==null) dao.insert(item); else dao.updateById(item); return item.getId(); }
    public void delete(Integer id) { dao.update(null, Wrappers.<StallCategory>lambdaUpdate().eq(StallCategory::getId, id).set(StallCategory::getEnabled, false)); }
}
