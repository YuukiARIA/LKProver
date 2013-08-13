package lovelogic.gui.figure;

import java.util.Arrays;
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

	public abstract <TParam> void accept(VisitorP<TParam> visitor, TParam param);

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

		public ProofFigure getSubFigure(int i)
		{
			return subFigures.get(i);
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

		public <TParam> void accept(VisitorP<TParam> visitor, TParam param)
		{
			visitor.visit(this, param);
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

		public <TParam> void accept(VisitorP<TParam> visitor, TParam param)
		{
			visitor.visit(this, param);
		}
	}

	public interface VisitorP<TParam>
	{
		public void visit(DeductionNode node, TParam param);
		public void visit(AxiomNode node, TParam param);
	}
}
