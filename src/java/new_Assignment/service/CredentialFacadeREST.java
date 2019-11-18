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
import new_Assignment.Credential;
import new_Assignment.Report;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("new_assignment.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "Assignment_newPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credential entity) {
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
    public Credential find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})   
    public List<Credential> findByUsername (@PathParam("username") String username)
    {
        Query query = em.createNamedQuery("Credential.findByUsername");
        query.setParameter("username",username);
        return query.getResultList();
    }

     @GET
    @Path("findByPassword/{password}")
    @Produces({"application/json"})   
    public List<Credential> findByPassword (@PathParam("password") String password)
    {
        Query query = em.createNamedQuery("Credential.findByPassword");
        query.setParameter("password",password);
        return query.getResultList();
    }
    
    @GET
    @Path("findBySigndate/{signdate}")
    @Produces({"application/json"})   
    public List<Credential> findBySigndate (@PathParam("signdate") java.sql.Date signdate)
    {
        Query query = em.createNamedQuery("Credential.findBySigndate");
        query.setParameter("signdate",signdate);
        return query.getResultList();
    }
    @GET
    @Path("findByUsernameANDPassword/{username}/{password}")
    @Produces({"application/json"})
    public List<Credential> findByUsernameANDPassword (@PathParam("username") String Username, @PathParam("password") String Password )
    {
       TypedQuery<Credential> c = em.createQuery("SELECT c FROM Credential c WHERE c.username = :username AND c.password = :password", Credential.class);
        c.setParameter("username", Username);
        c.setParameter("password", Password);
        return c.getResultList();
    }
}
