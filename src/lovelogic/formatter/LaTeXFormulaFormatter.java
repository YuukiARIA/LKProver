package lovelogic.formatter;

import lovelogic.syntax.Formula.Literal;

public class LaTeXFormulaFormatter extends FormulaFormatter
{
	private static LaTeXFormulaFormatter instance;

	private LaTeXFormulaFormatter() { }

	protected OperatorSet getOperatorSet()
	{
		return OperatorSet.getLaTeXSet();
	}

	protected void appendLiteral(StringBuilder buf, Literal x)
	{
		buf.append("\\mathit{" + x.name + "}");
	}

	public static LaTeXFormulaFormatter getInstance()
	{
		if (instance == null)
		{
			instance = new LaTeXFormulaFormatter();
		}
		return instance;
	}
}
