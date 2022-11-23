package ru.itmo.FirstService.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itmo.FirstService.entity.Coordinates;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
@Repository
@RequiredArgsConstructor
public class CoordinatesDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Coordinates findById(int id) {
        Query query = entityManager.createQuery("from Coordinates where id = " + id);

        if(query.getResultList().isEmpty()) {
            return null;
        }
        return (Coordinates) query.getResultList().get(0);
    }


    public Coordinates findByXY(float x, Integer y) {
        Query query = entityManager.createQuery("from Coordinates where x = " + x + " and y = " + y);

        if(query.getResultList().isEmpty()) {
            return null;
        }
        return (Coordinates) query.getResultList().get(0);
    }


    public void save(Coordinates coordinates) {
        entityManager.persist(coordinates);
        entityManager.flush();
    }
}
