package com.studykt.controller.vo;

import java.util.List;

public class ProblemVO {

    private String id;

    private Integer type;

    private String category;

    private String content;

    private String analysis;

    private Integer pIndex;

    private Integer anoIndex;

    private Integer browseCount;

    private List<String> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getpIndex() {
        return pIndex;
    }

    public void setpIndex(Integer pIndex) {
        this.pIndex = pIndex;
    }

    public Integer getAnoIndex() {
        return anoIndex;
    }

    public void setAnoIndex(Integer anoIndex) {
        this.anoIndex = anoIndex;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
