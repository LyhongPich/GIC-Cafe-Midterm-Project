package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gic.i4b.group6.CafeManagement.services.TableService;
import gic.i4b.group6.CafeManagement.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TableController {

    private TableService tableService;
    private UserService userService;

    public TableController(TableService tableService, UserService userService) {
        this.tableService = tableService;
        this.userService = userService;
    }

    @PostMapping("/tableSelection")
    public String tableSelection(HttpServletRequest request, Model model) {
        Integer cashierId = (Integer) request.getAttribute("cashierId");
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("tables", tableService.getAllTables());
        return "Table_selection";
    }

    @GetMapping("/admin/tableManagement")
    public String manageTableView(Model model) {
        model.addAttribute("tableCount", tableService.getTableNum());
        return "Manage_table";
    }

    @PostMapping("/admin/tableManagement")
    public String manageTable(@RequestParam("tableNum") Integer tableNum) {
        if(tableNum > 5 && tableNum <= 100) {
            tableService.setTableNum(tableNum);
            return "redirect:/admin/tableManagement";
        }
        return "redirect:/admin/tableManagement?invalid";
    }
}
