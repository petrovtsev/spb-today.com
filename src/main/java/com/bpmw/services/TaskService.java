package com.bpmw.services;

import com.bpmw.persistence.TaskGroup;
import com.bpmw.persistence.Task;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.*;
import java.util.List;

@Named
@RequestScoped
public class TaskService {

    @PersistenceContext(unitName ="persistence")
    private EntityManager em;


    public List<Task> returnAllTasks(){
        List<Task> resultList = em.createNamedQuery("Task.findAll", Task.class)
            .getResultList();
        return resultList; 
    }

    public List<Task> returnUserTasks(TaskGroup taskGroup){
        String query = "select t from Task t WHERE t.taskGroup.id = " + taskGroup.getId();
        List<Task> resultList = em.createQuery(query, Task.class)
                .getResultList();
        return resultList;
    }

    public void addTask(Task task){
        em.persist(task);
    }

    public void delTask(Integer id){
        Task task = em.find(Task.class, id);
        em.remove(task);
    }

    public Task getTask(Integer id){
        return em.find(Task.class, id);
    }

    public void updTask(Task task){
        em.merge(task);
    }

}

