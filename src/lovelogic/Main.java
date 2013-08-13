package lovelogic;

import java.util.ArrayList;
import java.util.List;

import lovelogic.sequent.Deducer;
import lovelogic.sequent.Sequent;
import lovelogic.sequent.SequentList;
import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.ParserException;
import util.MTree;
import util.StringUtils;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			Formula x = Formula.parse("~((A \\/ B) /\\ (A \\/ ~B) /\\ (~A \\/ B) /\\ (~A \\/ ~B))");
			Sequent s = Sequent.createGoal(x);
			SequentList sl = new SequentList().addSequent(s);
			MTree<SequentList> proof = proveMin(sl);
			if (proof != null)
			{
				System.out.println("Proved.");
				showProof(proof, 0);
			}
			else
			{
				System.out.println("Unprovable.");
			}
		}
		catch (ParserException e)
		{
			e.printStackTrace();
		}
	}

	public static boolean prove(Sequent s, int depth)
	{
		String indent = StringUtils.repeat(depth, "  ");

		if (s.isAxiom())
		{
			System.out.println(indent + s + " (Axiom)");
			return true;
		}

		List<SequentList> deductions = new ArrayList<SequentList>();
		for (Formula a : s.getAllFormulae())
		{
			Deducer.getDeductionList(s, a, deductions);
		}
		for (SequentList subGoals : deductions)
		{
			boolean allProved = true;
			for (Sequent subGoal : subGoals.getSubGoals())
			{
				if (!prove(subGoal, depth + 1))
				{
					allProved = false;
					break;
				}
			}
			if (allProved)
			{
				System.out.println(indent + s);
				return true;
			}
		}
		return false;
	}

	public static MTree<SequentList> proveMin(SequentList sl)
	{
		MTree<SequentList> pr = MTree.of(sl);
		for (Sequent goal : sl.getSubGoals())
		{
			if (goal.isAxiom())
			{
				continue;
			}

			List<SequentList> deductions = new ArrayList<SequentList>();
			for (Formula a : goal.getAllFormulae())
			{
				Deducer.getDeductionList(goal, a, deductions);
			}

			MTree<SequentList> subprMin = null;
			for (SequentList subGoals : deductions)
			{
				MTree<SequentList> subpr = proveMin(subGoals);
				if (subpr != null && (subprMin == null || subpr.getMaxDepth() < subprMin.getMaxDepth())) 
				{
					subprMin = subpr;
				}
			}
			if (subprMin == null)
			{
				return null;
			}
			pr.addSub(subprMin);
		}
		return pr;
	}

	private static void showProof(MTree<SequentList> tree, int depth)
	{
		System.out.println(StringUtils.repeat(depth, "  ") + tree.get());
		for (MTree<SequentList> subtree : tree.getSubtrees())
		{
			showProof(subtree, depth + 1);
		}
	}
}
