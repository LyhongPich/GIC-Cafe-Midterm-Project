package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gic.i4b.group6.CafeManagement.services.AddonService;
import gic.i4b.group6.CafeManagement.services.CategoryService;
import gic.i4b.group6.CafeManagement.services.DrinkService;
import gic.i4b.group6.CafeManagement.services.OrderService;
import gic.i4b.group6.CafeManagement.services.SizeService;
import gic.i4b.group6.CafeManagement.services.TableService;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class OrderController {
    private UserService userService;
    private TableService tableService;
    private CategoryService categoryService;
    private DrinkService drinkService;
    private OrderService orderService;
    private AddonService addonService;
    private SizeService sizeService;

    public OrderController(UserService userService, 
                        TableService tableService, 
                        CategoryService categoryService, 
                        DrinkService drinkService, 
                        OrderService orderService, 
                        AddonService addonService,
                        SizeService sizeService) {
        this.userService = userService;
        this.tableService = tableService;
        this.categoryService = categoryService;
        this.drinkService = drinkService;
        this.orderService = orderService;
        this.addonService = addonService;
        this.sizeService = sizeService;
    }

    @GetMapping("/drinkSelection/table={tableId}/cashier={cashierId}")
    public String drinkView(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink";
    }

    @GetMapping("/increaseQuantity/quantity={qtd}/order={orderId}/table={tableId}/cashier={cashierId}")
    public String increaseQuantity(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         @PathVariable("orderId") Integer orderId,
                         @PathVariable("qtd") Integer quantity,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        orderService.increaseQuantity(orderId, quantity);
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}";
    }

    @GetMapping("/decreaseQuantity/quantity{qtd}/order={orderId}/table={tableId}/cashier={cashierId}")
    public String decreaseQuantity(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         @PathVariable("orderId") Integer orderId,
                         @PathVariable("qtd") Integer quantity,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        orderService.decreaseQuantity(orderId, quantity);    
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}";
    }

    @GetMapping("/drinkSelection/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String drinkViewWithCategorySelect(@PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink_Select";
    }

    @GetMapping("/increaseQuantityCate/quantity={qtd}/order={orderId}/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String increaseQuantityCateView(@PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    @PathVariable("orderId") Integer orderId,
                                    @PathVariable("qtd") Integer quantity,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        orderService.increaseQuantity(orderId, quantity);
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}/category={categoryId}";
    }

    @GetMapping("/decreaseQuantityCate/quantity={qtd}/order={orderId}/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String decreaseQuantityCateView(@PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    @PathVariable("orderId") Integer orderId,
                                    @PathVariable("qtd") Integer quantity,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        orderService.decreaseQuantity(orderId, quantity);
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}/category={categoryId}";
    }

    @GetMapping("/makeOrder/drink={drinkId}/table={tableId}/cashier={cashierId}")
    public String drinkMakeOrder(@PathVariable("drinkId") Integer drinkId,
                                @PathVariable("tableId") Integer tableId,
                                @PathVariable("cashierId") Integer cashierId,
                                Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("drinkSelect", drinkService.getDrinkById(drinkId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink_Make_Order";
    }

    @GetMapping("/makeOrderByCategory/drink={drinkId}/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String drinkMakeOrderByCategorySelect(@PathVariable("drinkId") Integer drinkId,
                                    @PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("drinkSelect", drinkService.getDrinkById(drinkId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink_Select_Make_Order";
    }

    @PostMapping("/addOrder/drink={drinkId}/table={tableId}/cashier={cashierId}")
    public String addOrder(@PathVariable("drinkId") Integer drinkId,
                            @PathVariable("tableId") Integer tableId,
                            @PathVariable("cashierId") Integer cashierId,
                            @RequestParam(value="addCream", required = false) Integer addonId,
                            @RequestParam("size") Integer sizeId) {
        orderService.setOrder(drinkId, tableId, sizeId, addonId, 1);

        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}";
    }
    @GetMapping("/editOrder/order={orderId}/drink={drinkId}/table={tableId}/cashier={cashierId}")
    public String editOrder(@PathVariable("orderId") Integer orderId,
                            @PathVariable("drinkId") Integer drinkId,
                            @PathVariable("tableId") Integer tableId,
                            @PathVariable("cashierId") Integer cashierId,
                            Model model) {
        model.addAttribute("orderObject", orderService.getOrderById(orderId));
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("drinkSelect", drinkService.getDrinkById(drinkId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink_Edit_Order";
    }

    @PostMapping("/editOrder/order={orderId}/drink={drinkId}/table={tableId}/cashier={cashierId}")
    public String updateOrder(@PathVariable("orderId") Integer orderId,
                            @PathVariable("drinkId") Integer drinkId,
                            @PathVariable("tableId") Integer tableId,
                            @PathVariable("cashierId") Integer cashierId,
                            @RequestParam(value="addCream", required = false) Integer addonId,
                            @RequestParam("size") Integer sizeId) {
        orderService.editOrder(orderId, drinkId, tableId, sizeId, addonId);
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}";
    }
    @GetMapping("/removeOrder/order={orderId}/table={tableId}/cashier={cashierId}")
    public String removeOrder(@PathVariable("orderId") Integer orderId) {
        orderService.removeOrder(orderId);
        return "redirect:/drinkSelection/table={tableId}/cashier={cashierId}";
    }

    @GetMapping("/checkout/table={tableId}/cashier={cashierId}")
    public String checkoutView(@PathVariable("tableId") Integer tableId,
                                @PathVariable("cashierId") Integer cashierId,
                                Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("totalprice", orderService.checkoutView(tableId));
        return "Calculate";
    }
    @GetMapping("/newCheckout/table={tableId}/cashier={cashierId}")
    public String checkout(@PathVariable("tableId") Integer tableId,
                            @PathVariable("cashierId") Integer cashierId,
                            @RequestParam("cashIn") Float cash_in,
                            Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("totalprice", orderService.checkoutView(tableId));
        model.addAttribute("change", orderService.checkoutIfTaxExist(orderService.checkoutView(tableId), cash_in));
        model.addAttribute("cashIn", cash_in);
        return "Checkout";
    }

    @GetMapping("/drinkSelection/table={tableId}/cashier={cashierId}/checkout/cash={money}/total={price}/change={ch}")
    public String returnToDrinkAfterCheckout(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         @PathVariable("money") Float cash,
                         @PathVariable("price") Float totalprice,
                         @PathVariable("ch") Float change,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("cashInput", cash);
        model.addAttribute("priceTotal", totalprice);
        model.addAttribute("change", change);
        return "Drink";
    }

    @GetMapping("/drinkSelection/table={tableId}/cashier={cashierId}/category={categoryId}/checkout/cash={money}/total={price}/change={ch}")
    public String returnToDrinkCateAfterCheckout(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         @PathVariable("categoryId") Integer categoryId,
                         @PathVariable("money") Float cash,
                         @PathVariable("price") Float totalprice,
                         @PathVariable("ch") Float change,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("cashInput", cash);
        model.addAttribute("priceTotal", totalprice);
        model.addAttribute("change", change);
        return "Drink_Select";
    }

    @GetMapping("/checkoutInCategory/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String checkoutViewCate(@PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("totalprice", orderService.checkoutView(tableId));
        return "CalculateCate";
    }

    @GetMapping("/newCheckoutCate/table={tableId}/cashier={cashierId}/category={categoryId}")
    public String checkoutCate(@PathVariable("tableId") Integer tableId,
                            @PathVariable("cashierId") Integer cashierId,
                            @PathVariable("categoryId") Integer categoryId,
                            @RequestParam("cashIn") Float cash_in,
                            Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categorySelect", categoryService.getCategoryById(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addon", addonService.getAddonById(1));
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("totalprice", orderService.checkoutView(tableId));
        model.addAttribute("change", orderService.checkoutIfTaxExist(orderService.checkoutView(tableId), cash_in));
        model.addAttribute("cashIn", cash_in);
        return "CheckoutCate"; 
    }
    
}
