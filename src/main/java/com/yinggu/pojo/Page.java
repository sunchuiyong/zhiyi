package com.yinggu.pojo;

public class Page {
    private int pageSize;
    private int currentPage;
    private int totalCount;
    private int totalPage;
    private int sqlLimit;
    private String userName;
    private String respondent;

    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(int pageSize, int currentPage, int totalCount) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalCount = totalCount;//总数
        this.totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;//总页数
        this.sqlLimit = (currentPage-1) * pageSize;
    }
    public Page(int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;//总数
        this.totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;//总页数
        this.sqlLimit = (currentPage-1) * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getSqlLimit() {
        return sqlLimit;
    }

    public void setSqlLimit(int sqlLimit) {
        this.sqlLimit = sqlLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }
}
