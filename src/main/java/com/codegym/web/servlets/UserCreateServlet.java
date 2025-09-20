package com.codegym.web.servlets;

import com.codegym.web.model.User;
import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.codegym.web.servlets.utils.UserRequestMapper.bindUser;

@WebServlet(name = "UserCreateServlet", value = "/users/create")
public class UserCreateServlet extends HttpServlet {
    private final InMemoryUserRepository userRepository;

    public UserCreateServlet(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = bindUser(req);
        userRepository.save(user);
        req.setAttribute("users", userRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }

}
