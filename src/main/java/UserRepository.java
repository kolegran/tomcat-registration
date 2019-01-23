import java.util.Optional;

public interface UserRepository {
    void saveNewUser(User user);
    Optional<User> findByUsername(String userName);
}
