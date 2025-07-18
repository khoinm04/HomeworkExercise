package sum25.khoinm.homework2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sum25.khoinm.homework2.pojo.Category;
import sum25.khoinm.homework2.pojo.Orchid;
import sum25.khoinm.homework2.repository.CategoryRepository;
import sum25.khoinm.homework2.repository.OrchidRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrchidServiceImpl implements OrchidService{
    @Autowired
    private OrchidRepository orchidRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Orchid> getAllOrchids(Pageable pageable) {
        return orchidRepository.findAll(pageable);
    }

    @Override
    public Optional<Orchid> getOrchidById(Long id) {
        return orchidRepository.findById(id);
    }

    @Override
    public List<Orchid> getOrchidsByCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.map(orchidRepository::findByCategory).orElse(new ArrayList<>());
    }

    @Override
    public List<Orchid> searchOrchids(String keyword) {
        return orchidRepository.findByOrchidNameContainingIgnoreCase(keyword);
    }

    @Override
    public Orchid saveOrchid(Orchid orchid) {
        return orchidRepository.save(orchid);
    }

    @Override
    public void deleteOrchid(Long id) {
        orchidRepository.deleteById(id);
    }

    @Override
    public List<Orchid> getFeaturedOrchids() {
        return orchidRepository.findAll().stream()
                .limit(8)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return orchidRepository.existsById(id);
    }
}
