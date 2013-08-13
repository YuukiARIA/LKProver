package lovelogic.sequent;

import java.util.List;

import lovelogic.syntax.Formula;
import lovelogic.syntax.Formula.And;
import lovelogic.syntax.Formula.Equiv;
import lovelogic.syntax.Formula.Imply;
import lovelogic.syntax.Formula.Literal;
import lovelogic.syntax.Formula.Not;
import lovelogic.syntax.Formula.Or;
import util.Pair;

public class Deducer
{
	private static class Param extends Pair<Sequent, List<SequentList>>
	{
		public Param(Sequent fst, List<SequentList> snd)
		{
			super(fst, snd);
		}
	}

	private static VisitorImpl visitor = new VisitorImpl();

	public static void getDeductionList(Sequent s, Formula a, List<SequentList> results)
	{
		a.accept(visitor, new Param(s, results));
	}

	private static class VisitorImpl implements Formula.Visitor<Param, Void>
	{
		public Void visit(Equiv a, Param p)
		{
			Formula b = a.x.imply(a.y).and(a.y.imply(a.x));
			Sequent s = p.fst;
			if (s.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(s.copy().removeLeft(a).addLeft(b));
				p.snd.add(seqs);
			}
			else if (s.containsRight(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(s.copy().removeRight(a).addRight(b));
				p.snd.add(seqs);
			}
			return null;
		}

		public Void visit(Imply a, Param p)
		{
			Sequent s = p.fst;
			if (s.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				Sequent s0 = s.copy().removeLeft(a);

				seqs.addSequent(s0.copy().addRight(a.x));
				seqs.addSequent(s0.copy().addLeft(a.y));

				p.snd.add(seqs);
			}
			else if (s.containsRight(a))
			{
				SequentList seqs = new SequentList();
				Sequent s1 = s.copy().removeRight(a).addLeft(a.x).addRight(a.y);
				seqs.addSequent(s1);
				p.snd.add(seqs);
			}
			return null;
		}

		public Void visit(Or a, Param p)
		{
			Sequent s = p.fst;
			if (s.containsLeft(a))
			{
				SequentList seqs = new SequentList();

				Sequent s0 = s.copy().removeLeft(a);

				seqs.addSequent(s0.copy().addLeft(a.x));
				seqs.addSequent(s0.copy().addLeft(a.y));

				p.snd.add(seqs);
			}
			else if (s.containsRight(a))
			{
				Sequent s0 = s.copy().removeRight(a);

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.x));
					p.snd.add(seqs);
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.y));
					p.snd.add(seqs);
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.x, a.y));
					p.snd.add(seqs);
				}
			}
			return null;
		}

		public Void visit(And a, Param p)
		{
			Sequent s = p.fst;
			if (s.containsLeft(a))
			{
				Sequent s0 = s.copy().removeLeft(a);

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.x));
					p.snd.add(seqs);
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.y));
					p.snd.add(seqs);
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.x, a.y));
					p.snd.add(seqs);
				}
			}
			else if (s.containsRight(a))
			{
				SequentList seqs = new SequentList();

				Sequent s0 = s.copy().removeRight(a);

				seqs.addSequent(s0.copy().addRight(a.x));
				seqs.addSequent(s0.copy().addRight(a.y));

				p.snd.add(seqs);
			}
			return null;
		}

		public Void visit(Not a, Param p)
		{
			Sequent s = p.fst;
			if (s.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(s.copy().removeLeft(a).addRight(a.x));
				p.snd.add(seqs);
			}
			else if (s.containsRight(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(s.copy().removeRight(a).addLeft(a.x));
				p.snd.add(seqs);
			}
			return null;
		}

		public Void visit(Literal a, Param p)
		{
			return null;
		}
	}
}
