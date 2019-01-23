import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepositoryDB implements UserRepository {
    private final DataSource dataSource;

    public UserRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveNewUser(User user) {
        try {
            String sql = String.format("insert into users (firstName, lastName, userEmail, username, password) values ('%s','%s','%s','%s','%s')",
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUserEmail(),
                    user.getUsername(),
                    user.getPassword()
            );
            dataSource.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            ResultSet resultset = dataSource.getConnection().createStatement().executeQuery("select * from users where username = '" + username + "'");

            if (!resultset.next()) {
                return Optional.empty();
            }

            User user = new User();
            user.setFirstName(resultset.getString("firstName"));
            user.setLastName(resultset.getString("lastName"));
            user.setUserEmail(resultset.getString("userEmail"));
            user.setUsername(resultset.getString("username"));
            user.setPassword(resultset.getString("password"));

            return Optional.of(user);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
