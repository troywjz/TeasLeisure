package com.zbkj.front.controller;

import com.zbkj.common.model.article.Article;
import com.zbkj.common.model.category.Category;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.ArticleResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.ArticleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 文章
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Slf4j
@RestController("ArticleFrontController")
@RequestMapping("api/front/article")
@Tag(name = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页列表
     */
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getList(@PathVariable(name="cid") String cid,
                                                             @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(articleService.getList(cid, pageParamRequest)));
    }

    /**
     * 热门列表
     */
    @Operation(summary = "热门列表")
    @RequestMapping(value = "/hot/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getHotList() {
        return CommonResult.success(CommonPage.restPage(articleService.getHotList()));
    }

    /**
     * 轮播列表
     */
    @Operation(summary = "轮播列表")
    @RequestMapping(value = "/banner/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Article>> getList() {
        return CommonResult.success(CommonPage.restPage(articleService.getBannerList()));
    }

    /**
     * 文章分类列表
     */
    @Operation(summary = "文章分类列表")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Category>> categoryList() {
        return CommonResult.success(CommonPage.restPage(articleService.getCategoryList()));
    }

    /**
     * 查询文章详情
     * @param id Integer
     */
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name = "id", description = "文章ID")
    public CommonResult<ArticleResponse> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getVoByFront(id));
   }
}



