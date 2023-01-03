package com.armaan.dematic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book class to for a regular book entity consisting of properties
 * that every book contains. It is inherited by other classes.
 */
@Entity(name = "BOOK")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BOOK_TYPE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookType bookType;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "AUTHOR", nullable = false)
    private String author;
    @Column(
            name = "BARCODE",
            nullable = false,
            unique = true
    )
    private String barcode;
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
    @Column(name = "PRICE_PER_UNIT", nullable = false)
    private Double pricePerUnit;

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return quantity * pricePerUnit;
    }

    /**
     * Instantiates a new Book.
     *
     * @param bookType     the book type
     * @param name         the name
     * @param author       the author
     * @param barcode      the barcode
     * @param quantity     the quantity
     * @param pricePerUnit the price per unit
     */
    public Book(BookType bookType, String name, String author, String barcode, Integer quantity, Double pricePerUnit) {
        this.bookType = bookType;
        this.name = name;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return "Book --- " +
                "\nbookType=" + bookType +
                "\nname='" + name +
                "\nauthor='" + author +
                "\nbarcode='" + barcode +
                "\nquantity=" + quantity +
                "\npricePerUnit=" + pricePerUnit;
    }
}
