package com.codegym.web.servlets;

import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserDeleteServlet", value = "/users/delete")
public class UserDeleteServlet extends HttpServlet {
    private final InMemoryUserRepository userRepository;

    public UserDeleteServlet(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        userRepository.delete(id);
        resp.sendRedirect("/users");
    }
}
