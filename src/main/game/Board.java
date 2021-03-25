package main.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import main.game.GameStates;

public class Board {
	// board is stored as array of length 24
	// see doc for enumeration of positions
	private Integer[] boardLoc = new Integer[24];
	// whose turn it is, 0 - player 1; 1 - player 2
	private Integer playerTurn = 0;
	// map for adjacency list of locations
	private HashMap<Integer, Integer[]> adj = new HashMap<Integer, Integer[]>();
	private GameStates gameState;
	
	// (immutable) array of possible mills
	private static final Integer[][] possMills = {
		{0, 1, 2}, 
		{3, 4, 5},
		{6, 7, 8},
		{9, 10, 11},
		{12, 13, 14},
		{15, 16, 17},
		{18, 19, 20},
		{21, 22, 23},
		{0, 9, 21},
		{3, 10, 18},
		{6, 11, 15},
		{1, 4, 7},
		{16, 19, 22},
		{8, 12, 17},
		{5, 13, 20},
		{2, 14, 23}
	};

	// pieces for players
	private Integer[] unplacedPieces = new Integer[] {9, 9};
	private Integer[] livePieces = new Integer[] {9, 9};
	
	public Board() {
		/* constructor */
		// mark all spots on the board as empty
		Arrays.fill(boardLoc, 0, 24, 0);
		
		// adjacency list for each of the board locations
		adj.put(0, new Integer[] {1, 9});
		adj.put(1, new Integer[] {0, 2, 4});
		adj.put(2, new Integer[] {1, 14});
		adj.put(3, new Integer[] {4, 10});
		adj.put(4, new Integer[] {1, 3, 5, 7});
		adj.put(5, new Integer[] {4, 13});
		adj.put(6, new Integer[] {7, 11});
		adj.put(7, new Integer[] {4, 6, 8});
		adj.put(8, new Integer[] {7, 12});
		adj.put(9, new Integer[] {0, 10, 21});
		adj.put(10, new Integer[] {3, 9, 11, 18});
		adj.put(11, new Integer[] {6, 10, 15});
		adj.put(12, new Integer[] {8, 13, 17});
		adj.put(13, new Integer[] {5, 12, 14, 20});
		adj.put(14, new Integer[] {2, 13, 23});
		adj.put(15, new Integer[] {11, 16});
		adj.put(16, new Integer[] {15, 17, 19});
		adj.put(17, new Integer[] {12, 16});
		adj.put(18, new Integer[] {10, 19});
		adj.put(19, new Integer[] {16, 18, 20, 22});
		adj.put(20, new Integer[] {13, 19});
		adj.put(21, new Integer[] {9, 22});
		adj.put(22, new Integer[] {19, 21, 23});
		adj.put(23, new Integer[] {14, 22});
		
		// mark initial game state
		gameState = GameStates.move;
	}
	
	
	// setters and getters for dealing with the board
	private void PlacePiece(Integer playerNum, Integer location) {
		/* places a piece at a location and decrements unplaced count 
		
		@param playerNum number of player making action (0 - player 1, 1 - player 2)
		@param location the id of the intersection to place the piece (0-23) */
		unplacedPieces[playerNum]--;
		boardLoc[location] = playerNum + 1;
	}
	
	
	private void MovePiece(Integer playerNum, Integer locationTo, Integer locationFrom) {
		/* moves a pieces from one location to another

		@param playerNum number of player making action (0 - player 1, 1 - player 2)		
		@param locationTo the id of the intersection to place the piece (0-23)
		@param locationFrom the id of the intersection to take the piece (0-23) */
		boardLoc[locationTo] = playerNum + 1;
		boardLoc[locationFrom] = 0;
	}
	
	
	private void RemovePiece(Integer location) {
		/* removes a piece at location and updates board state
		
		@param location the id of the intersection to remove the piece (0-23) */
		Integer player = boardLoc[location] - 1;
		livePieces[player]--;
		boardLoc[location] = 0;		
	}
	
	
	public boolean IsPlayersTurn(Integer playerNum) {
		/* checks if it is a player's turn

		@param playerNum number of player making action (0 - player 1, 1 - player 2)
		@return if it is the player's turn */
		return (playerTurn == playerNum);
	}
	
	
	public boolean IsValidLoc(Integer location) {
		/* Checks if the location is valid for our data structure. The location must 
		be between 0 and 23, inclusive, in order to be a valid spot on the board.
		
		@param location the id of the intersection
		@return if it is a valid location */
		return !(location < 0 || location > 23);
	}
	
	
	public boolean IsEmpty(Integer location) {
		/* Checks if location is empty
		
		@param location the id of the intersection (0-23)
		@param return if the location is empty */
		return (boardLoc[location] == 0);
	}
	
	
	public Integer GetPlayersTurn() {
		/* gets players turn
		
		@return number of player whose turn it is (0 - Player 1, 1 - Player 2) */
		return playerTurn;
	}
	
	
	public boolean IsPlayersPiece(Integer playerNum, Integer location) {
		/* checks if the location's piece is the player's
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@param location the id of the intersection (0-23)
		@return if the location contains this player's piece */
		return (boardLoc[location] == (playerNum + 1));
	}
	
	
	public boolean HasUnplacedPieces(Integer playerNum) {
		/* checks if a player has unplaced pieces
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@return if the player has unplaced pieces */
		return (unplacedPieces[playerNum] > 0);
	}
	
	
	public boolean AreAdjacent(Integer loc1, Integer loc2) {
		/* checks if the location are adjacent

		@param loc1 location of the first intersection
		@param loc2 location of the second intersection
		@return whether the two locations are adjacent */
		return (Arrays.asList(adj.get(loc1)).contains(loc2));
	}
	
	
	public Integer NumUnplacedPieces(Integer playerNum) {
		/* getter for number of unplaced pieces 
		 
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@return number of unplaced pieces for a player */
		return unplacedPieces[playerNum];
	}


