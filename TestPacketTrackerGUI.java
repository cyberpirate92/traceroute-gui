import javax.swing.SwingUtilities;

class TestPacketTrackerGUI
{
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new PacketTrackerGUI();
			}
		});
	}
}