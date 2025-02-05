package ru.akiselev.bookStore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;

import java.util.List;

@Component
public class CustomBookRepository {

    @PersistenceContext
    private EntityManager em;
    public List<Book> findByFilter(BookFilter filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<Book> book = criteriaQuery.from(Book.class);
        Predicate where = criteriaBuilder.conjunction();
        if (filter.getName() != null) {
            where = criteriaBuilder.and(where, criteriaBuilder.equal(book.get("name"), filter.getName()));
        }
        if (filter.getBrand() != null) {
            where = criteriaBuilder.and(where, criteriaBuilder.equal(book.get("brand"), filter.getBrand()));
        }
        if (filter.getCover() != null) {
            where = criteriaBuilder.and(where, criteriaBuilder.equal(book.get("cover"), filter.getCover()));
        }
        if (filter.getCount() != null) {
            where = criteriaBuilder.and(where, criteriaBuilder.equal(book.get("count"), filter.getCount()));
        }
        criteriaQuery.where(where);

//        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(book.get("name"), filter.getName()),
//                criteriaBuilder.equal(book.get("brand"), filter.getBrand()),
//                criteriaBuilder.equal(book.get("cover"), filter.getCover()),
//                criteriaBuilder.equal(book.get("count"), filter.getCount())));
//        criteriaQuery.where(criteriaBuilder.equal(book.get("name"), filter.getName()));
//        criteriaQuery.where(criteriaBuilder.equal(book.get("brand"), filter.getBrand()));
//        criteriaQuery.where(criteriaBuilder.equal(book.get("cover"), filter.getCover()));
//        criteriaQuery.where(criteriaBuilder.equal(book.get("count"), filter.getCount()));
        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
