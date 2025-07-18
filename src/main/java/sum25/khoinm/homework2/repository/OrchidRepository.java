package sum25.khoinm.homework2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.khoinm.homework2.pojo.Category;
import sum25.khoinm.homework2.pojo.Orchid;

import java.util.List;

@Repository
public interface OrchidRepository extends JpaRepository<Orchid, Long> {
    List<Orchid> findByCategory(Category category);
    List<Orchid> findByOrchidNameContainingIgnoreCase(String name);
    Page<Orchid> findByCategory(Category category, Pageable pageable);
}
