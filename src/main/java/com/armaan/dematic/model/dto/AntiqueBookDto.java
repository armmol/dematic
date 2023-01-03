package com.armaan.dematic.model.dto;

import com.armaan.dematic.model.AntiqueBook;
import com.armaan.dematic.model.BookType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO class to check Antique Book entity inputs
 */
public class AntiqueBookDto extends BookDto {
    @NotBlank(groups = {Integer.class}, message = "Release Year is required.")
    @Min(1900)
    @Max(2023)
    private Integer releaseYear;

    /**
     * Instantiates a new Antique book dto.
     *
     * @param bookType     the book type
     * @param name         the name
     * @param author       the author
     * @param barcode      the barcode
     * @param quantity     the quantity
     * @param pricePerUnit the price per unit
     * @param releaseYear  the release year
     */
//Required for Testing
    public AntiqueBookDto(BookType bookType, String name, String author, String barcode,
                          Integer quantity, Double pricePerUnit, Integer releaseYear) {
        super(bookType, name, author, barcode, quantity, pricePerUnit);
        this.releaseYear = releaseYear;
    }

    /**
     * Function to create an Antique Book from DTO object
     *
     * @return Antique Book Object
     */
    public AntiqueBook toAntiqueBook() {
        return new AntiqueBook(super.getBookType(),
                super.getName(),
                super.getAuthor(),
                super.getBarcode(),
                super.getQuantity(),
                super.getPricePerUnit(),
                releaseYear);
    }
}
