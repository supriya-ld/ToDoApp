package com.at.repository;

import com.at.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepository implements CrudRepository<User, Integer> {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        //trebuie creat QUERY
        return null;
    }

    public void save(User user) {
        entityManager.getTransaction().begin();    //in caz de eroare
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

    public void deleteById(Integer id) {
        User user = findById(id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }

    }

    public User findById(Integer id) {
        // to do: add try catch
        try {
            User user = entityManager.find(User.class, id);
            return user;
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    public User findByUsername(String username) {
        try {
            int id = entityManager
                    .createQuery("SELECT * FROM Username u WHERE u.username = :username")
                    .setParameter("Username", username)
                    .getFirstResult();
            return findById(id);
        }catch (Exception e){
            return null;
        }

        /**
        User user = (User) entityManager
                .createQuery("SELECT * FROM Username u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList().get(0);
        return user;  **/
    }

}
