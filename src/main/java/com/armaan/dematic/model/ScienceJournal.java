package com.armaan.dematic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Science Journal entity class that describes a regular book but with a science index parameter.
 */
@EqualsAndHashCode(callSuper = true)
@Entity(name = "SCIENCE_JOURNAL")
@Data
@NoArgsConstructor
public class ScienceJournal extends Book {
    @Column(name = "SCIENCE_INDEX")
    private Integer scienceIndex;

    /**
     * Instantiates a new Science journal.
     *
     * @param bookType     the book type
     * @param name         the name
     * @param author       the author
     * @param barcode      the barcode
     * @param quantity     the quantity
     * @param pricePerUnit the price per unit
     * @param scienceIndex the science index
     */
    public ScienceJournal(BookType bookType, String name, String author, String barcode, Integer quantity, Double pricePerUnit, Integer scienceIndex) {
        super(bookType, name, author, barcode, quantity, pricePerUnit);
        this.scienceIndex = scienceIndex;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() * scienceIndex;
    }

    @Override
    public String toString() {
        return "ScienceJournal ---" +
                "\nbookType=" + super.getBookType() +
                "\nname='" + super.getName() +
                "\nauthor='" + super.getAuthor() +
                "\nbarcode='" + super.getBarcode() +
                "\nquantity=" + super.getQuantity() +
                "\npricePerUnit=" + super.getPricePerUnit() +
                "scienceIndex=" + scienceIndex;
    }
}
