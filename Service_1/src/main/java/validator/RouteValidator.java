package validator;

import dao.CoordinatesDao;
import dao.LocationDao;

public class RouteValidator {


    public boolean validate(String name, Integer coordinatesId, Integer fromId, Integer toId,
                            Integer distance) {
        if(name == null || name.length() == 0) {
            return false;
        }
        if(coordinatesId == null || new CoordinatesDao().findById(coordinatesId) == null) {
            return false;
        }
        if(fromId != null && new LocationDao().findById(fromId) == null) {
            return false;
        }
        if(toId != null && new LocationDao().findById(toId) == null) {
            return false;
        }
        if(distance != null && distance < 1) {
            return false;
        }
        return true;
    }
}
