import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("GUI/Views/LogIn.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}