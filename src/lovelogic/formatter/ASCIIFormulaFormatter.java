package lovelogic.formatter;

import lovelogic.syntax.Formula.Literal;

public class ASCIIFormulaFormatter extends FormulaFormatter
{
	private static ASCIIFormulaFormatter instance;

	private ASCIIFormulaFormatter() { }

	protected OperatorSet getOperatorSet()
	{
		return OperatorSet.getASCIISet();
	}

	protected void appendLiteral(StringBuilder buf, Literal x)
	{
		buf.append(x.name);
	}

	public static ASCIIFormulaFormatter getInstance()
	{
		if (instance == null)
		{
			instance = new ASCIIFormulaFormatter();
		}
		return instance;
	}
}
