package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			Category fiction = categoryRepository.save(new Category("Fiction"));
			Category classics = categoryRepository.save(new Category("Classics"));
			Category dystopian = categoryRepository.save(new Category("Dystopian"));

			Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 10.99);
			book1.setCategory(fiction);
			bookRepository.save(book1);

			Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", 7.99);
			book2.setCategory(classics);
			bookRepository.save(book2);

			Book book3 = new Book("1984", "George Orwell", 1949, "9780451524935", 8.99);
			book3.setCategory(dystopian);
			bookRepository.save(book3);
		};
	}

	@Bean
	public Converter<String, Category> categoryConverter(CategoryRepository categoryRepository) {
		return new Converter<String, Category>() {
			@Override
			public Category convert(String id) {
				if (id == null || id.isEmpty()) {
					return null;
				}
				return categoryRepository.findById(Long.parseLong(id)).orElse(null);
			}
		};
	}

}