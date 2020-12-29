package com.lagou.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Article {

    private Integer id;
    private String title;
    private String content;

    private String categories;

    private String tags;
    private Integer allow_comment;
    private String thumbnail;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date modified;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categories='" + categories + '\'' +
                ", tags='" + tags + '\'' +
                ", allow_comment=" + allow_comment +
                ", thumbnail='" + thumbnail + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getAllow_comment() {
        return allow_comment;
    }

    public void setAllow_comment(Integer allow_comment) {
        this.allow_comment = allow_comment;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
