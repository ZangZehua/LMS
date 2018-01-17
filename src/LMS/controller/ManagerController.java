package LMS.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import LMS.model.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    @FXML private Label welcome;
    @FXML private Button exit;

    private ConnectDB connectDB = new ConnectDB();
    private Administrator_info administrator_info = connectDB.getAdministrator_info();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long current_Date = System.currentTimeMillis();//new日期对象
    Date current_Date1 = new Date(current_Date);//转换提日期输出格式
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText(administrator_info.getAdmin_name());
        refresh();
    }

    public void refresh() {
        findAbook();
        setBooks_borrowed();
        setBook_lost();
        findLoss();
        findAbook_tb();
        findBorrow();
        findAreader();
        setBooks_expired();
        pc_choose1();
    }

    //查看过期图书区域
    @FXML private TableView tableView21;
    @FXML private TableColumn c211; @FXML private TableColumn c221; @FXML private TableColumn c231; @FXML private TableColumn c241; @FXML private TableColumn c251;
    public void setBooks_expired()
    {
        c211.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c221.setCellValueFactory(new PropertyValueFactory("book_id"));
        c231.setCellValueFactory(new PropertyValueFactory("date_out"));
        c241.setCellValueFactory(new PropertyValueFactory("date_in"));
        c251.setCellValueFactory(new PropertyValueFactory("isLost"));

        ObservableList<Books_expired> books_expireds = connectDB.findaBooks_expireds("","");
        tableView21.setItems(books_expireds);
    }
    //查看已借图书汇总区域
    @FXML private TableView<Books_borrowed> tableView2;
    @FXML private TableColumn c21; @FXML private TableColumn c22; @FXML private TableColumn c23; @FXML private TableColumn c24; @FXML private TableColumn c25;
    public void setBooks_borrowed() {
        c21.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c22.setCellValueFactory(new PropertyValueFactory("book_id"));
        c23.setCellValueFactory(new PropertyValueFactory("date_out"));
        c24.setCellValueFactory(new PropertyValueFactory("date_in"));
        c25.setCellValueFactory(new PropertyValueFactory("isLost"));

        ObservableList<Books_borrowed> books_borrowed = connectDB.findabook_borrowed("","");
        tableView2.setItems(books_borrowed);
    }

    @FXML private TableView<Books_lost> tableView3;
    @FXML private TableColumn<Books_lost, String> c31; @FXML private TableColumn<Books_lost, String>  c32; @FXML private TableColumn<Books_lost, String>  c33; @FXML private TableColumn<Books_lost, String>  c34;
    public void setBook_lost() {
        c31.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c32.setCellValueFactory(new PropertyValueFactory("book_id"));
        c33.setCellValueFactory(new PropertyValueFactory("lost_date"));

        ObservableList<Books_lost> books_losts = connectDB.findBooks_losts();
        tableView3.setItems(books_losts);
    }

    //图书入库区域
    @FXML private TextField input_bookid6;
    @FXML private TextField input_bookname6;
    @FXML private TextField input_author6;
    @FXML private TextField input_publishing6;
    @FXML private TextField input_price6;
    @FXML private TextField input_numin6;
    @FXML private MenuButton choseCate6;
    @FXML private Label datein_6;
    @FXML private Label sit6;
    public void newBook(ActionEvent actionEvent)
    {
        datein_6.setText(dateFormat.format(current_Date1));
        sit6.setTextFill(Color.web("#FF0000"));
        String bookid = input_bookid6.getText();
        System.out.println(bookid);
        if(bookid.equals("Book_ID"))
        {
            sit6.setText("未输入图书编号");
        }
        String bookname = input_bookname6.getText();
        if(bookname.equals("Book_Name"))
        {
            sit6.setText("未输入书名");
        }
        String author = input_author6.getText();
        if(author.equals("Author"))
        {
            sit6.setText("未输入作者");
        }
        String publishing = input_publishing6.getText();
        if(publishing.equals("Publishing"))
        {
            sit6.setText("未输入出版社");
        }
        String pricestring = input_price6.getText();
        if(pricestring.equals("Price"))
        {
            sit6.setText("未输入单价");
        }
        double price = Double.parseDouble(pricestring);
        String numinstring = input_numin6.getText();
        if(numinstring.equals("0"))
        {
            sit6.setText("未输入入库数量");
        }
        int numin = Integer.parseInt(numinstring);
        String category = choseCate6.getText();
        if(category.equals("0.00"))
        {
            sit6.setText("未选择类别");
        }
        String date_in = datein_6.getText();
        connectDB.Book_in(bookid,bookname,author,publishing,category,price,date_in,numin,0,0);
        sit6.setTextFill(Color.web("#00FF00"));
        sit6.setText("图书入库成功");
    }
    public void setC61(ActionEvent actionEvent)
    {
        choseCate6.setText("1");
    }
    public void setC62(ActionEvent actionEvent)
    {
        choseCate6.setText("2");
    }
    public void setC63(ActionEvent actionEvent)
    {
        choseCate6.setText("3");
    }
    public void setC64(ActionEvent actionEvent)
    {
        choseCate6.setText("4");
    }
    public void setC65(ActionEvent actionEvent)
    {
        choseCate6.setText("5");
    }
    public void setC66(ActionEvent actionEvent)
    {
        choseCate6.setText("6");
    }
    public void setC67(ActionEvent actionEvent)
    {
        choseCate6.setText("7");
    }
    public void setC68(ActionEvent actionEvent)
    {
        choseCate6.setText("8");
    }
    public void setC69(ActionEvent actionEvent)
    {
        choseCate6.setText("9");
    }
    public void setC610(ActionEvent actionEvent)
    {
        choseCate6.setText("10");
    }

    //修改密码区域
    @FXML private Button changePW;
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
        String OldPW = oldPW.getText();
        String NewPW = newPW.getText();
        String NewPWC = newPWC.getText();
        if(!NewPW.equals(NewPWC))
        {
            changePWsit.setTextFill(Color.web("#FF0000"));
            changePWsit.setText("两次新密码不一致");
            return;
        }
        else if(!oldPW.equals(connectDB.getAdministrator_info().getPassword()))
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

    //查找图书区域
    @FXML private TextField bookid5;
    @FXML private TextField bookname5;
    @FXML private TextField author5;
    @FXML private TableView<Books> tableView5;
    @FXML private TableColumn<Books,String> c51; @FXML private TableColumn<Books,String> c52; @FXML private TableColumn<Books,String> c53; @FXML private TableColumn<Books,String> c54; @FXML private TableColumn<Books,String> c55;
    @FXML private TableColumn<Books,Double> c56; @FXML private TableColumn<Books,String> c57; @FXML private TableColumn<Books,Integer> c58; @FXML private TableColumn<Books,Integer> c59; @FXML private TableColumn<Books,Integer> c510;
    @FXML private TableColumn<Books,Integer> c511;
    public void findAbook()
    {
        String bookid = bookid5.getText();
        String bookname = bookname5.getText();
        String author = author5.getText();
        System.out.println(bookid + " " + bookname + " " + author);
        c51.setCellValueFactory(new PropertyValueFactory("book_id"));
        c52.setCellValueFactory(new PropertyValueFactory("book_name"));
        c53.setCellValueFactory(new PropertyValueFactory("author"));
        c54.setCellValueFactory(new PropertyValueFactory("publishing"));
        c55.setCellValueFactory(new PropertyValueFactory("book_category"));
        c56.setCellValueFactory(new PropertyValueFactory("price"));
        c57.setCellValueFactory(new PropertyValueFactory("data_in"));
        c58.setCellValueFactory(new PropertyValueFactory("number_in"));
        c59.setCellValueFactory(new PropertyValueFactory("number_out"));
        c510.setCellValueFactory(new PropertyValueFactory("number_loss"));
        c511.setCellValueFactory(new PropertyValueFactory("stock"));

        ObservableList<Books> books = connectDB.findabook(bookid, bookname, author);
        tableView5.setItems(books);
    }
    //借书区域
    @FXML private TextField bookid7;
    @FXML private TextField bookname7;
    @FXML private TextField author7;
    @FXML private TableView tableView7 ;
    @FXML private TableColumn c71; @FXML private TableColumn c72; @FXML private TableColumn c73; @FXML private TableColumn c74; @FXML private TableColumn c75;
    @FXML private TableColumn c76; @FXML private TableColumn c77; @FXML private TableColumn c78;
    @FXML private Label output_sit7;
    @FXML private Label output_bookid7;
    @FXML private Label output_bookname7;
    @FXML private Label output_author7;
    @FXML private Label output_category7;
    @FXML private Label output_stock7;
    @FXML private TextField input_readerid7;
    @FXML private Label date_in7;
    @FXML private Label date_out7;
    public void findAbook_tb()
    {
        String bookid = bookid7.getText();
        String bookname = bookname7.getText();
        String author = author7.getText();
        System.out.println(bookid + " " + bookname + " " + author);
        c71.setCellValueFactory(new PropertyValueFactory("book_id"));
        c72.setCellValueFactory(new PropertyValueFactory("book_name"));
        c73.setCellValueFactory(new PropertyValueFactory("author"));
        c74.setCellValueFactory(new PropertyValueFactory("publishing"));
        c75.setCellValueFactory(new PropertyValueFactory("book_category"));
        c76.setCellValueFactory(new PropertyValueFactory("price"));
        c77.setCellValueFactory(new PropertyValueFactory("data_in"));
        c78.setCellValueFactory(new PropertyValueFactory("stock"));

        ObservableList<Books> books = connectDB.findabook(bookid, bookname, author);
        tableView7.setItems(books);
        tableView7.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView7.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int index = tableView7.getSelectionModel().getSelectedIndex();
                output_bookid7.setText(books.get(index).getBook_id());
                output_bookname7.setText(books.get(index).getBook_name());
                output_author7.setText(books.get(index).getAuthor());
                output_category7.setText(books.get(index).getBook_category());
                output_stock7.setText("" + books.get(index).getStock());
            }
        });
    }
    public void borrow(ActionEvent actionEvent)
    {
        String readerid = input_readerid7.getText();
        String bookid = output_bookid7.getText();
        if(readerid.equals(""))
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("请输入读者证编号！");
            return;
        }
        else if(bookid.equals(""))
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("请选择借阅图书！");
            return;
        }
        if(connectDB.checkReaderReport(readerid) == 1)
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("本读者证已挂失！");
            return;
        }
        System.out.println(readerid + " " + bookid);
        int booksHave = connectDB.checkBooksHave(readerid);
        if(booksHave == -1)
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("读者未办证！");
            return;
        }
        int level = connectDB.checkLevel(readerid);
        int days = connectDB.getLevelDays(level);
        int nums = connectDB.getLevelNums(level);
        int stock = Integer.parseInt(output_stock7.getText());
        if(nums <= booksHave)
        {
            System.out.println(nums + " " + booksHave);
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("读者借阅书数已达上限！");
            return;
        }
        if(stock <=0 )
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("本书库存不足！");
            return;
        }
        //可以借书了
        long borrow_date_in = current_Date + (long)days * 1000 * 60 * 60 * 24;
        Date BorrowDateIn = new Date(borrow_date_in);
        String date_out = dateFormat.format(current_Date1);
        String date_in = dateFormat.format(BorrowDateIn);
        if(connectDB.borrowBook(readerid, bookid, date_out, date_in))
        {
            date_out7.setText(dateFormat.format(current_Date1));
            date_in7.setText(dateFormat.format(BorrowDateIn));
            output_sit7.setTextFill(Color.web("#00FF00"));
            output_sit7.setText("借阅成功！");
            refresh();
        }
        else
        {
            output_sit7.setTextFill(Color.web("#FF0000"));
            output_sit7.setText("已经借阅过本书！");
        }

        return;
    }

    //还书区域
    @FXML private TextField input_readerid8;
    @FXML private TextField input_bookid8;
    @FXML private TableView tableView8;
    @FXML private TableColumn c81; @FXML private TableColumn c82; @FXML private TableColumn c83; @FXML private TableColumn c84; @FXML private TableColumn c85;
    @FXML private Label output_sit8;
    @FXML private Label output_readerid8;
    @FXML private Label output_bookid8;
    @FXML private Label output_date_out8;
    @FXML private Label output_date_in8;
    public void findBorrow()
    {
        String reader_id = input_readerid8.getText();
        String book_id = input_bookid8.getText();

        c81.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c82.setCellValueFactory(new PropertyValueFactory("book_id"));
        c83.setCellValueFactory(new PropertyValueFactory("date_out"));
        c84.setCellValueFactory(new PropertyValueFactory("date_in"));
        c85.setCellValueFactory(new PropertyValueFactory("isLost"));

        ObservableList<Books_borrowed> books_borrowed = connectDB.findabook_borrowed(reader_id, book_id);
        tableView8.setItems(books_borrowed);
        tableView8.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView8.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int index = tableView8.getSelectionModel().getSelectedIndex();
                output_readerid8.setText(books_borrowed.get(index).getReader_id());
                output_bookid8.setText(books_borrowed.get(index).getBook_id());
                output_date_out8.setText(books_borrowed.get(index).getDate_out());
                output_date_in8.setText(books_borrowed.get(index).getDate_in());
            }
        });
    }
    public void returnbook(ActionEvent actionEvent)
    {
        long dueDays;
        Date date_in1;
        double arrears = 0;
        String reader_id = output_readerid8.getText();
        String book_id = output_bookid8.getText();
        String date_in = output_date_in8.getText();
        try {
            date_in1 = dateFormat.parse(date_in);
            connectDB.returnBook(reader_id, book_id);
            refresh();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //读者证挂失操作区域
    @FXML private TableView tableView4;
    @FXML private TableColumn c41;@FXML private TableColumn c42;
    @FXML private TextField input_readerid4;
    @FXML private Label output_readerid4;
    @FXML private Label output_lossdate4;
    @FXML private Label output_sit4;
    public void findLoss()
    {
        String reader_id = input_readerid4.getText();

        c41.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c42.setCellValueFactory(new PropertyValueFactory("loss_date"));

        ObservableList<Loss_report> loss_reports = connectDB.findLoss_report(reader_id);
        tableView4.setItems(loss_reports);
        tableView4.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int index = tableView4.getSelectionModel().getSelectedIndex();
                output_readerid4.setText(loss_reports.get(index).getReader_id());
                output_lossdate4.setText(loss_reports.get(index).getLoss_date());
            }
        });
    }
    public void cancelReport(ActionEvent actionEvent)
    {
        String reader_id = output_readerid4.getText();
        if(connectDB.cancelreport(reader_id) == 1)
        {
            output_sit4.setTextFill(Color.web("#00FF00"));
            output_sit4.setText("取消挂失成功！");
            findLoss();
        }
        else
        {
            output_sit4.setTextFill(Color.web("#FF0000"));
            output_sit4.setText("取消挂失失败！");
        }
    }

    //办理读者证区域
    @FXML private Label output_readerid9;
    @FXML private Label output_level;
    @FXML private Label output_date9;
    @FXML private Label output_sit9;
    @FXML private TextField input_readername9;
    @FXML private TextField input_contect9;
    @FXML private TextField input_phone9;
    @FXML private TextField input_cardID9;
    @FXML private TextField input_birthday9;
    @FXML private TextField input_pw9;
    @FXML private TextField input_pwc9;
    @FXML private MenuButton input_sex9;
    @FXML private MenuButton input_cardtype9;
    public void newCard(ActionEvent actionEvent)
    {
        output_date9.setText(dateFormat.format(current_Date1));
        String reader_name = input_readername9.getText();
        String contect = input_contect9.getText();
        String phone = input_phone9.getText();
        String sex = input_sex9.getText();
        String cardtype = input_cardtype9.getText();
        int cardID = Integer.parseInt(input_cardID9.getText());
        String birthday = input_birthday9.getText();
        String password = input_pw9.getText();
        String passwordc = input_pwc9.getText();
        String date_in = output_date9.getText();
        output_readerid9.setText(input_cardID9.getText());
        String reader_id = output_readerid9.getText();
        if (reader_name.equals(""))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("未输入姓名");
            return;
        }
        else if (input_cardID9.equals(""))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("未输入卡号");
            return;
        }
        else if (birthday.equals(""))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("未输入生日");
            return;
        }
        else if (sex.equals(""))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("未选择性别");
            return;
        }
        else if (password.equals(""))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("未输入密码");
            return;
        }
        else if (!password.equals(passwordc))
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("两次密码输入不匹配");
            return;
        }
        if (connectDB.newCard(reader_id,reader_name,sex,birthday,contect,phone,cardtype,cardID,1,date_in,password) == 1)
        {
            output_sit9.setTextFill(Color.web("#00FF00"));
            output_sit9.setText("办卡成功\n请牢记读者证编号和密码");
            return;
        }
        else
        {
            output_sit9.setTextFill(Color.web("#FF0000"));
            output_sit9.setText("办卡时错误");
            return;
        }
    }
    public void male(ActionEvent actionEvent)
    {
        input_sex9.setText("男");
    }
    public void female()
    {
        input_sex9.setText("女");
    }
    public void eCard()
    {
        input_cardtype9.setText("一卡通");
    }

    //查看所有用户区域
    @FXML private TextField input_readerid10;
    @FXML private TextField input_readername10;
    @FXML private TableView<Reader_info> tableView10;
    @FXML private TableColumn<Reader_info, String> c10_1; @FXML private TableColumn<Reader_info, String> c10_2; @FXML private TableColumn<Reader_info, String> c10_3; @FXML private TableColumn<Reader_info, String> c10_4; @FXML private TableColumn<Reader_info, String> c10_5;
    @FXML private TableColumn<Reader_info, String> c10_6; @FXML private TableColumn<Reader_info, String> c10_7; @FXML private TableColumn<Reader_info, Integer> c10_8; @FXML private TableColumn<Reader_info, Integer> c10_9; @FXML private TableColumn<Reader_info, String> c10_10;
    public void findAreader()
    {
        String reader_id = input_readerid10.getText();
        String reader_name = input_readername10.getText();

        c10_1.setCellValueFactory(new PropertyValueFactory("reader_id"));
        c10_2.setCellValueFactory(new PropertyValueFactory("reader_name"));
        c10_3.setCellValueFactory(new PropertyValueFactory("reader_sex"));
        c10_4.setCellValueFactory(new PropertyValueFactory("birthday"));
        c10_5.setCellValueFactory(new PropertyValueFactory("number"));
        c10_6.setCellValueFactory(new PropertyValueFactory("phone"));
        c10_8.setCellValueFactory(new PropertyValueFactory("cardID"));
        c10_9.setCellValueFactory(new PropertyValueFactory("level"));
        c10_10.setCellValueFactory(new PropertyValueFactory("date_in"));
        c10_7.setCellValueFactory(new PropertyValueFactory("cardtype"));

        ObservableList<Reader_info> reader_infos = connectDB.findReader_infos(reader_id, reader_name);

        tableView10.setItems(reader_infos);
    }

    //查看统计部分
    @FXML private PieChart pieChart;
    @FXML private Label pc_cate;
    @FXML private Label pc_number;
    @FXML private Label pc_rate;
    public void pc_choose1()
    {
        int booksNum = connectDB.getAllBooksNum();
        pieChart.setData(connectDB.getBooks_info());
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            pc_cate.setText("种类：" + data.getName());
                            pc_number.setText("数量：" + (int)data.getPieValue());
                            pc_rate.setText("占比：" + decimalFormat.format( data.getPieValue()/booksNum) + "%");
                        }
                    });
        }
    }
    public void pc_choose2()
    {
        int borrowedBooksNum = connectDB.getAllBorrowedBooksNum();
        pieChart.setData(connectDB.getBorrowed_books_info());
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            pc_cate.setText("种类：" + data.getName());
                            pc_number.setText("数量：" + (int)data.getPieValue());
                            pc_rate.setText("占比：" + decimalFormat.format( data.getPieValue()/borrowedBooksNum) + "%");
                        }
                    });
        }
    }
    public void pc_choose3()
    {
        int readerSexNum = connectDB.getAllReadersNum();
        pieChart.setData(connectDB.getReaders_Sex_info());
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            pc_cate.setText("性别：" + data.getName());
                            pc_number.setText("数量：" + (int)data.getPieValue());
                            pc_rate.setText("占比：" + decimalFormat.format( data.getPieValue()/readerSexNum) + "%");
                        }
                    });
        }
    }

    //返回登录界面区域
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
