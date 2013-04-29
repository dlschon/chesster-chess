package chesster;

import java.util.ArrayList;

/**
 * MoveGenerator.java calculates a set of all possible moves in a give board.
 */
public class MoveGenerator 
{
	
	//===========================================
	//FIELDS
	//===========================================
	
	public /*static*/ byte[][] board;
	/** source x: the origin of the move (rank)*/
	public /*static*/ byte sX;
	/** source y: the origin of the move (file)*/
	public /*static*/ byte sY;
	/** test x: the destination of the move (rank)*/
	public /*static*/ byte tX;
	/** test y: the destination of the move (file)*/
	public /*static*/ byte tY;
	/** piece: the type of piece being tested */
	public /*static*/ byte piece;
	/** side: -1 for black, 1 for white */
	public /*static*/ int side;
	/** Marks whether this is a counter-gen, used to test for check*/
	public boolean cFlag;
	public /*static*/ ArrayList<byte[]> moves;
	
	//===========================================
	//Constructor
	//===========================================
	
	/**
	 * @param board
	 * @param side
	 * @param counterFlag
	 * */
	
	public MoveGenerator(byte[][] b, int s, boolean counterFlag)
	{
		board = b;
		side = s;
		cFlag = counterFlag;
	}
	
	//===========================================
	//METHODS
	//===========================================
	
	/**
	 * Get the set of possible moves
	 */
	public /*static*/ byte[][] generate()
	{

		moves = new ArrayList<byte[]>();
		
		for(sY = 0; sY < 8; sY++)
		{
			for(sX = 0; sX < 8; sX++)
			{
				
				piece = board[sY][sX];
				
				if (Util.signEquals(piece,(byte) side)) //if the piece is on the correct side
				{	
					
					byte pSide = -1;	//-1 is black, 1 is white
					if (piece > 0){
						pSide = 1;
					}
					byte absPiece = (byte) Math.abs(piece);
					
					//Define the piece movements
					
					if (absPiece == 1)
					{	//Pawn
						
						boolean canDouble = false;
						if (side == -1 && sX == 6 || side == 1 && sX == 1)
							canDouble = true;
						for(int i = 1; i <= 2; i++)
						{
							tX = (byte) (sX + i*pSide);
							tY = sY;
							if (i == 2 && !canDouble)
							{
								break;
							}
							if (doPMove()){
								break;
							}
						}
						tX = (byte) (sX + pSide);
						
						tY = (byte) (sY - 1);
						doPAttack();
						
						tY = (byte) (sY + 1);
						doPAttack();
					}
					
					if (absPiece == 2)
					{	//Rook
						tX = sX;
						tY = sY;
						do
						{
							tY--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tX++;
						}while(!doTest());
					}
					
					if (absPiece == 3)
					{	//Knight
						tY = (byte) (sY - 2); tX = (byte) (sX - 1); 
						doTest();
						tY = (byte) (sY - 2); tX = (byte) (sX + 1); 
						doTest();
						tY = (byte) (sY - 1); tX = (byte) (sX - 2); 
						doTest();
						tY = (byte) (sY - 1); tX = (byte) (sX + 2); 
						doTest();
						tY = (byte) (sY + 1); tX = (byte) (sX - 2); 
						doTest();
						tY = (byte) (sY + 1); tX = (byte) (sX + 2); 
						doTest();
						tY = (byte) (sY + 2); tX = (byte) (sX - 1); 
						doTest();
						tY = (byte) (sY + 2); tX = (byte) (sX + 1); 
						doTest();
					}
					
					if (absPiece == 4)
					{	//Bishop
						tX = sX;
						tY = sY;
						do
						{
							tY--;
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY--;
							tX++;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
							tX++;
						}while(!doTest());
					}
					
					if (absPiece == 5)
					{	//Queen
						
						tX = sX;
						tY = sY;
						do
						{
							tY--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tX++;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY--;
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
							tX--;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY--;
							tX++;
						}while(!doTest());
						
						tX = sX;
						tY = sY;
						do
						{
							tY++;
							tX++;
						}while(!doTest());
					}
					
					if (absPiece == 6)
					{	//King
						tY = (byte) (sY - 1); tX = (byte) (sX - 1); 
						doTest();
						tY = (byte) (sY - 1); tX = (byte) (sX); 
						doTest();
						tY = (byte) (sY - 1); tX = (byte) (sX + 1); 
						doTest();
						tY = (byte) (sY); tX = (byte) (sX + 1); 
						doTest();
						tY = (byte) (sY); tX = (byte) (sX - 1); 
						doTest();
						tY = (byte) (sY + 1); tX = (byte) (sX - 1); 
						doTest();
						tY = (byte) (sY + 1); tX = (byte) (sX); 
						doTest();
						tY = (byte) (sY + 1); tX = (byte) (sX + 1); 
						doTest();
					}
				}
			}		
		}		
		byte[][] rArray = new byte[moves.size()][4];
		for (int m = 0; m < moves.size(); m++)
		{
			rArray[m] = moves.get(m);
		}
		return rArray;
	}
	
