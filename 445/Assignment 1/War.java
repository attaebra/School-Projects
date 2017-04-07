/*Atta Ebrahimi War
 *
 *The war game doesn't work entirely as I'd like
 *It fails when the players run out of cards
 *and also does not accomdate for the tie properly
*/

public class War{

	public static void main(String[] args){
	
	System.out.println("Welcome to the Game of War!");
	System.out.println("\nNow dealing the cards to the players...");
	
	MultiDS<Card> playerCards = new MultiDS<Card>(26);
	MultiDS<Card> otherCards = new MultiDS<Card>(26);
	MultiDS<Card> totalCards = new MultiDS<Card>(52);
	MultiDS<Card> playerDiscard = new MultiDS<Card>(52);
	MultiDS<Card> otherDiscard = new MultiDS<Card>(52);
		for(Card.Suits s : Card.Suits.values()){
			for(Card.Ranks r: Card.Ranks.values()){
				Card c = new Card(s, r);
				totalCards.addItem(c);
			}
		}
		
		
		totalCards.shuffle();
		
		for(int i=0; i < 52; i++){
			if (i%2 == 0){
				playerCards.addItem(totalCards.removeItem());
			}
			else{
				otherCards.addItem(totalCards.removeItem());
			}
		}
		
	System.out.println("\nHere is Player One's Hand: " + playerCards.toString());
	System.out.println("\nHere is Player Two's Hand: " + otherCards.toString());
	
	System.out.println("\n Starting the WAR!\n\n");
	
		while(!playerCards.empty() || !otherCards.empty()){
			Card p = playerCards.removeItem();
			Card q = otherCards.removeItem();
			double winner = compareCards(p, q);
		//Does game if cards aren't empty	
		if(playerCards.empty() && playerDiscard.empty()){
			//Player 1 wins adds cards to discarded
			if(winner > 0){
				playerDiscard.addItem(p);
				playerDiscard.addItem(q);
			}
			//Player 2 wins add cards to discarded
			else if (winner < 0){
				otherDiscard.addItem(q);
				otherDiscard.addItem(p);
			}
			//If tie go to war
			else if (winner == 0){
				Card r = playerCards.removeItem();
				Card s = otherCards.removeItem();
				System.out.println("\tPlayer 1: "+ r +" and Player 2: "+ s +" are at risk!");
				Card t = playerCards.removeItem();
				Card u = playerCards.removeItem();
				double winner2 = compareCards(t, u);
				//If player 1 wins they are suppose to get all the cards
				if (winner2 > 0){
					playerDiscard.addItem(p);
					playerDiscard.addItem(q);
					playerDiscard.addItem(r);
					playerDiscard.addItem(s);
					playerDiscard.addItem(t);
					playerDiscard.addItem(u);
				}
				//If player 2 wins they are supppose to get all the cards
				else if (winner2 < 0){
					otherDiscard.addItem(p);
					otherDiscard.addItem(q);
					otherDiscard.addItem(r);
					otherDiscard.addItem(s);
					otherDiscard.addItem(t);
					otherDiscard.addItem(u);
				}
			}
			//If player 1 cards are empty, take from discarded
			if(playerCards.empty()){
					System.out.println("Getting and shuffling cards for Player 1");
					for(int i = 0; i < playerDiscard.size(); i++){
						playerCards.addItem(playerDiscard.removeItem());
					}
					playerCards.shuffle();
					playerCards.shiftLeft();
			}
			//If player 2 cards are empty, take from discarded
			if(otherCards.empty() == true){
				System.out.println("Getting and shuffling cards for Player 2");
				for(int j = 0; j < otherDiscard.size(); j++){
					otherCards.addItem(otherDiscard.removeItem());
				}
				otherCards.shuffle();
				otherCards.shiftLeft();
			}
		}
		}
		//If player one is empty player two wins
		if(playerDiscard.empty() && playerCards.empty()){
			System.out.println("Player Two Wins!");
		}
		//If player two is out of cards player one wins
		else{
			System.out.println("Player One Wins!");
		}
	}
	/** Compare this object to another Card.  Cards of the same rank are
	 *  considered equal, regardless of the suit.  Note that this differs
	 *  from the equals method.  Java enum types are automatically Comparable,
	 *  based on the order in which the values are defined (smallest to
     *  largest).
     */
	public static double compareCards(Card x, Card y){
		double result = x.compareTo(y);
		if (result > 0){
			System.out.println("Player 1 Wins: "+ x + " beats " + y);
		}
		else if (result < 0){
			System.out.println("Player 2 Wins: "+ x + " loses to " + y);
		}
		else{
			result = 0;
			System.out.println("WAR: "+ x + " ties " + y);
		}
		return result;
	}
}