package resource;

import dao.RouteDao;
import dto.RouteDto;
import entity.Location;
import entity.Route;
import org.apache.ibatis.annotations.Update;
import validator.RouteValidator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/routes")
public class RouteResource {

    @GET
    @Path("/{id}")
    public Route findRouteById(@PathParam("id") long id) {
        RouteDao routeDao = new RouteDao();
        return routeDao.findById(id);
    }

    @DELETE
    @Path("/{id}")
    public int deleteRouteById(@PathParam("id") long id) {
        RouteDao routeDao = new RouteDao();
        return routeDao.deleteById(id);
    }

    @POST
    @Path("/")
    public int createRoute(@Valid RouteDto routeDto) {
        RouteDao routeDao = new RouteDao();
        return routeDao.save(routeDto);
    }

    @POST
    @Path("/distances/sum")
    public Long getRouteSumDistance() {
        RouteDao routeDao = new RouteDao();
        return routeDao.getSum();
    }


    @POST
    @Path("/distances/less/{value}")
    public Long getLessDistances(@PathParam("value") int value) {
        RouteDao routeDao = new RouteDao();
        return routeDao.getLessDistancesNumber(value);
    }


    @POST
    @Path("/distances/unique")
    public List getUniqueDistances(@QueryParam("pageSize") int pageSize, @QueryParam("pageNumber") int pageNumber) {
        RouteDao routeDao = new RouteDao();
        return routeDao.getUniqueDistances(pageSize, pageNumber);
    }


  /*  @PUT
    @Path("/{id}")
    public Response updateRoute(@PathParam("id") long id, String name, Integer coordinatesId, Integer fromId, Integer toId,
                                Integer distance) {
        RouteDao routeDao = new RouteDao();

        if(new RouteValidator().validate(name, coordinatesId, fromId, toId, distance) &&
                routeDao.findById(id) != null) {
            routeDao.update(id, name, coordinatesId, fromId, toId, distance);
        }
        return Response.status(Response.Status.fromStatusCode(400)).entity("Invalid data").build();

    } */


  /*  @POST
    @Path("/")
    public Response insertRoute(String name, Integer coordinatesId, Integer fromId, Integer toId,
                                Integer distance) {
        if(new RouteValidator().validate(name, coordinatesId, fromId, toId, distance)) {
            RouteDao routeDao = new RouteDao();
            routeDao.insertRoute(name, coordinatesId, fromId, toId, distance);
            return Response.status(Response.Status.OK).entity("No distances").build();
        }
        return Response.status(Response.Status.fromStatusCode(400)).entity("Invalid data").build();

    } */


}
