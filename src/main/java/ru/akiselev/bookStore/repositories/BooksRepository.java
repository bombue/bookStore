package ru.akiselev.bookStore.repositories;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    interface Specs {
        static Specification<Book> byName(String name) {
            return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name));
        }

        static Specification<Book> byBrand(String brand) {
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("brand"), brand));
        }

        static Specification<Book> byCover(Cover cover) {
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("cover"), cover));
        }

        static Specification<Book> byCount(Integer count) {
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("count"), count));
        }

        static Specification<Book> byFilter(BookFilter filter) {
            return ((root, query, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.conjunction();
                if (filter.getName() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), filter.getName()));
                }
                if (filter.getBrand() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("brand"), filter.getBrand()));
                }
                if (filter.getCover() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cover"), filter.getCover()));
                }
                if (filter.getCount() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("count"), filter.getCount()));
                }
                return predicate;
            });
        }
    }
}
