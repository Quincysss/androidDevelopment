/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_Assignment;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FOOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f")
    , @NamedQuery(name = "Food.findByFoodid", query = "SELECT f FROM Food f WHERE f.foodid = :foodid")
    , @NamedQuery(name = "Food.findByFoodname", query = "SELECT f FROM Food f WHERE f.foodname = :foodname")
    , @NamedQuery(name = "Food.findByFoodCategory", query = "SELECT f FROM Food f WHERE f.foodCategory = :foodCategory")
    , @NamedQuery(name = "Food.findByTotalCalorie", query = "SELECT f FROM Food f WHERE f.totalCalorie = :totalCalorie")
    , @NamedQuery(name = "Food.findByServingunit", query = "SELECT f FROM Food f WHERE f.servingunit = :servingunit")
    , @NamedQuery(name = "Food.findByTotalServing", query = "SELECT f FROM Food f WHERE f.totalServing = :totalServing")
    , @NamedQuery(name = "Food.findByFat", query = "SELECT f FROM Food f WHERE f.fat = :fat")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @NotNull
    @Column(name = "FOODID")
    private Integer foodid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FOODNAME")
    private String foodname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FOOD_CATEGORY")
    private String foodCategory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_CALORIE")
    private double totalCalorie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "SERVINGUNIT")
    private String servingunit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_SERVING")
    private double totalServing;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAT")
    private double fat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foodid")
    private Collection<Consumption> consumptionCollection;

    public Food() {
    }

    public Food(Integer foodid) {
        this.foodid = foodid;
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

    @XmlTransient
    public Collection<Consumption> getConsumptionCollection() {
        return consumptionCollection;
    }

    public void setConsumptionCollection(Collection<Consumption> consumptionCollection) {
        this.consumptionCollection = consumptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodid != null ? foodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.foodid == null && other.foodid != null) || (this.foodid != null && !this.foodid.equals(other.foodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "new_Assignment.Food[ foodid=" + foodid + " ]";
    }
    
}
