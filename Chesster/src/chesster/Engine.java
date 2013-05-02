package chesster;

/**
 * Engine.java does all of the move generation, searching, and evaluating
 */



public class Engine 
{
	
	public String start(byte[][] board, int side)
	{
		Search search = new Search(board, side, this);
		SearchInfo info = search.search();
		Util.print("info nps " + (long)(info.nodes / (info.time / 1000)) + " nodes " + info.nodes);
		Util.print("Search complete: " + info.nodes + " in " + info.time  + " milliseconds.");
		Main.doMove(info.bestMove);
		
		return "bestmove " + info.bestMove;
	}
	
	
	
	
}
