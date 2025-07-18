package sum25.khoinm.homework2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.pojo.Order;
import sum25.khoinm.homework2.pojo.OrderDetail;
import sum25.khoinm.homework2.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrderOrderByIdAsc(order);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrchid(Orchid orchid) {
        return orderDetailRepository.findByOrchid(orchid);
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public void deleteOrderDetailsByOrder(Order order) {
        orderDetailRepository.deleteByOrder(order);
    }

    @Override
    public Optional<OrderDetail> findByOrderAndOrchid(Order order, Orchid orchid) {
        return orderDetailRepository.findByOrderAndOrchid(order, orchid);
    }
}
