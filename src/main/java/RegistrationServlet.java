import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private  final UserService userService;

    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationFormDto form = new RegistrationFormDto();

        form.setFirstName(req.getParameter("firstname"));
        form.setLastName(req.getParameter("lastname"));
        form.setUserEmail(req.getParameter("email"));
        form.setUsername(req.getParameter("username"));
        form.setPassword(req.getParameter("password"));

        if (userService.register(form)) {
            resp.sendRedirect("/");
        } else {
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }
}
