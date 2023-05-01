package BacarratClient.src.main.java;

//https://www.vegasslotsonline.com/table-games/baccarat/

import java.util.HashMap;

import JavaFX_GUIServer_Maven.src.main.java.BacarratInfo;
//import BacarratGame.BacarratDealer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
//import BacarratGame;

public class GuiClient extends Application{

	
	TextField c2, ipAddressField;
	Button serverChoice,clientChoice, b1, b2, b3, b4, betButton, playAgainButton, setIP;
	Button playGame;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox, playingGame, bettingVBox;
	Scene startScene;
	BorderPane startPane;
	Client clientConnection;
	Text welcomeText, chooseWhoWins, chooseBetAmount, resultsText, setIpText;
	EventHandler<ActionEvent> clickPlay, chooseDealer, chooseTie, choosePlayer;
	ListView<String> gameResults, finalGameResults;
	BacarratInfo thisGameInfo = new BacarratInfo();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		
		// ---- CLIENT ITEMS ---- //
		primaryStage.setTitle("Bacarrat Client BY: William Kopec & Amaan Hussain");
		
		gameResults = new ListView<String>();
		c2 = new TextField();
		ipAddressField = new TextField();
		b2 = new Button("Dealer");
		b3 = new Button("Tie");
		b4 = new Button("Player");
		setIP = new Button("Set IP");
		betButton = new Button("Bet!");
		playGame = new Button("Play Bacarrat!");
		playAgainButton = new Button("Play Bacarrat Again!");
		chooseWhoWins = new Text("What would you like to bet on?");
		welcomeText = new Text("Welcome to Baccarat!");
		chooseBetAmount = new Text("Amount to bet:");
		resultsText = new Text("Game done... RESULTS:");
		setIpText = new Text("Server IP: ");
				  	//----------END OF CLIENT ITEMS---------//
		
		//--------------------------------------------------------------------------//
					
					//-------------Button Handlers------------//
		//ClickPlay - Connects the clients and waits to add items sent from the server to gameResults listView
		clickPlay = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				primaryStage.setScene(sceneMap.get("initBettingScreen"));
				clientConnection = new Client(data->{
					Platform.runLater(()->{gameResults.getItems().add(data.toString());
					});
				});
				clientConnection.start();
			}
		};
		
		//Choose buttons: sets the appropriate choose booleans and sets them in the gameInfo
		chooseDealer = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				thisGameInfo.choseTie = false;
				thisGameInfo.choseBanker = true;
				thisGameInfo.chosePlayer = false;
			}
		};
		
		choosePlayer = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				thisGameInfo.choseTie = false;
				thisGameInfo.choseBanker = false;
				thisGameInfo.chosePlayer = true;
			}
		};
		
		chooseTie = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				thisGameInfo.choseTie = true;
				thisGameInfo.choseBanker = false;
				thisGameInfo.chosePlayer = false;
			}
		};
		
		//Bet Button: sets the currentbet in the gameInfo to the clients bet and sends that gameInfo object to the server
		//then goes to the playScreen where the results of that game are shown to the client
		betButton.setOnAction(e->{
			thisGameInfo.currentBet = Integer.parseInt(c2.getText());
			c2.clear();
			thisGameInfo.isPlayingTheGame = true;
			clientConnection.send(thisGameInfo);
			//clientConnection.recieveServerGameInfo(data);
			primaryStage.setScene(sceneMap.get("playScreen"));
		});

		//Play Again: Resets all the possible changed variables from previous game and resends the GameInfo object to the server
		//they then go to the betting screen where they can rebet and change the variables for the new gameInfo..
		playAgainButton.setOnAction(e->{
			thisGameInfo.currentBet = 0;
			thisGameInfo.choseBanker = false;
			thisGameInfo.chosePlayer = false;
			thisGameInfo.choseTie = false;
			thisGameInfo.isPlayingAgain = true;
			thisGameInfo.isPlayingTheGame = false;
			clientConnection.send(thisGameInfo);
			primaryStage.setScene(sceneMap.get("initBettingScreen"));
		});
		
		//SetIP button: used to change the IP address of the server that the client wants to connect to.
		setIP.setOnAction(e->{
			BacarratGame.ipAddress = ipAddressField.getText();
			ipAddressField.clear();
			//primaryStage.setScene(sceneMap.get("playScreen"));
		});
					
		//--------END OF BUTTON HANDLERS----------//
		
		//----  Arrange Client buttons/Text/lists  -----//
		b2.setOnAction(chooseDealer);
		b3.setOnAction(chooseTie);
		b4.setOnAction(choosePlayer);
		
		c2.setMaxWidth(225);
		c2.setTranslateX(195);
		c2.setTranslateY(90);
		ipAddressField.setMaxWidth(250);
		ipAddressField.setTranslateX(250);
		ipAddressField.setTranslateY(130);
		
		b2.setTranslateX(200);
		b2.setTranslateY(280);
		b2.setMinSize(120, 50);
		b3.setTranslateX(350);
		b3.setTranslateY(230);
		b3.setMinSize(120, 50);
		b4.setTranslateX(500);
		b4.setMinSize(120, 50);
		b4.setTranslateY(180);
		betButton.setTranslateX(430);
		betButton.setTranslateY(65);
		c2.setPromptText("Enter the amount to bet");
		
		
		playGame.setMinHeight(75);
		playGame.setMinWidth(120);
		playGame.setTranslateX(290);
		playGame.setTranslateY(115);
		playGame.setOnAction(clickPlay);
		playAgainButton.setMinHeight(75);
		playAgainButton.setMinWidth(120);
		playAgainButton.setTranslateX(450);
		playAgainButton.setTranslateY(300);
		setIP.setTranslateY(-26);
		setIP.setTranslateX(500);
		
		welcomeText.setStyle("-fx-font: 24 arial;");
		welcomeText.setTranslateX(250);
		chooseWhoWins.setStyle("-fx-font: 27 arial;");
		chooseWhoWins.setTranslateX(230);
		chooseBetAmount.setStyle("-fx-font: 15 arial;");
		chooseBetAmount.setTranslateX(200);
		resultsText.setStyle("-fx-font: 24 arial;");
		setIpText.setStyle("-fx-font: 16 arial;");
		setIpText.setTranslateX(170);
		setIpText.setTranslateY(160);
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("initBettingScreen", initBettingScreen());
		sceneMap.put("playScreen", playTheGameScreen());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		clientBox = new VBox(10, setIpText, ipAddressField, welcomeText, playGame, setIP);
		clientBox.setStyle("-fx-background-color: blue");
		startScene = new Scene(clientBox, 700,500);
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}

	//the betting screen where the client is able to play the game...
	public Scene initBettingScreen() {
		bettingVBox = new VBox(0, chooseWhoWins, b2, b3, b4, c2, betButton, chooseBetAmount, gameResults);
		bettingVBox.setStyle("-fx-background-color: blue");
		return new Scene(bettingVBox, 800, 600);
	}
	
	//The results screen where the results of the game are shown and the client is asked if they want to play again
	public Scene playTheGameScreen() {
		finalGameResults = gameResults;
		finalGameResults.setTranslateY(-50);
		finalGameResults.setMaxSize(400, 200);
		playingGame = new VBox(0, resultsText, playAgainButton, finalGameResults);
		playingGame.setStyle("-fx-background-color: blue");
		return new Scene(playingGame, 700, 500);
	}

}
