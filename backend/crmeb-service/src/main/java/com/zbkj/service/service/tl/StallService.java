package com.zbkj.service.service.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.StallSearchRequest;
import com.zbkj.common.request.tl.StallSubmissionRequest;
import com.zbkj.common.request.tl.StallVisibilityRequest;
import com.zbkj.common.request.tl.StallDuplicateSearchRequest;
import com.zbkj.common.response.tl.StallDuplicateCandidateResponse;
import com.zbkj.common.response.tl.StallPublicResponse;
import java.util.List;

public interface StallService {
    PageInfo<StallPublicResponse> discover(StallSearchRequest request);
    StallPublicResponse getPublished(Long id);
    Long submit(StallSubmissionRequest request);
    Long createByOperator(StallSubmissionRequest request);
    PageInfo<Stall> getPending(PageParamRequest request);
    PageInfo<Stall> manage(PageParamRequest page, String keyword, String cityCode, String districtCode,
                           Integer categoryId, Integer auditStatus);
    void setManagedVisibility(Long id, boolean hide, StallVisibilityRequest request);
    List<StallDuplicateCandidateResponse> findDuplicateCandidates(StallDuplicateSearchRequest request);
    void review(Long id, boolean approve, String note);
}
