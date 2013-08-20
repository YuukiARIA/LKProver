package lovelogic.sequent;

import java.util.HashSet;
import java.util.Set;

import lovelogic.latex.LaTeXStringBuilder;
import lovelogic.syntax.Formula;
import util.StringUtils;

public class Sequent
{
	private Set<Formula> left;
	private Set<Formula> right;

	public Sequent()
	{
		this(new HashSet<Formula>(), new HashSet<Formula>());
	}

	private Sequent(Set<Formula> left, Set<Formula> right)
	{
		this.left = left;
		this.right = right;
	}

	public Sequent copy()
	{
		return new Sequent(new HashSet<Formula>(left), new HashSet<Formula>(right));
	}

	public Sequent addLeft(Formula ... as)
	{
		for (Formula a : as)
		{
			left.add(a);
		}
		return this;
	}

	public Sequent removeLeft(Formula f)
	{
		left.remove(f);
		return this;
	}

	public boolean containsLeft(Formula f)
	{
		return left.contains(f);
	}

	public Sequent addRight(Formula ... as)
	{
		for (Formula a : as)
		{
			right.add(a);
		}
		return this;
	}

	public Sequent removeRight(Formula f)
	{
		right.remove(f);
		return this;
	}

	public boolean containsRight(Formula f)
	{
		return right.contains(f);
	}

	public boolean isAxiom()
	{
		for (Formula a : left)
		{
			if (right.contains(a))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isIntuitionistic()
	{
		return right.size() <= 1;
	}

	public String toString()
	{
		String s1 = StringUtils.join(left, ", ");
		String s2 = StringUtils.join(right, ", ");
		return s1 + " |- " + s2;
	}

	public String toLaTeXString()
	{
		String s1 = StringUtils.join(left, ",\\ ", LaTeXStringBuilder.getStringConverter());
		String s2 = StringUtils.join(right, ",\\ ", LaTeXStringBuilder.getStringConverter());
		return "\\strut " + s1 + " \\ \\vdash \\ " + s2;
	}

	public static Sequent createGoal(Formula formula)
	{
		Sequent s = new Sequent();
		s.right.add(formula);
		return s;
	}

	public Set<Formula> getAllFormulae()
	{
		Set<Formula> set = new HashSet<Formula>(left);
		set.addAll(right);
		return set;
	}
}
