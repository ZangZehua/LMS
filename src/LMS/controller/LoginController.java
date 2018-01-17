package LMS.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import LMS.model.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button log_in;
    @FXML
    private Button findpw;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label exception;

    private String username_reader = null;
    private String password_reader = null;

    public void ReaderLogin()
    {
        exception.setTextFill(Color.web("#00EE00"));
        exception.setText("登陆成功");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log_in.getScene().getWindow().hide();
        //open the new stage
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/LMS/view/Reader.fxml"));
            System.out.println("Reader.xml loaded!");
            Scene scene = new Scene(root);
            stage.setTitle("图书管理系统");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ManagerLogin()
    {
        exception.setTextFill(Color.web("#00EE00"));
        exception.setText("登陆成功");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log_in.getScene().getWindow().hide();
        //open the new stage
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/LMS/view/Manager.fxml"));
            System.out.println("Magager.xml loaded!");
            Scene scene = new Scene(root);
            stage.setTitle("图书管理系统");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // When user click on myButton
    // this method will be called.
    public void Log_in(ActionEvent event) {
        ConnectDB connectDB = new ConnectDB();//connect to mysql

        username_reader = textField.getText();//get username on the screen
        password_reader = passwordField.getText();//get password on the screen

        System.out.println(username_reader + " " + password_reader);//test\\\

        if (connectDB.getUserInfo(username_reader, password_reader) == 0)//check the count and return the situation, the user is a reader
        {
            ReaderLogin();
        }
        else if (connectDB.getUserInfo(username_reader, password_reader) == 1)//check the count and return the situation, the user is a manager
        {
            ManagerLogin();
        }
        else if (connectDB.getUserInfo(username_reader, password_reader) == -2 || connectDB.getUserInfo(username_reader, password_reader) == -3) {
            exception.setTextFill(Color.web("#FF0000"));
            exception.setText("用户名或密码错误");
        }

    }

}
