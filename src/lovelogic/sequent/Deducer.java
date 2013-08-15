package lovelogic.sequent;

import java.util.List;

import lovelogic.prover.Deduction;
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

	public static void getDeductionList(List<Deduction> results, Sequent s, Formula a)
	{
		visitor.sequent = s;
		visitor.deductions = results;
		a.accept(visitor, null);
	}

	private static class VisitorImpl implements Formula.Visitor<Void, Void>
	{
		protected Sequent sequent;
		protected List<Deduction> deductions;

		public Void visit(Equiv a, Void p)
		{
			Formula b = a.x.imply(a.y).and(a.y.imply(a.x));
			if (sequent.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(sequent.copy().removeLeft(a).addLeft(b));
				deductions.add(Deduction.of(seqs, "(Unfold-L)"));
			}
			else if (sequent.containsRight(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(sequent.copy().removeRight(a).addRight(b));
				deductions.add(Deduction.of(seqs, "(Unfold-R)"));
			}
			return null;
		}

		public Void visit(Imply a, Void p)
		{
			if (sequent.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				Sequent s0 = sequent.copy().removeLeft(a);

				seqs.addSequent(s0.copy().addRight(a.x));
				seqs.addSequent(s0.copy().addLeft(a.y));

				deductions.add(Deduction.of(seqs, "(Imp-L)"));
			}
			else if (sequent.containsRight(a))
			{
				SequentList seqs = new SequentList();
				Sequent s1 = sequent.copy().removeRight(a).addLeft(a.x).addRight(a.y);
				seqs.addSequent(s1);
				deductions.add(Deduction.of(seqs, "(Imp-R)"));
			}
			return null;
		}

		public Void visit(Or a, Void p)
		{
			if (sequent.containsLeft(a))
			{
				SequentList seqs = new SequentList();

				Sequent s0 = sequent.copy().removeLeft(a);

				seqs.addSequent(s0.copy().addLeft(a.x));
				seqs.addSequent(s0.copy().addLeft(a.y));

				deductions.add(Deduction.of(seqs, "(Or-L)"));
			}
			else if (sequent.containsRight(a))
			{
				Sequent s0 = sequent.copy().removeRight(a);

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.x));
					deductions.add(Deduction.of(seqs, "(Or-R1)"));
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.y));
					deductions.add(Deduction.of(seqs, "(Or-R2)"));
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addRight(a.x, a.y));
					deductions.add(Deduction.of(seqs, "(Or-R)"));
				}
			}
			return null;
		}

		public Void visit(And a, Void p)
		{
			if (sequent.containsLeft(a))
			{
				Sequent s0 = sequent.copy().removeLeft(a);

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.x));
					deductions.add(Deduction.of(seqs, "(And-L1)"));
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.y));
					deductions.add(Deduction.of(seqs, "(And-L2)"));
				}

				{
					SequentList seqs = new SequentList();
					seqs.addSequent(s0.copy().addLeft(a.x, a.y));
					deductions.add(Deduction.of(seqs, "(And-L)"));
				}
			}
			else if (sequent.containsRight(a))
			{
				SequentList seqs = new SequentList();

				Sequent s0 = sequent.copy().removeRight(a);

				seqs.addSequent(s0.copy().addRight(a.x));
				seqs.addSequent(s0.copy().addRight(a.y));

				deductions.add(Deduction.of(seqs, "(And-R)"));
			}
			return null;
		}

		public Void visit(Not a, Void p)
		{
			if (sequent.containsLeft(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(sequent.copy().removeLeft(a).addRight(a.x));
				deductions.add(Deduction.of(seqs, "Not-L"));
			}
			else if (sequent.containsRight(a))
			{
				SequentList seqs = new SequentList();
				seqs.addSequent(sequent.copy().removeRight(a).addLeft(a.x));
				deductions.add(Deduction.of(seqs, "Not-R"));
			}
			return null;
		}

		public Void visit(Literal a, Void p)
		{
			return null;
		}
	}
}
