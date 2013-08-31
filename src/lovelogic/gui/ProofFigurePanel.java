package lovelogic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.JPanel;

import lovelogic.gui.figure.ProofFigure;
import lovelogic.gui.figure.ProofFigureDrawer;

@SuppressWarnings("serial")
public class ProofFigurePanel extends JPanel
{
	private ProofFigureDrawer drawer = new ProofFigureDrawer();

	public ProofFigurePanel()
	{
		setFigureFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
	}

	public void setFigureFont(Font font)
	{
		drawer.setFont(font);
	}

	public Font getFigureFont()
	{
		return drawer.getFont();
	}

	public void setProofFigure(ProofFigure pf)
	{
		drawer.setProofFigure(pf);
		Graphics g = getGraphics();
		if (g != null)
		{
			drawer.layout(g);
			setPreferredSize(drawer.getSize());
			g.dispose();
		}
		revalidate();
		repaint();
	}

	public RenderedImage getDrawingAsImage()
	{
		BufferedImage dummy = new BufferedImage(1, 1, Transparency.OPAQUE);
		Graphics gdummy = dummy.getGraphics();
		((Graphics2D)gdummy).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		drawer.layout(gdummy);
		gdummy.dispose();

		Dimension size = drawer.getSize();
		int width = size.width;
		int height = size.height;
		BufferedImage image = new BufferedImage(width, height, Transparency.OPAQUE);
		Graphics g = image.getGraphics();
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		drawContents(g, width, height);
		g.dispose();
		return image;
	}

	private void drawContents(Graphics g, int width, int height)
	{
		g.setColor(Color.BLACK);
		drawer.drawCenter(g, 0, 0, width, height);
	}

	protected void paintComponent(Graphics g)
	{
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		drawContents(g, getWidth(), getHeight());
	}
}
