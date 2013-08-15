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
	}

	public void setProofFigure(ProofFigure pf)
	{
		this.pf = pf;
		repaint();
	}

	protected void paintComponent(Graphics g)
	{
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (pf != null)
		{
			g.setColor(Color.BLACK);
			pf.layout(g);
			pf.drawCenter(g, 0, 0, getWidth(), getHeight());
		}
	}
}
