package chesster;

public class Tables {
	
	private static byte[][] wCenterControl = 
		{
			{00,00,00,00,00,00,00,00},
			{00,00,00,10,00,00,00,00},
			{00,00,20,30,30,20,00,00},
			{00,00,00,40,40,20,00,00},
			{00,00,00,40,40,20,00,00},
			{00,00,20,30,30,20,00,00},
			{00,00,00,10,00,00,00,00},
			{00,00,00,00,00,00,00,00}
			
		};
	private static byte[][] bCenterControl = 
		{
			{00,00,00,00,00,00,00,00},
			{00,00,00,00,10,00,00,00},
			{00,00,20,30,30,20,00,00},
			{00,00,20,40,40,00,00,00},
			{00,00,20,40,40,00,00,00},
			{00,00,20,30,30,20,00,00},
			{00,00,00,00,10,00,00,00},
			{00,00,00,00,00,00,00,00}
			
		};
	private static byte[][][] centerControlLookup = 
		{
			bCenterControl,
			null,
			wCenterControl
		};
	
	/**
	 * 
	 * @param side
	 * @return center control table
	 */
	public static byte[][] getCenterControlTable(int side)
	{
		return centerControlLookup[side+1];
	}
}
