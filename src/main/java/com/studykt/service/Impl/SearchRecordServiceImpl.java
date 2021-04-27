package com.studykt.service.Impl;

import com.studykt.entity.SearchRecord;
import com.studykt.mapper.SearchRecordMapper;
import com.studykt.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchRecordServiceImpl implements SearchRecordService {

    @Autowired
    private SearchRecordMapper searchRecordMapper;

    @Override
    public List<String> selectHotRecords(Integer numOfRecords) {
        if (numOfRecords == null || numOfRecords == 0) {
            numOfRecords = 5;
        }
        return searchRecordMapper.selectContentOrderByCount(numOfRecords);
    }

    @Override
    public int addSearchRecord(SearchRecord record) {
        return searchRecordMapper.insertSelective(record);
    }
}
