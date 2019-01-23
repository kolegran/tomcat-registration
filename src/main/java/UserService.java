import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(RegistrationFormDto form) {
        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setUserEmail(form.getUserEmail());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        userRepository.saveNewUser(user);
        return true;
    }

    public boolean verify(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return false;

        return user.get().getPassword().equals(password);
    }
}
