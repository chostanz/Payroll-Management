package controller;

import dao.UserDAO;

public class LoginController {

    UserDAO dao;

    public LoginController() {

        dao = new UserDAO();

    }

    public boolean login(String username,
            String password) {

        return dao.login(username, password);

    }
}