package sum25.khoinm.homework2.pojo;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Digits(integer = 10, fraction = 2, message = "Giá không đúng định dạng")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column
    private Integer quantity;

    @NotNull(message = "Đơn hàng không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "Sản phẩm không được để trống")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orchid_id")
    private Orchid orchid;

    // Constructors
    public OrderDetail() {}

    public OrderDetail(BigDecimal price, Integer quantity, Order order, Orchid orchid) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.orchid = orchid;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Orchid getOrchid() { return orchid; }
    public void setOrchid(Orchid orchid) { this.orchid = orchid; }
}

