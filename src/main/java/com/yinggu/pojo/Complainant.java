package com.yinggu.pojo;

public class Complainant {
    private int id;
    private String complainant;
    private String respondent;
    private String content;
    private String data;
    private String evidence1;
    private String evidence2;
    private String evidence3;
    private String savePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEvidence1() {
        return evidence1;
    }

    public void setEvidence1(String evidence1) {
        this.evidence1 = evidence1;
    }

    public String getEvidence2() {
        return evidence2;
    }

    public void setEvidence2(String evidence2) {
        this.evidence2 = evidence2;
    }

    public String getEvidence3() {
        return evidence3;
    }

    public void setEvidence3(String evidence3) {
        this.evidence3 = evidence3;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "Complainant{" +
                "id=" + id +
                ", complainant='" + complainant + '\'' +
                ", respondent='" + respondent + '\'' +
                ", content='" + content + '\'' +
                ", data='" + data + '\'' +
                ", evidence1='" + evidence1 + '\'' +
                ", evidence2='" + evidence2 + '\'' +
                ", evidence3='" + evidence3 + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
