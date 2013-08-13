package lovelogic.syntax.parser;

import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.LexerException;
import lovelogic.syntax.parser.exception.ParserException;

public class Parser
{
	private Lexer lexer;
	private Token token;

	public Parser(String text)
	{
		lexer = new Lexer(text);
	}

	public Formula parse() throws ParserException
	{
		succ();
		Formula e = equiv();
		if (token.kind != Kind.END)
		{
			throw new ParserException("extra tokens", token.column);
		}
		return e;
	}

	private Formula equiv() throws ParserException
	{
		Formula e = imp();
		while (token.kind == Kind.EQUIV)
		{
			succ();
			e = new Formula.Equiv(e, imp());
		}
		return e;
	}

	private Formula imp() throws ParserException
	{
		Formula e = or();
		if (token.kind == Kind.IMPLY)
		{
			succ();
			e = new Formula.Imply(e, imp());
		}
		return e;
	}

	private Formula or() throws ParserException
	{
		Formula e = and();
		while (token.kind == Kind.OR)
		{
			succ();
			e = new Formula.Or(e, and());
		}
		return e;
	}

	private Formula and() throws ParserException
	{
		Formula e = unary();
		while (token.kind == Kind.AND)
		{
			succ();
			e = new Formula.And(e, unary());
		}
		return e;
	}

	private Formula unary() throws ParserException
	{
		if (token.kind == Kind.NOT)
		{
			succ();
			return new Formula.Not(unary());
		}
		return primary();
	}

	private Formula primary() throws ParserException
	{
		switch (token.kind)
		{
		case L_PAREN:
			succ();
			Formula e = equiv();
			if (token.kind == Kind.R_PAREN)
			{
				succ();
				return e;
			}
			else
			{
				throw new ParserException("missing ')'", token.column);
			}
		case LITERAL:
			String name = token.text;
			succ();
			return new Formula.Literal(name);
		case END:
			throw new ParserException("unexpected end of input", token.column);
		default:
			throw new ParserException("unexpected token " + token.text, token.column);
		}
	}

	private void succ() throws ParserException
	{
		try
		{
			token = lexer.lex();
		}
		catch (LexerException e)
		{
			throw new ParserException("lexical error - " + e.getMessage(), e.getColumn());
		}
	}
}
