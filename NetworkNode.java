import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.Inet4Address;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.w3c.dom.NodeList;

class NetworkNode
{
	private String countryName,countryCode,regionCode,regionName,cityName,zip,latitude,longitude,timeZone,isp,as,org,query;
	private static String API_KEY_FILENAME = "D:\\APIKEY.txt";
	private static final String API_KEY = NetworkNode.getKeyFromFile(API_KEY_FILENAME);

	public NetworkNode(NodeList all)
	{
		this.countryName = all.item(3).getTextContent();
		this.countryCode = all.item(5).getTextContent();
		this.regionCode = all.item(7).getTextContent();
		this.regionName = all.item(9).getTextContent();
		this.cityName = all.item(11).getTextContent();
		this.zip = all.item(13).getTextContent();
		this.latitude = all.item(15).getTextContent();
		this.longitude = all.item(17).getTextContent();
		this.timeZone = all.item(19).getTextContent();
		this.isp = all.item(21).getTextContent();
		this.org = all.item(23).getTextContent();
		this.as = all.item(25).getTextContent();
		this.query = all.item(27).getTextContent();
	}
	public String getCountryName()
	{
		return this.countryName;
	}
	public String getCountryCode()
	{
		return this.countryCode;
	}
	public String getRegionCode()
	{
		return this.regionCode;
	}
	public String getRegionName()
	{
		return this.regionName;
	}
	public String getCityName()
	{
		return this.cityName;
	}
	public String getLatitude()
	{
		return this.latitude;
	}
	public String getLongitude()
	{
		return this.longitude;
	}
	public String geTimeZone()
	{
		return this.timeZone;
	}
	public String getISP()
	{
		return this.isp;
	}
	public String getOrg()
	{
		return this.org;
	}
	public String getAS()
	{
		return this.as;
	}
	public String getQuery()
	{
		return this.query;
	}
	public MapCoordinates getMapCoordinates()
	{
		MapCoordinates location = new MapCoordinates(this.latitude,this.longitude);
		return location;
	}
	public void displayAll()
	{
		System.out.println("Country Name : "+this.countryName);
		System.out.println("Country Code : "+this.countryCode);
		System.out.println("Region Code  : "+this.regionCode);
		System.out.println("Region Name  : "+this.regionName);
		System.out.println("City Name    : "+this.cityName);
		System.out.println("ZIP Code     : "+this.zip);
		System.out.println("Latitude     : "+this.latitude);
		System.out.println("Longitude    : "+this.longitude);
		System.out.println("Timezone     : "+this.timeZone);
		System.out.println("ISP          : "+this.isp);
		System.out.println("Organization : "+this.org);
		System.out.println("AS           : "+this.as);
		System.out.println("Query        : "+this.query);
	}
	public void displayLocationMap()
	{
		try
		{
			Image image = null;
			URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&center="+latitude+","+longitude+"&size=600x600&visible=44&zoom=5&markers=color:red%7Clabel:X%7C"+latitude+","+longitude+"&key="+API_KEY);
			image = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(image);
			JFrame frame = new JFrame("Location");
			JLabel label = new JLabel(icon);
			frame.getContentPane().add(label);
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void displayLocationMap(String title)
	{
		try
		{
			Image image = null;
			URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&center="+latitude+","+longitude+"&size=600x600&visible=44&zoom=5&markers=color:red%7Clabel:X%7C"+latitude+","+longitude+"&key="+API_KEY);
			image = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(image);
			JFrame frame = new JFrame("Location : "+title);
			JLabel label = new JLabel(icon);
			frame.getContentPane().add(label);
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void saveLocationMapFile(String filename)
	{
		try
		{
			if(this.isReservedIP(this.query))
			{
				throw new Exception("NetworkNode.saveLocationMapFile : Cannot draw map for Reserved IP : "+query);
			}
			BufferedImage image = null;
			URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?maptype=roadmap&center="+latitude+","+longitude+"&size=600x600&visible=44&zoom=5&markers=color:red%7Clabel:X%7C"+latitude+","+longitude+"&key="+API_KEY);
			image = ImageIO.read(url);
			File outputFile = new File(filename);
			ImageIO.write(image, "png", outputFile);
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	private boolean isReservedIP(String ipAddr)
	{
		try
		{
			if(Inet4Address.getByName(ipAddr).isSiteLocalAddress())
				return true;
			else return false;
		}
		catch(Exception e)
		{
			System.out.println("IpMapper.isReservedIP() | ERROR : "+e);
			return true;
		}
	}
	public static String getAPIKey()
	{
		return NetworkNode.API_KEY;
	}
	public static String getKeyFromFile(String filename)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String api_key = reader.readLine();
			api_key = api_key.trim();
			if(api_key == null || api_key.length() == 0)
			{
				System.out.println("ERROR: API Key file is missing. Please provide the CORRECT api key file. Current File : ("+API_KEY_FILENAME+").");
			}
			System.out.println("Retreived Key : "+api_key);
			return api_key;
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Error during API Key retreival");
			e.printStackTrace();
			return null;
		}
	}
}
