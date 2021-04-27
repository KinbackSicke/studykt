package com.studykt.controller.vo;

import java.util.Date;

public class CourseFavorVO {

    private String id;

    private String favorId;

    private String title;

    private String coverUrl;

    private Integer studyCount;

    private Integer favorCount;

    private Date FavorDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavorId() {
        return favorId;
    }

    public void setFavorId(String favorId) {
        this.favorId = favorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(Integer studyCount) {
        this.studyCount = studyCount;
    }

    public Integer getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(Integer favorCount) {
        this.favorCount = favorCount;
    }

    public Date getFavorDate() {
        return FavorDate;
    }

    public void setFavorDate(Date favorDate) {
        FavorDate = favorDate;
    }
}
