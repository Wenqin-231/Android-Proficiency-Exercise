package com.test.gank.model;

import com.google.gson.annotations.SerializedName;
import com.test.gank.db.DateConverter;
import com.test.gank.db.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenqin on 2017/7/22.
 */
@Entity
public class GankItem extends LoadMoreBean implements Serializable {

    /**
     * gankID : 5971719e421aa97de5c7c97d
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

    @Id
    private Long DbId; // 自增ID

    @SerializedName("_id")
    @Unique
    private String gankID;
    private String createdAt;
    private String desc;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @Convert(converter = StringConverter.class, columnType = String.class)
    private List<String> images;

    @Generated(hash = 1104031970)
    public GankItem(Long DbId, String gankID, String createdAt, String desc,
            String publishedAt, String source, String type, String url,
            boolean used, String who, List<String> images) {
        this.DbId = DbId;
        this.gankID = gankID;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.images = images;
    }

    @Generated(hash = 1292024746)
    public GankItem() {
    }

    public Long getDbId() {
        return DbId;
    }

    public void setDbId(Long DbId) {
        this.DbId = DbId;
    }

    public String getGankID() {
        return gankID;
    }

    public void setGankID(String gankID) {
        this.gankID = gankID;
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

    public boolean getUsed() {
        return this.used;
    }
}
