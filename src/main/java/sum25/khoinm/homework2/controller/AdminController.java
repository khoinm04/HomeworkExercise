package sum25.khoinm.homework2.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.khoinm.homework2.pojo.*;
import sum25.khoinm.homework2.service.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrchidService orchidService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrderDetailService orderDetailService;

    // Trang dashboard chính
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if(account == null || !account.getRole().getRoleName().equals("ADMIN")) {
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không có tài khoản trong session
        }
        List<Account> accounts = accountService.getAllAccounts();
        List<Orchid> orchids = orchidService.getAllOrchids(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        List<Order> orders = orderService.getAllOrders();

        model.addAttribute("accounts", accounts);
        model.addAttribute("orchids", orchids);
        model.addAttribute("orders", orders);
        model.addAttribute("newAccount", new Account());
        model.addAttribute("newOrchid", new Orchid());
        model.addAttribute("newOrder", new Order());

        return "admin-dashboard";
    }

    @PostMapping("/accounts/create")
    public String createAccount(@Valid @ModelAttribute("newAccount") Account newAccount, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("newAccount", newAccount);
            model.addAttribute("error", "Có lỗi trong quá trình tạo tài khoản: " + result.getAllErrors().get(0).getDefaultMessage());
            return "create-account-admin";
        }
        try {
            if (accountService.existsByEmail(newAccount.getEmail())) {
                model.addAttribute("error", "Email đã tồn tại!");
                List<Role> roles = roleService.getAllRoles();
                model.addAttribute("roles", roles);
                model.addAttribute("newAccount", newAccount);
                return "create-account-admin";
            }
            accountService.saveAccount(newAccount);
            redirectAttributes.addFlashAttribute("success", "Tạo tài khoản thành công!");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi trong quá trình tạo tài khoản: " + e.getMessage());
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("newAccount", newAccount);
            return "create-account-admin";
        }
    }

    @GetMapping("/create-account-admin")
    public String showCreateAccountForm(Model model , HttpSession session) {
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            return "redirect:/login";
        }
        if (account.getRole() == null || !account.getRole().getRoleName().equals("ADMIN")) {
            return "redirect:/login";
        }
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("newAccount", new Account());
        model.addAttribute("roles", roles);
        return "create-account-admin";
    }

    @GetMapping("/create-orchid-admin")
    public String showCreateOrchidForm(Model model , HttpSession session) {
        Account account = (Account) session.getAttribute("account");

        if (account == null || !account.getRole().getRoleName().equals("ADMIN")) {
            System.out.println("No account in session - redirecting to login");
            return "redirect:/login";
        }

        if (account.getRole() == null || !account.getRole().getRoleName().equals("ADMIN")) {
            System.out.println("User is not admin - redirecting to login");
            return "redirect:/login";
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("newOrchid", new Orchid());
        System.out.println("Returning create-account-admin view");
        return "create-orchid-admin";
    }

    @GetMapping("/accounts/edit/{id}")
    public String showEditAccountForm(@PathVariable Long id, Model model) {
        Optional<Account> optionalAccount = accountService.getAccountById(id);
        if (!optionalAccount.isPresent()) {
            model.addAttribute("error", "Không tìm thấy account để sửa!");
            return "edit-account-admin";
        }
        Account account = optionalAccount.get();

        List<Role> roles = roleService.getAllRoles(); // Lấy roles cho select
        model.addAttribute("editAccount", account);
        model.addAttribute("roles", roles);
        return "edit-account-admin";
    }

    @PostMapping("/accounts/update/{id}")
    public String processEditAccount(@PathVariable Long id,
                                     @Valid @ModelAttribute("editAccount") Account account,
                                     BindingResult result,
                                     Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("editAccount", account);
            return "edit-account-admin";
        }
        try {
            account.setAccountId(id); // Đảm bảo đúng id khi update
            accountService.updateAccount(account); // Update vào DB
            model.addAttribute("success", "Chỉnh sửa thành công!");
//            List<Role> roles = roleService.getAllRoles();
//            model.addAttribute("roles", roles);
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("editAccount", account);
            model.addAttribute("error", "Có lỗi: " + e.getMessage());
            return "edit-account-admin";
        }
    }

    @GetMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable Long id, HttpSession session) {
        Account currentAccount = (Account) session.getAttribute("account");
        if(currentAccount == null || !currentAccount.getRole().getRoleName().equals("ADMIN")) {
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không có tài khoản trong session
        }
        accountService.deleteAccount(id);
        return "redirect:/admin/dashboard";
    }

    // CRUD Orchid
    @PostMapping("/orchids/create")
    public String createOrchid(@Valid @ModelAttribute("newOrchid") Orchid newOrchid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("newOrchid", newOrchid);
            model.addAttribute("error", "Có lỗi trong quá trình tạo lan: " + result.getAllErrors().get(0).getDefaultMessage());
            return "create-orchid-admin";
        }
        try {
            orchidService.saveOrchid(newOrchid);
            model.addAttribute("success", "Tạo lan thành công!");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("newOrchid", new Orchid()); // Reset form
            return "create-orchid-admin";
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("newOrchid", newOrchid);
            model.addAttribute("error", "Có lỗi trong quá trình tạo lan: " + e.getMessage());
            return "create-orchid-admin";
        }
    }

    @GetMapping("/orchids/edit/{id}")
    public String editOrchid(@PathVariable Long id, Model model) {
        Optional<Orchid> orchid = orchidService.getOrchidById(id);
        if (orchid.isPresent()) {
            model.addAttribute("editOrchid", orchid.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit-orchid-admin";
        }
        return "redirect:/admin/dashboard"; // Nếu không tìm thấy, chuyển hướng về dashboard
    }

    @PostMapping("/orchids/update/{id}")
    public String updateOrchid(@PathVariable Long id, @Valid @ModelAttribute Orchid newOrchid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editOrchid", newOrchid);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit-orchid-admin";
        }
        try{
            Optional<Orchid> existingOrchid = orchidService.getOrchidById(id);
            if (!existingOrchid.isPresent()) {
                model.addAttribute("editOrchid", newOrchid);
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("error", "Không tìm thấy lan để sửa!");
                return "edit-orchid-admin";
            }
            newOrchid.setOrchidId(id); // Đảm bảo id đúng khi update
            orchidService.saveOrchid(newOrchid);
            model.addAttribute("success", "Cập nhật lan thành công!");
            model.addAttribute("editOrchid", new Orchid()); // Reset form
            return "edit-orchid-admin";
        } catch (Exception e) {
            model.addAttribute("editOrchid", newOrchid);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("error", "Có lỗi trong quá trình cập nhật lan: " + e.getMessage());
            return "edit-orchid-admin";
        }
    }

    @GetMapping("/orchids/delete/{id}")
    public String deleteOrchid(@PathVariable Long id) {
        try{
            if (!orchidService.existsById(id)) {
                return "redirect:/admin/dashboard"; // Nếu không tìm thấy, chuyển hướng về dashboard
            }
            orchidService.deleteOrchid(id);
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            return "redirect:/admin/dashboard"; // Nếu có lỗi, chuyển hướng về dashboard
        }
    }

    // CRUD Order
    @PostMapping("/orders/create")
    public String createOrder(@Valid @ModelAttribute("newOrder") Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/dashboard";
        }
        orderService.createOrder(order.getAccount(), order.getOrderDetails());
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/orders/update/{id}")
    public String updateOrder(@PathVariable Long id, @Valid @ModelAttribute Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/dashboard";
        }
        order.setId(id);
        orderService.updateOrderStatus(id, order.getOrderStatus());
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.cancelOrder(id); // Hoặc xóa hoàn toàn nếu cần
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }

    @GetMapping("/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", new Category());
        return "admin/categories";
    }

    @PostMapping("/categories")
    public String saveCategory(@Valid @ModelAttribute Category category,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/categories";
        }

        // Kiểm tra trùng tên
        if (categoryService.existsByCategoryName(category.getCategoryName())) {
            model.addAttribute("error", "Tên danh mục đã tồn tại!");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/categories";
        }

        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/categories";
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
