package chesster;

public class Util {
	
	/**
	 * Convert algebraic notation to a byte[4]
	 * @param algNotation
	 * @return byte[4]
	 */
	public static byte[] algToArr(String move) 
	{
		byte[] rAr = new byte[move.length()];
		char c = move.charAt(0);
		switch(c)
		{
			case('a'): rAr[0] = 0;
				break;
			case('b'): rAr[0] = 1;
				break;
			case('c'): rAr[0] = 2;
				break;
			case('d'): rAr[0] = 3;
				break;
			case('e'): rAr[0] = 4;
				break;
			case('f'): rAr[0] = 5;
				break;
			case('g'): rAr[0] = 6;
				break;
			case('h'): rAr[0] = 7;
				break;
		}
		rAr[1] = (byte) ((byte) move.charAt(1) - 49);
		c = move.charAt(2);
		switch(c)
		{
			case('a'): rAr[2] = 0;
				break;
			case('b'): rAr[2] = 1;
				break;
			case('c'): rAr[2] = 2;
				break;
			case('d'): rAr[2] = 3;
				break;
			case('e'): rAr[2] = 4;
				break;
			case('f'): rAr[2] = 5;
				break;
			case('g'): rAr[2] = 6;
				break;
			case('h'): rAr[2] = 7;
				break;
		}
		rAr[3] = (byte) ((byte) move.charAt(3) - 49);
		if (move.length() == 5)
		{
			byte type = 5;
			char pChar = move.charAt(4);
			if (pChar == 'r' || pChar == 'R')
				type = 2;
			if (pChar == 'n' || pChar == 'N')
				type = 3;
			if (pChar == 'b' || pChar == 'B')
				type = 4;
			if (pChar == 'q' || pChar == 'Q')
				type = 5;
			rAr[4] = type;
		}
		return rAr;
	}
	
	/**
	 * abbreviation of System.out.println()
	 */
	public static void print(Object o)
	{
		System.out.println(o);
	}
	
	/**
	 * Convert algebraic notation to a byte[4]
	 * @param byte[4]
	 * @return algNotation
	 */
	public static String arrToAlg(byte[] move)
	{
		String rStr = "";
		char[] alg = new char[4];
		alg[0] = (char) (move[0] + 97);
		alg[1] = (char) (move[1] + 49);
		alg[2] = (char) (move[2] + 97);
		alg[3] = (char) (move[3] + 49);
		
		rStr = String.valueOf(alg);
		return rStr;
	}
	
	/**
	 * Returns an exact replica of the given board
	 * @param board
	 * @return newBoard
	 */
	public static byte[][] copyBoard(byte[][] board)
	{
		byte[][] cBoard = new byte[8][8];
		for (int i1 = 0; i1 < 8; i1++)
		{
			for (int i2 = 0; i2 < 8; i2++)
			{
				cBoard[i1][i2] = board[i1][i2];
			}
		}
		return cBoard;
	}
	
	/**
	 * Returns a duplicate of the given board after a given move
	 * @param board
	 * @param move
	 * @return newBoard
	 */
	public static byte[][] doMove(byte[][] board, byte[] move)
	{
		byte[][] cBoard = copyBoard(board);
		cBoard[move[2]][move[3]] = cBoard[move[0]][move[1]];
		cBoard[move[0]][move[1]] = 0;
		byte piece = cBoard[move[2]][move[3]];
		if (piece == 1 && move[3] == 7 || piece == -1 && move[3] == 0)
		{
			//promote
			byte pType;
			if (move.length == 5)
			{
				pType = move[4];
			}
			else
			{
				pType = 5;
			}
			cBoard[move[2]][move[3]] = (byte) (pType*piece);
		}
		return cBoard;
	}
	
	/**
	 * Compare the signs of two bytes
	 * @param a
	 * @param b
	 * @return isSignEqual
	 */
	public static boolean signEquals(int a, int b)
	{
		if (a < 0 && b < 0)
			return true;
		if (a > 0 && b > 0)
			return true;
		if (a == 0 && b == 0)
			return true;
		return false;
	}
	
	/**
	 * Prints a visual representation of a board
	 * @param board
	 */
	public static void printout(byte[][] b)
	{
		print("x_12345678");
		for(int i1 = 0; i1 < 8; i1++)
		{
			System.out.print((char)('a'+i1) + "|");
			for(int i2 = 0; i2 < 8; i2++)
			{
				byte p = b[i1][i2];
				char c = '.';
				switch (p){
					case(-6):{c = 'K'; break;}
					case(-5):{c = 'Q'; break;}
					case(-4):{c = 'B'; break;}
					case(-3):{c = 'N'; break;}
					case(-2):{c = 'R'; break;}
					case(-1):{c = 'P'; break;}
					case(0):{c = '.'; break;}
					case(6):{c = 'k'; break;}
					case(5):{c = 'q'; break;}
					case(4):{c = 'b'; break;}
					case(3):{c = 'n'; break;}
					case(2):{c = 'r'; break;}
					case(1):{c = 'p'; break;}
				}
				System.out.print(c);
			}
			print("");
		}
	}
	
	/**
	 * 
	 * @param double
	 * @return
	 */
	public static boolean isEven(double d)
	{
		d /= 2;
		int i = (int) d;
		if (i == d)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * returns -1 for negative, 0 for zero, and 1 for positive
	 * @param int
	 * @return
	 */
	public static int getSign(int i)
	{
		if (i < 0)
			return -1;
		if (i > 0)
			return 1;
		return 0;
	}
}
