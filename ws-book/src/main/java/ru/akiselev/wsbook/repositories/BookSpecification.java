package ru.akiselev.wsbook.repositories;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.akiselev.wsbook.model.Book;
import ru.akiselev.wsbook.model.Book_;
import ru.akiselev.wsbook.model.BookFilter;

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
