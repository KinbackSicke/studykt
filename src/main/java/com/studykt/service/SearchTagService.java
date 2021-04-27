package com.studykt.service;

import com.studykt.entity.SearchTag;

import java.util.List;

public interface SearchTagService {

    List<String> selectTagNameOrderByCount(Integer numOfRecords);

    int addSearchTag(SearchTag searchTag);
}
