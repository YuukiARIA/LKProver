package lovelogic.syntax;

import lovelogic.syntax.parser.Parser;
import lovelogic.syntax.parser.exception.ParserException;

public abstract class Formula
{
	public static Formula parse(String s) throws ParserException
	{
		Parser parser = new Parser(s);
		return parser.parse();
	}

	public Equiv equiv(Formula x)
	{
		return new Equiv(this, x);
	}

	public Imply imply(Formula x)
	{
		return new Imply(this, x);
	}

	public Or or(Formula x)
	{
		return new Or(this, x);
	}

	public And and(Formula x)
	{
		return new And(this, x);
	}

	public Not not()
	{
		return new Not(this);
	}

	public abstract <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param);

	protected boolean rightAssoc()
	{
		return false;
	}

	protected abstract int prec();

	public String toString()
	{
		return FormulaStringBuilder.toString(this);
	}

	public static abstract class Binary extends Formula
	{
		public final Formula x;
		public final Formula y;

		public Binary(Formula x, Formula y)
		{
			this.x = x;
			this.y = y;
		}

		public int hashCode()
		{
			return 17 * x.hashCode() + 31 * y.hashCode();
		}

		public boolean equals(Object o)
		{
			return o == this || getClass() == o.getClass() && bothEq((Binary)o);
		}

		protected boolean bothEq(Binary b)
		{
			return x.equals(b.x) && y.equals(b.y);
		}
	}

	public static class Equiv extends Binary
	{
		public Equiv(Formula x, Formula y)
		{
			super(x, y);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 0;
		}
	}

	public static class Imply extends Binary
	{
		public Imply(Formula x, Formula y)
		{
			super(x, y);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 1;
		}

		protected boolean rightAssoc()
		{
			return true;
		}
	}

	public static class Or extends Binary
	{
		public Or(Formula x, Formula y)
		{
			super(x, y);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 2;
		}
	}

	public static class And extends Binary
	{
		public And(Formula x, Formula y)
		{
			super(x, y);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 3;
		}
	}

	public static class Not extends Formula
	{
		public final Formula x;

		public Not(Formula x)
		{
			this.x = x;
		}

		public int hashCode()
		{
			return 17 * x.hashCode();
		}

		public boolean equals(Object o)
		{
			return o == this || o instanceof Not && x.equals(((Not)o).x);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 4;
		}
	}

	public static class Literal extends Formula
	{
		public final String name;

		public Literal(String name)
		{
			this.name = name;
		}

		public int hashCode()
		{
			return name.hashCode();
		}

		public boolean equals(Object o)
		{
			return o == this || o instanceof Literal && name.equals(((Literal)o).name);
		}

		public <TParam, TRet> TRet accept(Visitor<TParam, TRet> visitor, TParam param)
		{
			return visitor.visit(this, param);
		}

		protected int prec()
		{
			return 5;
		}
	}

	public interface Visitor<TParam, TRet>
	{
		public TRet visit(Equiv eqv, TParam param);
		public TRet visit(Imply imp, TParam param);
		public TRet visit(Or or, TParam param);
		public TRet visit(And and, TParam param);
		public TRet visit(Not not, TParam param);
		public TRet visit(Literal lit, TParam param);
	}
}
