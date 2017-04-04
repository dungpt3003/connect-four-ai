// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;
/**
 * Field class
 *
 * Field class that contains the field status data and various helper functions.
 *
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class Field {
	public int[][] mBoard;
	private int mCols = 0, mRows = 0;
	private String mLastError = "";
	public int mLastColumn = 0;
	public int mBotId = 0;

	public Field(int columns, int rows) {
		mBoard = new int[columns][rows];
		mCols = columns;
		mRows = rows;
		clearBoard();
	}

	/**
	 * Sets the number of columns (this clears the board)
	 * @param args : int cols
	 */
	public void setColumns(int cols) {
		mCols = cols;
		mBoard = new int[mCols][mRows];
	}

	/**
	 * Sets the number of rows (this clears the board)
	 * @param args : int rows
	 */
	public void setRows(int rows) {
		mRows = rows;
		mBoard = new int[mCols][mRows];
	}

	/**
	 * Clear the board
	 */
	public void clearBoard() {
		for (int x = 0; x < mCols; x++) {
			for (int y = 0; y < mRows; y++) {
				mBoard[x][y] = 0;
			}
		}
	}

	/**
	 * Adds a disc to the board
	 * @param args : command line arguments passed on running of application
	 * @return : true if disc fits, otherwise false
	 */
	public Boolean addDisc(int column, int disc) {
		mLastError = "";
		if (column < mCols) {
			for (int y = mRows-1; y >= 0; y--) { // From bottom column up
				if (mBoard[column][y] == 0) {
					mBoard[column][y] = disc;
					mLastColumn = column;
					return true;
				}
			}
			mLastError = "Column is full.";
		} else {
			mLastError = "Move out of bounds.";
		}
		return false;
	}

	/**
	 * Initialise field from comma separated String
	 * @param String :
	 */
	public void parseFromString(String s) {
		s = s.replace(';', ',');
		String[] r = s.split(",");
		int counter = 0;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				mBoard[x][y] = Integer.parseInt(r[counter]);
				counter++;
			}
		}
	}

	/**
	 * Returns the current piece on a given column and row
	 * @param args : int column, int row
	 * @return : int
	 */
	public int getDisc(int column, int row) {
		return mBoard[column][row];
	}

	/**
	 * Returns whether a slot is open at given column
	 * @param args : int column
	 * @return : Boolean
	 */
	public Boolean isValidMove(int[][] currBoard, int column) {
		return (currBoard[column][0] == 0);
	}

	/**
	 * Returns reason why addDisc returns false
	 * @param args :
	 * @return : reason why addDisc returns false
	 */
	public String getLastError() {
		return mLastError;
	}

	@Override
	/**
	 * Creates comma separated String with every cell.
	 * @param args :
	 * @return : String
	 */
	public String toString() {
		String r = "";
		int counter = 0;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				if (counter > 0) {
					r += ",";
				}
				r += mBoard[x][y];
				counter++;
			}
		}
		return r;
	}

	/**
	 * Checks whether the field is full
	 * @return : Returns true when field is full, otherwise returns false.
	 */
	public boolean isFull() {
		for (int x = 0; x < mCols; x++)
		  for (int y = 0; y < mRows; y++)
		    if (mBoard[x][y] == 0)
		      return false; // At least one cell is not filled
		// All cells are filled
		return true;
	}

	/**
	 * Checks whether the given column is full
	 * @return : Returns true when given column is full, otherwise returns false.
	 */
	public boolean isColumnFull(int column) {
		return (mBoard[column][0] != 0);
	}

	/**
	 * @return : Returns the number of columns in the field.
	 */
	public int getNrColumns() {
		return mCols;
	}

	/**
	 * @return : Returns the number of rows in the field.
	 */
	public int getNrRows() {
		return mRows;
	}

	/**
	 * @return : Display board as a matrix of 0, 1, 2
	 */
	public void printBoard() {
		for (int y = 0; y < mRows; y++){
			for (int x = 0; x < mCols - 1; x++)
				System.out.print(mBoard[x][y]);
			System.out.println(mBoard[mCols - 1][y]);
		}
	}

	/**
	 * @return : Check number of 4/3/2 consecutive chips on the board
	 */
	 public int checkState(int[][] currBoard, int state, int bot) {
		 int countState = 0;
		 boolean check = true;
		 // Check horizontal
		 for (int y = 0; y < mRows; y++)
 			for (int x = 0; x < mCols - state + 1; x++)
				if (currBoard[x][y] == bot){
					for (int j = 1; j < state; j++)
						if (currBoard[x + j][y] != bot){
							check = false;
							break;
						}
					if (check == true) countState += 1;
					else check = true;
				}


		 // Check vertical
		 for (int x = 0; x < mCols; x++)
		 	for (int y = 0; y < mRows - state + 1; y++)
				if (currBoard[x][y] == bot){
					for (int j = 1; j < state; j++)
						if (currBoard[x][y + j] != bot){
							check = false;
							break;
						}
					if (check == true) countState += 1;
					else check = true;
				}

		check = true;
		 // Check diagonal
		 for (int y = 0; y < mRows - state + 1; y++)
 			for (int x = 0; x < mCols - state + 1; x++)
				if (currBoard[x][y] == bot){
					for (int j = 1; j < state; j++)
						if (currBoard[x + j][y + j] != bot){
							check = false;
							break;
						}
					if (check == true) countState += 1;
				}

		 return countState;
	 }

	 /**
 	 * @return : Heuristic function to evaluate current state of the board
 	 */
	 public int evaluateBoard(int[][] currBoard){
		 int fours = checkState(currBoard, 4, mBotId);
		//  if (fours > 0)
		//  	return 1000000;
		 int oppFours = checkState(currBoard, 4, 3 - mBotId);
		//  if (oppFours > 0)
		//  	return -1000000;
		 int threes = checkState(currBoard, 3, mBotId);
		 int twos   = checkState(currBoard, 2, mBotId);
		 int oppThrees = checkState(currBoard, 3, 3 - mBotId);
		 int oppTwos = checkState(currBoard, 2, 3 - mBotId);
		 return 10000 * fours + 100 * threes + 10 * twos - 10000 * oppFours - 100 * oppThrees - 10 * oppTwos;
	 }

	 /**
	 * @return: Given state of the board and a move, return the state after
	 */
	 public int[][] makeMove(int[][] currBoard, int col, int bot){
		 int[][] tempBoard = currBoard;
	     for (int j = mRows - 1; j >= 0; j--)
		 	if (currBoard[col][j] == 0){
				currBoard[col][j] = bot;
				break;
			}
		 return currBoard;
	 }

	 /**
	 * @return: Undo a move
	 */
	 public int[][] removeMove(int[][] currBoard, int col){
		 int[][] tempBoard = currBoard;
	     for (int j = 0; j < mRows; j++)
		 	if (currBoard[col][j] != 0){
				currBoard[col][j] = 0;
				break;
			}
		 return currBoard;
	 }

	 /**
	 * Recursive function to evaluate all available moves
	 */
	 public int minimax(int[][] currBoard, int depth, int col, int bot){
         if(depth == 0) {
			 int res = evaluateBoard(currBoard);
             return res;
         }

		 int maxScore = 0;

		 if (isValidMove(currBoard, col)){
			 currBoard = makeMove(currBoard, col, bot);
			//  for (int y = 0; y < mRows; y++){
	 	// 		for (int x = 0; x < mCols - 1; x++)
	 	// 			System.out.print(currBoard[x][y]);
	 	// 		System.out.println(currBoard[mCols - 1][y]);
			 //  }
			//  System.out.print("\n");
			 for (int x = 0; x < mCols; x++)
			 	if (isValidMove(currBoard, x)) {
					int next = minimax(currBoard, depth - 1, x, 3 - bot);
					if (next > maxScore)
						maxScore = next;
					//maxScore += next;
			 	}
			currBoard = removeMove(currBoard, col);
	 	 }

		 return maxScore * (depth + 1);
	 }

	 /**
	 * Get best moves for a given state of the board
	 **/
	 public int getBestMove(int[][] currBoard, int depth, int bot){
		 int moves[] = new int[mCols];
		 int[][] tempBoard = currBoard;
		 for (int i = 0; i < mCols; i++)
		 	if (isValidMove(tempBoard, i))
				moves[i] = minimax(tempBoard, depth, i, bot);
			else
				moves[i] = -1;

		//  for (int i = 0; i < mCols; i++)
		//  	System.out.print(moves[i] + " ");
		//  System.out.println("");
		 int bestMove = 0;
		 int bestValue = -1000000000;

		 for (int i = 0; i < mCols; i++)
			 if (moves[i] > bestValue && moves[i] != -1){
				 bestValue = moves[i];
 				 bestMove = i;
			 }

		 return bestMove;
	 }
}
