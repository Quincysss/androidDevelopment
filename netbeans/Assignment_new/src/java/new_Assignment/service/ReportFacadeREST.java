/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_Assignment.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import new_Assignment.Consumption;
import new_Assignment.Food;
import new_Assignment.Report;
import new_Assignment.Users;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("new_assignment.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "Assignment_newPU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByReportDate/{reportDate}")
    @Produces({"application/json"})
    public List<Report> findByReportDate(@PathParam("reportDate") java.sql.Date reportDate) {
        Query query = em.createNamedQuery("Report.findByReportDate");
        query.setParameter("reportDate", reportDate);
        return query.getResultList();
    }
    @GET
    @Path("findByUserId/{userid}")
    @Produces({"application/json"})
    public List<Report> findByUserId(@PathParam("userid") int userid) {
        Query query = em.createNamedQuery("Report.findByUserid");
        query.setParameter("userid", userid);
        return query.getResultList();
    }
    @GET
    @Path("findByBurnedCalories/{burnedCalories}")
    @Produces({"application/json"})
    public List<Report> findByBurnedCalories(@PathParam("burnedCalories") double burnedCalories) {
        Query query = em.createNamedQuery("Report.findByBurnedCalories");
        query.setParameter("burnedCalories", burnedCalories);
        return query.getResultList();
    }

    @GET
    @Path("findByStepsPerMile/{stepsPerMile}")
    @Produces({"application/json"})
    public List<Report> findByStepsPerMile(@PathParam("stepsPerMile") int stepsPerMile) {
        Query query = em.createNamedQuery("Report.findByStepsPerMile");
        query.setParameter("stepsPerMile", stepsPerMile);
        return query.getResultList();
    }

    @GET
    @Path("findByCaloriesConsumed/{caloriesConsumed}")
    @Produces({"application/json"})
    public List<Report> findByCaloriesConsumed(@PathParam("caloriesConsumed") double caloriesConsumed) {
        Query query = em.createNamedQuery("Report.findByCaloriesConsumed");
        query.setParameter("caloriesConsumed", caloriesConsumed);
        return query.getResultList();
    }

    @GET
    @Path("findByCaloriesGoal/{caloriesGoal}")
    @Produces({"application/json"})
    public List<Report> findByCaloriesGoal(@PathParam("caloriesGoal") double caloriesGoal) {
        Query query = em.createNamedQuery("Report.findByCaloriesGoal");
        query.setParameter("caloriesGoal", caloriesGoal);
        return query.getResultList();
    }

    @GET
    @Path("findByBurnedCaloriesANDSurname/{burnedCalories}/{surname}")
    @Produces({"application/json"})
    public List<Report> findByBurnedCaloriesANDSurname(@PathParam("burnedCalories") double burnedCalories,
            @PathParam("surname") String surname) {
        Query query = em.createNamedQuery("Report.findByBurnedCaloriesANDSurname");
        query.setParameter("burnedCalories", burnedCalories);
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @GET
    @Path("findByBurnedCaloriesANDFirstName/{burnedCalories}/{firstname}")
    @Produces({"application/json"})
    public List<Report> findByBurnedCaloriesANDFirstName(@PathParam("burnedCalories") double burnedCalories,
            @PathParam("firstname") String firstname) {
        TypedQuery<Report> r = em.createQuery("SELECT r FROM Report r WHERE r.burnedCalories = :burnedCalories AND r.userid.firstname = :firstname", Report.class);
        r.setParameter("burnedCalories", burnedCalories);
        r.setParameter("firstname", firstname);
        return r.getResultList();
    }

    @GET
    @Path("CalculateBurnPerStep/{userid}")
    @Produces({"application/json"})
    public JsonObject CalculateBurnPerStep(@PathParam("userid") int userid) {
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.userid = :userid", Users.class);
        query.setParameter("userid", userid);
        List<Users> list = query.getResultList();
        double BurnPerStep = 0;
        for (Users u : list) {
            BurnPerStep = u.getWeight() * 2.204 * 0.49 / u.getSteps();
        }
        JsonObject StepObject = Json.createObjectBuilder().add("BurnPerStep", Double.toString(BurnPerStep)).build();
        return StepObject;      
    }

    @GET
    @Path("CalculateBMR/{userid}")
    @Produces({"application/json"})
    public JsonObject CalculateBMR(@PathParam("userid") int userid) {
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.userid = :userid", Users.class);
        query.setParameter("userid", userid);
        List<Users> list = query.getResultList();
        double BMR = 0.0;
        for (Users u : list) {
            Date current = new Date();
            int age = current.getYear() - u.getDob().getYear();
            if (u.getGender().equals("male")) {
                BMR = (13.75 * u.getWeight()) + (5.003 * u.getHeight()) - (6.755 * age) + 66.5;
            } else if (u.getGender().equals("female")) {
                BMR = (9.563 * u.getWeight()) + (1.85 * u.getHeight()) - (4.676 * age) + 655.1;
            }
        }
        JsonObject BMRObject = Json.createObjectBuilder().add("BMR", Double.toString(BMR)).build();
        return BMRObject;
    }

    @GET
    @Path("CalculateRestBurned/{userid}")
    @Produces({"application/json"})
    public JsonObject CalculateRestBurned(@PathParam("userid") int userid) {
        Query query = em.createQuery("SELECT u from Users u WHERE u.userid = :userid", Report.class);
        query.setParameter("userid", userid);
        List<Users> list = query.getResultList();
        double totalCalories = 0;
        for (Users u : list) {
            double BMR = Double.parseDouble(CalculateBMR(userid).getString("BMR"));
            switch (u.getActlevel()) {
                case 1:
                    totalCalories = BMR * 1.2;break;
                case 2:
                    totalCalories = BMR * 1.375;break;
                case 3:
                    totalCalories = BMR * 1.55;break;
                case 4:
                    totalCalories = BMR * 1.55;break;
                case 5:
                    totalCalories = BMR * 1.9;break;
            }
        }
        JsonObject totalCaloriesObject = Json.createObjectBuilder().add("totalCalories", Double.toString(totalCalories)).build();
        return totalCaloriesObject;
    }

    @GET
    @Path("TodayTotalCalories/{userid}/{conDate}")
    @Produces({"application/json"})
    public JsonObject TodayTotalCalories(@PathParam("userid") Integer userid, @PathParam("conDate") java.sql.Date conDate) {
        Query query = em.createQuery("SELECT c from Consumption c WHERE c.userid.userid = :userid AND c.conDate = :conDate", Consumption.class);
        query.setParameter("userid", userid);
        query.setParameter("conDate", conDate);
        List<Consumption> list = query.getResultList();
        double totalCalories = 0;
        List<Food> list0 = null;
        for (Consumption c : list) {   
            Query query0 = em.createQuery("SELECT f FROM Food f WHERE f.foodid = :foodid", Food.class);
            query0.setParameter("foodid", c.getFoodid().getFoodid());
            list0 = query0.getResultList();
            for(Food i: list0){
                totalCalories += (c.getQuantityFood() * i.getTotalCalorie());    
            }
        }
        JsonObject totalCaloriesObject = Json.createObjectBuilder().add("TodayTotalCalories", Double.toString(totalCalories)).build();
        return totalCaloriesObject;
    }
    
    @GET
    @Path("RemainingCalorie/{userid}/{reportDate}")
    @Produces({"application/json"})
    public JsonObject RemainingCalorie (@PathParam("userid") Integer userid, @PathParam("reportDate") java.sql.Date reportDate)
    {
        Query query = em.createQuery("SELECT r from Report r WHERE r.userid.userid = :userid AND r.reportDate = :reportDate", Report.class);
        query.setParameter("userid", userid);
        query.setParameter("reportDate",reportDate);
        List<Report> list = query.getResultList();
        double remainCalories = 0;
        double totalCaloriesBurned = 0;
        double  totalcaloriesConsumed = 0;
        for (Report r : list)
        {
        remainCalories =r.getCaloriesGoal()-( r.getCaloriesConsumed() - r.getBurnedCalories()); 
        totalCaloriesBurned = r.getBurnedCalories();
        totalcaloriesConsumed = r.getCaloriesConsumed();
        }
        JsonObject totalCaloriesObject = Json.createObjectBuilder()
                .add("remainCalories", Double.toString(remainCalories))
                .add("totalCaloriesBurned", Double.toString(totalCaloriesBurned))
                .add("totalcaloriesConsumed",  Double.toString(totalcaloriesConsumed)).build();
        return totalCaloriesObject;
    }
    @GET
    @Path("PeriodTimeCalories/{userid}/{startDate}/{endDate}")
    @Produces({"application/json"})
    public JsonObject PeriodTimeCalories (@PathParam("userid") Integer userid, @PathParam("startDate") java.sql.Date startDate, @PathParam("endDate") java.sql.Date endDate)
    {
    Query query = em.createQuery("SELECT r FROM Report r WHERE r.userid.userid = :userid AND (r.reportDate BETWEEN :startDate AND :endDate)",Report.class);
    query.setParameter("userid", userid);
    query.setParameter("startDate", startDate);
    query.setParameter("endDate",endDate);
    double totalCaloriesBurned = 0;
    double  totalcaloriesConsumed = 0;
    int totalstepstaken =0;
    List<Report> list = query.getResultList();
    for (Report r : list)
    {
      totalCaloriesBurned += r.getBurnedCalories();
      totalcaloriesConsumed += r.getCaloriesConsumed();
      totalstepstaken += r.getStepsPerMile();
    }
    JsonObject totalCalorieObject = Json.createObjectBuilder()
                .add("totalstepstaken", Integer.toString(totalstepstaken))
                .add("totalCaloriesBurned", Double.toString(totalCaloriesBurned))
                .add("totalcaloriesConsumed",  Double.toString(totalcaloriesConsumed)).build();
     return totalCalorieObject;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
