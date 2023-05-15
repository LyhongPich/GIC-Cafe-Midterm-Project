package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.User;

public interface UserService {
    User addUser(User user);
    List<User> getAllUser();
    String login(String username, String password);
}
