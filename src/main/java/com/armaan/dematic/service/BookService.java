package com.armaan.dematic.service;

import com.armaan.dematic.contract.BookServiceContract;
import com.armaan.dematic.model.Book;
import com.armaan.dematic.repository.AntiqueBookRepository;
import com.armaan.dematic.repository.BookRepository;
import com.armaan.dematic.repository.ScienceJournalRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Class for all books
 */
@Service
public class BookService implements BookServiceContract {

    private final BookRepository bookRepository;
    private final AntiqueBookRepository antiqueBookRepository;
    private final ScienceJournalRepository scienceJournalRepository;

    public BookService(BookRepository bookRepository, AntiqueBookRepository antiqueBookRepository,
                       ScienceJournalRepository scienceJournalRepository) {
        this.bookRepository = bookRepository;
        this.antiqueBookRepository = antiqueBookRepository;
        this.scienceJournalRepository = scienceJournalRepository;
    }

    /**
     * Get All Books Function
     * @return String of all Books information
     */
    @Override
    public String getAllBooks() {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%20s %20s %20s %20s %20s %20s\n", "Book Type", "Name", "Author", "Quantity", "Price Per Unit", "Total Price"));
        allBooks.forEach(book ->
                stringBuilder.append(String.format("%20s %20s %20s %20s %20s %20s\n",
                        book.getBookType(), book.getName(), book.getAuthor(),
                        book.getQuantity(), book.getPricePerUnit(), book.getTotalPrice())));
        return stringBuilder.toString();
    }

    /**
     * Function for adding any book to the database
     * @param book Book Object
     * @return int value to evaluate if the book was saved as a new book, or just the quantity was updated or t
     */
    @Override
    public int addAnyBook(Book book) {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        if (allBooks.stream().anyMatch(bookTemp -> bookTemp.getBarcode().equals(book.getBarcode()))) {
            Book bookCopy = allBooks.stream().filter(bookTemp -> bookTemp.getBarcode().equals(book.getBarcode())).findFirst().get();
            if (bookCopy.getBookType() == book.getBookType()) {
                bookCopy.setQuantity(bookCopy.getQuantity() + book.getQuantity());
                bookRepository.save(bookCopy);
                return 0;
            } else
                return -1;
        } else {
            switch (book.getBookType()) {
                case ANY_BOOK -> bookRepository.save(book);
                case ANTIQUE_BOOK -> antiqueBookRepository.save(book);
                case SCIENCE_JOURNAL -> scienceJournalRepository.save(book);
            }
            return 1;
        }
    }

    /**
     * Function to get a book by barcode
     * @param barcode barcode of book to be searched
     * @return Book Object of barcode searched or null if not found
     */
    @Override
    public Book getBookByBarcode(String barcode) {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        if (allBooks.stream().anyMatch(book -> book.getBarcode().equals(barcode))) {
            return allBooks.stream().filter(book -> book.getBarcode().equals(barcode)).findFirst().get();
        } else
            return null;
    }

    /**
     * Update Function to update a book
     * @param book book Object to be updated
     */
    @Override
    public void updateBookByBarcode(Book book) {
        switch (book.getBookType()) {
            case ANY_BOOK -> bookRepository.save(book);
            case ANTIQUE_BOOK -> antiqueBookRepository.save(book);
            case SCIENCE_JOURNAL -> scienceJournalRepository.save(book);
        }
    }

    /**
     * Function to get all Barcodes
     * @return returns barcodes sorted by Quantity
     */
    @Override
    public HashMap<Integer, List<String>> getAllBarcodes() {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        HashMap<Integer, List<String>> barcodesByQuantity = new HashMap<>();
        for (Book book : allBooks) {
            if (!barcodesByQuantity.containsKey(book.getQuantity())) {
                barcodesByQuantity.put(book.getQuantity(), new ArrayList<>());
            }
            barcodesByQuantity.get(book.getQuantity()).add(book.getBarcode());
            barcodesByQuantity.put(book.getQuantity(), barcodesByQuantity.get(book.getQuantity()));
        }
        return barcodesByQuantity;
    }

    /**
     * Function to sort by Total price
     * @param barcodesByQuantity List of barcodes of books to be sorted
     * @return String of sorted barcodes
     */
    @Override
    public String sortByTotalPrice(HashMap<Integer, List<String>> barcodesByQuantity) {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
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
        return stringBuilder.toString();
    }

    /**
     * Function to get total prices of all books
     * @return string of total prices of all books
     */
    @Override
    public String getTotalPricesOfAllBooks() {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%20s %20s %20s\n", "Book Type", "Book Name", "Total Price"));
        allBooks.forEach(book ->
                stringBuilder.append(String.format("%20s %20s %20s\n",
                        book.getBookType(), book.getName(), book.getTotalPrice())));
        return stringBuilder.toString();
    }
}
