import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PacketTrackerGUI extends JFrame
{
	JPanel titlePanel,buttonPanel,tablePanel,mapPanel,queryPanel;
	JButton ipButtons[];
	JTextField queryField;
	NetworkDiagnostics nd;
	JProgressBar progressBar;

	public PacketTrackerGUI()
	{
		super();
		titlePanel = new JPanel();
		buttonPanel = new JPanel();
		tablePanel = new JPanel();
		mapPanel = new JPanel();
		queryPanel = new JPanel();
		queryField = new JTextField(20);
		progressBar = new JProgressBar();
		nd = new NetworkDiagnostics();

		initLayout();
		initContent();

		// just some inits
		progressBar.setMinimum(1);
		progressBar.setMaximum(100);

		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void initLayout()
	{
		JPanel centerPanel1 = new JPanel(), centerPanel2 = new JPanel();			// temporary
		this.setLayout(new BorderLayout());
		getContentPane().add(titlePanel,BorderLayout.NORTH);
		getContentPane().add(centerPanel1,BorderLayout.CENTER);

		centerPanel1.setLayout(new BorderLayout());
		centerPanel1.add(buttonPanel,BorderLayout.EAST);
		centerPanel1.add(centerPanel2,BorderLayout.CENTER);
		centerPanel1.add(progressBar,BorderLayout.SOUTH);

		centerPanel2.setLayout(new BorderLayout());
		centerPanel2.add(queryPanel,BorderLayout.NORTH);
		centerPanel2.add(tablePanel,BorderLayout.SOUTH);
		centerPanel2.add(mapPanel,BorderLayout.CENTER);
	}

	public void initContent()
	{
		//Title Panel
		JLabel title = new JLabel("Network Mapper");
		Font f = new Font("Arial",20,Font.BOLD);
		title.setFont(f);
		titlePanel.add(title);

		// Query Panel
		JButton okButton = new JButton("Map");
		queryPanel.add(queryField);
		queryPanel.add(okButton);
		okButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				class ProgressWorker extends SwingWorker<String,Void>
				{
					protected String doInBackground()
					{
						System.out.println("Initializing Progress Bar..");
						progressBar.setVisible(true);
						progressBar.setIndeterminate(true);
						return "";
					}
				}
				new ProgressWorker().execute();
				String query = queryField.getText();
				System.out.println("Query : "+query);
				nd.map(query);
			}
		});
	}

}
