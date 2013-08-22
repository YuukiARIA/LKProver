package lovelogic.latex;

import lovelogic.prover.ProofStep;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import lovelogic.syntax.Formula.And;
import lovelogic.syntax.Formula.Binary;
import lovelogic.syntax.Formula.Equiv;
import lovelogic.syntax.Formula.Imply;
import lovelogic.syntax.Formula.Literal;
import lovelogic.syntax.Formula.Not;
import lovelogic.syntax.Formula.Or;
import util.MTree;
import util.StringUtils;
import util.strconv.IStringConverter;

public class LaTeXStringBuilder
{
	private static VisitorImpl visitor;
	private static LaTeXStringConverter converter;

	private LaTeXStringBuilder() { }

	public static String toLaTeX(MTree<ProofStep> prtree)
	{
		StringBuilder buf = new StringBuilder();
		toLaTeX(buf, prtree, 0);
		return buf.toString();
	}

	private static void toLaTeX(StringBuilder buf, MTree<ProofStep> prtree, int depth)
	{
		String indent = StringUtils.repeat(depth, " ");

		if (prtree.hasNoSubtree())
		{
			buf.append(indent);
			toString(buf, prtree.get().getSequent());
			buf.append('\n');
			return;
		}

		ProofStep pr = prtree.get();
		buf.append(indent + "\\infer");
		if (!pr.getDeductionName().isEmpty())
		{
			buf.append("[\\hbox{" + pr.getDeductionName() + "}]");
		}
		buf.append("{");
		toString(buf, pr.getSequent());
		buf.append("}\n");

		buf.append(indent + "{\n");
		boolean first = true;
		for (MTree<ProofStep> subtree : prtree.getSubtrees())
		{
			if (!first)
			{
				buf.append(indent + "&\n");
			}
			first = false;
			toLaTeX(buf, subtree, depth + 1);
		}
		buf.append(indent + "}\n");
	}

	private static void toString(StringBuilder buf, Sequent sequent)
	{
		if (converter == null)
		{
			converter = new LaTeXStringConverter();
		}
		buf.append("\\strut ");
		buf.append(StringUtils.join(sequent.getLeftFormulae(), ",\\ ", converter));
		buf.append(" \\ \\vdash \\ ");
		buf.append(StringUtils.join(sequent.getRightFormulae(), ",\\ ", converter));
	}

	public static String toString(Formula e)
	{
		StringBuilder buf = new StringBuilder();
		buildString(e, buf);
		return buf.toString();
	}

	private static void buildString(Formula e, StringBuilder buf)
	{
		if (visitor == null)
		{
			visitor = new VisitorImpl();
		}
		e.accept(visitor, buf);
	}

	private static void parenString(StringBuilder buf, Formula e, boolean paren)
	{
		if (paren) buf.append('(');
		buildString(e, buf);
		if (paren) buf.append(')');
	}

	private static void buildBinary(StringBuilder buf, Binary e, String op)
	{
		boolean parenL = e.rightAssoc() ? e.x.prec() <= e.prec() : e.x.prec() < e.prec();
		boolean parenR = e.y.prec() < e.prec();
		parenString(buf, e.x, parenL);
		buf.append(' ');
		buf.append(op);
		buf.append(' ');
		parenString(buf, e.y, parenR);
	}

	public static class VisitorImpl implements Formula.Visitor<StringBuilder, Void>
	{
		public Void visit(Equiv eqv, StringBuilder buf)
		{
			buildBinary(buf, eqv, "\\Leftrightarrow");
			return null;
		}

		public Void visit(Imply imp, StringBuilder buf)
		{
			buildBinary(buf, imp, "\\Rightarrow");
			return null;
		}

		public Void visit(Or or, StringBuilder buf)
		{
			buildBinary(buf, or, "\\vee");
			return null;
		}

		public Void visit(And and, StringBuilder buf)
		{
			buildBinary(buf, and, "\\wedge");
			return null;
		}

		public Void visit(Not not, StringBuilder buf)
		{
			boolean paren = not.x.prec() < not.prec();
			buf.append("\\neg");
			parenString(buf, not.x, paren);
			return null;
		}

		public Void visit(Literal lit, StringBuilder buf)
		{
			buf.append("\\mathit{" + lit.name + "}");
			return null;
		}
	}

	private static class LaTeXStringConverter implements IStringConverter<Formula>
	{
		public String toString(Formula x)
		{
			return LaTeXStringBuilder.toString(x);
		}
	}
}
