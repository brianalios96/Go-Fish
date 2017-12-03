import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable
{
	private static final long serialVersionUID = 3563714765503218503L;
	private CardRank rank;
	
	public Card(CardRank rank)
	{
		this.rank = rank;
	}
	
	public CardRank getRank()
	{
		return rank;
	}

	@Override
	public int compareTo(Card other)
	{
		return rank.compareTo(other.rank);
	}
}
