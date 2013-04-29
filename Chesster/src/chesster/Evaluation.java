package chesster;

/**
 * Evaluation.java calculates the value of a given chess board
 */
public class Evaluation 
{
	
	//===========================================
	//FIELDS
	//===========================================
	
	public static final int[] PIECE_VALUES = 
	{
		1,		//Pawn
		5,		//Rook
		3,		//Knight
		3,		//Bishop
		9,		//Queen
		200		//King
	};
	
	public static double MOBILITY_BONUS = .1;
	
	public static byte[][] board;
	public static int side; 
	
	//===========================================
	//METHODS
	//===========================================
	
	/**
	 * get a score for a board location
	 * @param board
	 * @param side
	 * @return score
	 */
	public static double evaluate(byte[][] b, int s)
	{
		board = b;
		side = s;
		double score = 0;
		
		//Evaluate piece value
		for(byte y = 0; y < 8; y++)
		{
			for(byte x = 0; x < 8; x++)
			{
				byte piece = board[y][x];							//piece id
				if (piece != 0){
					byte absPiece = (byte) (Math.abs(piece));			//used to find piece value
					byte valMult = (byte) ((piece / absPiece));	//whether the piece is good or bad
					absPiece--;
					score += PIECE_VALUES[absPiece] * valMult * side;
				}
			}
		}
		
		//evaluate mobility
		/*MoveGenerator mGen1 = new MoveGenerator(board, side, false);
		MoveGenerator mGen2 = new MoveGenerator(board, -side, false);
		int posMoves1 = mGen1.generate().length;
		int posMoves2 = mGen2.generate().length;
		
		score += MOBILITY_BONUS*(posMoves1-posMoves2);*/
		
		return score;
	}
}
