import java.util.ArrayList;
class Tokenizer
{
	public static final int NO_SPACE = -1;
	public static String[] tokenizeData(String data)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		data.trim();										// trim spaces before and after
		if(data.length() <= 0)
		{
			System.out.println("ERROR      : could not tokenize input data.");
			System.out.println("INPUT DATA : '"+data+"'");
			System.exit(0);
		}
		if(Tokenizer.spaceCount(data) <= 0) 				// if data does not contain any spaces , return the data as it is 
		{
			String[] temp = new String[1];
			temp[0] = data;
			return temp;
		}

		int nextSpaceIndex = 0 , currentIndex = 0;
		while(true)
		{
			nextSpaceIndex = Tokenizer.nextSpaceIndex(data,currentIndex);
			if(nextSpaceIndex != NO_SPACE)
			{
				tokens.add(data.substring(currentIndex,nextSpaceIndex).trim());
				currentIndex = nextSpaceIndex + 1;
			}
			else
			{
				tokens.add(data.substring(currentIndex,data.length()).trim());
				break;
			}
		}
		return Tokenizer.toStringArray(tokens);
	}
	public static int spaceCount(String data)
	{
		int spaceCount = 0;
		for(int i=0;i<data.length();i++)
		{
			if(data.charAt(i) == ' ')
				spaceCount ++;
		}
		return spaceCount;
	}
	public static int nextSpaceIndex(String data,int offset)	  // if no space after offset, returns -1;
	{
		for(int i=offset+1;i<data.length();i++)
		{
			if(data.charAt(i) == ' ' && data.charAt(i+1)!=' ')
			{
				return i;
			}
		}
		return NO_SPACE;
	}
	public static String[] toStringArray(ArrayList<String> data)	// utility function to convert an ArrayList to an String array.
	{
		String[] array = new String[data.size()];
		int i = 0;
		for(String temp : data)
		{
			array[i++] = temp;
		}
		return array;
	}
}