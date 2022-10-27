package resource;


import dao.CoordinatesDao;
import dao.LocationDao;
import dao.RouteDao;
import entity.Coordinates;
import entity.Location;
import entity.Route;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.UUID;

@Path("/navigator")
public class NavigatorResource {

    @POST
    @Path("/route/{id-from}/{id-to}/{distance}")
    public Integer addRoute(@PathParam("id-from") Integer idFrom,
                             @PathParam("id-to") Integer idTo,
                             @PathParam("distance") Integer distance) {
        String defaultRouteName = UUID.randomUUID().toString().replace("-", "").toLowerCase(); // Cause the name shouldn't be null. But we don't need to know the name.

        LocationDao locationDao = new LocationDao();
        Location locationFrom = locationDao.findById(idFrom);
        Location locationTo = locationDao.findById(idTo);
        if (locationFrom == null || locationTo == null || (distance != null && distance <= 1)) {
            return -1;
        }

        Coordinates coordinates = new Coordinates((float) (4 * Math.random()), 4);
        CoordinatesDao coordinatesDao = new CoordinatesDao();
        coordinatesDao.save(coordinates);
        Route route = new Route(defaultRouteName, coordinates, locationFrom, locationTo, distance); // We don't need to know the coordinates' id. So we set it to 0 as the default.
        RouteDao routeDao = new RouteDao();
        routeDao.save(route);
        return 0;
    }


    @POST
    @Path("/routes/{id-from}/{id-to}/{shortest}")
    public Route findLongestOrShortestRoute(@PathParam("id-from") int idFrom,
                                            @PathParam("id-to") int idTo,
                                            @PathParam("shortest") int shortest) {
        if(shortest != 1 && shortest != 0) {
            return null;
        }
        RouteDao routeDao = new RouteDao();
        return routeDao.findLongestOrShortestRoute(idFrom, idTo, shortest);
    }



}
