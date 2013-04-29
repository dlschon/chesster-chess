package chesster;

import java.util.ArrayList;

public class SearchInfo {
	
	public long nodes;
	public double time;
	public String bestMove;
	
	public SearchInfo(long nodes, long time, String bestMove)
	{
		this.nodes = nodes;
		this.time = time;
		this.bestMove = bestMove;
	}
}
