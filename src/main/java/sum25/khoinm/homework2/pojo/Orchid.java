package sum25.khoinm.homework2.pojo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Entity
@Table(name = "orchids")
public class Orchid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orchidId;

    @NotNull(message = "Thông tin tự nhiên không được để trống")
    @Column(name = "is_natural")
    private Boolean isNatural;

    @NotBlank(message = "Mô tả lan không được để trống")
    @Size(min = 10, max = 1000, message = "Mô tả phải có độ dài từ 10-1000 ký tự")
    @Column(name = "orchid_description", columnDefinition = "nvarchar(1000)")
    private String orchidDescription;

    @NotBlank(message = "Tên lan không được để trống")
    @Size(min = 2, max = 200, message = "Tên lan phải có độ dài từ 2-200 ký tự")
    @Column(name = "orchid_name", nullable = false, columnDefinition = "nvarchar(1000)")
    private String orchidName;

    @URL(message = "URL hình ảnh không đúng định dạng")
    @Column(name = "orchid_url")
    private String orchidUrl;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Digits(integer = 8, fraction = 2, message = "Giá không đúng định dạng")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Danh mục không được để trống")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "orchid")
    private OrderDetail orderDetail;

    // Constructors
    public Orchid() {}

    public Orchid(String orchidName, String orchidDescription, BigDecimal price,
                  Boolean isNatural, String orchidUrl, Category category) {
        this.orchidName = orchidName;
        this.orchidDescription = orchidDescription;
        this.price = price;
        this.isNatural = isNatural;
        this.orchidUrl = orchidUrl;
        this.category = category;
    }

    // Getters and Setters
    public Long getOrchidId() { return orchidId; }
    public void setOrchidId(Long orchidId) { this.orchidId = orchidId; }

    public Boolean getIsNatural() { return isNatural; }
    public void setIsNatural(Boolean isNatural) { this.isNatural = isNatural; }

    public String getOrchidDescription() { return orchidDescription; }
    public void setOrchidDescription(String orchidDescription) { this.orchidDescription = orchidDescription; }

    public String getOrchidName() { return orchidName; }
    public void setOrchidName(String orchidName) { this.orchidName = orchidName; }

    public String getOrchidUrl() { return orchidUrl; }
    public void setOrchidUrl(String orchidUrl) { this.orchidUrl = orchidUrl; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public OrderDetail getOrderDetail() { return orderDetail; }
    public void setOrderDetail(OrderDetail orderDetail) { this.orderDetail = orderDetail; }
}