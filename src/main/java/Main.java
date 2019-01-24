import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws LifecycleException, ServletException, SQLException {
        Properties properties = new Properties();
        InputStream input;

        try {
            input = new FileInputStream("/home/zoe/projects/java-initial/tomcat-registration/app.properties");
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        try {
            input.close();
        } catch (IOException e) {
            throw new IllegalStateException();
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(properties.getProperty("datasource.host", "localhost"));
        dataSource.setPortNumber(Integer.parseInt(properties.getProperty("datasource.port", String.valueOf(5432))));
        dataSource.setUser(properties.getProperty("datasource.user", "postgres"));
        dataSource.setPassword(properties.getProperty("datasource.password", "postgres"));
        dataSource.setDatabaseName(properties.getProperty("datasource.databaseName", "postgres"));
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
