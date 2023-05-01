package BacarratClient.src.main.java;

import java.util.Collections;
import java.util.ArrayList;
	
	public class BacarratDealer{

		ArrayList<Card> deck = new ArrayList<Card>();
		
		/*
		 * This create a deck of 52 cards with 13 cards of each suite
		 * Uses a double for loop and return type is void
		 */
		public void generateDeck() {
			
			for(int i = 0; i < 4; i++) {
				for(int j = 1; j <= 13; j++) {
					if(i == 0) {
						Card thisCard = new Card("Spades", j);
						deck.add(thisCard);
					} else if (i == 1) {
						Card thisCard = new Card("Hearts", j);
						deck.add(thisCard);
					} else if (i == 2) {
						Card thisCard = new Card("Clubs", j);
						deck.add(thisCard);
					} else if(i == 3) {
						Card thisCard = new Card("Diamonds", j);
						deck.add(thisCard);
					}
				}
			}	
		}
		
		/*
		 * This functions deals a hand by removing two cards from 
		 * the deck and adding it to a temp ArrayList
		 * It returns the ArrayList which contains the hand
		 */
		public ArrayList<Card> dealHand(){
			ArrayList<Card> temp = new ArrayList<Card>();
			temp.add(deck.get(0));
			temp.add(deck.get(1));
			deck.remove(0);
			deck.remove(1);
			
			return temp;
		}
		
		/*
		 * This functions removes one card from the deck
		 * and returns the card it removed
		 */
		public Card drawOne() {
			Card temp = new Card(null, 0);
			temp = deck.get(0);
			deck.remove(0);
			return temp;
		}
		
		/*
		 * This functions shuffles the deck
		 * return type is void
		 */
		public void shuffleDeck() {
			Collections.shuffle(deck);
		}
		
		/*
		 * This function returns the size of the
		 * deck as an int
		 */
		public int deckSize() {
			return deck.size();
		}
	}