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
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId")
    , @NamedQuery(name = "Report.findByReportDate", query = "SELECT r FROM Report r WHERE r.reportDate = :reportDate")
    , @NamedQuery(name = "Report.findByBurnedCalories", query = "SELECT r FROM Report r WHERE r.burnedCalories = :burnedCalories")
    , @NamedQuery(name = "Report.findByStepsPerMile", query = "SELECT r FROM Report r WHERE r.stepsPerMile = :stepsPerMile")
    , @NamedQuery(name = "Report.findByCaloriesConsumed", query = "SELECT r FROM Report r WHERE r.caloriesConsumed = :caloriesConsumed")
    , @NamedQuery(name = "Report.findByCaloriesGoal", query = "SELECT r FROM Report r WHERE r.caloriesGoal = :caloriesGoal")
    , @NamedQuery(name = "Report.findByBurnedCaloriesANDSurname", query = "SELECT r FROM Report r WHERE r.burnedCalories = :burnedCalories AND r.userid.surname = :surname")
    , @NamedQuery(name = "Report.findByUserid", query = "SELECT r FROM Report r WHERE r.userid.userid = :userid")
})
    
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_ID")
    private Integer reportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_DATE")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BURNED_CALORIES")
    private Double burnedCalories;
    @Column(name = "STEPS_PER_MILE")
    private Integer stepsPerMile;
    @Column(name = "CALORIES_CONSUMED")
    private Double caloriesConsumed;
    @Column(name = "CALORIES_GOAL")
    private Double caloriesGoal;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Report(Integer reportId, Date reportDate) {
        this.reportId = reportId;
        this.reportDate = reportDate;
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

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "new_Assignment.Report[ reportId=" + reportId + " ]";
    }
    
}
