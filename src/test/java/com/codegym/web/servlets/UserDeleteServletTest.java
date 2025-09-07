package com.codegym.web.servlets;

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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeleteServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    InMemoryUserRepository userRepository;

    UserDeleteServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserDeleteServlet(userRepository);
    }

    @Test
    void doPost_deleteUserAndRedirect() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");

        servlet.doPost(req, resp);

        verify(userRepository).delete(1L);
        verify(resp).sendRedirect("/users");
        verifyNoMoreInteractions(userRepository);
    }
}