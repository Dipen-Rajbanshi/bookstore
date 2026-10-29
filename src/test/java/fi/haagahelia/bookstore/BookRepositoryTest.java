package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Book newBook() {
        Category category = categoryRepository.save(new Category("Test category"));
        Book book = new Book("Dune", "Frank Herbert", 1965, "9780441172719", 9.99);
        book.setCategory(category);
        return book;
    }

    @Test
    void createBook() {
        Book saved = bookRepository.save(newBook());

        assertThat(saved.getId()).isNotNull();
        assertThat(bookRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void searchBookByAuthor() {
        bookRepository.save(newBook());

        List<Book> found = bookRepository.findByAuthor("Frank Herbert");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo("Dune");
    }

    @Test
    void deleteBook() {
        Book saved = bookRepository.save(newBook());

        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.findById(saved.getId())).isEmpty();
    }
}