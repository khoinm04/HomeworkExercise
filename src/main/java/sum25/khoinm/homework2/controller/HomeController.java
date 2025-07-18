package sum25.khoinm.homework2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.khoinm.homework2.pojo.Account;
import sum25.khoinm.homework2.pojo.Category;
import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.service.AccountService;
import sum25.khoinm.homework2.service.CategoryService;
import sum25.khoinm.homework2.service.OrchidService;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private OrchidService orchidService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    // Trang chủ
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Orchid> orchids = orchidService.getAllOrchids(pageable);
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("orchids", orchids);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orchids.getTotalPages());

        return "homepage";
    }

    // Hiển thị trang login
    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    // API Login - Spring MVC thuần túy
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session,
                        Model model, RedirectAttributes redirectAttributes) {

        if( email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Email không được để trống");
            return "login";
        }
        if( password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Mật khẩu không được để trống");
            return "login";
        }
        System.out.println("Login attempt with email: " + email + " and password: " + password);
        try{
            Account account = accountService.isAccountExist(email, password);

            if(account != null) {
                session.setAttribute("account", account);
                session.setAttribute("isLoggedIn", true);
                if( account.getRole() != null && account.getRole().getRoleName().equals("ADMIN")) {
                    return "redirect:/admin/dashboard";
                }
                redirectAttributes.addFlashAttribute("message", "Đăng nhập thành công");
                return "redirect:/";
            } else {
                model.addAttribute("error", "Email hoặc mật khẩu không đúng");
                return "login";
            }
        }catch(Exception ex) {
            model.addAttribute("error", ex);
            return "login";
        }
    }
    // API Logout - Spring MVC thuần túy
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Đăng xuất thành công");
        return "redirect:/login";
    }

    @GetMapping("/orchid/{id}")
    public String orchidDetail(@PathVariable Long id, Model model) {
        Optional<Orchid> orchid = orchidService.getOrchidById(id);
        if (orchid.isPresent()) {
            model.addAttribute("orchid", orchid.get());
            return "orchid-detail";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Orchid> orchids = orchidService.searchOrchids(keyword);
        model.addAttribute("orchids", orchids);
        model.addAttribute("keyword", keyword);
        return "search-results";
    }
}
