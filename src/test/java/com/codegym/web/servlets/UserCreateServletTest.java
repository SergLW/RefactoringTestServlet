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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserCreateServletTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    InMemoryUserRepository userRepository;

    UserCreateServlet servlet;
    @BeforeEach
    void setUp() {
        servlet = new UserCreateServlet(userRepository);
    }

    @Test
    void doPost_addUserToRepository() throws ServletException, Exception {
        when(req.getParameter("id")).thenReturn(null);
        when(req.getParameter("firstName")).thenReturn("John");
        when(req.getParameter("lastName")).thenReturn("Doe");
        when(req.getParameter("email")).thenReturn("john.doe@gmail.com");
        when(req.getParameter("age")).thenReturn("25");

        var list = List.of(new User(1L, "John", "Doe", "john.doe@gmail.com", 25));
        when(userRepository.findAll()).thenReturn(list);
        when(req.getRequestDispatcher("/WEB-INF/jsp/users.jsp")).thenReturn(dispatcher);

        servlet.doPost(req, resp);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User user = userCaptor.getValue();
        assertNull(user.getId(), "Id should be null before save");
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertEquals(25, user.getAge());

        verify(req).setAttribute("users", list);
        verify(dispatcher).forward(req, resp);
    }

    @Test
    void doPost_wrongAge() {
        when(req.getParameter("age")).thenReturn("NaN");

        assertThrows(NumberFormatException.class, () -> servlet.doPost(req, resp));

        verify(userRepository, never()).save(any());
        verifyNoInteractions(resp);
    }
}