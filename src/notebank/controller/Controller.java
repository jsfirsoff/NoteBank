package notebank.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private void onEnter(ActionEvent event) {
    	String text = tags.getText();
    	System.out.println("new tag: " + text);
    	createTag(text);
    	tags.clear();
    }
    
    private void createTag(String text) {
    	//todo: create tag
    	Label label = new Label(text);
    	label.getStyleClass().add("tag");
    	
    	int len = tagBox.getChildren().size();
    	tagBox.getChildren().add(len-1, label);
    }
}
