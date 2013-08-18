package lovelogic.gui.figure;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProofFigure
{
	private static final int MIN_H_GAP = 40;
	private static final int LABEL_GAP = 5;

	private int x;
	private int y;
	private int wholeHeight;
	private Rectangle contentBounds = new Rectangle();
	private int wholeWidth;
	private int labelWidth;
	private String content;
	private String deductionName;
	private List<ProofFigure> subFigures = new ArrayList<ProofFigure>();

	public ProofFigure(String content)
	{
		this.content = content;
		this.deductionName = "";
	}

	public ProofFigure(String content, String deductionName, ProofFigure ... subFigures)
	{
		this.content = content;
		this.deductionName = deductionName;
		this.subFigures.addAll(Arrays.asList(subFigures));
	}

	public void add(ProofFigure pf)
	{
		subFigures.add(pf);
	}

	public int getWholeWidth()
	{
		return wholeWidth;
	}

	public int getWholeHeight()
	{
		return wholeHeight;
	}

	public String getContent()
	{
		return content;
	}

	public String getDeductionName()
	{
		return deductionName;
	}

	public boolean isSubFiguresEmpty()
	{
		return subFigures.isEmpty();
	}

	public int countSubFigures()
	{
		return subFigures.size();
	}

	public Iterable<ProofFigure> getSubFigures()
	{
		return subFigures;
	}

	public int getMaxDepth()
	{
		int d = 0;
		for (ProofFigure sub : subFigures)
		{
			d = Math.max(sub.getMaxDepth(), d);
		}
		return d + 1;
	}

	protected boolean isAxiomNode()
	{
		return subFigures.isEmpty();
	}

	public void layout(Graphics g)
	{
		FontMetrics fm = g.getFontMetrics();
		calcSize(fm);
		locate(0, wholeHeight);
	}

	private int getLeftBottomX()
	{
		return subFigures.get(0).contentBounds.x;
	}

	private int getSubtreeOriginX()
	{
		return subFigures.get(0).x;
	}

	private int getRightBottomX()
	{
		ProofFigure r = subFigures.get(subFigures.size() - 1);
		return (int)r.contentBounds.getMaxX();
	}

	private int getSubBottomWidth()
	{
		ProofFigure l = subFigures.get(0);
		ProofFigure r = subFigures.get(subFigures.size() - 1);
		return r.contentBounds.x + r.contentBounds.width - l.contentBounds.x;
	}

	protected void calcSize(FontMetrics fm)
	{
		contentBounds.setSize(fm.stringWidth(content), fm.getHeight());

		int subHeightMax = 0;
		for (ProofFigure pf : subFigures)
		{
			pf.calcSize(fm);
			subHeightMax = Math.max(pf.wholeHeight, subHeightMax);
		}
		if (!subFigures.isEmpty())
		{
			labelWidth = fm.stringWidth(deductionName);
		}
		else
		{
			labelWidth = 0;
		}
		wholeHeight = subHeightMax + 2 * 4 + contentBounds.height;
	}

	protected void locate(int x0, int y0)
	{
		x = x0;
		y = y0;
		if (isAxiomNode())
		{
			contentBounds.x = x0;
			wholeWidth = contentBounds.width;
		}
		else
		{
			int subY = y0 - contentBounds.height - 2 * 4;
			locateSubtrees(x0, subY);
			contentBounds.x = getLeftBottomX() + (getSubBottomWidth() - contentBounds.width) / 2;
			x = Math.min(contentBounds.x, getSubtreeOriginX());
			translateX(x0 - x);
			wholeWidth = Math.max(getRightBottomX(), (int)contentBounds.getMaxX()) - x;
			if (labelWidth != 0)
			{
				wholeWidth += LABEL_GAP + labelWidth;
			}
			wholeWidth = Math.max(calcSubtreesWidth(), wholeWidth);
		}
		contentBounds.y = y0 - contentBounds.height;
	}

	private void locateSubtrees(int x0, int y0)
	{
		int x = x0;
		for (ProofFigure sub : subFigures)
		{
			sub.locate(x, y0);
			x += sub.wholeWidth + MIN_H_GAP;
		}
	}

	private int calcSubtreesWidth()
	{
		int w = (subFigures.size() - 1) * MIN_H_GAP;
		for (int i = 0; i < subFigures.size(); i++)
		{
			ProofFigure sub = subFigures.get(i);
			w += sub.wholeWidth;
		}
		return w;
	}

	private void translateX(int dx)
	{
		x += dx;
		contentBounds.x += dx;
		for (ProofFigure sub : subFigures)
		{
			sub.translateX(dx);
		}
	}

	public void drawCenter(Graphics g, int x, int y, int width, int height)
	{
		int x0 = x + (width - wholeWidth) / 2;
		int y0 = y + (height - wholeHeight) / 2;

		g.setColor(Color.BLACK);
		draw(g, x0, y0);
	}

	public void draw(Graphics g, int x, int y)
	{
		g.translate(x, y);
		draw(g);
		g.translate(-x, -y);
	}

	public void draw(Graphics g)
	{
		//g.setColor(new Color(230, 255, 230));
		//g.fillRect(contentBounds.x, contentBounds.y, contentBounds.width, contentBounds.height);

		//g.setColor(new Color(200, 200, 255));
		//g.drawRect(x, y - wholeHeight, wholeWidth, wholeHeight);

		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics();
		for (ProofFigure sub : subFigures)
		{
			sub.draw(g);
		}

		int baseLine = contentBounds.y + fm.getAscent();
		g.drawString(content, contentBounds.x, baseLine);
		if (!isAxiomNode())
		{
			g.drawLine(getLineLeft(), contentBounds.y - 4, getLineRight(), contentBounds.y - 4);
			g.drawString(deductionName, getLineRight() + LABEL_GAP, baseLine - 4 - fm.getHeight() / 2);
		}
	}

	private int getLineLeft()
	{
		int x = contentBounds.x;
		if (!subFigures.isEmpty())
		{
			x = Math.min(x, subFigures.get(0).contentBounds.x);
		}
		return x;
	}

	private int getLineRight()
	{
		int x = (int)contentBounds.getMaxX();
		if (!subFigures.isEmpty())
		{
			ProofFigure rightMost = subFigures.get(subFigures.size() - 1);
			x = Math.max(x, (int)rightMost.contentBounds.getMaxX());
		}
		return x;
	}
}
