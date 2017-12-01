import java.util.*;

public class GoFish
{
	private static final String SELECT_RANK = " Please select the rank of the card you wish to ask for (integer 1-10)";
	private static final String SELECT_PLAYER = " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1";
	private static final String PLAYER_OUT_OF_CARDS = " ran out of cards and there are still cards in the deck";
	private static final String SKIP_PLAYER = " does not have any cards and no more cards in the deck, their turn is skipped";
	
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		Player players[] = new Player[Dealer.NUMBER_OF_PLAYERS];

		for (int i = 0; i < players.length; i++)
		{
			players[i] = new Player("Player " + (i + 1), (i + 1));
		}

		playgame(players, scan);

		for(Player player: players)
		{
			player.closeConnection();
		}
		
		scan.close();
	}

	/**
	 * Main game loop
	 */
	private static void playgame(Player[] players, Scanner scan)
	{
		int totalscore = calcTotalScore(players);
		while (totalscore != 10)
		{
			// each player gets a turn
			for (Player player: players)
			{
				boolean yourTurn = true;
				while (yourTurn)
				{
					if (player.getPlayerHand().size() == 0)
					{
						if(player.getRemainingDeck() > 0)
						{
							System.out.println(player.getPlayerName() + PLAYER_OUT_OF_CARDS);
							player.draw();
						}
						else
						{
							System.out.println(player.getPlayerName() + SKIP_PLAYER);
							break;
						}
					}					
					
					printCurrentPlayersCards(player);

					CardRank rank = getRankFromPlayer(player, scan);
					
					int otherplace = getOtherPlayer(player, scan, players.length);
					Player other = players[otherplace - 1];
					
					yourTurn = player.requestCardsFromOther(rank, other);
				}
			}// for loop for each player

			// see if anyone has won
			totalscore = calcTotalScore(players);

			if (totalscore != 10)
			{
				printAllPlayersHands(players);
			}
		}// while loop for the entire game
	} // playGame()

	
	/**
	 * prompts the user for another player, represented by their place in the play order
	 */
	private static int getOtherPlayer(Player player, Scanner scan, int numberOfPlayers)
	{
		System.out.println(player.getPlayerName() + SELECT_PLAYER);
		int otherplace = scan.nextInt();
		while (otherplace > numberOfPlayers || player.getPlayerNumber() == otherplace)
		{
			System.out.println("Select a player between 1 and " + numberOfPlayers + " that is not yourself");
			System.out.println(player.getPlayerName() + SELECT_PLAYER);
			otherplace = scan.nextInt();
		}
		return otherplace;
	}
	
	/**
	 * gets a CardRank from the user, checks that the rank is in the user's hand, and re asks if necessary
	 */
	private static CardRank getRankFromPlayer(Player player, Scanner scan)
	{		
		CardRank rank = checkUserRankinput(scan, player);
		while (!player.checkIfInHand(rank))
		{
			System.out.println("Selected rank is not in " + player.getPlayerName() + "'s hand");
			rank = checkUserRankinput(scan, player);
		}
		return rank;
	}
	
	/**
	 * helps getRankFromPlayer
	 * gets an int from the user, checks that the int is a valid card rank, and converts the int into the appropriate card rank
	 */
	private static CardRank checkUserRankinput(Scanner scan, Player player)
	{
		int newrank = 0;
		while (newrank < 1 || newrank > 10)
		{
			System.out.println(player.getPlayerName() + SELECT_RANK);
			newrank = scan.nextInt();
		}
		return getCardRank(newrank);
	}
	
	/**
	 * converts int into card rank
	 */
	private static CardRank getCardRank(int userrank)
	{
		for(CardRank rank : CardRank.values())
		{
			if(userrank == rank.getValue())
			{
				return rank;
			}
		}
		return null; // only happens if invalid number is passed
	}

	/**
	 * Prints each player's individual score
	 * Calculates and return total score
	 */
	private static int calcTotalScore(Player[] players)
	{
		int totalscore = 0;
		for (int i = 0; i < players.length; i++)
		{
			totalscore += players[i].getScore();
			System.out.println(players[i].getPlayerName() + " has " + players[i].getScore() + " points");
		}
		System.out.println();
		System.out.println();
		return totalscore;
	}

	/**
	 * prints all player's hands
	 */
	private static void printAllPlayersHands(Player[] players)
	{
		for (int i = 0; i < players.length; i++)
		{
			printCurrentPlayersCards(players[i]);
			System.out.println();
		}
	}

	/**
	 * prints the hand for one player
	 */
	private static void printCurrentPlayersCards(Player player)
	{
		System.out.println(player.getPlayerName() + "'s cards");
		for(Card card: player.getPlayerHand())
		{
			System.out.print(" " + card.getRank());
		}
		System.out.println();
	}
}
