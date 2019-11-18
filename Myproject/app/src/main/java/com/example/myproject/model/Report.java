package com.example.myproject.model;

import java.util.Date;

public class Report {
    private Integer reportId;
    private Date reportDate;
    private Double burnedCalories;
    private Integer stepsPerMile;
    private Double caloriesConsumed;
    private Double caloriesGoal;
    private Users userid;

    public Report(Date reportDate, Double burnedCalories, Integer stepsPerMile, Double caloriesConsumed, Double caloriesGoal, Users userid) {
        this.reportDate = reportDate;
        this.burnedCalories = burnedCalories;
        this.stepsPerMile = stepsPerMile;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesGoal = caloriesGoal;
        this.userid = userid;
    }

    public Report() {

    }

    public Users getUserid() { return userid; }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    public Report(Integer reportId, Date reportDate, Double burnedCalories, Integer stepsPerMile, Double caloriesConsumed, Double caloriesGoal) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.burnedCalories = burnedCalories;
        this.stepsPerMile = stepsPerMile;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesGoal = caloriesGoal;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Double getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(Double burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public Integer getStepsPerMile() {
        return stepsPerMile;
    }

    public void setStepsPerMile(Integer stepsPerMile) {
        this.stepsPerMile = stepsPerMile;
    }

    public Double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(Double caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public Double getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(Double caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }
}
