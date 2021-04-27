package com.studykt.service.Impl;

import com.studykt.entity.SearchTag;
import com.studykt.mapper.SearchTagMapper;
import com.studykt.service.SearchTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTagServiceImpl implements SearchTagService {

    @Autowired
    private SearchTagMapper searchTagMapper;

    @Override
    public List<String> selectTagNameOrderByCount(Integer numOfRecords) {
        return searchTagMapper.selectTagOrderByCount(numOfRecords);
    }

    @Override
    public int addSearchTag(SearchTag searchTag) {
        return searchTagMapper.insertSelective(searchTag);
    }
}
