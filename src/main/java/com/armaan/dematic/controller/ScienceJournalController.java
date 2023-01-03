package com.armaan.dematic.controller;

import com.armaan.dematic.model.BookType;
import com.armaan.dematic.model.ScienceJournal;
import com.armaan.dematic.model.dto.ScienceJournalDto;
import com.armaan.dematic.service.BookService;
import com.armaan.dematic.util.Check;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

/**
 * Rest Controller for Science Journal Book
 */
@RestController
@RequestMapping(path = "/scienceJournal")
public class ScienceJournalController {
    @Autowired
    private BookService bookService;

    /**
     * Add Book API for Science Journal Book
     * @param book Science Journal Object
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PostMapping(value = "/addBook", consumes = "application/json")
    public ResponseEntity<String> addBook(@Valid @RequestBody ScienceJournalDto book) {
        if (book.getBookType() != null && book.getBookType() != BookType.SCIENCE_JOURNAL) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Book Type SCIENCE_JOURNAL is required for this API. Please check your input.");
        } else {
            if (bookService.addAnyBook(book.toScienceJournal()) == 1) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("A Science Journal Book Added Successfully\n" + book.toBook());
            } else if (bookService.addAnyBook(book.toScienceJournal()) == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book already exists. Only quantity increased by " + book.getQuantity());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Book exists as another Book Type. Please check your input.");
            }
        }
    }

    /**
     * Update Book API for Science Journal Book
     * @param barcode barcode of book
     * @param name name of book
     * @param author author of book
     * @param quantity quantity of book
     * @param pricePerUnit price per unit of book
     * @param scienceIndex science index of book
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PutMapping(value = "/editBook", consumes = "application/json")
    public ResponseEntity<String> updateBook(@RequestParam(required = false) String barcode,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String author,
                                             @RequestParam(required = false) String quantity,
                                             @RequestParam(required = false) String pricePerUnit,
                                             @RequestParam(required = false) String scienceIndex) {
        if (barcode.equals("")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Enter barcode of book to edit");
        } else {
            if (bookService.getBookByBarcode(barcode) != null &&
                    bookService.getBookByBarcode(barcode).getBookType().equals(BookType.SCIENCE_JOURNAL)) {
                ScienceJournal bookFromBarcode = (ScienceJournal) bookService.getBookByBarcode(barcode);
                if (!name.equals("")) {
                    bookFromBarcode.setName(name);
                }
                if (!author.equals("")) {
                    bookFromBarcode.setAuthor(author);
                }
                if (!quantity.equals("")) {
                    if (Check.isInteger(quantity)) {
                        int parsedInt = Integer.parseInt(quantity);
                        bookFromBarcode.setQuantity(parsedInt);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Quantity type is Integer. Please enter an Integer value.");
                    }
                }
                if (!pricePerUnit.equals("")) {
                    if (Check.isDouble(pricePerUnit)) {
                        double parseDouble = Double.parseDouble(pricePerUnit);
                        bookFromBarcode.setPricePerUnit(parseDouble);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Price per unit type is Double. Please enter an Double value.");
                    }
                }
                if (!scienceIndex.equals("")) {
                    if (Check.isInteger(scienceIndex)) {
                        int parsedInt = Integer.parseInt(scienceIndex);
                        if (parsedInt >= 1900 && parsedInt <= Year.now().getValue()) {
                            bookFromBarcode.setScienceIndex(parsedInt);
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("Science Journal has to have science index between 1 and 10. Please try again.");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Science Journal type is Integer. Please enter an Integer value.");
                    }
                }
                bookService.updateBookByBarcode(bookFromBarcode);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book with barcode " + barcode + " was updated");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Science Journal with barcode " + barcode + " does not exist.\nPlease enter a correct barcode and try again.");
            }
        }
    }
}
