package ru.akiselev.bookStore.repositories.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.models.Book_;

@Component
public class BookSpecification {
    public Specification<Book> byFilter(BookFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filter.getName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Book_.name), filter.getName()));
            }
            if (filter.getBrand() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Book_.brand), filter.getBrand()));
            }
            if (filter.getCover() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Book_.cover), filter.getCover()));
            }
            if (filter.getCount() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Book_.count), filter.getCount()));
            }
            return predicate;
        });
    }
}
