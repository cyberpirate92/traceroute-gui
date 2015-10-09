import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkDiagnostics
{
 	private final String os = System.getProperty("os.name");
 	private IpMapper mapper = new IpMapper();
  	public String traceRoute(InetAddress address)
  	{
    	String route = "",temp = "",command = "";
    	BufferedReader br;
    	try 
    	{
	        Process traceRt;
	        System.out.println(os);
	        if(os.contains("Win")||os.contains("win"))
	        {
				command = "tracert ";
			}
	        else 
	        {
				command = "traceroute ";
			}
			traceRt = Runtime.getRuntime().exec(command + address.getHostAddress()); 
			br = new BufferedReader(new InputStreamReader(traceRt.getInputStream()));
	        while((temp = br.readLine())!=null)
	        {
				//System.out.println(temp);
				route += temp + "\n";
			}
			return route;
    	}
    	catch (IOException ioe) 
    	{
      	  System.out.println("Error while performing traceroute command | "+ioe);
      	  System.exit(0);
   		}
    	return route;
	}
	public IpMapper getMapper()
	{
		return this.mapper;
	}
	public void map(String host)
	{
		try
		{
			String temp;
			InetAddress addr = InetAddress.getByName(host);
			temp = traceRoute(addr);
			System.out.println(host + "'s ip is "+addr);
			/*System.out.println("Split Parts | Split using Tokenizer -- ");*/
			String[] parts = Tokenizer.tokenizeData(temp);
			boolean firstIP = true;
			for(int i=0;i<parts.length;i++)
			{
				/*System.out.println("[Part "+(i+1)+" : ]"+parts[i]);*/
				// ************************
				// checking for ip-address pattern
				Pattern p = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
				Matcher m = p.matcher(parts[i]);
				if(m.matches())
				{
					if(firstIP) //ignoring first ip address.
					{
						firstIP = false;
					}
					else
					{
						mapper.add(parts[i]);
						System.out.println(parts[i]+" added to ip list.");
					}
				}
				// ************************
			}
			// the final, map having path
			mapper.displayMap();
			System.out.println("End --");
			return;
		}
		catch(UnknownHostException uhe)
		{
			System.out.println("Error : host could not be resolved | "+uhe);
		}
		catch(Exception e)
		{
			System.out.println("Unhandled Exception |"+e);
		}
	}
	public static void main(String[] args)
	{
		String host;
		if (args.length != 1) 
		{
			Scanner input = new Scanner(System.in);
			System.out.println("Host : ");
			host = input.nextLine().trim();
			input.close();
		}
		else
		{
			host = args[0];
		}
		NetworkDiagnostics ndiag = new NetworkDiagnostics();
		ndiag.map(host);
	}
}
