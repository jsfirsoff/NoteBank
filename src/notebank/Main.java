package notebank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/notebank.fxml"));
        primaryStage.setTitle("NoteBank v0.1");
        
        
       // Button button1 = new Button();
        
      //  VBox vbox = new VBox(button1);
        
       // root.getChildrenUnmodifiable().add(vbox);
        
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("view/stylesheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
	public static void main(String[] args) {
		launch(args);

	}

}
