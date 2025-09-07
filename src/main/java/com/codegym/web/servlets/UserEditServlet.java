package com.codegym.web.servlets;

import com.codegym.web.model.User;
import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="UserEditServlet", value = "/users/edit")
public class UserEditServlet extends HttpServlet {

    private final InMemoryUserRepository userRepository;

    public UserEditServlet(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.valueOf(req.getParameter("id"));
        User user = userRepository.findById(id).orElse(null);
        req.setAttribute("mode", "edit");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    }
}
