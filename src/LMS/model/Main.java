package LMS.model;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LMS/view/Login.fxml"));
            stage.setTitle("登陆");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void main(String[] args)
    {
        launch(args);
    }

}