	public Integer NumLivePieces(Integer playerNum) {
		/* getter for number of live pieces

		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@return number of pieces a player has left */
		return livePieces[playerNum];
	}


	public boolean IsPlacementStage() {
		/* getter for if it is the placement stage 
		the game is either in the placement stage, where the
		players place pieces, or the movement stage, where the 
		players move pieces already on the board

		@return whether the game is in the placement stage */
		// if not, it is the movement stage
		return (HasUnplacedPieces(0) || HasUnplacedPieces(1));
	}

	
	public boolean CanFly(Integer playerNum) {
		/* checks if a player can fly, this means the player has 3 pieces
		and they can move them anywhere
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@return whether the player can fly their pieces */
		return livePieces[playerNum] <= 3;
	}
	
	
	public boolean IsMill(Integer location) {
		/* checks if the location is part of a mill, which is formed if
		a player has three pieces in a straight line - 
		either vertical or horizontal 
		
		@param location the id of intersection on board (0-23)
		@return whether this location is part of a mill */
		// get the value of the location
		Integer playerNum = boardLoc[location];
		// trivial case - is empty
		if (playerNum == 0) {
			return false;
		}
		Integer[] first, second;
		first = new Integer[3]; 
		second = new Integer[3];
		// search the array of possMills search for the two possiblities
		// of this lcoation
		boolean isFirst = true;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 3; j++) {
				if (possMills[i][j] == location) {
					// not this is just a reference
					if (isFirst) {
						first = possMills[i];
						isFirst = false;
					}
					else {
						second = possMills[i];
					}
				}
			}
		}
		if (boardLoc[first[0]] == playerNum && boardLoc[first[1]] == playerNum && boardLoc[first[2]] == playerNum) {
				return true;
		}
		if (boardLoc[second[0]] == playerNum && boardLoc[second[1]] == playerNum && boardLoc[second[2]] == playerNum) {
				return true;
		}
		return false;
	}
	
	
	public boolean HasLegalMoves(Integer playerNum) {
		/* checks if a player has a possible move
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@return whether the player can make any valid moves */
		// if during placement
		if (HasUnplacedPieces(playerNum)) {
			return true;
		}
		// if looking for movement
		if (gameState == GameStates.move) {
			// if they can fly, there is a guaranteed move
			if (CanFly(playerNum)) {
				return true;
			}
			// otherwise start with location 0 
			// see if the player is in that position
			// and there are open adjacent spot
			int i = 0;
			while (i < 24) {
				if (boardLoc[i] == (playerNum + 1)) {
					Integer[] possibleAdj = adj.get(i);
					for (int j = 0; j < possibleAdj.length; j++) {
						if (IsEmpty(possibleAdj[j])) {
							return true;
						}
					}
				}
				i++;
			}
			// if we iterated through the full board and they cannot move
			return false;
		}
		// want to find a piece of the opponent that is not in a mill
		if (gameState == GameStates.remove) {
			int i = 0;
			while (i < 24) {
				if (boardLoc[i] == ((playerNum + 1) % 2)) {
					if (!IsMill(i)) {
						return true;
					}
				}
				i++;
			}
			return false;
		}
		// if neither of those game states (impossible since enum)
		return false;
	}
	
	
	public GameStates GetGameState() {
		/* gets the game state on board

		@return the game state (either move or remove) */
		return gameState;
	}
	
	
	public boolean IsEnd() {
		/* checks if it is the end of the game

		@return if the game is over */
		// if a player has two pieces
		if (livePieces[0] <= 2 || livePieces[1] <= 2) {
			return true;
		}
		// check if either player has legal moves
		if (!HasLegalMoves(0) || !HasLegalMoves(1)) {
			return true;
		}
		return false;
	}
	
	
	// public checks for valid moves and end game
	public boolean IsValidMovement(Integer playerNum, Integer location) {
		/* Method to check if a particular movement is valid 
		movement with only one location is placement 
		 
		@param playerNum: the player number (0 - Player 1, 1 - Player 2)
		@param location the id of the intersection (0-23)
		@return whether the placement is valid */
		// check that the location exists
		if (!IsValidLoc(location)) {
			return false;
		}
		// check that it is the player's turn
		if (!IsPlayersTurn(playerNum)) {
			return false;
		}
		// check that the player has piece's left
		if (!HasUnplacedPieces(playerNum)) {
			return false;
		}
		// check that the location is empty
		if (!IsEmpty(location)) {
			return false;
		}
		// check game state
		if (GetGameState() != GameStates.move) {
			return false;
		}
		return true;		
	}
	
	
	public boolean IsValidMovement(Integer playerNum, Integer locationTo, Integer locationFrom) {
		/* Method to check if a particular movement is valid 
		movement with two locations is adj. or flying 
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)		
		@param locationTo the id of the intersection to be moved to (0-23)
		@param locationFrom the id of the intersection to move the piece from (0-23)
		@return whether the movement is valid */
		// check that both locations exist
		if (!IsValidLoc(locationTo)) {
			return false;
		}
		if (!IsValidLoc(locationFrom)) {
			return false;
		}
		// check that it is the player's turn
		if (!IsPlayersTurn(playerNum)) {
			return false;
		}
		// check that the player has no piece's left
		if (HasUnplacedPieces(playerNum)) {
			return false;
		}
		// check that the location to is empty
		if (!IsEmpty(locationTo)) {
			return false;
		}
		// check that the locationFrom is the correct player
		if (!IsPlayersPiece(playerNum, locationFrom)) {
			return false;
		}		
		// check if the two locations are adjacent or if flying is valid
		// !Adj && !CanFly <==> !(Adj || CanFly)
		if (!(AreAdjacent(locationTo, locationFrom) || CanFly(playerNum))) {
			return false;
		}
		// check game state
		if (GetGameState() != GameStates.move) {
			return false;
		}
		return true;
	}
	
	
	public boolean IsValidRemoval(Integer playerNum, Integer location) {
		/* Checks if a removal is valid

		@param playerNum the player number (0 - Player 1, 1 - Player 2)		
		@param location the id of the intersection (0-23)		
		@return whether the removal is valid */
		// check that the location exists
		if (!IsValidLoc(location)) {
			return false;
		}
		// check that it is the player's turn
		if (!IsPlayersTurn(playerNum)) {
			return false;
		}
		// check that the location is nonempty
		if (IsEmpty(location)) {
			return false;
		}
		// check that the location correct player
		if (!IsPlayersPiece(((playerNum + 1) % 2), location)) {
			return false;
		}
		// check game state
		if (GetGameState() != GameStates.remove) {
			return false;
		}
		// check if the location is a part of a mill
		if (IsMill(location)) {
			return false;
		}
		return true;	
	}
	
	
	// public functions for making moves
	public boolean MakeMove(Integer playerNum, Integer location) throws Exception {
		/* make the move (with one location argument this will be placement) 
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)		
		@param location the id of the intersection (0-23)
		@return whether the placement formed a mill */
		if (!IsValidMovement(playerNum, location)) {
			throw new Exception("Invalid placement");
		}
		// otherwise is valid move
		PlacePiece(playerNum, location);
		Boolean formedMill = IsMill(location);
		if (formedMill) {
			gameState = GameStates.remove;
		}
		else {
			playerTurn = (playerTurn + 1) % 2;
		}
		return (formedMill);
	}
	
	
	public boolean MakeMove(Integer playerNum, Integer locationTo, Integer locationFrom) throws Exception{
		/* Make the move (with two location arguments this will be movement) 
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@param locationTo the id of the intersection to move the piece to (0-23)
		@param locationFrom the id of the intersection to move the piece from (0-23)
		@return whether the movement formed a mill */
		if (!IsValidMovement(playerNum, locationTo, locationFrom)) {
			throw new Exception("Invalid movement");
		}
		// otherwise is valid move
		MovePiece(playerNum, locationTo, locationFrom);
		Boolean formedMill = IsMill(locationTo);
		if (formedMill) {
			gameState = GameStates.remove;
		}
		else {
			playerTurn = (playerTurn + 1) % 2;
		}
		return (formedMill);
	}
	
	
	public void RemoveMan(Integer playerNum, Integer location) throws Exception {
		/* remove a piece from the board
		
		@param playerNum the player number (0 - Player 1, 1 - Player 2)
		@param location the id of the intersection to remove the piece from (0-23) */
		if (!IsValidRemoval(playerNum, location)) {
			throw new Exception("Invalid movement");
		}
		// otherwise is valid move
		RemovePiece(location);
		gameState = GameStates.move;
		playerTurn = (playerTurn + 1) % 2;
	}	
}