package com.studykt.controller;


import com.studykt.response.CommonReturnType;
import com.studykt.service.SearchRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "api for Search Record", tags = {"搜索记录相关接口"})
@RestController
@RequestMapping("/searchKey")
public class SearchRecordController extends BaseController {

    @Autowired
    private SearchRecordService searchRecordService;

    @ApiOperation(value = "get hot search keys", notes = "获取热门搜索关键词api")
    @ApiImplicitParam(name = "numOfRecords", value = "获取的记录条数", required = true, dataType = "Integer", paramType = "query")
    @PostMapping("/hotKeys")
    public CommonReturnType hotKeys(Integer numOfRecords) {
        List<String> list = searchRecordService.selectHotRecords(numOfRecords);
        return CommonReturnType.create(list);
    }

}
