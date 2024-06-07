package org.example.qltv.Home;

public class Book {
    private int bookID;
    private String bookName;
    private String authorName;
    private int publishingYear;
    private int quantityBook;
    public Book(int bookID, String bookName, String authorName, int publishingYear, int quantityBook){
        this.bookID = bookID;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publishingYear = publishingYear;
        this.quantityBook = quantityBook;
    }
    public Book(){
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getQuantityBook() {
        return quantityBook;
    }

    public void setQuantityBook(int quantityBook) {
        this.quantityBook = quantityBook;
    }
}
