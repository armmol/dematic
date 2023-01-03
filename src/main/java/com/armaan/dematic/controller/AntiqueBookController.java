package com.armaan.dematic.controller;

import com.armaan.dematic.model.AntiqueBook;
import com.armaan.dematic.model.BookType;
import com.armaan.dematic.model.dto.AntiqueBookDto;
import com.armaan.dematic.service.BookService;
import com.armaan.dematic.util.Check;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;


/**
 * Rest Controller for Antique Book
 */
@RestController
@RequestMapping(path = "/antiqueBook")
public class AntiqueBookController {

    @Autowired
    private BookService bookService;

    /**
     * Add Book API for Antique Book
     * @param book Antique Book Object
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PostMapping(value = "/addBook", consumes = "application/json")
    public ResponseEntity<String> addBook(@Valid @RequestBody AntiqueBookDto book) {
        if (book.getBookType() != null && book.getBookType() != BookType.ANTIQUE_BOOK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Book Type ANY_BOOK is required for this API. Please check your input.");
        } else {
            if (bookService.addAnyBook(book.toAntiqueBook()) == 1) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("An Antique Book Added Successfully\n" + book.toBook());
            } else if (bookService.addAnyBook(book.toAntiqueBook()) == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book already exists. Only quantity increased by " + book.getQuantity());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Book exists as another Book Type. Please check your input.");
            }
        }
    }

    /**
     * Update Book API for Antique Book
     * @param barcode barcode of book
     * @param name name of book
     * @param author author of book
     * @param quantity quantity of book
     * @param pricePerUnit price per unit of book
     * @param releaseYear release year of book
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PutMapping(value = "/editBook", consumes = "application/json")
    public ResponseEntity<String> updateBook(@RequestParam(required = false) String barcode,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String author,
                                             @RequestParam(required = false) String quantity,
                                             @RequestParam(required = false) String pricePerUnit,
                                             @RequestParam(required = false) String releaseYear) {
        if (barcode.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Enter barcode of book to edit");
        } else {
            if (bookService.getBookByBarcode(barcode) != null &&
                    bookService.getBookByBarcode(barcode).getBookType().equals(BookType.ANTIQUE_BOOK)) {
                AntiqueBook bookFromBarcode = (AntiqueBook) bookService.getBookByBarcode(barcode);
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
                if (!releaseYear.equals("")) {
                    if (Check.isInteger(releaseYear)) {
                        int parsedInt = Integer.parseInt(releaseYear);
                        if (parsedInt >= 1900 && parsedInt <= Year.now().getValue()) {
                            bookFromBarcode.setReleaseYear(parsedInt);
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("Antique Book has to have release Year between 1900 and "
                                            + Year.now().getValue() + " inclusive. Please try again.");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Release Year type is Integer. Please enter an Integer value.");
                    }
                }
                bookService.updateBookByBarcode(bookFromBarcode);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book with barcode " + barcode + " was updated\n" + bookFromBarcode);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("An Antique Book with barcode " + barcode + " does not exist.\nPlease enter a correct barcode and try again.");
            }
        }
    }
}
