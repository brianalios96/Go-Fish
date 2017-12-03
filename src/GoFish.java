import java.util.*;

public class GoFish
{
	private static final String SELECT_RANK = " Please select the rank of the card you wish to ask for (integer 1-10)";
	private static final String SELECT_PLAYER = " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1\n";
	private static final String PLAYER_OUT_OF_CARDS = " ran out of cards and there are still cards in the deck\n";
	private static final String SKIP_PLAYER = " does not have any cards and no more cards in the deck, their turn is skipped\n";
	private static final String IP_ERROR = "Must type IP address";

	public static void main(String args[])
	{
		if (args.length == 0)
		{
			System.out.println(IP_ERROR);
			System.exit(1);
		}

		Scanner scan = new Scanner(System.in);

		Player player = new Player(args[0]);
		playgame(player, scan);
		player.closeConnection();

		scan.close();
	}

	/**
	 * Main game loop
	 */
	private static void playgame(Player player, Scanner scan)
	{
		boolean firstTurn = true;

		int scores[] = new int[player.getNumberOfPlayers()];

		int totalscore = calcTotalScore(scores);
		while (totalscore != 10)
		{
			if (!firstTurn || player.getPlayerNumber() != 0)
			{
				scores = player.waitForTurn();
			}
			firstTurn = false;
			boolean yourTurn = true;
			while (yourTurn)
			{
				if (player.getPlayerHand().size() == 0)
				{
					if (player.getRemainingDeck() > 0)
					{
						System.out.println(player.getPlayerName() + PLAYER_OUT_OF_CARDS);
						player.draw();
					} else
					{
						System.out.println(player.getPlayerName() + SKIP_PLAYER);
						break;
					}
				}
				printNumberInHand(player.getNumberCardsInHands());
				
				printCurrentPlayersCards(player);

				CardRank rank = getRankFromPlayer(player, scan);

				int otherplace = getOtherPlayer(player, scan);
				int other = otherplace;
				yourTurn = player.requestCardsFromOther(rank, other);
			}

			player.endTurn(scores);

			// see if anyone has won
			totalscore = calcTotalScore(scores);
		} // while loop for the entire game
	} // playGame()

	/**
	 * prints how many cards are in everyone's hands
	 */
	private static void printNumberInHand(int[] handSize)
	{
		System.out.println("Number of cards in hands");
		for(int i = 0; i < handSize.length; i++)
		{
			if(i != 0)
			{
				System.out.print(", ");
			}
			
			System.out.print("Player " + i + ": " + handSize[i]);
		}
		System.out.println("\n");
	}
	
	/**
	 * Checks the user's input to see if it is a number.
	 * If not, false is returned
	 * 
	 */
	private static boolean isParsable(String input) {
		boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	
	/**
	 * prompts the user for another player, represented by their place in the
	 * play order
	 */
	private static int getOtherPlayer(Player player, Scanner scan)
	{
		int numberOfPlayers = player.getNumberOfPlayers();
		System.out.println(player.getPlayerName() + SELECT_PLAYER);

		String userinput= scan.nextLine().trim();

		while (!isParsable(userinput) || Integer.parseInt(userinput) > (numberOfPlayers - 1) || player.getPlayerNumber() == Integer.parseInt(userinput)
				|| Integer.parseInt(userinput)<0)
		{
			System.out.println("Select a player between 0 and " + (numberOfPlayers-1) + " that is not yourself\n");
			System.out.println(player.getPlayerName() + SELECT_PLAYER);
			userinput=scan.nextLine().trim();
		}
		return Integer.parseInt(userinput);
	}

	/**
	 * gets a CardRank from the user, checks that the rank is in the user's
	 * hand, and re asks if necessary
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
	 * helps getRankFromPlayer gets an int from the user, checks that the int is
	 * a valid card rank, and converts the int into the appropriate card rank
	 */
	private static CardRank checkUserRankinput(Scanner scan, Player player)
	{
		int newrank = 0;
		String userinput;
		while (newrank < 1 || newrank > 10)
		{
			System.out.println(player.getPlayerName() + SELECT_RANK);
			userinput=scan.nextLine().trim();
			while(!isParsable(userinput))
			{
				System.out.println(player.getPlayerName() + SELECT_RANK);
				userinput=scan.nextLine().trim();
			}
			newrank = Integer.parseInt(userinput);//scan.nextInt();
		}
		return getCardRank(newrank);
	}

	/**
	 * converts int into card rank
	 */
	private static CardRank getCardRank(int userrank)
	{
		for (CardRank rank : CardRank.values())
		{
			if (userrank == rank.getValue())
			{
				return rank;
			}
		}
		return null; // only happens if invalid number is passed
	}

	/**
	 * Prints each player's individual score Calculates and return total score
	 */
	private static int calcTotalScore(int[] scores)
	{
		int totalscore = 0;
		for (int i = 0; i < scores.length; i++)
		{
			totalscore += scores[i];
			System.out.println("Player " + i + " has " + scores[i] + " points");
		}
		System.out.println();
		System.out.println();
		return totalscore;
	}

	/**
	 * prints the hand for one player
	 */
	private static void printCurrentPlayersCards(Player player)
	{
		System.out.println(player.getPlayerName() + "'s cards");
		for (Card card : player.getPlayerHand())
		{
			System.out.print(" " + card.getRank());
		}
		System.out.println("\n");
	}
}
