package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.services.DrinkService;
import gic.i4b.group6.CafeManagement.services.InvoiceService;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class UserController {
    private UserService userService;
    private DrinkService drinkService;
    private InvoiceService invoiceService;

    public UserController(UserService userService, DrinkService drinkService, InvoiceService invoiceService) {
        this.userService = userService;
        this.drinkService = drinkService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/admin/addCashier")
    public String addCashierView() {
        return "Admin/New_cashier";
    }
    
    @PostMapping("/admin/addCashier")
    public String addCashier(@RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("gender") String gender,
                            @RequestParam("dob") String dob,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("profile") MultipartFile profile) {
        userService.setUser(firstName, lastName, gender, dob, username, password, profile);
        return "redirect:/admin/addCashier";
    }

    @GetMapping("/admin")
    public String adminView(Model model) {
        model.addAttribute("admin", userService.getAdmin());
        model.addAttribute("cashiers", userService.getAllCashier());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("invoiceNum", invoiceService.getLastInvoice());
        model.addAttribute("orderNumList", drinkService.getNumberOfDrink());
        return "Admin/admin";
    }

    @GetMapping("/admin/edit_cashier/{id}")
    public String editCashier(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("cashier", userService.getCashierById(id));
        return "Admin/Edit_cashier";
    }

    @PostMapping("/cashierUpdated/{id}")
    public String cashierEdited(@PathVariable("id") Integer id,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("gender") String gender,
                                @RequestParam("dob") String dob,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("profile") MultipartFile profile) {
        userService.editUser(id, firstName, lastName, gender, dob, username, password, profile);
        return "redirect:/admin";
    }
    @GetMapping("/admin/confirm_remove_cashier/{id}")
    public String cashierRemoveConfimation(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("cashier", userService.getCashierById(id));
        return "Admin/Remove_Cashier_Confirm";
    }
    @PostMapping("/admin/remove_cashier/{id}")
    public String cashierRemoved(@PathVariable("id") Integer id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String login() {
        return "Login/Login";
    }
}
