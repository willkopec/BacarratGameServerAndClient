package BacarratClient.src.main.java;

import java.util.ArrayList;

public class BacarratGame {
	//info needed for each game
	ArrayList<Card> playerHand = new ArrayList<Card>();
	ArrayList<Card> bankerHand = new ArrayList<Card>();
	public BacarratDealer theDealer = new BacarratDealer();
	double currentBet;
	double totalWinnings;
	double currentBalance = 10000;
	
	boolean chosePlayer;
	boolean choseBanker;
	boolean choseTie;
	boolean playerWon = false;
	
	static int portNumber = 5555;
	static String ipAddress = "127.0.0.1";
	
	//evalWinnings: used to show the winnings of the player depending on their
	//choice and whether or not they won that game
	public double evaluateWinnings() {
		double winnings = 0;
		
		if(playerWon == true && chosePlayer == true) {
			winnings = currentBet;
		} else if (playerWon == true && choseTie == true){
			winnings = currentBet;
		} else if (playerWon == false && choseBanker == true) {
			winnings = currentBet;
		} else if(playerWon == true && choseBanker == true){
			winnings = (currentBet * -1.0);
		}
		
		return winnings;
	}
}
