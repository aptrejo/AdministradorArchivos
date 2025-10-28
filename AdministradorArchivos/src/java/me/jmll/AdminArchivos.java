package me.jmll;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminArchivos", urlPatterns = {"/Admin.do"})
public class AdminArchivos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        Object errores = request.getAttribute("errores");
        if (errores != null) {
            request.setAttribute("errores", errores);
        }

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/DirForm.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
