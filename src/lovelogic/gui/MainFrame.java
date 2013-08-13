package lovelogic.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private ProofFigurePanel pfPanel;

	public MainFrame()
	{
		setTitle("LK Prover");

		pfPanel = new ProofFigurePanel();
		add(pfPanel, BorderLayout.CENTER);

		pack();
	}

	public static void main(String[] args)
	{
		System.out.println("GUI test.");

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				MainFrame f = new MainFrame();
				f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		});
	}
}
