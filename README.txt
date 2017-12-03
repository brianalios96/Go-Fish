Isaac Plunkett
Briana Liosatos
CSc 422 Distributive Program

To start the GO FISH game,
java Dealer (on one computer)
java GoFish dealer_machine_name (for the other 4 player computers, dealer_machine_name is the name of the cs lab machine the dealer is running)

Possible commands to do (error checking):

When the game starts, this hand is given ONE, TWO, SEVEN, NINE, TEN

select card rank you wish to get (prompt by the program): -1 (user input)
	returns prompt for rank again
	
select card rank you wish to get (prompt by the program): 11(user input)
	returns prompt for rank again
	
select card rank you wish to get (prompt by the program): 3 (user input)
	returns prompt for rank again

select player you wish to get cards from (prompt by the program): -1 (user input)
	returns prompt for player again

select player you wish to get cards from (prompt by the program): 4 (user input)
	returns prompt for player again (number of players is 0 indexed)
	
select player you wish to get cards from (prompt by the program): 1(user input)
	returns prompt for player again (if player 1 is the current player asking for cards)