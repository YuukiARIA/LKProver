package lovelogic.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import lovelogic.gui.figure.ProofFigure;
import lovelogic.gui.figure.ProofFigureBuilder;
import lovelogic.prover.ProofStep;
import lovelogic.prover.Prover;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.ParserException;
import util.MTree;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private ProofFigurePanel pfPanel;
	private JTextField textInput;
	private JButton buttonProve;

	public MainFrame()
	{
		setTitle("LK Prover");

		pfPanel = new ProofFigurePanel();
		add(pfPanel, BorderLayout.CENTER);

		textInput = new JTextField();
		buttonProve = new JButton("Prove");
		buttonProve.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String input = textInput.getText();
				prove(input);
			}
		});

		JPanel panelBottom = new JPanel(new BorderLayout());
		panelBottom.add(textInput, BorderLayout.CENTER);
		panelBottom.add(buttonProve, BorderLayout.EAST);
		add(panelBottom, BorderLayout.SOUTH);

		pack();
	}

	private void prove(String input)
	{
		try
		{
			Formula x = Formula.parse(input);
			Sequent s = Sequent.createGoal(x);
			MTree<ProofStep> proof = Prover.findMinimumProof(s);
			if (proof != null)
			{
				ProofFigure pf = ProofFigureBuilder.build(proof);
				pfPanel.setProofFigure(pf);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Unprovable", "Result", JOptionPane.INFORMATION_MESSAGE);
				pfPanel.setProofFigure(null);
			}
		}
		catch (ParserException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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
