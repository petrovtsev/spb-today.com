package com.bpmw.web.model.task;

import com.bpmw.services.TaskService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Named
@Stateless
public class StaticticTaskModel {

    @EJB
    private TaskService taskService;

    private Map dataGraph = new LinkedHashMap<String, Integer>();

    public Map fillCalendar() {
        Map graph = new  LinkedHashMap<Date, Integer>();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -15);
        date = c.getTime();
        for (int i = 0; i < 15; i++) {
            c.add(Calendar.DATE, 1);
            date = c.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,M,d");
            String dateString = dateFormat.format(date);
            graph.put(dateString, 0);
        }
        return graph;
    }

    public void getDataGraph (String login){
        dataGraph = taskService.graphs(login, fillCalendar());
    }

    public Map getDataGraph() {
        return dataGraph;
    }

    public void setDataGraph(Map dataGraph) {
        this.dataGraph = dataGraph;
    }
}