package lovelogic.formatter;

public final class OperatorSet
{
	private static OperatorSet asciiSet;
	private static OperatorSet unicodeSet;
	private static OperatorSet latexSet;

	public final String opEqv;
	public final String opImp;
	public final String opOr;
	public final String opAnd;
	public final String opNot;

	public OperatorSet(String opEqv, String opImp, String opOr, String opAnd, String opNot)
	{
		this.opEqv = opEqv;
		this.opImp = opImp;
		this.opOr = opOr;
		this.opAnd = opAnd;
		this.opNot = opNot;
	}

	public static OperatorSet getASCIISet()
	{
		if (asciiSet == null)
		{
			asciiSet = new OperatorSet("<=>", "=>", "\\/", "/\\", "~");
		}
		return asciiSet;
	}

	public static OperatorSet getUnicodeSet()
	{
		if (unicodeSet == null)
		{
			unicodeSet = new OperatorSet("⇔", "⇒", "∨", "∧", "¬");
		}
		return unicodeSet;
	}

	public static OperatorSet getLaTeXSet()
	{
		if (latexSet == null)
		{
			latexSet = new OperatorSet("\\Leftrightarrow", "\\Rightarrow", "\\vee", "\\wedge", "\\neg");
		}
		return latexSet;
	}
}
