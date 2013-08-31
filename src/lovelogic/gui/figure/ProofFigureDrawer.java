package lovelogic.gui.figure;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class ProofFigureDrawer
{
	private boolean needLayout;
	private Font font;
	private ProofFigure pf;

	public void setProofFigure(ProofFigure pf)
	{
		this.pf = pf;
		needLayout = true;
	}

	public void setFont(Font font)
	{
		this.font = font;
		needLayout = true;
	}

	public Font getFont()
	{
		return font;
	}

	public Dimension getSize()
	{
		if (pf != null)
		{
			return new Dimension(pf.getWholeWidth(), pf.getWholeHeight());
		}
		return new Dimension(0, 0);
	}

	public void drawCenter(Graphics g, int x, int y, int width, int height)
	{
		if (pf == null)
		{
			return;
		}

		layout(g);

		Font old = g.getFont();
		if (font != null)
		{
			g.setFont(font);
		}
		pf.drawCenter(g, x, y, width, height);
		g.setFont(old);
	}

	public void layout(Graphics g)
	{
		if (pf != null && needLayout)
		{
			Font old = g.getFont();
			if (font != null)
			{
				g.setFont(font);
			}
			pf.layout(g);
			g.setFont(old);
			needLayout = false;
		}
	}
}
