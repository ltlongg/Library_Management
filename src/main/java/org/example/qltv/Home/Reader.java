package org.example.qltv.Home;

import java.util.Date;

public class Reader {
    private Integer bookID;
    private String bookName;
    private Integer readerID;
    private String readerName;
    private String phone;
    private String gmail;
    private Date issueDate;
    private Date dueDate;

    public Reader(Integer bookID, String bookName, Integer readerID, String readerName, String phone, String gmail, Date issueDate, Date dueDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.readerID = readerID;
        this.readerName = readerName;
        this.phone = phone;
        this.gmail = gmail;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    public Reader(){

    }
    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getReaderID() {
        return readerID;
    }

    public void setReaderID(Integer readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
