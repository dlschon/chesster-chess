package chesster;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Main.java starts the program and communicates with the GUI
 */
public class Main 
{
	
	//===========================================
	//FIELDS
	//===========================================
	
	public static final int REVISION_NO = 23;
	
	public static final byte[][] STARTBOARD = 
	{
		
		     /* 0 1 2 3 4 5  6  7 */
		     /* 1 2 3 4 5 6  7  8 */
		/*0|a*/{2,1,0,0,0,0,-1,-2},		//Meaning:
		/*1|b*/{3,1,0,0,0,0,-1,-3},		//+ is white, - is black
		/*2|c*/{4,1,0,0,0,0,-1,-4},		//1: Pawn
		/*3|d*/{5,1,0,0,0,0,-1,-5},		//2: Rook
		/*4|e*/{6,1,0,0,0,0,-1,-6},		//3: Knight
		/*5|f*/{4,1,0,0,0,0,-1,-4},		//4: Bishop
		/*6|g*/{3,1,0,0,0,0,-1,-3},		//5: Queen
		/*7|h*/{2,1,0,0,0,0,-1,-2},		//6: King
		
	};
	
	public static int side = 1;
	public static byte[][] currentBoard = new byte[8][8];
	public static Engine engine;
	
	public static int moves = 0;
	
	//===========================================
	//METHODS
	//===========================================
	
	/**
	 * Main entry point of the program
	 */
	public static void main(String[] args)
	{
		startNewGame();
		
		Util.print("======\\CHESSTER/======");
		Util.print("=======\\-r" + REVISION_NO + "--/=======");
		Util.print("By Daniel Levi Schon I");
		Util.print("type \"uci\" to enter uci mode");
		
		Scanner scanner = new Scanner(System.in);
		while (true){
			String cmd = scanner.nextLine();
			
			if (cmd.equals("uci"))
			{
				uci();
			}
			
			if (cmd.startsWith("quit"))
			{
				scanner.close();
				System.exit(0);
			}
		}
	}
	
	/**
	 * UCI mode input and output
	 */
	public static void uci()
	{
		Util.print("id name Chesster");
		Util.print("id author Daniel Levi Schon I");
		
		Util.print("uciok");
		
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			String[] params = parseCommand(scanner.nextLine());
			String cmd;
			if (params.length > 0)
			{
				cmd = params[0];
			}else{
				cmd = "";
			}
			
			//kill the program
			if (cmd.equals("quit"))
			{
				scanner.close();
				System.exit(0);
			}
			
			//used by the GUI to see if the engine is busy
			if (cmd.equals("isready"))
			{
				Util.print("readyok");
			}
			
			//start a new game
			if (cmd.equals("ucinewgame"))
			{
				startNewGame();
			}
			
			//set the position of the board. To be followed by 'go'
			if (cmd.equals("position"))
			{
				if (engine == null)
				{
					engine = new Engine();
				}
				
				if (params[1].equals("startpos"))
        {
						parsePos(params);
				}
				else
				{
					setFenPos();
				}
			}
			
			//think and output 
			if (cmd.equals("go"))
			{
				Util.print(engine.start(currentBoard, side));	
			}
			
			//print a visual representation of the board
			if (cmd.equals("show"))
			{
				Util.printout(currentBoard);
			}
				
			
		}
	}
	
	/**
	 * set the board from a 'position' command
	 * @param params
	 */
	private static void parsePos(String[] params) 
	{
		setStartPos();
		int count = 0;
		moves = params.length - 3;
		if (params.length == 2)
		{
			setStartPos();
			side = 1;
		}
		else
		{
			for (String str : params)
			{
				if (str.length() == 4 || str.length() == 5 && !str.equals("moves"))
				{
					count++;
					doMove(str);
				}
			}
			if (!Util.isEven(count))
			{
				side = -1;
			}
			else
			{
				side = 1;
			}
		}
	}

	/**
	 * Do a move given in algebraic notation
	 * @param move
	 */
	public static void doMove(String move) 
	{
		byte[] m = Util.algToArr(move);
		currentBoard = Util.doMove(currentBoard, m);
	}
	
	/**
	 * Chops up one string into an array, using spaces as delimiters
	 */
	public static String[] parseCommand(String s)
	{
		ArrayList<String> params = new ArrayList<String>();
		Scanner cmdParser = new Scanner(s);
		while(cmdParser.hasNext())
		{
			params.add(cmdParser.next());
		}
		cmdParser.close();
		String[] rArray = new String[params.size()];
		for (int i = 0; i < params.size(); i++)
		{
			rArray[i] = params.get(i);
		}
		return rArray;
	}
	
	/**
	 * Starts a new game
	 */
	public static void startNewGame()
	{
		if (engine == null)
		{
			engine = new Engine();
		}
		setStartPos();
	}
	
	/**
	 * Set the current board to the startposition, as defined by the char[][] STARTBOARD
	 */
	public static void setStartPos()
	{
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				currentBoard[y][x] = STARTBOARD[y][x];
				moves = 0;
			}
		}
	}
	
	/**
	 * Set the current board to the position specified by a fenstring
	 */
	public static void setFenPos()
	{
		//TODO fen compatibility
	}
}
