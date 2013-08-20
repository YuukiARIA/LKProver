package lovelogic.syntax;

import lovelogic.syntax.Formula.And;
import lovelogic.syntax.Formula.Binary;
import lovelogic.syntax.Formula.Equiv;
import lovelogic.syntax.Formula.Imply;
import lovelogic.syntax.Formula.Literal;
import lovelogic.syntax.Formula.Not;
import lovelogic.syntax.Formula.Or;

public class FormulaStringBuilder
{
	private static VisitorImpl visitor = new VisitorImpl();

	private FormulaStringBuilder() { }

	public static String toString(Formula e)
	{
		StringBuilder buf = new StringBuilder();
		buildString(e, buf);
		return buf.toString();
	}

	private static void buildString(Formula e, StringBuilder buf)
	{
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
			buildBinary(buf, eqv, "<=>");
			return null;
		}

		public Void visit(Imply imp, StringBuilder buf)
		{
			buildBinary(buf, imp, "=>");
			return null;
		}

		public Void visit(Or or, StringBuilder buf)
		{
			buildBinary(buf, or, "\\/");
			return null;
		}

		public Void visit(And and, StringBuilder buf)
		{
			buildBinary(buf, and, "/\\");
			return null;
		}

		public Void visit(Not not, StringBuilder buf)
		{
			boolean paren = not.x.prec() < not.prec();
			buf.append("~");
			parenString(buf, not.x, paren);
			return null;
		}

		public Void visit(Literal lit, StringBuilder buf)
		{
			buf.append(lit.name);
			return null;
		}
	}
}
