package com.studykt.controller;


import com.studykt.entity.SearchRecord;
import com.studykt.entity.SearchTag;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.SearchTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(value = "api for Search Tag", tags = {"搜索分类相关接口"})
@RestController
@RequestMapping("/searchTag")
public class SearchTagController extends BaseController {

    @Autowired
    private SearchTagService searchTagService;

    @ApiOperation(value = "get hot search tags", notes = "获取热门分类api")
    @ApiImplicitParam(name = "numOfRecords", value = "获取的记录条数", required = false, dataType = "Integer", paramType = "query")
    @PostMapping("/hotTags")
    public CommonReturnType hotTags(Integer numOfRecords) {
        if (numOfRecords == null) {
            numOfRecords = 4;
        }
        List<String> list = searchTagService.selectTagNameOrderByCount(numOfRecords);
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "add search tags", notes = "添加搜索过的tag api")
    @PostMapping("/addSearchTag")
    public CommonReturnType addSearchTag(@RequestBody SearchTag searchTag) throws BusinessException {
        if (searchTag == null || StringUtils.isBlank(searchTag.getTagName())) {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
        searchTag.setId(sid.nextShort());
        searchTag.setCreateTime(new Date());
        searchTagService.addSearchTag(searchTag);
        return CommonReturnType.create();
    }

    

}
