package com.armaan.dematic.service;

import com.armaan.dematic.model.Book;
import com.armaan.dematic.model.BookType;
import com.armaan.dematic.repository.AntiqueBookRepository;
import com.armaan.dematic.repository.BookRepository;
import com.armaan.dematic.repository.ScienceJournalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Class for Service Layer
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookServiceTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AntiqueBookRepository antiqueBookRepository;
    @Autowired
    ScienceJournalRepository scienceJournalRepository;
    private Book book;
    private BookService bookService;
    private List<Book> allBooks;
    private StringBuilder stringBuilder;
    private HashMap<Integer, List<String>> barcodesByQuantity;

    @BeforeEach
    public void setUp() {
        book = new Book(BookType.ANY_BOOK, "A", "A", "A", 1, 1.0);
        bookService = new BookService(bookRepository, antiqueBookRepository, scienceJournalRepository);
        allBooks = (List<Book>) bookRepository.findAll();
        stringBuilder = new StringBuilder();
        barcodesByQuantity = new HashMap<>();
        for (Book book : allBooks) {
            if (!barcodesByQuantity.containsKey(book.getQuantity())) {
                barcodesByQuantity.put(book.getQuantity(), new ArrayList<>());
            }
            barcodesByQuantity.get(book.getQuantity()).add(book.getBarcode());
            barcodesByQuantity.put(book.getQuantity(), barcodesByQuantity.get(book.getQuantity()));
        }
    }

    @AfterEach
    public void tearDown() {
        bookService = new BookService(bookRepository, antiqueBookRepository, scienceJournalRepository);
    }

    @Test
    void getAllBooks() {
        bookService.addAnyBook(book);
        stringBuilder.append(String.format("%20s %20s %20s %20s %20s %20s\n", "Book Type", "Name", "Author", "Quantity", "Price Per Unit", "Total Price"));
        allBooks.forEach(book ->
                stringBuilder.append(String.format("%20s %20s %20s %20s %20s %20s\n",
                        book.getBookType(), book.getName(), book.getAuthor(),
                        book.getQuantity(), book.getPricePerUnit(), book.getTotalPrice())));
        assertEquals(stringBuilder.toString(), bookService.getAllBooks());
    }

    @Test
    void addAnyBook() {
        assertEquals(0, bookService.addAnyBook(book));
    }

    @Test
    void getBookByBarcode() {
        bookService.addAnyBook(book);
        assertEquals("A", bookService.getBookByBarcode("A").getAuthor());
    }

    @Test
    void updateBookByBarcode() {
        bookService.addAnyBook(book);
        Book bookByBarcode = bookService.getBookByBarcode("A");
        bookByBarcode.setAuthor("Updated");
        assertEquals("Updated", bookService.getBookByBarcode("A").getAuthor());
    }

    @Test
    void getAllBarcodes() {
        assertEquals(barcodesByQuantity.get(100).get(0), bookService.getAllBarcodes().get(100).get(0));
    }

    @Test
    void sortByTotalPrice() {
        HashMap<Integer, List<String>> copyBarcodesByQuantity = new HashMap<>(barcodesByQuantity);
        copyBarcodesByQuantity.forEach((quantity, barcodes) -> {
            List<Book> filteredBooks = new ArrayList<>(allBooks.stream()
                    .filter(book -> barcodes.contains(book.getBarcode())).toList());
            filteredBooks.sort(Comparator.comparingDouble(Book::getTotalPrice));
            barcodesByQuantity.get(quantity).clear();
            filteredBooks.forEach(book -> barcodesByQuantity.get(quantity).add(book.getBarcode()));
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Barcodes Sorted by total price -\n");
        barcodesByQuantity.forEach((key, values) -> stringBuilder.append("Quantity : ").append(key).append("\n")
                .append("Barcodes : ")
                .append(values.toString()).append("\n\n"));
        assertEquals(stringBuilder.toString(), bookService.sortByTotalPrice(barcodesByQuantity));
    }

    @Test
    void getTotalPricesOfAllBooks() {
        stringBuilder.append(String.format("%20s %20s %20s\n", "Book Type", "Book Name", "Total Price"));
        allBooks.forEach(book ->
                stringBuilder.append(String.format("%20s %20s %20s\n",
                        book.getBookType(), book.getName(), book.getTotalPrice())));
        assertEquals(stringBuilder.toString(), bookService.getTotalPricesOfAllBooks());
    }
}