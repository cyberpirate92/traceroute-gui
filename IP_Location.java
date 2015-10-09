import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

class IP_Location
{
	NetworkNode node;

	public IP_Location(String ip)
	{
		try
		{
			String url;
			Pattern p = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
			Matcher m = p.matcher(ip);
			if(!m.matches())
			{
				System.out.println("ERROR: Bad IPv4 Address");
				throw new Exception("Bad IPv4 Address");
			}
			url = "http://ip-api.com/xml/"+ip;
			DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = db.newDocumentBuilder();
			Document parsedXML = builder.parse(new URL(url).openStream());
			NodeList status = parsedXML.getElementsByTagName("status");
			if(status.item(0).getTextContent().equalsIgnoreCase("fail"))
			{
				System.out.println("---> IP_Location("+ip+") :");
				System.out.println("ERROR : IP Address("+ip+") is invalid.");
			}
			else
			{
				NodeList all = parsedXML.getElementsByTagName("query").item(0).getChildNodes();
				node = new NetworkNode(all);
			}
		}
		catch(Exception e)
		{
			System.out.println("[Argument : "+ip+"]");
			System.out.println(e);
			System.exit(0);
		}
	}
	public NetworkNode getNode()
	{
		return this.node;
	}
	public void displayAll()
	{
		node.displayAll();
	}
	public void displayLocationMap()
	{
		node.displayLocationMap();
	}
	public void displayLocationMap(String title)
	{
		node.displayLocationMap(title);
	}
	public static void main(String args[])
	{
		IP_Location location = new IP_Location("27.251.102.131");
		location.displayAll();
		location.displayLocationMap();
		location = null;
	}
}