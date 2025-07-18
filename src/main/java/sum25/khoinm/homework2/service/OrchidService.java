package sum25.khoinm.homework2.service;

import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrchidService {
    Page<Orchid> getAllOrchids(Pageable pageable);
    Optional<Orchid> getOrchidById(Long id);
    List<Orchid> getOrchidsByCategory(Long categoryId);
    List<Orchid> searchOrchids(String keyword);
    Orchid saveOrchid(Orchid orchid);
    void deleteOrchid(Long id);
    List<Orchid> getFeaturedOrchids();
    boolean existsById(Long id);
}

