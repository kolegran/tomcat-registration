import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.ServletException;
import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws LifecycleException, ServletException, SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(5432);
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.getConnection().createStatement().execute("create table if not exists users (id bigserial primary key, firstName text, lastName text, userEmail text, username text, password text)");

        UserRepositoryDB userRepository = new UserRepositoryDB(dataSource);
        UserService userService = new UserService(userRepository);

        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        Context ctx = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        Tomcat.addServlet(ctx, "Login", new LoginServlet(userService));
        ctx.addServletMapping("/login", "Login");

        Tomcat.addServlet(ctx, "Registration", new RegistrationServlet(userService));
        ctx.addServletMapping("/registration", "Registration");

        tomcat.start();
        tomcat.getServer().await();
    }
}
