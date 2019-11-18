package com.example.myproject;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;




@Entity
public class StepEntity {
    @PrimaryKey(autoGenerate = true)
    public Integer stepId;
    @ColumnInfo(name = "UserID")
    public Integer userid;
    @ColumnInfo(name = "Steps")
    public Integer steps;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    @ColumnInfo(name= "Date")
    public String date;

    public StepEntity(Integer userid, Integer steps, String date) {
        this.userid = userid;
        this.steps = steps;
        this.date = date;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }
}
