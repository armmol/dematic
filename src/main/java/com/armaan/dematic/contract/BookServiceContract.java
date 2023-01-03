package com.armaan.dematic.contract;

import com.armaan.dematic.model.Book;

import java.util.HashMap;
import java.util.List;

public interface BookServiceContract {

    String getAllBooks();
    int addAnyBook(Book book);

    Book getBookByBarcode(String barcode);

    void updateBookByBarcode(Book book);

    HashMap<Integer, List<String>> getAllBarcodes();

    String sortByTotalPrice(HashMap<Integer, List<String>> barcodesByQuantity);

    String getTotalPricesOfAllBooks();
}
