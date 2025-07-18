package sum25.khoinm.homework2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sum25.khoinm.homework2.pojo.CartItem;
import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.pojo.OrderDetail;
import sum25.khoinm.homework2.service.OrchidService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

@Controller
public class CartController {
    @Autowired
    private OrchidService orchidService;

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long orchidId, @RequestParam Integer quantity, HttpSession session) {
        if(session.getAttribute("account") == null){
            return "redirect:/login";
        }
        if (orchidId == null || quantity == null || quantity <= 0) {
            return "redirect:/"; // Redirect to the homepage with an error
        }
        System.out.println("Adding to cart: Orchid ID = " + orchidId + ", Quantity = " + quantity);
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        Orchid orchid = orchidService.getOrchidById(orchidId).orElse(null);
        System.out.println("Orchid found: " + (orchid != null ? orchid.getOrchidName() : "Not Found"));
        if(orchid == null) {
            return "redirect:/"; // Redirect to the homepage if orchid not found
        }

        // Update or add the cart item
        cart.computeIfAbsent(orchidId, id -> new CartItem(orchid, 0))
                .setQuantity(cart.get(orchidId).getQuantity() + quantity);
        return "redirect:/"; // Redirect to the cart page after adding
    }
    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        if(session.getAttribute("account") == null){
            return "redirect:/login";
        }
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if(cart == null || cart.isEmpty()) {
            session.setAttribute("cart", null); // Clear the cart if it's empty
            return "cart"; // Redirect to the homepage if cart is empty
        }
        double totalPrice = 0;
        for (CartItem item : cart.values()) {
            totalPrice += item.getOrchid().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
        }
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("totalPrice", totalPrice); // <-- Add this line
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long orchidId, HttpSession session) {
        if(session.getAttribute("account") == null){
            return "redirect:/login";
        }
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if(cart != null && cart.containsKey(orchidId)) {
            cart.remove(orchidId);
            if(cart.isEmpty()) {
                session.setAttribute("cart", null); // Clear the cart if it's empty
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart"; // Redirect to the cart page after removing
    }
}
