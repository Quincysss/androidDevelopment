/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_Assignment;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CONSUMPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumption.findAll", query = "SELECT c FROM Consumption c")
    , @NamedQuery(name = "Consumption.findByConsumptionId", query = "SELECT c FROM Consumption c WHERE c.consumptionId = :consumptionId")
    , @NamedQuery(name = "Consumption.findByConDate", query = "SELECT c FROM Consumption c WHERE c.conDate = :conDate")
    , @NamedQuery(name = "Consumption.findByQuantityFood", query = "SELECT c FROM Consumption c WHERE c.quantityFood = :quantityFood")})
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUMPTION_ID")
    private Integer consumptionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_DATE")
    @Temporal(TemporalType.DATE)
    private Date conDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTITY_FOOD")
    private Double quantityFood;
    @JoinColumn(name = "FOODID", referencedColumnName = "FOODID")
    @ManyToOne(optional = false)
    private Food foodid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Consumption() {
    }

    public Consumption(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Consumption(Integer consumptionId, Date conDate) {
        this.consumptionId = consumptionId;
        this.conDate = conDate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumptionId != null ? consumptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.consumptionId == null && other.consumptionId != null) || (this.consumptionId != null && !this.consumptionId.equals(other.consumptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "new_Assignment.Consumption[ consumptionId=" + consumptionId + " ]";
    }
    
}