	/**
	 * tests a square for a possible move/attack and adds it as a possible move if ok
	 */
	public /*static*/ boolean doTest()
	{
		if (tY < 0 || tY > 7 || tX < 0 || tX > 7)
		{//The square is off the board
			return true;
		}
		if (board[tY][tX] == 0)
		{//The square is empty
			addMove();
			return false;
		}
		
		if (Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the same team 
			return true;
		}
		if (!Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the other team 
			addMove();
			return true;
		}
		assert false : "doTest error!";
		return false;
		
	}
	
	/**
	 * tests a square for a possible pawn move and adds it as a possible move if ok
	 */
	public /*static*/ boolean doPMove()
	{
		if (tY < 0 || tY > 7 || tX < 0 || tX > 7)
		{//The square is off the board
			return true;
		}
		if (board[tY][tX] == 0)
		{//The square is empty
			addMove();
			return false;
		}
		
		if (Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the same team 
			return true;
		}
		if (!Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the other team 
			return true;
		}
		assert false : "doTest error!";
		return false;
		
	}
	
	/**
	 * tests a square for a possible pawn attack and adds it as a possible move if ok
	 */
	public /*static*/ boolean doPAttack()
	{
		if (tY < 0 || tY > 7 || tX < 0 || tX > 7)
		{//The square is off the board
			return true;
		}
		if (board[tY][tX] == 0)
		{//The square is empty
			return true;
		}
		
		if (Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the same team 
			return true;
		}
		if (!Util.signEquals(board[tY][tX], piece))
		{//The piece in the square is of the other team 
			addMove();
			return false;
		}
		assert false : "doTest error!";
		return false;
		
	}
	
	/**
	 * Adds the move to the list, if it doesn't cause check
	 */
	public /*static*/ void addMove()
	{
		byte[] nMove = {sY,sX, tY,tX};
		
		if (cFlag == false)
		{
			if (/*!causesCheck(board, nMove, this.side)*/ true)
			{
				moves.add(nMove);
			}
		}
		else
		{
			moves.add(nMove);
		}
	}
	
	/**
	 * Test to see if a move will cause check
	 * @param move
	 * @return
	 */
	public static boolean causesCheck(byte[][] board, byte[] m, int side)
	{
		boolean isCheck = false;
		
		//do the move
		byte[][] cBoard = Util.doMove(board, m);
		
		//Get the coords of Chesster's king
		byte kY = -1;
		byte kX = -1;
		for (byte i1 = 0; i1 < 8; i1++)
		{
			for (byte i2 = 0; i2 < 8; i2++)
			{
				if (cBoard[i1][i2] == 6*side)
				{
					kY = i1;
					kX = i2;	
					break;
				}
			}
		}
		assert kY != -1;
	
		byte[][] countermoves = new MoveGenerator(cBoard, (byte) -side, true).generate();
		
		for (int i = 0; i < countermoves.length; i++)
		{
			
			byte dY = countermoves[i][2];
			byte dX = countermoves[i][3];
			if (dY == kY && dX == kX)
			{//Chesster's king would be in check
				isCheck = true;
				break;
			}
		}
		return isCheck;
	}
	
}
