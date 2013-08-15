package lovelogic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import lovelogic.gui.figure.ProofFigure;

@SuppressWarnings("serial")
public class ProofFigurePanel extends JPanel
{
	private ProofFigure pf;

	public ProofFigurePanel()
	{
		setPreferredSize(new Dimension(500, 500));

		ProofFigure a1 = new ProofFigure("A");
		ProofFigure a2 = new ProofFigure("A => B");
		ProofFigure a3 = new ProofFigure("C");
		ProofFigure d1 = new ProofFigure("B", "(E-Imp)", a1, a2);
		pf = new ProofFigure("B /\\ C", "(I-Conj)", d1, a3);
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
