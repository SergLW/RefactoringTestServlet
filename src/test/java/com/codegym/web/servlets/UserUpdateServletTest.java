package com.codegym.web.servlets;

import com.codegym.web.model.User;
import com.codegym.web.repository.InMemoryUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUpdateServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    InMemoryUserRepository userRepository;

    UserUpdateServlet servlet;
    @BeforeEach
    void setUp() {
        servlet = new UserUpdateServlet(userRepository);
    }

    @Test
    void doPost_updateList() throws ServletException, Exception {
        when(req.getParameter("id")).thenReturn("5");
        when(req.getParameter("firstName")).thenReturn("John");
        when(req.getParameter("lastName")).thenReturn("Doe");
        when(req.getParameter("email")).thenReturn("john.doe@gmail.com");
        when(req.getParameter("age")).thenReturn("25");

        when(userRepository.update(any(User.class))).thenReturn(true);

        var list = List.of(new User(5L, "John", "Doe", "john.doe@gmail.com", 25));
        when(userRepository.findAll()).thenReturn(list);
        when(req.getRequestDispatcher("/WEB-INF/jsp/users.jsp")).thenReturn(dispatcher);

        servlet.doPost(req, resp);

        verify(userRepository).update(any(User.class));
        verify(req).setAttribute("users", list);
        verify(dispatcher).forward(req, resp);
        verify(resp, never()).sendError(anyInt());
    }
}