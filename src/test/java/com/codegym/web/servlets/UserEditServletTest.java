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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEditServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    InMemoryUserRepository userRepository;

    UserEditServlet servlet;
    @BeforeEach
    void setUp() {
        servlet = new UserEditServlet(userRepository);
    }

    @Test
    void doGet_findByIdAndSetAttributes_success() throws ServletException, Exception {
        when(req.getParameter("id")).thenReturn("10");
        var user = new User(10L, "John", "Obs", "adf@fdf", 20);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(req.getRequestDispatcher("/WEB-INF/jsp/form.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req).setAttribute("mode", "edit");
        verify(req).setAttribute("user", user);
        verify(dispatcher).forward(req, resp);
    }

}