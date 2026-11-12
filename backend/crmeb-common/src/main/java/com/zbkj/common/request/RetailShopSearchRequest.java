package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 分销员分页列表查询请求对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RetailShopSearchRequest", description = "分销员分页列表查询请求对象")
public class RetailShopSearchRequest extends UserCommonSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "today,yesterday,lately7,lately30,month,year,/yyyy-MM-dd hh:mm:ss,yyyy-MM-dd hh:mm:ss/")
    private String dateLimit;

}
