package com.armaan.dematic.model.dto;

import com.armaan.dematic.model.BookType;
import com.armaan.dematic.model.ScienceJournal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO class to check Science Journal entity inputs
 */
public class ScienceJournalDto extends BookDto {
    @NotBlank(groups = {Integer.class}, message = "scienceIndex cannot be empty")
    @Max(10)
    @Min(1)
    private Integer scienceIndex;

    //Required for Testing
    public ScienceJournalDto(BookType bookType, String name, String author,
                             String barcode, Integer quantity, Double pricePerUnit, Integer scienceIndex) {
        super(bookType, name, author, barcode, quantity, pricePerUnit);
        this.scienceIndex = scienceIndex;
    }

    /**
     * Function to create a Science Journal from DTO object
     *
     * @return Science Journal Object
     */
    public ScienceJournal toScienceJournal() {
        return new ScienceJournal(super.getBookType(),
                super.getName(),
                super.getAuthor(),
                super.getBarcode(),
                super.getQuantity(),
                super.getPricePerUnit(),
                scienceIndex);
    }
}
