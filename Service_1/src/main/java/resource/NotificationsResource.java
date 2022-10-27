package resource;

import dao.CoordinatesDao;
import entity.Coordinates;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/notifications")
public class NotificationsResource {
    @GET
    @Path("/ping")
    public String ping() {
        return "some";
    }
}