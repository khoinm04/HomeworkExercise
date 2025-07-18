package sum25.khoinm.homework2.pojo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ngày đặt hàng không được để trống")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @NotBlank(message = "Trạng thái đơn hàng không được để trống")
    @Pattern(regexp = "^(PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED)$",
            message = "Trạng thái không hợp lệ")
    @Column(name = "order_status")
    private String orderStatus;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn 0")
    @Digits(integer = 10, fraction = 2, message = "Tổng tiền không đúng định dạng")
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Tài khoản không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Valid
    @Size(min = 1, message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Constructors
    public Order() {}

    public Order(Account account, String orderStatus, BigDecimal totalAmount) {
        this.account = account;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.orderDate = LocalDateTime.now();
    }

    public Order(LocalDateTime orderDate, String orderStatus, BigDecimal totalAmount, Account account, List<OrderDetail> orderDetails) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.account = account;
        this.orderDetails = orderDetails;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
}
