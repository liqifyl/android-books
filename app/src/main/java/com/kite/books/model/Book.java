package com.kite.books.model;

public class Book {
    public String title;
    public long id;
    public String image;
    public String author;
    public String publisher;
    public String price;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("title:");
        builder.append(title);
        builder.append("id:");
        builder.append(id);
        builder.append("image:");
        builder.append(image);
        builder.append("author:");
        builder.append(author);
        builder.append("publisher:");
        builder.append(publisher);
        builder.append("price:");
        builder.append(price);
        return super.toString();
    }
}
