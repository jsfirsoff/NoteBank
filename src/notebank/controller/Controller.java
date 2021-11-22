package notebank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private TextArea title;
    
    @FXML
    private Button toggleBtn;
    
    @FXML
    private TextArea text;
    
    @FXML
    private TextField tags;
    
    @FXML
    private FlowPane tagBox;
    
    private String exitIconPath = "/src/notebank/view/media/exit3.png";

    public void initialize() {
        title.setText("Title here");
        text.setText("note here");
    }
     
    @FXML
    private void printOutput() 
    {
        System.out.println("Sup");
    }
    
    @FXML
    private void toggleSearch() 
    {
        System.out.println("Toggle Search");
        String btntext = toggleBtn.getText();
        
        if (btntext.equals(">")) {
        	toggleBtn.setText("<");
        }
        else {
        	toggleBtn.setText(">");
        }
    }
    
    @FXML
    private void onEnter(ActionEvent event) throws FileNotFoundException {
    	String text = tags.getText();
    	System.out.println("new tag: " + text);
    	createTag(text);
    	
    	//add to database with separate database service class?
    	
    	tags.clear();
    }
    
    private void createTag(String text) throws FileNotFoundException {

    	Label label = new Label(text);
    	label.getStyleClass().add("tag");
    	
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("tag selected");
				
				//search for selected tag
			}
    		
    	});
    	
    	
    	label.setGraphic(createButton(exitIconPath, label));
    	
    	label.setContentDisplay(ContentDisplay.RIGHT);
    	
    	int len = tagBox.getChildren().size();
    	tagBox.getChildren().add(len-1, label);
    }
    
    private ImageView resize(ImageView image, double size) {
    	image.setPreserveRatio(true);
    	image.setFitHeight(size);
    	
    	return image;
    }
    
    private Button createButton(String path, Label label) throws FileNotFoundException {
    	Image image = new Image(new FileInputStream(new File(System.getProperty("user.dir"), path)));
    	
    	ImageView icon = new ImageView(image);
    	
    	
    	Button button = new Button();
    	button.setGraphic(resize(icon, 15));
    	
    	button.setId("tagDelBtn");
    	
    	button.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent event) {
    			
    			tagBox.getChildren().remove(label);
    			
    			System.out.println("clicked");
    			
    			//remove tag from database
            }
    	});
    	
    	return button;
    }
}
