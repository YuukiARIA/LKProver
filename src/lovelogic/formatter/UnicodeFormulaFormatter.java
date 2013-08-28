package lovelogic.formatter;

import lovelogic.syntax.Formula.Literal;

public class UnicodeFormulaFormatter extends FormulaFormatter
{
	private static UnicodeFormulaFormatter instance;

	private UnicodeFormulaFormatter() { }

	protected OperatorSet getOperatorSet()
	{
		return OperatorSet.getUnicodeSet();
	}

	protected void appendLiteral(StringBuilder buf, Literal x)
	{
		buf.append(x.name);
	}

	public static UnicodeFormulaFormatter getInstance()
	{
		if (instance == null)
		{
			instance = new UnicodeFormulaFormatter();
		}
		return instance;
	}
}
