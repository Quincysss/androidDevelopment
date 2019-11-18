package com.example.myproject.model;

public class Food {
    Integer foodid;
    String foodname;
    String foodCategory;
    double totalCalorie;
    String servingunit;
    double totalServing;
    double fat;

    public Food(String foodname, String foodCategory, double totalCalorie, String servingunit, double totalServing, double fat) {
        this.foodname = foodname;
        this.foodCategory = foodCategory;
        this.totalCalorie = totalCalorie;
        this.servingunit = servingunit;
        this.totalServing = totalServing;
        this.fat = fat;
    }

    public Food(Integer foodid) {
        this.foodid = foodid;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public double getTotalCalorie() {
        return totalCalorie;
    }

    public void setTotalCalorie(double totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public double getTotalServing() {
        return totalServing;
    }

    public void setTotalServing(double totalServing) {
        this.totalServing = totalServing;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public Food(Integer foodid, String foodname, String foodCategory, double totalCalorie, String servingunit, double totalServing, double fat) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.foodCategory = foodCategory;
        this.totalCalorie = totalCalorie;
        this.servingunit = servingunit;
        this.totalServing = totalServing;
        this.fat = fat;
    }
}
