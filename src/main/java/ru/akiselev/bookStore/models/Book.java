package ru.akiselev.bookStore.models;


import jakarta.persistence.*;
import lombok.*;
import ru.akiselev.bookStore.enums.Cover;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover")
    private Cover cover;  // Enum типа обложки: твердый или мягкий переплет

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
}
