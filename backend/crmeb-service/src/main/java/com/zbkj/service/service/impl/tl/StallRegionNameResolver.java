package com.zbkj.service.service.impl.tl;

import com.zbkj.common.model.tl.Stall;
import com.zbkj.service.service.SystemCityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Resolves region labels for one response page with a single SystemCity query. */
@Component
public class StallRegionNameResolver {
    @Resource private SystemCityService systemCityService;

    public Map<Integer, String> resolve(Collection<Stall> stalls) {
        if (stalls == null || stalls.isEmpty()) return Collections.emptyMap();
        Set<Integer> ids = new HashSet<>();
        for (Stall stall : stalls) {
            addCode(ids, stall.getCityCode());
            addCode(ids, stall.getDistrictCode());
        }
        return ids.isEmpty() ? Collections.emptyMap() : systemCityService.getCityNameMap(ids);
    }

    public String name(String code, Map<Integer, String> names) {
        Integer id = parseCode(code);
        return id == null || names == null ? null : names.get(id);
    }

    private void addCode(Set<Integer> ids, String code) {
        Integer id = parseCode(code);
        if (id != null) ids.add(id);
    }

    private Integer parseCode(String code) {
        if (StringUtils.isBlank(code)) return null;
        try { return Integer.valueOf(code.trim()); }
        catch (NumberFormatException ignored) { return null; }
    }
}
