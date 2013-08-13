package lovelogic.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
}
