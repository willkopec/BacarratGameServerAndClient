package BacarratClient.src.main.java;

public class Card{
		
		// public field to store value and suit of card
		String suite;
		int value;
		
		/*
		 * Constructor that takes in value and suit for a card
		 */
		Card(String theSuite, int theValue){
			suite = theSuite;
			value = theValue;
		}
		
		/*
		 * Retrieves the value from the card object
		 * and returns it (an integer)
		 */
		public int getCardValue(Card thisCard) {
			if(thisCard.value <= 9) {
				return thisCard.value;
			} else {
				return 0;
			}
		}
		
	}