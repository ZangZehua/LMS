package LMS.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import LMS.model.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ReaderController implements Initializable {

    private ConnectDB connectDB = new ConnectDB();
    private Reader_info reader_info = connectDB.getReader_info();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long current_Date = System.currentTimeMillis();//new日期对象
    Date current_Date1 = new Date(current_Date);//转换提日期输出格式
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Label welcome;
    @FXML private Label arrears;
    @FXML private Label output_be_sit;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText(reader_info.getReader_name());
        refresh();
    }
    public void refresh()
    {
        if(connectDB.getLineCount(reader_info.getReader_id()) > 0)
        {
            output_be_sit.setTextFill(Color.web("#FF0000"));
            output_be_sit.setText("您有"+ connectDB.getLineCount(reader_info.getReader_id()) + "本书逾期未还！，请及时联系管理员还书！");
        }
        else
        {
            output_be_sit.setText("您的图书都在应还日期之内！");
        }

        findAbook();
        findbooksborrowed();
        findbooks_expired();
        if(connectDB.getArrears(reader_info.getReader_id())>0)
        {
            arrears.setTextFill(Color.web("#FF0000"));
        }
        arrears.setText(decimalFormat.format(connectDB.getArrears(reader_info.getReader_id())) + "元");

    }
    //查找图书区域
    @FXML private TextField bookid1;
    @FXML private TextField bookname1;
    @FXML private TextField author1;
    @FXML private TableView tableView1;
    @FXML private TableColumn c1_1; @FXML private TableColumn c1_2; @FXML private TableColumn c1_3; @FXML private TableColumn c1_4; @FXML private TableColumn c1_5;
    @FXML private TableColumn c1_6; @FXML private TableColumn c1_7; @FXML private TableColumn c1_8; @FXML private TableColumn c1_9; @FXML private TableColumn c1_10;
    @FXML private TableColumn c1_11;
    public void findAbook()
    {
        String bookid = bookid1.getText();
        String bookname = bookname1.getText();
        String author = author1.getText();
        c1_1.setCellValueFactory(new PropertyValueFactory("book_id"));
        c1_2.setCellValueFactory(new PropertyValueFactory("book_name"));
        c1_3.setCellValueFactory(new PropertyValueFactory("author"));
        c1_4.setCellValueFactory(new PropertyValueFactory("publishing"));
        c1_5.setCellValueFactory(new PropertyValueFactory("book_category"));
        c1_6.setCellValueFactory(new PropertyValueFactory("price"));
        c1_7.setCellValueFactory(new PropertyValueFactory("data_in"));
        c1_8.setCellValueFactory(new PropertyValueFactory("number_in"));
        c1_9.setCellValueFactory(new PropertyValueFactory("number_out"));
        c1_10.setCellValueFactory(new PropertyValueFactory("number_loss"));
        c1_11.setCellValueFactory(new PropertyValueFactory("stock"));

        ObservableList<Books> books = connectDB.findabook(bookid, bookname, author);
        tableView1.setItems(books);
    }

    //查看已借图书汇总区域
    @FXML private TextField input_bookid2;
    @FXML private TableView tableView2;
    @FXML private TableColumn c2_1; @FXML private TableColumn c2_2; @FXML private TableColumn c2_3; @FXML private TableColumn c2_4;
    @FXML private Label output_bookid2;
    @FXML private Label output_sit2;
    public void findbooksborrowed()
    {
        String book_id = input_bookid2.getText();

        c2_1.setCellValueFactory(new PropertyValueFactory("book_id"));
        c2_2.setCellValueFactory(new PropertyValueFactory("date_out"));
        c2_3.setCellValueFactory(new PropertyValueFactory("date_in"));
        c2_4.setCellValueFactory(new PropertyValueFactory("isLost"));

        ObservableList<Books_borrowed> books_borrowed = connectDB.findabook_borrowed(reader_info.getReader_id(), book_id);
        tableView2.setItems(books_borrowed);
        tableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int index = tableView2.getSelectionModel().getSelectedIndex();
                output_bookid2.setText(books_borrowed.get(index).getBook_id());
            }
        });
    }
    public void bookLostReport(ActionEvent actionEvent) {
        String reader_id = reader_info.getReader_id();
        String book_id = output_bookid2.getText();
        String lost_date = dateFormat.format(current_Date1);
        if (connectDB.reportBookLost(reader_id, book_id, lost_date) == 1)
        {
            output_sit2.setTextFill(Color.web("#00FF00"));
            output_sit2.setText("上报成功！");
        }
        else
        {
            output_sit2.setTextFill(Color.web("#FF0000"));
            output_sit2.setText("上报失败！");
        }
    }

    //查看已逾期图书
    @FXML private TableView tableView3;
    @FXML private TableColumn c3_1; @FXML private TableColumn c3_2; @FXML private TableColumn c3_3;
    public void findbooks_expired()
    {
        c3_1.setCellValueFactory(new PropertyValueFactory("book_id"));
        c3_2.setCellValueFactory(new PropertyValueFactory("date_out"));
        c3_3.setCellValueFactory(new PropertyValueFactory("date_in"));
        ObservableList<Books_expired> books_expireds = connectDB.findaBooks_expireds(reader_info.getReader_id(),"");
        tableView3.setItems(books_expireds);
        getArrears(books_expireds);
    }

    public double getArrears(ObservableList<Books_expired> books_expireds)
    {
        double arrears = 0;
        String date_in;
        for(int i = 0; i < connectDB.getLineCount(reader_info.getReader_id()); i++)
        {
            date_in = books_expireds.get(i).getDate_in();
            try {
                arrears += (current_Date - dateFormat.parse(date_in).getTime())/(24*60*60*1000)*0.1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        connectDB.setArrears(reader_info.getReader_id(), arrears);

        return arrears;
    }
    //读者证挂失区域
    @FXML private Label output_sit5;
    public void LossReporting(ActionEvent actionEvent) {
        String reader_id = reader_info.getReader_id();
        String date = dateFormat.format(current_Date1);
        if(connectDB.lossReport(reader_id, date) == 1)
        {
            output_sit5.setText("挂失成功！");
        }
        else
        {
            output_sit5.setText("挂失失败！");
        }
    }

    //修改密码区域
    @FXML private TextField oldPW;
    @FXML private TextField newPW;
    @FXML private TextField newPWC;
    @FXML private Label loldPW;
    @FXML private Label lnewPW;
    @FXML private Label lnewPWC;
    @FXML private Label changePWsit;
    public void changePW(ActionEvent actionEvent)
    {
        oldPW.setVisible(true);
        loldPW.setVisible(true);
        newPW.setVisible(true);
        lnewPW.setVisible(true);
        newPWC.setVisible(true);
        lnewPWC.setVisible(true);
        String OldPW = loldPW.getText();
        String NewPW = newPW.getText();
        String NewPWC = newPWC.getText();
        if(!NewPW.equals(NewPWC))
        {
            changePWsit.setTextFill(Color.web("#FF0000"));
            changePWsit.setText("两次新密码不一致");
            return;
        }
        else if(!OldPW.equals(connectDB.getAdministrator_info().getPassword()))
        {
            changePWsit.setTextFill(Color.web("#FF0000"));
            changePWsit.setText("旧密码错误");
            return;
        }
        connectDB.ChangePW(NewPW);
        changePWsit.setTextFill(Color.web("#00FF00"));
        changePWsit.setText("密码更改成功");
        return;
    }

    //返回登录界面区域
    @FXML private Button exit;
    public void setExit(ActionEvent actionEvent)
    {
        exit.getScene().getWindow().hide();
        //open the new stage
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/LMS/view/Login.fxml"));
            System.out.println("Magager.xml quit!");
            System.out.println("Login.xml loaded!");
            Scene scene = new Scene(root);
            stage.setTitle("图书管理系统");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
