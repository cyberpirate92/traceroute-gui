import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.Inet4Address;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class IpMapper
{
	private ArrayList<String> allIPList;
	private ArrayList<String> publicIPList;
	private ArrayList<Integer> reservedList;
	private ArrayList<MapCoordinates> coordinatesList;
	private int mapCount;

	public IpMapper()
	{
		allIPList = new ArrayList<String>();
		publicIPList = new ArrayList<String>();
		reservedList = new ArrayList<Integer>();
		coordinatesList = new ArrayList<MapCoordinates>();
		mapCount = 0;
	} 
	public void add(String item)
	{
		if(isReservedIP(item))
			reservedList.add(allIPList.size());   // storing indices of reserved IP addresses
		else
		{
			// saving public ip maps to file(pattern : locationx.png (where x =0,1,2,...)
			this.publicIPList.add(item);
			IP_Location location = new IP_Location(item);
			//location.displayLocationMap(item);
			location.getNode().saveLocationMapFile("location"+mapCount+".png");
			this.mapCount++;
		}
		
		if(exists(item))	// If item is already available in allIPList, return
			return;
		else
			allIPList.add(item);
	}
	public int getCount()
	{
		return allIPList.size();		// get the total number of IP Addresses, including reserved IPs
	}
	private boolean exists(String item)
	{
		for(String temp : allIPList)
		{	if(temp.equals(item))
			{
				return true;
			}
		}
		return false;
	}
	/* path mapping happens here */
	public void displayMap()
	{
		String baseURL = "https://maps.googleapis.com/maps/api/staticmap?maptype=terrain&scale=2&size=900x900&zoom=2";
		String API_KEY = "&key="+NetworkNode.getAPIKey();
		String parameters = "";
		IP_Location tempLocation;
		int count = 0;
		int firstLastCount = 0;
		for(String ip : allIPList)
		{
			if(!isReservedIP(ip))
			{
				tempLocation = new IP_Location(ip);
				MapCoordinates tempCoord  = tempLocation.getNode().getMapCoordinates();
				coordinatesList.add(tempCoord);
				if(firstLastCount == 0)
				{
					parameters = parameters + convertToURL(tempCoord,(char)('A'+count),"start");
				}
				else if(count == allIPList.size()-1)
				{
					parameters = parameters + convertToURL(tempCoord,(char)('A'+count),"end");
				}
				else
				{
					parameters = parameters + convertToURL(tempCoord,(char)('A'+count),"intermediate");
				}
				System.out.println((char)('A'+count)+" : "+ip);
				firstLastCount++;
			}
			else
			{
				System.out.println(">> Reserved IP : "+ip);
			}
			count++;
		}
		String finalURL = baseURL+parameters+getPathURL()+API_KEY;
		System.out.println("final url: "+finalURL);
		this.displayImageFromURL(finalURL);
	}
	public String convertToURL(MapCoordinates coord,char markerLabel,String colorIndicator)
	{
		String url;
		String color = "";
		if(colorIndicator.equals("intermediate"))
			color = "red";
		else if(colorIndicator.equals("start"))
			color = "blue";
		else if(colorIndicator.equals("end"))
			color = "green";
		else 
			color = "white";
		url = "&markers=color:"+color+"%7Csize:mid%7Clabel:"+markerLabel+"%7C"+coord.getLatitude()+","+coord.getLongitude();
		System.out.println("Converted URL : "+url);
		return url;
	}
	public boolean isReservedIP(String ipAddr)
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
	private String getPathURL()
	{
		String url = "&path=color:black%7Cweight:4%opacity:100";
		for(MapCoordinates pos : coordinatesList)
		{
			url = url + "%7C"+pos.getLatitude()+","+pos.getLongitude();
		}
		return url;
	}
	private void displayImageFromURL(String imageURL)
	{
		try
		{
			ImageIcon icon = getLocationMap(imageURL);
			JTable traceTable = getTraceTable();
			addTableEventHandler(traceTable);
			JFrame frame = new JFrame("IP Address Map");
			JLabel label = new JLabel(icon);
			JPanel tablePanel = new JPanel();
			tablePanel.add(new JScrollPane(traceTable));
			tablePanel.setPreferredSize(new Dimension(700,200));
			frame.getContentPane().add(label);
			frame.getContentPane().add(tablePanel,BorderLayout.SOUTH);
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	public ImageIcon getLocationMap(String imageURL)
	{
		try
		{
			Image image = null;
			URL url = new URL(imageURL);
			image = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(image);
			return icon;
		}
		catch(Exception e)
		{
			System.out.println("IpMapper.getLocationMap() > ERROR : "+e);
			System.exit(0);
		}
		return null;
	}
	@SuppressWarnings("rawtypes")
	public JTable getTraceTable()
	{
		JTable infoTable = null;
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector> rowData = new Vector<Vector>();
		
		columnNames.addElement("IP Address");
		columnNames.addElement("Node ID");
		columnNames.addElement("Reserved ? ");
		columnNames.addElement("Country");
		columnNames.addElement("Organization");

		char t = 'A';
		int i=0;
		NetworkNode nodeInfo = null;
		for(String temp : allIPList)
		{
			Vector<String> tempRowData = new Vector<String>();
			nodeInfo = new IP_Location(temp).getNode();
			tempRowData.addElement(temp);
			tempRowData.addElement(((char)(t+i))+"");
			tempRowData.addElement(isReservedIP(temp)+"");
			if(!isReservedIP(temp))
			{
				nodeInfo = new IP_Location(temp).getNode();
				tempRowData.addElement(nodeInfo.getCountryName());
				tempRowData.addElement(nodeInfo.getOrg());
			}
			else
			{
				tempRowData.addElement("#");
				tempRowData.addElement("#");
			}
			rowData.add(tempRowData);
			i++;
		}
		infoTable = new JTable(rowData,columnNames);
		infoTable.setRowHeight(20);
		return infoTable;
	}
	public int getMapCount()
	{
		return this.mapCount;
	}
	public void addTableEventHandler(JTable table)
	{
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				
				System.out.println("[D] Is Reserved IP ? "+target.getValueAt(row, 2));
				
				String ip = (String) target.getValueAt(row,0);
				try
				{
					File f = new File("location"+row+".png");
					if(f.exists())
					{
						NetworkNode netNode = new IP_Location(ip).getNode();
						IpDetailsViewer viewer = new IpDetailsViewer(ip,netNode,row);
					}
					else
					{
						System.out.println("[!] ERROR : Details of ip not available : "+ip);
						return;
					}
				}
				catch(Exception ex)
				{
					System.out.println("IpMapper.addTableEventHandler : "+ex);
					System.exit(0);
				}
			}
		});
	}
}