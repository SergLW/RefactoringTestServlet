package com.codegym.web.servlets;

import java.io.IOException;

import com.codegym.web.model.User;
import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UserUpdateServlet", value = "/users/update")
public class UserUpdateServlet extends HttpServlet {
  private final InMemoryUserRepository userRepository;

  public UserUpdateServlet(InMemoryUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    User user = bindUser(req);
    if (!userRepository.update(user)) {
      resp.sendError(400);
    }
    req.setAttribute("users", userRepository.findAll());
    req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
  }

  private User bindUser(HttpServletRequest req) {
    String idStr = req.getParameter("id");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    int age = Integer.parseInt(req.getParameter("age"));

    if (idStr == null) {
      return new User(firstName, lastName, email, age);
    } else {
      Long id = Long.valueOf(idStr);
      return new User(id, firstName, lastName, email, age);
    }
  }

}
