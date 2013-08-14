package lovelogic.gui.figure;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProofFigure
{
	private int x;
	private int y;
	private String content;
	private String deductionName;
	private List<ProofFigure> subFigures;

	public ProofFigure(String content)
	{
		this(content, "(Axiom)");
	}

	public ProofFigure(String content, String deductionName, ProofFigure ... subFigures)
	{
		this.content = content;
		this.deductionName = deductionName;
		this.subFigures = Collections.unmodifiableList(Arrays.asList(subFigures));
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getX()
	{
		return x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getY()
	{
		return y;
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

	public void draw(Graphics g)
	{
		for (ProofFigure sub : subFigures)
		{
			sub.draw(g);
		}
		g.drawString(content, x, y);
	}
}
