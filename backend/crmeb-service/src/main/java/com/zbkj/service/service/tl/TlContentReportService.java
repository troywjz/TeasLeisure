package com.zbkj.service.service.tl;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.TlContentReport;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.TlContentReportRequest;
public interface TlContentReportService {
 Long submit(TlContentReportRequest request);
 PageInfo<TlContentReport> pending(PageParamRequest page);
 void handle(Long id, boolean resolve, boolean hideTarget, String note);
}
