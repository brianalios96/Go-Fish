import java.util.ArrayList;
import java.util.Collections;

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
				theDeck.add(new Card(rank, null));
			}
		}
		shuffle();
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
//		if(theDeck.isEmpty())
//		{
//			return null;
//		}
		return theDeck.remove(0);
	}

}
