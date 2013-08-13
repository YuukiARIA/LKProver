package lovelogic.syntax.parser.exception;

@SuppressWarnings("serial")
public class ParserException extends Exception
{
	private int column;

	public ParserException(String message, int column)
	{
		super(message);
		this.column = column;
	}

	public int getColumn()
	{
		return column;
	}
}
