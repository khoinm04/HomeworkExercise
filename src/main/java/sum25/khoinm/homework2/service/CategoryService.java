package sum25.khoinm.homework2.service;

import sum25.khoinm.homework2.pojo.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
    Optional<Category> findByCategoryName(String categoryName);
    boolean existsById(Long id);
    boolean existsByCategoryName(String categoryName);
}
