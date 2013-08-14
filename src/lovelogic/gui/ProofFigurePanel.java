package lovelogic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import lovelogic.gui.figure.ProofFigure;

@SuppressWarnings("serial")
public class ProofFigurePanel extends JPanel
{
	private ProofFigure pf;

	public ProofFigurePanel()
	{
		setPreferredSize(new Dimension(500, 500));

		ProofFigure a1 = new ProofFigure("Axiom1");
		ProofFigure a2 = new ProofFigure("Axiom2");
		ProofFigure a3 = new ProofFigure("Axiom3");
		ProofFigure d1 = new ProofFigure("Sub Goal", "deduce", a1, a2);
		pf = new ProofFigure("Goal", "deduce", a3, d1);
	}

	protected void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		pf.draw(g);
	}
}
