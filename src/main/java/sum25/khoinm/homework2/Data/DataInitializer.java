package sum25.khoinm.homework2.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sum25.khoinm.homework2.pojo.Account;
import sum25.khoinm.homework2.pojo.Category;
import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.pojo.Role;
import sum25.khoinm.homework2.repository.AccountRepository;
import sum25.khoinm.homework2.repository.CategoryRepository;
import sum25.khoinm.homework2.repository.OrchidRepository;
import sum25.khoinm.homework2.repository.RoleRepository;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrchidRepository orchidRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("CUSTOMER"));
        }

        // Initialize categories
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Lan Hồ Điệp"));
            categoryRepository.save(new Category("Lan Cattleya"));
            categoryRepository.save(new Category("Lan Dendrobium"));
            categoryRepository.save(new Category("Lan Oncidium"));
        }

        // Initialize categories
        if (orchidRepository.count() == 0) {
            // Save categories first
            Category cat1 = categoryRepository.save(new Category("Lan Hồ Điệp"));
            Category cat2 = categoryRepository.save(new Category("Lan Cattleya"));
            Category cat3 = categoryRepository.save(new Category("Lan Dendrobium"));
            Category cat4 = categoryRepository.save(new Category("Lan Oncidium"));

            orchidRepository.save(new Orchid("Lan Hồ Điệp Vàng", "Mô tả lan Hồ Điệp Vàng", new BigDecimal("150000"), true, "https://example.com/orchid1.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Đỏ", "Mô tả lan Cattleya Đỏ", new BigDecimal("200000"), false, "https://example.com/orchid2.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Dendrobium Trắng", "Mô tả lan Dendrobium Trắng", new BigDecimal("180000"), true, "https://example.com/orchid3.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Oncidium Vàng", "Mô tả lan Oncidium Vàng", new BigDecimal("220000"), false, "https://example.com/orchid4.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Hồ Điệp Tím", "Mô tả lan Hồ Điệp Tím", new BigDecimal("160000"), true, "https://example.com/orchid5.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Trắng", "Mô tả lan Cattleya Trắng", new BigDecimal("210000"), false, "https://example.com/orchid6.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Dendrobium Hồng", "Mô tả lan Dendrobium Hồng", new BigDecimal("190000"), true, "https://example.com/orchid7.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Đỏ", "Mô tả lan Oncidium Đỏ", new BigDecimal("230000"), false, "https://example.com/orchid8.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Hồ Điệp Cam", "Mô tả lan Hồ Điệp Cam", new BigDecimal("170000"), true, "https://example.com/orchid9.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Vàng", "Mô tả lan Cattleya Vàng", new BigDecimal("250000"), false, "https://example.com/orchid10.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Dendrobium Xanh", "Mô tả lan Dendrobium Xanh", new BigDecimal("200000"), true, "https://example.com/orchid11.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Trắng", "Mô tả lan Oncidium Trắng", new BigDecimal("240000"), false, "https://example.com/orchid12.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Hồ Điệp Hồng", "Mô tả lan Hồ Điệp Hồng", new BigDecimal("155000"), true, "https://example.com/orchid13.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Cam", "Mô tả lan Cattleya Cam", new BigDecimal("205000"), false, "https://example.com/orchid14.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Dendrobium Vàng", "Mô tả lan Dendrobium Vàng", new BigDecimal("185000"), true, "https://example.com/orchid15.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Hồng", "Mô tả lan Oncidium Hồng", new BigDecimal("225000"), false, "https://example.com/orchid16.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Dendrobium Hồng1", "Mô tả lan Dendrobium Hồng", new BigDecimal("190000"), true, "https://example.com/orchid7.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Đỏ1", "Mô tả lan Oncidium Đỏ", new BigDecimal("230000"), false, "https://example.com/orchid8.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Hồ Điệp Cam1", "Mô tả lan Hồ Điệp Cam", new BigDecimal("170000"), true, "https://example.com/orchid9.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Vàng1", "Mô tả lan Cattleya Vàng", new BigDecimal("250000"), false, "https://example.com/orchid10.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Dendrobium Xanh1", "Mô tả lan Dendrobium Xanh", new BigDecimal("200000"), true, "https://example.com/orchid11.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Trắng1", "Mô tả lan Oncidium Trắng", new BigDecimal("240000"), false, "https://example.com/orchid12.jpg", cat4));
            orchidRepository.save(new Orchid("Lan Hồ Điệp Hồng1", "Mô tả lan Hồ Điệp Hồng", new BigDecimal("155000"), true, "https://example.com/orchid13.jpg", cat1));
            orchidRepository.save(new Orchid("Lan Cattleya Cam1", "Mô tả lan Cattleya Cam", new BigDecimal("205000"), false, "https://example.com/orchid14.jpg", cat2));
            orchidRepository.save(new Orchid("Lan Dendrobium Vàng1", "Mô tả lan Dendrobium Vàng", new BigDecimal("185000"), true, "https://example.com/orchid15.jpg", cat3));
            orchidRepository.save(new Orchid("Lan Oncidium Hồng1", "Mô tả lan Oncidium Hồng", new BigDecimal("225000"), false, "https://example.com/orchid16.jpg", cat4));

        }

        if (accountRepository.count() == 0) {
            Role adminRole = roleRepository.findByRoleName("ADMIN").orElse(null);
            Role customerRole = roleRepository.findByRoleName("CUSTOMER").orElse(null);

            // Tạo tài khoản Admin
            Account admin = new Account(
                    "Admin System",
                    "admin@orchidshop.com",
                    "admin123",
                    adminRole
            );
            accountRepository.save(admin);

            // Tạo tài khoản Customer mẫu
            Account customer1 = new Account(
                    "Nguyễn Văn An",
                    "nguyenvanan@gmail.com",
                    "password123",
                    customerRole
            );
            accountRepository.save(customer1);

            Account customer2 = new Account(
                    "Trần Thị Bình",
                    "tranthibinh@gmail.com",
                    "password123",
                    customerRole
            );
            accountRepository.save(customer2);

            Account customer3 = new Account(
                    "Lê Văn Cường",
                    "levancuong@gmail.com",
                    "567323213",
                    customerRole
            );
            accountRepository.save(customer3);

            Account customer4 = new Account(
                    "Phạm Thị Dung",
                    "phamthidung@gmail.com",
                    "3453213",
                    customerRole
            );
            accountRepository.save(customer4);

            Account customer5 = new Account(
                    "Hoàng Văn Em",
                    "hoangvanem@gmail.com",
                    "123321321",
                    customerRole
            );
            accountRepository.save(customer5);
        }
    }
}
