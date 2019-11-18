package com.example.myproject.model;

import java.util.Date;

public class Consumption {
    private Integer consumptionId;
    private Date conDate;
    private Double quantityFood;
    private Food foodid;
    private Users userid;

    public Consumption() {

    }

    public Consumption(Date conDate, Double quantityFood, Food foodid, Users userid) {
        this.conDate = conDate;
        this.quantityFood = quantityFood;
        this.foodid = foodid;
        this.userid = userid;
    }

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Date getConDate() {
        return conDate;
    }

    public void setConDate(Date conDate) {
        this.conDate = conDate;
    }

    public Double getQuantityFood() {
        return quantityFood;
    }

    public void setQuantityFood(Double quantityFood) {
        this.quantityFood = quantityFood;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }
}
