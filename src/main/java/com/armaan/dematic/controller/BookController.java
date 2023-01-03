package com.armaan.dematic.controller;

import com.armaan.dematic.model.Book;
import com.armaan.dematic.model.BookType;
import com.armaan.dematic.model.dto.BookDto;
import com.armaan.dematic.service.BookService;
import com.armaan.dematic.util.Check;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Rest Controller for Regular Book
 */
@Validated
@RestController
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    BookService bookService;

    /**
     * Add Book API for regular Book
     * @param book Book Object
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PostMapping(value = "/addBook", consumes = "application/json")
    public ResponseEntity<String> addBook(@Valid @RequestBody BookDto book) {
        if (book.getBookType() != null && book.getBookType() != BookType.ANY_BOOK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Book Type ANY_BOOK is required for this API. Please check your input.");
        } else {
            if (bookService.addAnyBook(book.toBook()) == 1) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("A Book Added Successfully\n" + book.toBook());
            } else if (bookService.addAnyBook(book.toBook()) == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book already exists. Only quantity increased by " + book.getQuantity());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Book exists as another Book Type. Please check your input.");
            }
        }
    }

    /**
     * Update Book API for regular Book
     * @param barcode barcode of book
     * @param name name of book
     * @param author author of book
     * @param quantity quantity of book
     * @param pricePerUnit price per unit of book
     * @return ResponseEntity consisting for information about response and HttpStatus.
     */
    @PutMapping(value = "/editBook", consumes = "application/json")
    public ResponseEntity<String> updateBook(@RequestParam() String barcode,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String author,
                                             @RequestParam(required = false) String quantity,
                                             @RequestParam(required = false) String pricePerUnit) {
        if (barcode.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Enter barcode of book to edit");
        } else {
            if (bookService.getBookByBarcode(barcode) != null &&
                    bookService.getBookByBarcode(barcode).getBookType().equals(BookType.ANY_BOOK)) {
                Book bookFromBarcode = bookService.getBookByBarcode(barcode);
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
                bookService.updateBookByBarcode(bookFromBarcode);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Book with barcode " + barcode + " was updated\n" + bookFromBarcode);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Regular Book with barcode " + barcode + " does not exist.\nPlease enter a correct barcode and try again.");
            }
        }
    }

    /**
     * Get all barcodes API
     * @return ResponseEntity consisting for information about all barcodes and HttpStatus.
     */
    @GetMapping(value = "/getBarcodes")
    public ResponseEntity<String> getBarcodes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.sortByTotalPrice(bookService.getAllBarcodes()));
    }

    /**
     * Get all Books API
     * @return ResponseEntity consisting for information about all books and HttpStatus.
     */
    @GetMapping(value = "/getAllBooks")
    public ResponseEntity<String> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getAllBooks());
    }

    /**
     * Get all barcodes API
     * @return ResponseEntity consisting for information about total prices of all books and HttpStatus.
     */
    @GetMapping(value = "/getTotalPrices")
    public ResponseEntity<String> getTotalPrice() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getTotalPricesOfAllBooks());
    }
}
