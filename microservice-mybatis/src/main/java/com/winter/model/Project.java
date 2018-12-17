package com.winter.model;

import java.util.Date;

public class Project {
    private Integer projid;

    private Integer parentid;

    private Integer level;

    private String taskname;

    private String durationdate;

    private Date startdate;

    private Date enddate;

    private Integer pretask;

    private String resource;

    private Date importtime;

    private String batchnum;

    public Integer getProjid() {
        return projid;
    }

    public void setProjid(Integer projid) {
        this.projid = projid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
    }

    public String getDurationdate() {
        return durationdate;
    }

    public void setDurationdate(String durationdate) {
        this.durationdate = durationdate == null ? null : durationdate.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getPretask() {
        return pretask;
    }

    public void setPretask(Integer pretask) {
        this.pretask = pretask;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    public Date getImporttime() {
        return importtime;
    }

    public void setImporttime(Date importtime) {
        this.importtime = importtime;
    }

    public String getBatchnum() {
        return batchnum;
    }

    public void setBatchnum(String batchnum) {
        this.batchnum = batchnum == null ? null : batchnum.trim();
    }
}