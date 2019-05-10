package servlet.util;

import dao.userUtil;
import domain.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "registerServlet", value = "/register")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String error = "";

        userUtil userUtil = new userUtil();
        user user = userUtil.getSelect(name);
        if(user != null) {
            error += "用户名已存在";
        }else {
            user = new user();
            user.setUsername(name);
            user.setPass(password);
            try {
                userUtil.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            user = userUtil.getSelect(name);

            session.setAttribute("user",user);
        }
        if(!error.isEmpty()){
            session.setAttribute("error",error);
        }
        response.sendRedirect(response.encodeURL(String.format("/home")));
    }
}
