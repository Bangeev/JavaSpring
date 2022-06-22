package homework.service;

import homework.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    User updateUser(User user);
    User deleteUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void loadData();




}
