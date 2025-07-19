package sum25.khoinm.homework2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sum25.khoinm.homework2.pojo.*;
import sum25.khoinm.homework2.service.OrderDetailService;
import sum25.khoinm.homework2.service.OrderService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        if (session.getAttribute("account") == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        try {
            Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
            if (cart == null || cart.isEmpty()) {
                return "redirect:/cart"; // Redirect to cart if it's empty
            }

            Map<Orchid, CartItem> mergedItems = new HashMap<>();
            for (CartItem item : cart.values()) {
                Orchid orchid = item.getOrchid();
                mergedItems.compute(orchid, (k, v) -> {
                    if (v == null) return item;
                    v.setQuantity(v.getQuantity() + item.getQuantity()); // Gộp quantity
                    return v;
                });
            }

            Order order = orderService.createOrder((Account) session.getAttribute("account"), mergedItems.values().stream()
                    .map(item -> new OrderDetail(
                            item.getOrchid().getPrice(),
                            item.getQuantity(),
                            null,
                            item.getOrchid()
                    ))
                    .toList());

            return "confirmation"; // Redirect to confirmation page after successful checkout
        }catch (Exception e){
            e.printStackTrace();
            return "error"; // Redirect to an error page if something goes wrong
        }
    }
}
