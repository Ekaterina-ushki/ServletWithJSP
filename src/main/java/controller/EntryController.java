package controller;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class EntryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserProfile userProfile = AccountService.getInstance().getUserBySessionId(req.getSession().getId());
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
        UserProfile userProfile = AccountService.getInstance().getUserByLogin(login);
        if (userProfile == null || !userProfile.getPass().equals(password)) {
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        AccountService.getInstance().addSession(req.getSession().getId(), userProfile);
        resp.sendRedirect("/ServletWithJSP_war/explorer");
    }
}