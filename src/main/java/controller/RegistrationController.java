package controller;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass1");
        String passwordConfirm = req.getParameter("pass2");
        String email = req.getParameter("email");

        clearErrors(req);

        if (!checkErrors(req, login, password, passwordConfirm, email)) {
            UserProfile userProfile = new UserProfile(login, password, email);
            AccountService.getInstance().addNewUser(userProfile);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.setAttribute("login", login);
            req.setAttribute("pass1", password);
            req.setAttribute("pass2", passwordConfirm);
            req.setAttribute("email", email);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }

    private boolean checkErrors(HttpServletRequest req, String login, String firstPassword, String secondPassword,
                                String email) {
        if (login == null || login.equals("")) {
            req.setAttribute("loginErr", "Поле не заполнено");
        } else if (firstPassword == null || firstPassword.equals("")) {
            req.setAttribute("pass1Err", "Поле не заполнено");
        } else if (secondPassword == null || secondPassword.equals("")) {
            req.setAttribute("pass2Err", "Поле не заполнено");
        } else if (email == null || email.equals("")) {
            req.setAttribute("emailErr", "Поле не заполнено");
        } else if (AccountService.getInstance().getUserByLogin(login) != null) {
            req.setAttribute("loginErr", "Данный логин уже существует");
        } else if (!firstPassword.equals(secondPassword)) {
            req.setAttribute("pass2Err", "Пароли не совпадают");
        } else
            return false;
        return true;
    }

    private void clearErrors(HttpServletRequest req) {
        req.setAttribute("loginErr", "");
        req.setAttribute("pass1Err", "");
        req.setAttribute("pass2Err", "");
        req.setAttribute("emailErr", "");
    }
}
