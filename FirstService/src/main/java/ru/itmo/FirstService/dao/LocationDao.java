package ru.itmo.FirstService.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.FirstService.entity.Location;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Transactional
@Repository
@RequiredArgsConstructor
public class LocationDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Location findById(int id) {
        Query query = entityManager.createQuery("from Location where id = " + id);

        if(query.getResultList().isEmpty()) {
            return null;
        }
        return (Location) query.getResultList().get(0);
    }

    public Location find(int x, float y, Long z, String name) {
        Query query = entityManager.createQuery("from Location where x = " + x + " and y = " +
                y + " and z = " + z + " and name = " + name);

        if(query.getResultList().isEmpty()) {
            return null;
        }
        return (Location) query.getResultList().get(0);
    }


}

