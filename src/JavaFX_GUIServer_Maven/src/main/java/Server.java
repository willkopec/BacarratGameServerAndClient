package JavaFX_GUIServer_Maven.src.main.java;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server extends BacarratGame{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	BacarratGame thisGame;
	BacarratInfo thisGameInfo;
	//BacarratDealer theDealer;
	
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(BacarratGame.portNumber);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("Client has connected to the Bacarrat server: " + "client #" + count);
				clients.add(c);
				c.start();
				count++;
				
			    }
			}//end of try
			catch(Exception e) {
				callback.accept("Server socket did not launch");
			}
			
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			//updateClients: Used to send information back to the client via the outputStream
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					}
					catch(Exception e) {
						callback.accept("Could not send clients data... something went wrong");
					}
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				//The Server-sided game-logic
				updateClients("A new client has joined the server! Welcome Client#" + count);
				thisGame = new BacarratGame();
				while(true) {
				    try {
				    	//recieve info from client
				    	thisGameInfo = (BacarratInfo) in.readObject();
				    	
				    	//set all info in theGame object to what was sent from gameInfo
				    	thisGame.choseBanker = thisGameInfo.choseBanker;
				    	thisGame.chosePlayer = thisGameInfo.chosePlayer;
				    	thisGame.choseTie = thisGameInfo.choseTie;
				    	thisGame.currentBet = thisGameInfo.currentBet;
				    	
				    	//Generate the deck and shuffle it..
				    	thisGame.theDealer.generateDeck();
				    	thisGame.theDealer.shuffleDeck();
				    	
				    	//check if the player is currently playing/has placed a bet
				    	if(thisGameInfo.isPlayingTheGame == true) {
				    		
				    		//deal the hands...
				    		thisGame.playerHand = thisGame.theDealer.dealHand();
					    	thisGame.bankerHand = thisGame.theDealer.dealHand();
					    	
					    	//Send client/Server info about the bet/display the deck information for both player and banker
					    	callback.accept("Client# " + count + ": has placed a bet for: $" + thisGame.currentBet  + thisGameInfo.displayBetDecision());
					    	updateClients("Client# " + count + ": has placed a bet for: $" + thisGame.currentBet  + thisGameInfo.displayBetDecision());
					    	callback.accept("Client#" + count + ": " + thisGameInfo.displayPlayerDeckMessage(thisGame.playerHand));
					    	callback.accept("Client#" + count + ": " + thisGameInfo.displayBankersDeckMessage(thisGame.bankerHand));
					    	updateClients("Client#" + count + ": " + thisGameInfo.displayPlayerDeckMessage(thisGame.playerHand));
					    	updateClients("Client#" + count + ": " + thisGameInfo.displayBankersDeckMessage(thisGame.bankerHand));
					    	
					    	//check if it is a natural win/Tie
					    	if((thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) == 8 || thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) == 9) && (thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == 8 || thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == 9) && thisGame.choseTie != true) {
					    		thisGame.playerWon = false;
					    		callback.accept("Client# " + count + " has lost the game. placed bet " + thisGameInfo.displayBetDecision() + " But it was a tie!");
					    		callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
					    		updateClients("Client# " + count + " has lost the game. placed bet " + thisGameInfo.displayBetDecision() + " But it was a tie!");
					    		thisGame.totalWinnings += thisGame.evaluateWinnings();
					    		//game done
					    	} else if ((thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) == 8 || thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) == 9) && (thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == 8 || thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == 9) && thisGame.choseTie == true) {
					    		thisGame.playerWon = true;
					    		callback.accept("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
					    		callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
								//game done
					    	}
					    	
					    	//This card is used to define the card drawn by the player: (if the player draws, these values will change,
					    	//if the player draws a card, otherwise it will stay this default value
					    	Card theCardDrawnByPlayer = new Card("", -1);
					    	
					    	//the players turn:
					    	if(BacarratGameLogic.evaluatePlayerDraw(thisGame.playerHand) == true) {
					    		theCardDrawnByPlayer = thisGame.theDealer.drawOne();
					    		thisGame.playerHand.add(theCardDrawnByPlayer);
					    		callback.accept("Client#" + count + ": " + "Drawing a card for the player...");
					    		callback.accept("Client#" + count + ": " + "Player drew a " + thisGameInfo.indentifyCard(theCardDrawnByPlayer));
					    		callback.accept("Client#" + count + ": " + thisGameInfo.displayPlayerDeckMessage(thisGame.playerHand));
					    		updateClients("Client#" + count + ": " + "Drawing a card for the player...");
					    		updateClients("Client#" + count + ": " + "Player drew a " + thisGameInfo.indentifyCard(theCardDrawnByPlayer));
					    		updateClients("Client#" + count + ": " + thisGameInfo.displayPlayerDeckMessage(thisGame.playerHand));
					    	}//else no more cards are dealt
					    	
					    	
					    	//the bankers turn:
					    	if (BacarratGameLogic.evaluateBankerDraw(thisGame.bankerHand, theCardDrawnByPlayer) == true) {
					    		Card theCardDrawn = thisGame.theDealer.drawOne();
					    		thisGame.bankerHand.add(theCardDrawn);
					    		callback.accept("Client#" + count + ": " + "Drawing a card for the banker...");
					    		callback.accept("Client#" + count + ": " + "Banker drew a " + thisGameInfo.indentifyCard(theCardDrawn));
					    		callback.accept("Client#" + count + ": " + thisGameInfo.displayBankersDeckMessage(thisGame.bankerHand));
					    		updateClients("Client#" + count + ": " + "Drawing a card for the banker...");
					    		updateClients("Client#" + count + ": " + "Banker drew a " + thisGameInfo.indentifyCard(theCardDrawn));
					    		updateClients("Client#" + count + ": " + thisGameInfo.displayBankersDeckMessage(thisGame.bankerHand));
					    	} else {
					    		//no more cards are drawn
					    	}
					    	
					    	//game is done for all possible outcomes, give the game results below...
							if(thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) > thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) && thisGameInfo.choseBanker == true) {
								thisGame.playerWon = true;
								callback.accept("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							} else if(thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) < thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) && thisGameInfo.chosePlayer == true) {
								thisGame.playerWon = true;
								callback.accept("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							} else if (thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == thisGameInfo.pointsForCurrentDeck(thisGame.playerHand) && thisGameInfo.choseTie == true) {
								thisGame.playerWon = true;
								callback.accept("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("Client# " + count + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) + " You have won!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							} else if (thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) == thisGameInfo.pointsForCurrentDeck(thisGame.playerHand)) {
								thisGame.playerWon = false;
								callback.accept("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							} else if(thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) > thisGameInfo.pointsForCurrentDeck(thisGame.playerHand)) {
								thisGame.playerWon = false;
								callback.accept("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							} else if(thisGameInfo.pointsForCurrentDeck(thisGame.bankerHand) < thisGameInfo.pointsForCurrentDeck(thisGame.playerHand)) {
								thisGame.playerWon = false;
								callback.accept("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								callback.accept("Total earnings from this game: " + thisGame.evaluateWinnings());
								updateClients("" + BacarratGameLogic.whoWon(thisGame.playerHand, thisGame.bankerHand) +" You have lost!");
								thisGame.totalWinnings += thisGame.evaluateWinnings();
							}
				    		
				    	}
				    	
				    	//if the player is playing again, send a message to the client and reset the gameInfo values to false because
				    	//the client will go to the initBettingScreen where these values will be reset
						if(thisGameInfo.isPlayingAgain == true) {
							callback.accept("Client#" + count + " is playing again! Their session Total winnings are: " + thisGame.totalWinnings);
							thisGameInfo.isPlayingAgain = false;
							thisGameInfo.isPlayingTheGame = false;
							updateClients("Client#" + count + " is playing again! Their session Total winnings are: " + thisGame.totalWinnings);
						}
						
				    	}
				    catch(Exception e) {
				    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
				    	updateClients("Someone left the SERVER!");
				    	e.printStackTrace();
				    	clients.remove(this);
				    	break;
				    } 
				}
				}//end of run
			
			
		}//end of client thread
}




	
	

	
