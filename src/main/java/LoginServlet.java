import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class LoginServlet extends HttpServlet {
    private UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.verify(req.getParameter("username"), req.getParameter("password"))) {
            req.setAttribute("username", req.getParameter("username"));
            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }
}
