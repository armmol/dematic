package com.armaan.dematic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Year;

/**
 * AntiqueBook entity class that describes a regular book but with a release year parameter.
 */
@EqualsAndHashCode(callSuper = true)
@Entity(name = "ANTIQUE_BOOK")
@Data
@NoArgsConstructor
public class AntiqueBook extends Book {
    @Column(name = "RELEASE_YEAR")
    private Integer releaseYear;

    public AntiqueBook(BookType bookType, String name, String author, String barcode, Integer quantity, Double pricePerUnit, Integer releaseYear) {
        super(bookType, name, author, barcode, quantity, pricePerUnit);
        this.releaseYear = releaseYear;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() * (Year.now().getValue() - releaseYear) / 10;
    }

    @Override
    public String toString() {
        return "AntiqueBook ---" +
                "\nbookType=" + super.getBookType() +
                "\nname='" + super.getName()+
                "\nauthor='" + super.getAuthor() +
                "\nbarcode='" + super.getBarcode() +
                "\nquantity=" + super.getQuantity() +
                "\npricePerUnit=" + super.getPricePerUnit() +
                "\nreleaseYear=" + releaseYear;
    }
}
