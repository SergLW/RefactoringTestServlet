package com.codegym.web.servlets.utils;

import com.codegym.web.model.User;
import jakarta.servlet.http.HttpServletRequest;

public final class UserRequestMapper {
    private UserRequestMapper() {}

    public static User bindUser(HttpServletRequest req) throws NumberFormatException {
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
