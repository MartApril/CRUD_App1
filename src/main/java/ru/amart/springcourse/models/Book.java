package ru.amart.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min=1, message = "Name size should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=1, message = "Name size should be between 2 and 30 characters")
    private String author;
    @Min(value =0, message = "Age should be greater than 0")
    private int year;

//    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal code (6 digits)")
//    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book(int id, String name, String author, int year) {
        this.id=id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book() {
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
