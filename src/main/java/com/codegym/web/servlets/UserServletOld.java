package com.codegym.web.servlets;

import java.io.IOException;
import com.codegym.web.model.User;
import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserServletOld extends HttpServlet {

  private final InMemoryUserRepository userRepository;

  public UserServletOld(InMemoryUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath();
    if ("/users/create".equals(path)) {
      User user = bindUser(req);
      userRepository.save(user);
      req.setAttribute("users", userRepository.findAll());
      req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    } else if ("/users/delete".equals(path)) {
      Long id = Long.valueOf(req.getParameter("id")); //TODO move to private method
      userRepository.delete(id);
      req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    } else if ("/users/update".equals(path)) {
      User user = bindUser(req);
      if (!userRepository.update(user)) {
        resp.sendError(400);
      }
      req.setAttribute("users", userRepository.findAll());
      req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath();
    if ("/users/new".equals(path)) {
      req.setAttribute("mode", "create");
      req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    } else if ("/users/edit".equals(path)) {
      Long id = Long.valueOf(req.getParameter("id")); //TODO move to private method
      User user = userRepository.findById(id).orElse(null);//TODO Create and throw own exception
      req.setAttribute("mode", "edit");
      req.setAttribute("user", user);
      req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    } else {
      req.setAttribute("users", userRepository.findAll());
      req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }
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
