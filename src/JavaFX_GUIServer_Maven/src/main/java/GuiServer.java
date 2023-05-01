package JavaFX_GUIServer_Maven.src.main.java;

import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.text.Text;

public class GuiServer extends Application{

	
	TextField s1,s2,s3,s4, c1;
	Button turnOnServer,turnOffServer, setPortButton;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Text portText;
	String portNumber;
	
	ListView<String> listItems;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Bacarrat Server GUI By: William Kopec and Amaan Hussain");
		
		turnOnServer = new Button("Turn Bacarrat Server ON");
		turnOnServer.setMinWidth(500);
		
		turnOnServer.setOnAction(e->{
				primaryStage.setTitle("The Server Is currently ONLINE");
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						listItems.getItems().add(data.toString());
					});

				});
											
		});
		
		turnOffServer = new Button("Turn Bacarrat Server OFF");
		turnOffServer.setMinWidth(500);
		
		turnOffServer.setOnAction(e->{
				primaryStage.setTitle("The Server Is currently OFFLINE");
				
		});
		
		listItems = new ListView<String>();
		
		c1 = new TextField();
		setPortButton = new Button("Set Port");
		c1.setMaxSize(200, 40);
		c1.setTranslateX(220);
		c1.setTranslateY(-35);
		portText = new Text("Choose which port you'd like to listen on:");
		setPortButton.setTranslateX(440);
		setPortButton.setTranslateY(-15);
		
		setPortButton.setOnAction(e->{
			BacarratGame.portNumber = Integer.parseInt(c1.getText());
			c1.clear();
		});
		
		sceneMap = new HashMap<String, Scene>();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		clientBox = new VBox(0, portText,setPortButton, c1, pane, turnOnServer, turnOffServer);
		
		startScene = new Scene(clientBox, 500,400);
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}

}
