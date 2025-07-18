package sum25.khoinm.homework2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.khoinm.homework2.pojo.*;
import sum25.khoinm.homework2.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public Order createOrder(Account account, List<OrderDetail> orderDetails) {
        Order order = new Order();
        order.setAccount(account);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("DELIVERED"); // Trạng thái mặc định

        // Tính tổng tiền
        BigDecimal totalAmount = orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        // ✅ Gán order vào chi tiết trước khi lưu
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(order);
        }

        // ✅ Gán danh sách chi tiết vào order
        order.setOrderDetails(orderDetails);

        // ✅ Lưu order (với cascade ALL thì các orderDetails sẽ được lưu cùng)
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccountOrderByOrderDateDesc(account);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setOrderStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void cancelOrder(Long orderId) {
        updateOrderStatus(orderId, "CANCELLED");
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatusOrderByOrderDateDesc(status);
    }
}
