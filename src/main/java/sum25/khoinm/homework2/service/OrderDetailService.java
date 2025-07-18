package sum25.khoinm.homework2.service;

import sum25.khoinm.homework2.pojo.OrderDetail;
import sum25.khoinm.homework2.pojo.Order;
import sum25.khoinm.homework2.pojo.Orchid;
import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    Optional<OrderDetail> getOrderDetailById(Long id);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    List<OrderDetail> getOrderDetailsByOrchid(Orchid orchid);
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(Long id);
    void deleteOrderDetailsByOrder(Order order);
    Optional<OrderDetail> findByOrderAndOrchid(Order order, Orchid orchid);
}