package notebank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import notebank.model.Note;
import notebank.tools.SearchUtility;

public class Controller {

	@FXML
	private GridPane grid;
	
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
    
    @FXML
    private VBox vbox2;
    
    @FXML
    private ToggleButton searchTag, searchTitle, searchNote;
    
    @FXML
    private ListView<Note> searchResults;
  
    private final double SEARCH_MENU_COLLAPSED = 1000;
    
    private final double SEARCH_MENU_EXPANDED = 0;
    
    private String exitIconPath = "/src/notebank/view/media/exit3.png";
    
    private SearchUtility sUtil = new SearchUtility();

    public void initialize() {
    	toggleSearchMenu(SEARCH_MENU_COLLAPSED);
    }
     
    @FXML
    private void search() 
    {  
        ArrayList<Note> results = new ArrayList<Note>();
        
        
        if (searchTag.isSelected()) {
        	//get search results
        	System.out.println("Tag selected");
        }
        
        if (searchTitle.isSelected()) {
        	//get search results
        	System.out.println("Title selected");
        }
        
        if (searchNote.isSelected()) {
        	//get search results
        	System.out.println("Note selected");
        }
        
        //return results
       
        
        ObservableList<Note> resultList = FXCollections.observableArrayList(results);
        if (!resultList.isEmpty()) searchResults.setItems(resultList);
        
    }
    
    @FXML
    private void toggleSearch(ActionEvent event) 
    {
    	String btntext = toggleBtn.getText();
        
        if (btntext.equals("<")) {
        	toggleBtn.setText(">");
        	toggleSearchMenu(SEARCH_MENU_EXPANDED); //refactor this out
        }
        else {
        	toggleBtn.setText("<");
        	toggleSearchMenu(SEARCH_MENU_COLLAPSED); //refactor this out
        }
    }

    //do this better?
    private void toggleSearchMenu(double position) {
    	TranslateTransition translate = new TranslateTransition(Duration.millis(500), vbox2);  
    	translate.setToX(position);  
        translate.setCycleCount(1);  
        translate.setAutoReverse(true);  
        
        ParallelTransition animations = new ParallelTransition(translate);
    	
        animations.play();
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
