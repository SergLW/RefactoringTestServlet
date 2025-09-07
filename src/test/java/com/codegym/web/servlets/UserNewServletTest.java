package com.codegym.web.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserNewServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;

    UserNewServlet servlet;
    @BeforeEach
    void setUp() {
        servlet = new UserNewServlet();
    }

    @Test
    void doGet_setCreateForm() throws ServletException, Exception {
        when(req.getRequestDispatcher("/WEB-INF/jsp/form.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req).setAttribute("mode", "create");
        verify(dispatcher).forward(req, resp);
        verifyNoInteractions(resp);
    }
}