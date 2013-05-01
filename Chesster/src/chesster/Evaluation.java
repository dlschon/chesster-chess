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
		90,			//Pawn
		500,		//Rook
		300,		//Knight
		310,		//Bishop
		900,		//Queen
		20000		//King
	};
	
	public static final int MIDGAME_START = 13;		//at this moves, phase shifts from opening to midgame
	
	/*Importance of certain factors relative to game phase*/
	public static final double[/*phase*/][/*weight*/] WEIGHTS = 
		{	
			//Opening phase
			{
				0.7,	//Material weight
				0.4,	//Mobility weight
				0.6,	//Center occupation weight
				0.4,	//Center control weight
				1.0		//Repetition penalty weight
			},
			//Midgame phase
			{
				1.0,	//Material weight
				0.2,	//Mobility weight
				0.2,	//Center occupation weight
				0.2,	//Center control weight
				0.3		//Repetition penalty weight
			},
		};
	
	/* Indices for the weights table */
	public static final int WEIGHT_MAT = 0;
	public static final int WEIGHT_MOB = 1;
	public static final int WEIGHT_OCC = 2;
	public static final int WEIGHT_CON = 3;
	public static final int WEIGHT_REP = 4;
	
	/* Penalties*/
	public static final int PENALTY_REP = 200;		//penalty for repeated movement 
	public static final int PENALTY_QUEEN= 100;		//penalty for early queen use (only for opening)
	
	public static byte[][] board;
	public static int side; 
	public static int phase;
	
	//===========================================
	//METHODS
	//===========================================
	
	/**
	 * get a score for a board location
	 * @param board
	 * @param side
	 * @return score
	 */
	public static int evaluate(byte[][] board, int side, byte[] rootMove, byte rootPiece)
	{
		
		phase = 0;
		if (Main.moves >= MIDGAME_START)
			phase = 1;
		int score = 0;
		
		byte absRootPiece = (byte) Math.abs(rootPiece);
		
		/*Apply the early queen use penalty, if applies*/
		if (absRootPiece == 5 && phase == 0)
			score -= PENALTY_QUEEN;
			
		/*Evaluate material value*/
		for(byte y = 0; y < 8; y++)
		{
			for(byte x = 0; x < 8; x++)
			{
				byte piece = board[y][x];							//piece id
				if (piece != 0){
					byte absPiece = (byte) (Math.abs(piece));		//used to find piece value
					byte valMult = (byte) ((piece / absPiece));		//whether the piece is good or bad
					absPiece--;										//decremented to work with the table
					score += WEIGHTS[phase][WEIGHT_MAT]*(PIECE_VALUES[absPiece] * valMult * side);
				}
			}
		}

		MoveGenerator mGenOwn = new MoveGenerator(board, side, false);
		MoveGenerator mGenOpponent = new MoveGenerator(board, -side, false);
		byte[][] ownMoves = mGenOwn.generate();
		byte[][] opponentMoves = mGenOpponent.generate();
		
		/*Evaluate mobility*/
		score += WEIGHTS[phase][WEIGHT_MOB]*(ownMoves.length-opponentMoves.length);

		/*Evaluate center occupation*/
		for (byte posY = 1; posY <= 7; posY++)
		{
			for (byte posX = 2; posX <= 4; posX++)
			{
				byte piece = board[posY][posX];
				score += Util.getSign(piece)*side*WEIGHTS[phase][WEIGHT_OCC]*Tables.getCenterControlTable(side)[posY][posX];
			}
		}

		/*Evaluate center control (attacking)*/
		for (byte[] move : ownMoves)
		{
			byte posY = move[2];
			byte posX = move[3];
			int val = Tables.getCenterControlTable(side)[posY][posX];
			score += WEIGHTS[phase][WEIGHT_CON]*val;
		}
		for (byte[] move : opponentMoves)
		{
			byte posY = move[2];
			byte posX = move[3];
			int val = Tables.getCenterControlTable(side)[posY][posX];
			score -= WEIGHTS[phase][WEIGHT_CON]*val;
		}
		
		/*Add repetition penalty if applies*/
		if (Main.pieceHistory.size() > 1)
		{
			byte lastPiece = Main.pieceHistory.get(Main.pieceHistory.size() - 2);
			assert (Util.getSign(rootPiece) == Util.getSign(lastPiece));
			if (rootPiece == lastPiece)
				score -= PENALTY_REP * WEIGHTS[phase][WEIGHT_REP];
		}
		return score;
	}
}
