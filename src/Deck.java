import java.util.*;

public class Deck
{
	private ArrayList<Card> theDeck;
	
	public Deck()
	{
		theDeck = new ArrayList<Card>(40);
		
		for(CardRank rank: CardRank.values())
		{
			for(int i = 0; i < 4; i++)
			{
				theDeck.add(new Card(rank));
			}
		}
		System.out.println("before");
		for(Card card: theDeck)
		{
			System.out.println(card.getRank());
		}
		shuffle();
		System.out.println("after");
		for(Card card: theDeck)
		{
			System.out.println(card.getRank());
		}
	}
	
	public int getNumofDeckLeft()
	{
		return theDeck.size();
	}
	
	public void shuffle()
	{
		Collections.shuffle(theDeck);
	}
	
	public Card drawCard()
	{
		return theDeck.remove(0);
	}

}
