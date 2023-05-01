package JavaFX_GUIServer_Maven.src.main.java;

public class Card{
		String suite;
		int value;
		
		Card(String theSuite, int theValue){
			suite = theSuite;
			value = theValue;
		}
		
		public int getCardValue(Card thisCard) {
			if(thisCard.value <= 9) {
				return thisCard.value;
			} else {
				return 0;
			}
		}
		
	}