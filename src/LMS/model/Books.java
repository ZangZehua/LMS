package LMS.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;

public class Books {
    private String book_id;
    private String book_name;
    private String author;
    private String publishing;
    private String book_category;
    private double price;
    private String data_in;
    private int number_in;
    private int number_out;
    private int number_loss;
    private int stock;

    public Books(String book_id, String book_name, String author, String publishing, String book_category, Double price, String data_in, Integer number_in, int number_out, Integer number_loss)
    {
        setBook_id(book_id);
        setNumber_loss(number_loss);
        setNumber_out(number_out);
        setNumber_in(number_in);
        setData_in(data_in);
        setPublishing(publishing);
        setAuthor(author);
        setBook_name(book_name);
        setPrice(price);
        setBook_category(book_category);
        getStock();
    }

    public int getStock()
    {
        int stock = getNumber_in()-getNumber_out()-getNumber_loss();
        this.stock = stock;
        return stock;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getData_in() {
        return data_in;
    }

    public void setData_in(String data_in) {
        this.data_in = data_in;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber_in() {
        return number_in;
    }

    public int getNumber_loss() {
        return number_loss;
    }

    public int getNumber_out() {
        return number_out;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNumber_in(int number_in) {
        this.number_in = number_in;
    }

    public void setNumber_loss(int number_loss) {
        this.number_loss = number_loss;
    }

    public void setNumber_out(int number_out) {
        this.number_out = number_out;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }
}
