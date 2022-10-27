package dao;

import dto.RouteDto;
import entity.Coordinates;
import entity.Location;
import entity.Route;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;


import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class RouteDao {

    public Route findById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from entity.Route where id = " + id);

        if (query.list().isEmpty()) {
            return null;
        }
        Route route = (Route) query.list().get(0);
        session.close();
        return route;
    }

    // return -1 - not found, 0 - deleted
    public int deleteById(long id) {
        Route route = findById(id);
        if (route == null) {
            return -1;
        }
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query q = session.createQuery("delete entity.Route where id = " + id);
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return 0;
    }


    public Long getSum() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("select sum(distance) from entity.Route");
        List<Long> result = q.list();
        if (result.isEmpty()) {
            return -1L;
        }
        session.close();
        return result.get(0);
    }


    public Long getLessDistancesNumber(int value) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("select count(*) from entity.Route where distance < " + value);
        Long distanceNumber = (Long) q.list().get(0);
        session.close();
        return distanceNumber;
    }

    public Route findLongestOrShortestRoute(int idFrom, int idTo, int shortest) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q;
        if (shortest == 0) {
            Integer maxDistance = (Integer) session.createQuery("SELECT max(distance) from entity.Route where from_id = " + idFrom +
                    " and to_id = " + idTo).getSingleResult();
            q = session.createQuery("from entity.Route where from_id = " + idFrom
                    + " and to_id = " + idTo + " and distance = " + maxDistance);
        } else {
            Integer minDistance = (Integer) session.createQuery("select min(distance) from entity.Route where from_id = " + idFrom
                    + " and to_id = " + idTo).getSingleResult();
            q = session.createQuery("from entity.Route where from_id = " + idFrom
                    + " and to_id = " + idTo + " and distance = " + minDistance);
        }

        if (q.list().isEmpty()) {
            return null;
        }
        Route route = (Route) q.list().get(0);
        session.close();
        return route;

    }



    public List getUniqueDistances(int pageSize, int pageNumber) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("select distinct distance from entity.Route");
        q.setFirstResult(((pageNumber - 1) * pageSize));
        q.setMaxResults(pageSize);
        return q.list();
    }


    public int save(RouteDto routeDto) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Route route = new Route();
        route.setName(routeDto.getName());
        Coordinates coordinates = new CoordinatesDao().findByXY(routeDto.getCoordinates().getX(), routeDto.getCoordinates().getY());
        if(coordinates == null) {
            return -1;
        }
        LocalDateTime d = LocalDateTime.now();
        route.setCreationDate(d);
        Location from = new LocationDao().find(routeDto.getFrom().getX(), routeDto.getFrom().getY(),
                routeDto.getFrom().getZ(), routeDto.getFrom().getName());
        Location to = new LocationDao().find(routeDto.getTo().getX(), routeDto.getTo().getY(),
                routeDto.getTo().getZ(), routeDto.getTo().getName());
        if(from == null || to == null) {
            return -1;
        }
        route.setFromLocation(from);
        route.setTo(to);
        route.setDistance(routeDto.getDistance());
        session.getTransaction().begin();
        session.save(route);
        session.getTransaction().commit();
        session.close();
        return 0;
    }


    public void save(Route route) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(route);
        session.getTransaction().commit();
        session.close();
    }


/*
    public boolean update(long id, String name, Integer coordinatesId, Integer fromId, Integer toId,
                            Integer distance) {

        Route route = findById(id);
        if(route == null) {
           return false;
        }

        route.setName(name);
        route.setCoordinates(new CoordinatesDao().findById(coordinatesId));
        route.setCoordinates(new CoordinatesDao().findById(coordinatesId));
        route.setFrom(new LocationDao().findById(fromId));
        route.setTo(new LocationDao().findById(toId));
        route.setDistance(distance);

        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.update(route);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
 */
}

