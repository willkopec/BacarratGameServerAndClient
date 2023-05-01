package JavaFX_GUIServer_Maven.src.main.java;

import java.util.ArrayList;

public class BacarratGame {
	ArrayList<Card> playerHand = new ArrayList<Card>();
	ArrayList<Card> bankerHand = new ArrayList<Card>();
	BacarratDealer theDealer = new BacarratDealer();
	double currentBet;
	double totalWinnings;
	double currentBalance = 10000;
	
	boolean chosePlayer;
	boolean choseBanker;
	boolean choseTie;
	boolean playerWon = false;
	
	static int portNumber = 5555;
	static String ipAddress = "127.0.0.1";
	
	public double evaluateWinnings() {
		double winnings;
		
		if(playerWon == true && chosePlayer == true) {
			winnings = currentBet;
		} else if (playerWon == true && choseTie == true){
			winnings =  7 * currentBet;
		} else if (playerWon && choseBanker == true) {
			winnings = currentBet - (0.05 * currentBet);
		} else {
			winnings = -currentBet;
		}
		
		return winnings;
	}
}
