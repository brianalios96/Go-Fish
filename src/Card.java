import java.io.Serializable;

import javafx.scene.image.Image;

public class Card implements Comparable<Card>, Serializable
{
	private static final long serialVersionUID = 3563714765503218503L;
	private CardRank rank;
	private Image picture;
	
	public Card(CardRank rank, Image picture)
	{
		this.rank = rank;
		this.picture = picture;
	}
	
	public CardRank getRank()
	{
		return rank;
	}
	
	public Image getPicture()
	{
		return picture;
	}

	@Override
	public int compareTo(Card other)
	{
		return rank.compareTo(other.rank);
	}
}
