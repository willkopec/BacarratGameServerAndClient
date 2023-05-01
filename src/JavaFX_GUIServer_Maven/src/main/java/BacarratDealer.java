package JavaFX_GUIServer_Maven.src.main.java;

import java.util.Collections;
	import java.util.ArrayList;
	
	public class BacarratDealer{

		ArrayList<Card> deck = new ArrayList<Card>();
		
		public void generateDeck() {
			
			for(int i = 0; i < 4; i++) {
				for(int j = 1; j <= 13; j++) {
					if(i == 1) {
						Card thisCard = new Card("Spades", j);
						deck.add(thisCard);
					} else if (i == 2) {
						Card thisCard = new Card("Hearts", j);
						deck.add(thisCard);
					} else if (i == 3) {
						Card thisCard = new Card("Clubs", j);
						deck.add(thisCard);
					} else if(i == 4) {
						Card thisCard = new Card("Diamonds", j);
						deck.add(thisCard);
					}
				}
			}
			
		}
		
		public ArrayList<Card> dealHand(){
			ArrayList<Card> temp = new ArrayList<Card>();
			temp.add(deck.get(0));
			temp.add(deck.get(1));
			deck.remove(0);
			deck.remove(1);
			
			return temp;
		}
		
		public Card drawOne() {
			Card temp = new Card(null, 0);
			temp = deck.get(0);
			deck.remove(0);
			
			
			return temp;
		}
		
		public void shuffleDeck() {
			Collections.shuffle(deck);
		}
		
		public int deckSize() {
			return deck.size();
		}
	}