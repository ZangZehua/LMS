package LMS.model;

public class Books_lost {
    String reader_id;
    String book_id;
    String lost_date;
    public Books_lost(String reader_id, String book_id, String lost_date)
    {
        setReader_id(reader_id);
        setBook_id(book_id);
        setLost_date(lost_date);
    }

    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getReader_id() {
        return reader_id;
    }

    public String getLost_date() {
        return lost_date;
    }

    public void setLost_date(String lost_date) {
        this.lost_date = lost_date;
    }
}
