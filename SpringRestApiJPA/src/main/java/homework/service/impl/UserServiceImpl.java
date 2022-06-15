package homework.service.impl;

import homework.dao.UserRepository;
import homework.exception.InvalidEntityDataException;
import homework.exception.NonexistingEntityException;
import homework.model.User;
import homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static homework.model.MockUsers.MOCK_USERS;


@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Override
    public void loadData() {
        if(userRepository.count() == 0) {
            userRepository.saveAll(Arrays.asList(MOCK_USERS));
        }
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("User with ID='%s' does not exist.", id))
        );
    }

    @Override
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        if (!old.getUsername().equals(user.getUsername())) {
            throw new InvalidEntityDataException(
                    String.format("Username '%s' can not be changed to '%s'.",
                            old.getUsername(), user.getUsername()));
        }

        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User deleteUserById(Long id) {
        User old = userRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("User with ID='%s' does not exist.", id)));
        userRepository.deleteById(id);

        return old;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("User with Username='%s' does not exist.", username)));

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
