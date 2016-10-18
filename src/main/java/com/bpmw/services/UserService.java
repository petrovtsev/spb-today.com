package com.bpmw.services;

import com.bpmw.persistence.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ppetr on 28.09.2016.
 */

@Stateless
public class UserService {

    @PersistenceContext(unitName ="persistence")
    private EntityManager em;

    public List<User> returnAllUsers(){
        List<User> resultList = em.createNamedQuery("User.findAll", User.class)
                .getResultList();
        return resultList;
    }

    public void addUser(User user){
        em.persist(user);
    }

    public void delUser(String login){
        User user = em.find(User.class, login);
        em.remove(user);
    }

    public User getUser(String userId){
        return em.find(User.class, userId);
    }

    public void updUser(User user){
        em.merge(user);
    }
}