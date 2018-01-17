package LMS.model;


import javafx.beans.property.SimpleStringProperty;

public class Loss_report {
    private SimpleStringProperty reader_id;
    private SimpleStringProperty loss_date;

    public Loss_report(String reader_id, String loss_date)
    {
        this.reader_id = new SimpleStringProperty(reader_id);
        this.loss_date = new SimpleStringProperty(loss_date);
    }

    public void setReader_id(String reader_id) {
        this.reader_id.set(reader_id);
    }

    public String getReader_id() {
        return reader_id.get();
    }

    public void setLoss_date(String loss_date) {
        this.loss_date.set(loss_date);
    }

    public String getLoss_date() {
        return loss_date.get();
    }
}
