package ru.itmo.FirstService.dao;

import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.FirstService.dto.FilterDto;
import ru.itmo.FirstService.dto.RouteDto;
import ru.itmo.FirstService.entity.Coordinates;
import ru.itmo.FirstService.entity.Location;
import ru.itmo.FirstService.entity.Route;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
@RequiredArgsConstructor
public class RouteDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final CoordinatesDao coordinatesDao;
    private final LocationDao locationDao;

    public Route findById(long id) {
        Query query = entityManager.createQuery("from Route where id = " + id);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (Route) query.getResultList().get(0);
    }

    // return -1 - not found, 0 - deleted
    public int deleteById(long id) {
        Route route = findById(id);
        if (route == null) {
            return -1;
        }
        entityManager.remove(route);
        return 0;
    }


    public Long getSum() {
        Query q = entityManager.createQuery("select sum(distance) from Route", Long.class);
        List<Long> result = q.getResultList();
        if (result.isEmpty()) {
            return -1L;
        }
        return result.get(0);
    }


    public Long getLessDistancesNumber(int value) {
        Query q = entityManager.createQuery("select count(*) from Route where distance < " + value);
        return (Long) q.getResultList().get(0);
    }

    public Route findLongestOrShortestRoute(int idFrom, int idTo, int shortest) {
        Query q;
        if (shortest == 0) {
            Integer maxDistance = (Integer) entityManager.createQuery("SELECT max(distance) from Route where from_id = " + idFrom +
                    " and to_id = " + idTo).getSingleResult();
            q = entityManager.createQuery("from Route where from_id = " + idFrom
                    + " and to_id = " + idTo + " and distance = " + maxDistance);
        } else {
            Integer minDistance = (Integer) entityManager.createQuery("select min(distance) from Route where from_id = " + idFrom
                    + " and to_id = " + idTo).getSingleResult();
            q = entityManager.createQuery("from Route where from_id = " + idFrom
                    + " and to_id = " + idTo + " and distance = " + minDistance);
        }

        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Route) q.getResultList().get(0);
    }



    public List getUniqueDistances(int pageSize, int pageNumber) {
        Query q = entityManager.createQuery("select distinct distance from Route");
        q.setFirstResult(((pageNumber - 1) * pageSize));
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public Integer getUniqueDistancesCount() {
        Query q = entityManager.createQuery("select distinct distance from Route");
        return q.getResultList().size();
    }


    public Long getRoutesCount(FilterDto filterDto) {
        if(getFilterString(filterDto.getFilters()) != null) {
            return (Long) entityManager.createQuery("select count(*) from Route" + getFilterString(filterDto.getFilters()))
                    .getSingleResult();
        }
        return null;


    }


    public Route save(RouteDto routeDto) {
        Route route = new Route();
        route.setName(routeDto.getName());
        Coordinates coordinates = coordinatesDao.findById(routeDto.getCoordinatesId());
        route.setCoordinates(coordinates);
        Location from = null;
        if(routeDto.getFromId() != null) {
            from = locationDao.findById(routeDto.getFromId());
        }
        Location to = null;
        if(routeDto.getToId() != null) {
            to = locationDao.findById(routeDto.getToId());
        }
        route.setFromLocation(from);
        route.setTo(to);
        route.setDistance(routeDto.getDistance());
        route.setCreationDate(java.time.LocalDateTime.now());
        entityManager.persist(route);
        entityManager.flush();
        return route;
    }


    public void save(Route route) {
        entityManager.persist(route);
        entityManager.flush();
    }


    public List getRoutes(Integer pageNumber, Integer pageSize, Map<String, String> orders,
                          Map<String, String> filters) {
        if(getFilterString(filters) != null) {
            Query q = entityManager.createQuery("from Route" + getFilterString(filters) + getOrderString(orders));
            q.setFirstResult(((pageNumber - 1) * pageSize));
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
        return null;
    }



    public Route update(long id, RouteDto routeDto) {

        Route route = findById(id);
        if(route == null) {
           return null;
        }

        route.setName(routeDto.getName());
        route.setCoordinates(coordinatesDao.findById(routeDto.getCoordinatesId()));
        route.setFromLocation(locationDao.findById(routeDto.getFromId()));
        route.setTo(locationDao.findById(routeDto.getToId()));
        route.setDistance(routeDto.getDistance());

        entityManager.merge(route);
        return route;
    }



    private String getOrderString(Map<String, String> orders) {
        String orderString = " ORDER BY ";
        for(Map.Entry<String, String> order : orders.entrySet()) {
            if(order.getValue() != null) {
                orderString += order.getKey() + " " + order.getValue() + ",";
            }
        }
        if(orderString.equals(" ORDER BY ")) {
            orderString = "";
        }
        else {
            orderString = orderString.substring(0, orderString.length() - 1);
        }
        return orderString;
    }


    private String getFilterString(Map<String, String> filters) {
        String filterString = " WHERE ";
        for(Map.Entry<String, String> filter : filters.entrySet()) {
            if(filter.getValue() != null) {
                if(!filter.getValue().trim().equals("")) {
                    if(filter.getKey().equals("name")) {
                        filterString += filter.getKey() + " = \'" + filter.getValue() + "\'" + " AND ";
                    }
                    else {
                        try {
                            Integer id = Integer.parseInt(filter.getValue());
                            filterString += filter.getKey() + " = " + filter.getValue() + " AND ";
                        }
                        catch (Exception e) {
                            return null;
                        }
                    }
                }
            }
        }
        if(filterString.equals(" WHERE ")) {
            filterString = "";
        }
        else {
            filterString = filterString.substring(0, filterString.length() - 5);
        }
        return filterString;
    }
}


