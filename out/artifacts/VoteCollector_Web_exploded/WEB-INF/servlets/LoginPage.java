package org.hillel.it.votecollector.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;


/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 19.12.13
 * Time: 20:26
 */

@WebServlet("/LoginPage")
public class LoginPage extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.getWriter().write("wwwww");
    }
}
