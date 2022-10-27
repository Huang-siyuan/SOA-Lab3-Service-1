package dao;

import entity.Coordinates;
import entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;


public class CoordinatesDao {


    public Coordinates findById(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from entity.Coordinates where id = " + id);

        if(query.list().isEmpty()) {
            return null;
        }
        Coordinates coordinates = (Coordinates) query.list().get(0);
        session.close();
        return coordinates;
    }


    public Coordinates findByXY(float x, Integer y) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from entity.Coordinates where x = " + x + " and y = " + y);

        if(query.list().isEmpty()) {
            return null;
        }
        Coordinates coordinates = (Coordinates) query.list().get(0);
        session.close();
        return coordinates;
    }


    public void save(Coordinates coordinates) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(coordinates);
        session.getTransaction().commit();
        session.close();
    }
}
