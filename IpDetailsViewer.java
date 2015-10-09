import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class IpDetailsViewer extends JFrame 
{
	JPanel imagePanel,infoPanel;
	int locationCount;
	NetworkNode targetNode;

	public IpDetailsViewer(String title,NetworkNode node,int count) 
	{
		super(title);
		this.locationCount = count;
		this.targetNode = node;
		imagePanel = new JPanel();
		infoPanel = new JPanel();
		
		setLayouts();
		
		this.setSize(1000,700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void setLayouts()
	{
		JLabel tempLabel = new JLabel();
		tempLabel.setIcon(new ImageIcon("location"+this.locationCount+".png"));
		imagePanel.setLayout(new BorderLayout());
		imagePanel.add(tempLabel);
		
		infoPanel.setLayout(new GridLayout(10,2));
		infoPanel.add(new JLabel("Country Name : "));
		infoPanel.add(new JLabel(targetNode.getCountryName()));
		infoPanel.add(new JLabel("Country Code : "));
		infoPanel.add(new JLabel(targetNode.getCountryCode()));
		infoPanel.add(new JLabel("Region Name : "));
		infoPanel.add(new JLabel(targetNode.getRegionName()));
		infoPanel.add(new JLabel("Region Code : "));
		infoPanel.add(new JLabel(targetNode.getRegionCode()));
		infoPanel.add(new JLabel("City Name : "));
		infoPanel.add(new JLabel(targetNode.getCityName()));
		infoPanel.add(new JLabel("Latitude : "));
		infoPanel.add(new JLabel(targetNode.getLatitude()));
		infoPanel.add(new JLabel("Longitude : "));
		infoPanel.add(new JLabel(targetNode.getLongitude()));
		infoPanel.add(new JLabel("Time Zone : "));
		infoPanel.add(new JLabel(targetNode.geTimeZone()));
		infoPanel.add(new JLabel("ISP : "));
		infoPanel.add(new JLabel(targetNode.getISP()));
		infoPanel.add(new JLabel("Organization : "));
		infoPanel.add(new JLabel(targetNode.getOrg()));
		
		this.getContentPane().add(imagePanel,BorderLayout.CENTER);
		this.getContentPane().add(infoPanel,BorderLayout.EAST);
	}
}
