package chesster;

import java.util.ArrayList;
import java.util.Collections;


public class Search 
{
	
	public static final int SEARCH_DEPTH = 4;
	
	public byte[][] board;
	public double bestScore = -200;
	public int side;
	public byte[] bestMove = {0,0,0,0};
	public Engine engine;
	public long nodes = 0;
	
	public Search(byte[][] b, int s, Engine engine)
	{
		this.engine = engine;
		board = b;
		side = s;
	}
	
	/**
	 * Starts the negamax alpha-beta search
	 * @return searchInfo
	 */
	public SearchInfo search()
	{
		long start = System.currentTimeMillis();
		ArrayList<byte[]> bestMoves = searchRoot(SEARCH_DEPTH, board, side);
		long end = System.currentTimeMillis();
		
		for (byte[] move: bestMoves)
		{
			if (!MoveGenerator.causesCheck(board, move, side))
			{
				bestMove = move;
				break;
			}
		}
		
		return new SearchInfo(nodes, end-start, Util.arrToAlg(bestMove));
		
	}
	
	/**
	 * negamax for the top node
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @param board
	 * @param side
	 * @return
	 */
	public ArrayList<byte[]> searchRoot(int depth, byte[][] board, int side)
	{
		nodes++;
		MoveGenerator mGen = new MoveGenerator(board, side, false);
		byte[][] moveset = mGen.generate();
		
		/*Contains a list of possible moves */
		ArrayList<byte[]> bestMoves = new ArrayList<byte[]>();
		/*Contains the value of each move. Used to sort moves from best to worst*/
		ArrayList<Integer> moveVals = new ArrayList<Integer>();
		
		for (byte[] move : moveset)
		{
			byte piece = board[move[0]][move[1]];
			byte[][] nBoard = Util.doMove(board, move);
			int score = -alphaBeta(-2000, 2000, depth-1, nBoard, -side, move, piece);
			bestMoves.add(move);
			moveVals.add(score);
		}
		bestMoves = sortMoves(bestMoves, moveVals);
		Util.print("info depth " + SEARCH_DEPTH);
		return bestMoves;
		
	}
	
	/**
	 * Returns a list of moves ordered from strongest to weakest
	 * @param bestMoves
	 * @param moveVals
	 * @return
	 */
	public ArrayList<byte[]> sortMoves(ArrayList<byte[]> bestMoves, ArrayList<Integer> moveVals) 
	{
		ArrayList<byte[]> rArrayList = new ArrayList<byte[]>();
		ArrayList<Integer> indexOrder = new ArrayList<Integer>();
		//copy of moveVals to prevent modification
						@SuppressWarnings("unchecked")
		ArrayList<Integer> cMoveVals = (ArrayList<Integer>) moveVals.clone();
		//Sort values in descending order
		Collections.sort(cMoveVals, new IntComparator());
		Util.print("bestScore: " + cMoveVals.get(0));
		for (int val : moveVals)
		{
			int index = cMoveVals.indexOf(val);
			cMoveVals.set(index, -99999);
			indexOrder.add(index);
			rArrayList.add(null);
		}
		for (int count = 0; count < indexOrder.size(); count++)
		{
			int index = indexOrder.get(count);
			rArrayList.set(index, bestMoves.get(count));
		}
		
		return rArrayList;
	}

	/**
	 * Searches one node, or possible board, and applies the negamax algorithm
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @param board
	 * @param side
	 * @param rootMove
	 * @return
	 */
	public int alphaBeta(int alpha, int beta, int depth, byte[][] board, int side, byte[] rootMove, byte rootPiece)
	{
		nodes++;
		if (depth == 0)
		{
			return Evaluation.evaluate(board, side, rootMove, rootPiece);
		}
		else
		{
			MoveGenerator mGen = new MoveGenerator(board, side, false);
			byte[][] moveset = mGen.generate();
			for (byte[] move : moveset)
			{
				byte[][] nBoard = Util.doMove(board, move);
				
				int score = -alphaBeta(-beta, -alpha, depth-1, nBoard, -side, rootMove, rootPiece);
				if (score >= beta)
				{
					return beta;	//cutoff
				}
				if (score > alpha)
				{
					alpha = score;
				}
			}
		}
		return alpha;
	}
}
