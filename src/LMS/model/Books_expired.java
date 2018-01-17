package LMS.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Books_expired {
    private SimpleStringProperty reader_id;
    private SimpleStringProperty book_id;
    private SimpleStringProperty date_out;
    private SimpleStringProperty date_in;
    private SimpleBooleanProperty isLost;

    public Books_expired(String reader_id, String book_id, String date_out,String date_in,boolean isLost)
    {
        super();
        this.reader_id = new SimpleStringProperty(reader_id);
        this.book_id = new SimpleStringProperty(book_id);
        this.date_out = new SimpleStringProperty(date_out);
        this.date_in = new SimpleStringProperty(date_in);
        this.isLost = new SimpleBooleanProperty(isLost);
    }

    public void setDate_out(String date_out) {
        this.date_out.set(date_out);
    }

    public void setBook_id(String book_id) {
        this.book_id.set(book_id);
    }

    public void setReader_id(String reader_id) {
        this.reader_id.set(reader_id);
    }

    public void setDate_in(String date_in) {
        this.date_in.set(date_in);
    }

    public void setIsLost(boolean isLost) {
        this.isLost.set(isLost);
    }

    public String getBook_id() {
        return book_id.get();
    }

    public String getDate_in() {
        return date_in.get();
    }

    public String getDate_out() {
        return date_out.get();
    }

    public String getReader_id() {
        return reader_id.get();
    }

    public boolean isIsLost() {
        return isLost.get();
    }

}
