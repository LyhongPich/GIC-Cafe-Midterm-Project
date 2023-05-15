package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.User;
import gic.i4b.group6.CafeManagement.repositories.UserRepository;
import gic.i4b.group6.CafeManagement.services.UserService;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    
    
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String login(String username, String password) {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if(user.getRole().equals("Cashier")) {
                    return "Cashier";
                }
                else{
                    return "Admin";
                }
            }

            System.out.println(("User: "+user.getUsername()));
        }
        return "";
    }
}
