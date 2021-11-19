package notebank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
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
    	tags.clear();
    }
    
    private void createTag(String text) throws FileNotFoundException {
    	//todo: create tag
    	Label label = new Label(text);
    	label.getStyleClass().add("tag");
    	
    	label.setGraphic(resize(createIcon(exitIconPath), 15));
    	
    	label.setContentDisplay(ContentDisplay.RIGHT);
    	
    	int len = tagBox.getChildren().size();
    	tagBox.getChildren().add(len-1, label);
    }
    
    private ImageView createIcon(String path) throws FileNotFoundException {
    	Image image = new Image(new FileInputStream(new File(System.getProperty("user.dir"), path)));
    	
    	ImageView icon = new ImageView(image);
    	
    	icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
            public void handle(MouseEvent arg0) {
              
    			FlowPane pane = (FlowPane) icon.getParent();
    			
    			pane.getChildren().remove(icon);
    			System.out.println("clicked");
    			//not working
             // Label label =(Label) icon.getParent();
              
            }
    	});
    	
    	return icon;
    }
    
    private ImageView resize(ImageView image, double size) {
    	image.setPreserveRatio(true);
    	image.setFitHeight(size);
    	
    	return image;
    }
}
