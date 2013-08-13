package lovelogic.syntax.parser;

import lovelogic.syntax.parser.exception.LexerException;

public class Lexer
{
	private char[] cs;
	private int p;
	private int column;

	public Lexer(String text)
	{
		cs = text.toCharArray();
	}

	public Token lex() throws LexerException
	{
		skipws();

		if (end())
		{
			return new Token(Kind.END, "$", p + 1);
		}

		column = p + 1;

		switch (peek())
		{
		case '(':
			succ();
			return token(Kind.L_PAREN, "(");
		case ')':
			succ();
			return token(Kind.R_PAREN, ")");
		case '&':
			succ();
			return token(Kind.AND, "&");
		case '*':
			succ();
			return token(Kind.AND, "*");
		case '|':
			succ();
			return token(Kind.OR, "|");
		case '+':
			succ();
			return token(Kind.OR, "+");
		case '=':
			succ();
			if (peek() == '>')
			{
				succ();
				return token(Kind.IMPLY, "=>");
			}
			throw new RuntimeException();
		case '<':
			succ();
			if (peek() == '=')
			{
				succ();
				if (peek() == '>')
				{
					succ();
					return token(Kind.EQUIV, "<=>");
				}
			}
			throw new RuntimeException();
		case '/':
			succ();
			if (peek() == '\\')
			{
				succ();
				return token(Kind.AND, "/\\");
			}
			throw new RuntimeException();
		case '\\':
			succ();
			if (peek() == '/')
			{
				succ();
				return token(Kind.OR, "\\/");
			}
			throw new RuntimeException();
		case '!':
			succ();
			return token(Kind.NOT, "!");
		case '~':
			succ();
			return token(Kind.NOT, "~");
		}

		if (isAlpha(peek()) || isDigit(peek()))
		{
			StringBuilder buf = new StringBuilder();
			while (isAlpha(peek()) || isDigit(peek()))
			{
				buf.append(peek());
				succ();
			}
			return token(Kind.LITERAL, buf.toString());
		}

		throw new LexerException("illegal character '" + peek() + "'", column);
	}

	private Token token(Kind kind, String text)
	{
		return new Token(kind, text, column);
	}

	private void skipws()
	{
		while (!end() && Character.isWhitespace(peek())) succ();
	}

	private char peek()
	{
		return !end() ? cs[p] : 0;
	}

	private void succ()
	{
		if (!end()) ++p;
	}

	private boolean end()
	{
		return p >= cs.length;
	}

	private static boolean isAlpha(char c)
	{
		return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
	}

	private static boolean isDigit(char c)
	{
		return '0' <= c && c <= '9';
	}
}
