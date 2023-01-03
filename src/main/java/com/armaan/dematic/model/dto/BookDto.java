package com.armaan.dematic.model.dto;

import com.armaan.dematic.exceptions.validator.EnumBookTypePattern;
import com.armaan.dematic.model.Book;
import com.armaan.dematic.model.BookType;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO class to check Book entity inputs
 */
@Data
public class BookDto {

    @EnumBookTypePattern(regexp = "ANY_BOOK|ANTIQUE_BOOK|SCIENCE_JOURNAL|", message = "Enter correct form of Book Type")
    private BookType bookType;
    @NotEmpty(message = "Name cannot be null")
    @NotBlank(message = "Book Name cannot be empty")
    private String name;
    @NotEmpty(message = "Author cannot be null")
    @NotBlank(message = "Book Author cannot be empty")
    private String author;
    @NotEmpty(message = "Barcode cannot be null")
    @NotBlank(message = "Book barcode cannot be empty")
    private String barcode;
    @NotNull(message = "Quantity cannot be null")
    @NotBlank(message = "Quantity cannot be empty.", groups = Integer.class)
    @Pattern(regexp = "^[1-9][0-9]*$", groups = Integer.class,
            message = "Quantity has to be filled as a number.")
    @PositiveOrZero(message = "Quantity cannot be less than 0")
    private Integer quantity;

    @NotNull(message = "Price Per Unit cannot be null")
    @NotBlank(message = "Price per unit cannot be empty", groups = Double.class)
    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", groups = Double.class,
            message = "Price per unit has to be filled as a number.")
    @PositiveOrZero(message = "Price per unit cannot be negative.")
    private Double pricePerUnit;

    //Required for Testing
    public BookDto(BookType bookType, String name, String author, String barcode, Integer quantity, Double pricePerUnit) {
        this.bookType = bookType;
        this.name = name;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    /**
     * Function to create a Book from DTO object
     * @return Book Object
     */
    public Book toBook() {
        return new Book(bookType,
                name,
                author,
                barcode,
                quantity,
                pricePerUnit);
    }
}
