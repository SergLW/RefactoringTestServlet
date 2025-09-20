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

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    InMemoryUserRepository userRepository;

    UsersServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UsersServlet(userRepository);
    }

    @Test
    void doGet_addUserAndGoToJsp() throws ServletException, IOException {
        var list = List.of(new User(1L, "John", "Doe", "e@d", 20));
        when(userRepository.findAll()).thenReturn(list);
        when(req.getRequestDispatcher("/WEB-INF/jsp/users.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req).setAttribute("users", list);
        verify(dispatcher).forward(req, resp);
        verify(resp, never()).sendRedirect(any());
        verifyNoMoreInteractions(resp);
    }
}