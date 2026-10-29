package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createCategory() {
        Category saved = categoryRepository.save(new Category("Science fiction"));

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void searchCategoryByName() {
        categoryRepository.save(new Category("Science fiction"));

        List<Category> found = categoryRepository.findByName("Science fiction");

        assertThat(found).hasSize(1);
    }

    @Test
    void deleteCategory() {
        Category saved = categoryRepository.save(new Category("Temporary"));

        categoryRepository.deleteById(saved.getId());

        assertThat(categoryRepository.findById(saved.getId())).isEmpty();
    }
}