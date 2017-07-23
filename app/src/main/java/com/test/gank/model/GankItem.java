package com.test.gank.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenqin on 2017/7/22.
 */

public class GankItem extends LoadMoreBean implements Serializable{

    /**
     * _id : 5971719e421aa97de5c7c97d
     * createdAt : 2017-07-21T11:14:38.609Z
     * desc : 一款非常漂亮的 Material Design 风格的音乐播放器！超棒！
     * images : ["http://img.gank.io/9f05efe7-3196-4de4-af65-24e0a919a584"]
     * publishedAt : 2017-07-21T12:39:43.370Z
     * source : chrome
     * type : Android
     * url : https://github.com/aliumujib/Orin
     * used : true
     * who : 代码家
     */

    private static final long serialVersionUID = 7526472295622776147L;
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
