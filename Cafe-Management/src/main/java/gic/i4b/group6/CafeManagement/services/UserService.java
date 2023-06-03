package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Users;

public interface UserService {
    void setUser(String firstName, String lastName, String gender, String dob, String username, String password, MultipartFile profile);
    void editUser(Integer id, String firstName, String lastName, String gender, String dob, String username, String password, MultipartFile profile);
    void removeUser(Integer id);
    // String verifyUser(String username, String password);
    Users getAdmin();
    Users getCashierById(Integer id);
    List<Users> getAllUsers();
    List<Users> getAllCashier();
    List<Integer> getAgeAllCashier();
}
