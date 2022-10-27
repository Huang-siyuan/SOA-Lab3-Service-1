package dao;

import entity.Location;
import entity.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;


public class LocationDao {


    public Location findById(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from entity.Location where id = " + id);

        if(query.list().isEmpty()) {
            return null;
        }
        Location location = (Location) query.list().get(0);
        session.close();
        return location;
    }

    public Location find(int x, float y, Long z, String name) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from entity.Location where x = " + x + " and y = " +
                y + " and z = " + z + " and name = " + name);

        if(query.list().isEmpty()) {
            return null;
        }
        Location location = (Location) query.list().get(0);
        session.close();
        return location;
    }


}

