package JavaFX_GUIServer_Maven.src.main.java;

import java.util.ArrayList;
	public class BacarratGameLogic {
		
		public static int handTotal(ArrayList<Card> hand) {
			int total = 0;
			for(Card cards : hand) {
				 total += cards.getCardValue(cards);
			}
			if(total >= 10) {
				total = total % 10;
			}
			return total;
		}
		
		public static String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand) {
			if(handTotal(playerHand) > handTotal(bankerHand)) {
				return "The player won";
			} else if (handTotal(playerHand) < handTotal(bankerHand)) {
				return "The dealer won";
			} else if (handTotal(playerHand) == handTotal(playerHand)) {
				return "It was a draw";
			}
			return "The Player won";
		}
		
		public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
	        //determine if the banker draws a card or not
	         

	        int currHandTotal = handTotal(hand);

	        int playerCardValue = 0;

	        //if the player does not get to draw a card then playerCard == null and playerCardValue == -99
	        if (playerCard != null) {
	            playerCardValue = playerCard.value;
	        } else {
	            playerCardValue = -1;
	        }

	        //banker draws if total is less than 2. regardless of playerval
	        if (currHandTotal <= 2) {
	            return true;
	        }

	        //deciding to draw based on both the banker and player's cards
	        switch (currHandTotal) {
	            case 3:
	                if (playerCardValue == 8) {
	                    return false;
	                } else {
	                    return true;
	                }
	            case 4:
	                if (playerCardValue == -1) {
	                    return true;
	                } else if (playerCardValue >= 2 && playerCardValue <= 7) {
	                    return true;
	                } else {
	                    return false;
	                }
	            case 5:
	                if (playerCardValue == -1) {
	                    return true;
	                } else if (playerCardValue >= 4 && playerCardValue <= 7) {
	                    return true;
	                }
	            case 6:
	                if (playerCardValue == 6 || playerCardValue == 7) {
	                    return true;
	                }
	            default:
	                return false;
	        }
	    }
		
		public static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
			if(handTotal(hand) <= 5) {
				return true;
			}
			return false;
		}
	}