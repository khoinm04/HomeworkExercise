package sum25.khoinm.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.khoinm.homework2.pojo.Account;
import sum25.khoinm.homework2.pojo.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccountOrderByOrderDateDesc(Account account);
    List<Order> findByOrderStatusOrderByOrderDateDesc(String status);
}
