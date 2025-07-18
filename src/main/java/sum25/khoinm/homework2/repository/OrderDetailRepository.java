package sum25.khoinm.homework2.repository;
import sum25.khoinm.homework2.pojo.OrderDetail;
import sum25.khoinm.homework2.pojo.Order;
import sum25.khoinm.homework2.pojo.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);
    List<OrderDetail> findByOrchid(Orchid orchid);
    Optional<OrderDetail> findByOrderAndOrchid(Order order, Orchid orchid);
    void deleteByOrder(Order order);
    List<OrderDetail> findByOrderOrderByIdAsc(Order order);
}