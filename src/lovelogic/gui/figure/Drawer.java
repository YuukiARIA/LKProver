package lovelogic.gui.figure;

import java.awt.Graphics;

import lovelogic.gui.figure.ProofFigure.AxiomNode;
import lovelogic.gui.figure.ProofFigure.DeductionNode;

public class Drawer
{
	private static VisitorImpl visitor;

	private Drawer() { }

	public static void draw(ProofFigure pf, Graphics g)
	{
		if (visitor == null)
		{
			visitor = new VisitorImpl();
		}
		pf.accept(visitor, g);
	}

	private static class VisitorImpl implements ProofFigure.Visitor<Void, Graphics>
	{
		public Void visit(DeductionNode node, Graphics g)
		{
			for (ProofFigure sub : node.getSubFigures())
			{
				sub.accept(this, g);
			}
			return null;
		}

		public Void visit(AxiomNode node, Graphics g)
		{
			g.drawString(node.getContent(), node.getX(), node.getY());
			return null;
		}
	}
}
