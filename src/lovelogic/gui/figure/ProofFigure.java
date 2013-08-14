package lovelogic.gui.figure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ProofFigure
{
	private int x;
	private int y;
	private String content;

	public ProofFigure(String content)
	{
		this.content = content;
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

	public abstract int getMaxDepth();

	public abstract <T, U> T accept(Visitor<T, U> visitor, U param);

	public static class DeductionNode extends ProofFigure
	{
		private String deductionName;
		private List<ProofFigure> subFigures;

		public DeductionNode(String content, String deductionName, ProofFigure ... subFigures)
		{
			super(content);
			this.deductionName = deductionName;
			this.subFigures = Arrays.asList(subFigures);
		}

		public String getDeductionName()
		{
			return deductionName;
		}

		public int countSubFigures()
		{
			return subFigures.size();
		}

		public Iterable<ProofFigure> getSubFigures()
		{
			return Collections.unmodifiableCollection(subFigures);
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

		public <T, U> T accept(Visitor<T, U> visitor, U param)
		{
			return visitor.visit(this, param);
		}
	}

	public static class AxiomNode extends ProofFigure
	{
		public AxiomNode(String content)
		{
			super(content);
		}

		public int getMaxDepth()
		{
			return 1;
		}

		public <T, U> T accept(Visitor<T, U> visitor, U param)
		{
			return visitor.visit(this, param);
		}
	}

	public interface Visitor<T, U>
	{
		public T visit(DeductionNode node, U param);
		public T visit(AxiomNode node, U param);
	}
}
