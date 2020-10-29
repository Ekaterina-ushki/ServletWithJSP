package controller;

import dbService.DBException;
import dbService.DBService;
import dbService.data.UserData;
import services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class EntryController extends HttpServlet {

    private DBService dbService = new DBService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserData userProfile = SessionService.getInstance().getUserBySessionId(req.getSession().getId());
        if (userProfile != null) {
            resp.sendRedirect("/ServletWithJSP_war/explorer");
            return;
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");

        clearErrors(req);

        if (checkErrors(req, login, password)) {
            req.setAttribute("login", login);
            req.setAttribute("pass", password);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            UserData userData = dbService.getUser(login);
            if (userData == null || !userData.getPassword().equals(password)) {
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
                return;
            }
            SessionService.getInstance().addSession(req.getSession().getId(), userData);
            resp.sendRedirect("/ServletWithJSP_war/explorer");
        }
    }

    private boolean checkErrors(HttpServletRequest req, String login, String password) {

        if (login == null || login.equals("")) {
            req.setAttribute("loginErr", "Поле не заполнено");
        } else if (password == null || password.equals("")) {
            req.setAttribute("passErr", "Поле не заполнено");
        } else if (dbService.getUser(login) == null) {
            req.setAttribute("loginErr", "Аккаунта с таким логином не существует");
        } else if (!dbService.getUser(login).getPassword().equals(password)) {
            req.setAttribute("passErr", "Неверный пароль");
        } else return false;
        return true;
    }

    private void clearErrors(HttpServletRequest req) {
        req.setAttribute("loginErr", "");
        req.setAttribute("passErr", "");
    }

}