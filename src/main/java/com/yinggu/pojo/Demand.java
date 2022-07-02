package com.yinggu.pojo;

import org.springframework.web.multipart.MultipartFile;

public class Demand {
    private int id;
    private String userName;
    private String title;
    private String content;
    private String time;
    private String place;
    private String saveImgName;
    private String savePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSaveImgName() {
        return saveImgName;
    }

    public void setSaveImgName(String saveImgName) {
        this.saveImgName = saveImgName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", saveImgName='" + saveImgName + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
