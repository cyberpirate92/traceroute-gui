import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	JPanel queryPanel,imagePanel,tablePanel;
	JTextField queryField;
	JButton queryButton;
	JRadioButton mapRadio,nodeRadio;
	JTable infoTable;
	ButtonGroup radioGroup;
	
	public MainPanel()
	{
		super();
		queryPanel = new JPanel();
		imagePanel = new JPanel();
		tablePanel = new JPanel();
		initializeQueryPanel();
		initializeImagePanel();
		initializeTablePanel();
		this.setLayout(new BorderLayout());
		this.add(queryPanel,BorderLayout.NORTH);
		this.add(imagePanel,BorderLayout.CENTER);
		this.add(tablePanel,BorderLayout.SOUTH);
	}
	public void initializeQueryPanel()
	{
		queryField = new JTextField(25);
		queryButton = new JButton("Trace");
		queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioGroup = new ButtonGroup();
		mapRadio = new JRadioButton("Map",true);
		nodeRadio = new JRadioButton("Node");

		radioGroup.add(mapRadio);
		radioGroup.add(nodeRadio);

		queryPanel.add(queryField);
		queryPanel.add(queryButton);
		queryPanel.add(mapRadio);
		queryPanel.add(nodeRadio);
	}
	public void initializeImagePanel()
	{

	}
	@SuppressWarnings("rawtypes")
	public void initializeTablePanel()
	{
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("IP-Address");
		columnNames.addElement("Node ID");

		Vector<String> row1 = new Vector<String>();
		Vector<String> row2 = new Vector<String>();
		
		row1.addElement("172.16.25.1");
		row1.addElement("A");
		row2.addElement("27.251.123.12");
		row2.addElement("B");

		Vector<Vector> rowData = new Vector<Vector>();
		rowData.addElement(row1);
		rowData.addElement(row2);

		infoTable = new JTable(rowData,columnNames);
		infoTable.setRowHeight(25);
		infoTable.doLayout();
		tablePanel.add(new JScrollPane(infoTable));
	}
}