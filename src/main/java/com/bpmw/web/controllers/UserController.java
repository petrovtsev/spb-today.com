package com.bpmw.web.controllers;

import com.bpmw.persistence.TaskGroup;
import com.bpmw.web.model.TaskModel;
import com.bpmw.web.model.UserModel;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.Date;

public class UserController extends HttpServlet{


    @Inject
    private UserModel userModel;

    @Inject
    private TaskModel taskModel;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        userModel.getUser(request.getParameter("name"));
        request.getRequestDispatcher("personalArea.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dateBirth = request.getParameter("dateBirth");
            String city = request.getParameter("city");
            String taskGroupId = request.getParameter("taskGroupId");
            String phone = request.getParameter("phone");
            String mail = request.getParameter("mail");
            userModel.addUser(login, passwordHash(password), firstName, lastName, dateBirth, city, taskGroupId, phone, mail);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (ServletException ex){
            request.getRequestDispatcher("error.jsp");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("error.jsp");
    }

    private String passwordHash (String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}