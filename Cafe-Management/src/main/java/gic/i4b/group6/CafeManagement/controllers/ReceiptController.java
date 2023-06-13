package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import gic.i4b.group6.CafeManagement.services.HistoryService;
import gic.i4b.group6.CafeManagement.services.InvoiceService;
import gic.i4b.group6.CafeManagement.services.OrderService;
import gic.i4b.group6.CafeManagement.services.TableService;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class ReceiptController {
    private InvoiceService invoiceService;
    private UserService userService;
    private TableService tableService;
    private OrderService orderService;
    private HistoryService historyService;

    public ReceiptController(InvoiceService invoiceService, UserService userService, TableService tableService, OrderService orderService, HistoryService historyService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.tableService = tableService;
        this.orderService = orderService;
        this.historyService = historyService;
    }

    @GetMapping("/setReceipt/cashier={cashierId}/table={tableId}/cash={money}/total={price}")
    public String setReceipt(@PathVariable("cashierId") Integer cashierId,
                                @PathVariable("tableId") Integer tableId,
                                @PathVariable("money") Float cash,
                                @PathVariable("price") Float total) {
        invoiceService.setInvoice(tableId, cashierId);
        historyService.setHistory(tableId);
        tableService.setUnavailibility(tableId);
        return "redirect:/viewReceipt/cashier={cashierId}/table={tableId}/cash={money}/total={price}";
    }

    @GetMapping("/viewReceipt/cashier={cashierId}/table={tableId}/cash={money}/total={price}")
    public String viewReceipt(@PathVariable("cashierId") Integer cashierId,
                                @PathVariable("tableId") Integer tableId,
                                @PathVariable("money") Float cash,
                                @PathVariable("price") Float total,
                                Model model) {
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("invoice", invoiceService.getInvoiceByTableId(tableId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("firstOrder", orderService.getFirstOrderByTableId(tableId));
        model.addAttribute("cash", cash);
        model.addAttribute("totalInUSA", total);
        model.addAttribute("totalInRiel", Math.round(total)*4060);
        return "receipt";
    }
    
}
