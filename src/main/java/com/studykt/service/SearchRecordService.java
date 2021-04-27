package com.studykt.service;

import com.studykt.entity.SearchRecord;

import java.util.List;

public interface SearchRecordService {

    List<String> selectHotRecords(Integer numOfRecords);

    int addSearchRecord(SearchRecord record);

}
