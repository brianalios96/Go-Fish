import java.io.Serializable;

public enum CardRank implements Serializable
{
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10);
	
	private int value;

	CardRank(int val) {
		value = val;
	}

	public int getValue() {
		return value;
	}
}
