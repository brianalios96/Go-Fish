import java.util.ArrayList;
import java.util.Collections;

public class Player
{
	private ArrayList<Card> hand;
	private int score;
	private Deck deck;
	private String name;
	private int playernumber;
	
	public Player(Deck deck, String name, int num)
	{
		hand = new ArrayList<Card>();
		score = 0;
		this.deck = deck;
		this.name=name;
		playernumber=num;
	}

	public int getPlayerNumber()
	{
		return playernumber;
	}
	
	public String getPlayerName()
	{
		return name;
	}
	
	public boolean requestCardsFromOther(CardRank rank, Player other)
	{
		boolean retval = true;
		Card[] input = other.giveCards(rank);
		
		//other player did not have requested card, draw a 
		//card from the deck
		if(input.length == 0)
		{
			//TODO put if statement checking if the deck is empty
			Card tmp = deck.drawCard();
			hand.add(tmp);
			if(tmp.getRank() != rank)
			{
				retval = false;
			}
		}
		
		//other player had card, input requested cards into current player's hand
		else
		{
			for(Card card: input)
			{
				hand.add(card);
			}
		}
		
		for(CardRank aRank: CardRank.values())
		{
			int count = 0;
			
			for(Card card: hand)
			{
				if(card.getRank() == aRank)
				{
					count++;
				}
			}
			
			if(count == 4)
			{
				score++;
				for(int i = 0; i < hand.size(); i++)
				{
					if(hand.get(i).getRank() == aRank)
					{
						hand.remove(i);
						i--;
					}
				}
			}
		}
		
		Collections.sort(hand);
		return retval;
	}
	
	public Card[] giveCards(CardRank rank)
	{
		int count = 0;
		for(Card card: hand)
		{
			if(card.getRank() == rank)
			{
				count++;
			}
		}
		Card cards[] = new Card[count];
		
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < hand.size(); j++)
			{
				if(hand.get(j).getRank() == rank)
				{
					Card tmp = hand.remove(j);
					j--;
					cards[i] = tmp;
					i++;
				}
			}
		}
		
		return cards;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public ArrayList<Card> getPlayerHand()
	{
		return hand;
	}

	public void getStatingHand() {
		for(int i=0; i<5; i++)
		{
			Card temp= deck.drawCard();
			hand.add(temp);
		}
		Collections.sort(hand);
	}

	public boolean checkIfInHand(CardRank rank) {
		for(int i=0; i<hand.size(); i++)
		{
			if(rank== hand.get(i).getRank())
			{
				return true;
			}
		}
		return false;
	}

	public void draw() {
		for(int i=0; i<5; i++)
		{
			if(deck.getNumofDeckLeft()==0)
			{
				break;
			}
			hand.add(deck.drawCard());
		}
	}
}
