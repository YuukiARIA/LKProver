package lovelogic.syntax.parser;

final class Token
{
	public final Kind kind;
	public final String text;
	public final int column;

	public Token(Kind kind, String text, int column)
	{
		this.kind = kind;
		this.text = text;
		this.column = column;
	}
}
