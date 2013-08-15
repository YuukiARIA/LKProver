package lovelogic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import util.MTree;

import lovelogic.gui.figure.ProofFigure;
import lovelogic.gui.figure.ProofFigureBuilder;
import lovelogic.prover.Prover;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.ParserException;

@SuppressWarnings("serial")
public class ProofFigurePanel extends JPanel
{
	private ProofFigure pf;

	public ProofFigurePanel()
	{
		setPreferredSize(new Dimension(500, 500));

		try
		{
			Formula x = Formula.parse("~((A \\/ B) /\\ (A \\/ ~B) /\\ (~A \\/ B) /\\ (~A \\/ ~B))");
			Sequent s = Sequent.createGoal(x);
			MTree<Sequent> proof = Prover.findProof(s);
			if (proof != null)
			{
				System.out.println("Proved.");
				pf = ProofFigureBuilder.build(proof);
			}
			else
			{
				System.out.println("Unprovable.");
			}
		}
		catch (ParserException e)
		{
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g)
	{
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		pf.layout(g);
		pf.drawCenter(g, 0, 0, getWidth(), getHeight());
	}
}
