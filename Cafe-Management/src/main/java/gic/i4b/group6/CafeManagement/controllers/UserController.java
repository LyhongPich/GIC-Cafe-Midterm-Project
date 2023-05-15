package gic.i4b.group6.CafeManagement.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gic.i4b.group6.CafeManagement.models.User;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class UserController {

    private UserService userService;
    

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adminHome")
    public String homeAdmin(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "AdminHome";
    }



    @GetMapping("/newUser")
    public String newUser() {
        return "AddUser";
    }

    @PostMapping("/newUser/addUser")
    public String addUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                        @RequestParam("gender") String gender, @RequestParam("dob") String dob,
                        @RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("role") String role, @RequestParam("profile") String profile) throws ParseException {
        User user = new User();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();

        Calendar input = Calendar.getInstance();
        input.setTime(format.parse(dob));

        if(now.compareTo(input)>0) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGender(gender);
            user.setDateOfBirth(format.parse(dob));
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setProfilePicture(profile);
            userService.addUser(user);
            return "redirect:/newUser";
        }
        return "redirect:/newUser?invalidDate";
    }

}
