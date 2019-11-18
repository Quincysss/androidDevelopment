/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_Assignment.service;

import java.util.List;
import javax.ejb.Stateless;
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
import new_Assignment.Users;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("new_assignment.users")
public class UsersFacadeREST extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "Assignment_newPU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Users entity) {
        super.create(entity);
    }
    
    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create2(Users entity) {
        super.create(entity);
        return entity.getUserid();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Users entity) {
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
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    @GET
    @Path("findByFirstname/{firstname}")
    @Produces({"application/json"})
    public List<Users> findByFirstname(@PathParam("firstname") String
    Firstname) 
    {
    Query query = em.createNamedQuery("Users.findByFirstname");
    query.setParameter("firstname", Firstname);
    return query.getResultList();
    }
    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Users> findBySurname (@PathParam("surname") String Surname)
    {
    Query query = em.createNamedQuery("Users.findBySurname");
    query.setParameter("surname", Surname);
    return query.getResultList();
    }
    
    @GET
    @Path("findByeMail/{eMail}")
    @Produces({"application/json"})
    public List<Users> findByEMail (@PathParam("eMail") String eMail)
    {
    Query query = em.createNamedQuery("Users.findByEMail");
    query.setParameter("eMail", eMail);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Users> findByDob (@PathParam("dob") java.sql.Date dob)
    {
    Query query = em.createNamedQuery("Users.findByDob");
    query.setParameter("dob", dob);
    return query.getResultList();
    }
    
    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Users> findByHeight (@PathParam("height") int height)
    {
    Query query = em.createNamedQuery("Users.findByHeight");
    query.setParameter("height", height);
    return query.getResultList();
    }
    
    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Users> findByWeight (@PathParam("weight") int weight)
    {
    Query query = em.createNamedQuery("Users.findByWeight");
    query.setParameter("weight", weight);
    return query.getResultList();
    }
    
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})   
    public List<Users> findByGender (@PathParam("gender") String gender)
    {
        Query query = em.createNamedQuery("Users.findByGender");
        query.setParameter("gender",gender);
        return query.getResultList();
    
    }
    
    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})   
    public List<Users> findByAddress (@PathParam("address") String address)
    {
        Query query = em.createNamedQuery("Users.findByAddress");
        query.setParameter("address",address);
        return query.getResultList();
    
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})   
    public List<Users> findByPostcode (@PathParam("postcode") String postcode)
    {
        Query query = em.createNamedQuery("Users.findByPostcode");
        query.setParameter("postcode",postcode);
        return query.getResultList();
    }
    
    @GET
    @Path("findBySteps/{steps}")
    @Produces({"application/json"})   
    public List<Users> findBySteps (@PathParam("steps") int steps)
    {
        Query query = em.createNamedQuery("Users.findBySteps");
        query.setParameter("steps",steps);
        return query.getResultList();
    }
    
    @GET
    @Path("findByHeightANDWeight/{height}/{weight}")
    @Produces({"application/json"})
    public List<Users> findByHeightANDWeight(@PathParam("height") double height,
            @PathParam("weight") double weight)
    {
     TypedQuery<Users> u = em.createQuery("SELECT u FROM Users u WHERE u.height = :height AND u.weight = :weight", Users.class);
     u.setParameter("height", height);
     u.setParameter("weight", weight);
     return u.getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
