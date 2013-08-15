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
			MTree<Sequent> proof = proveMin(s);
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

	private static MTree<Sequent> proveMin(Sequent goal)
	{
		if (goal.isAxiom())
		{
			return MTree.of(goal);
		}

		MTree<Sequent> prMin = null;
		List<SequentList> deductions = new ArrayList<SequentList>();
		for (Formula a : goal.getAllFormulae())
		{
			Deducer.getDeductionList(goal, a, deductions);
		}

		for (SequentList subGoals : deductions)
		{
			MTree<Sequent> pr = MTree.of(goal);
			for (Sequent subGoal : subGoals.getSubGoals())
			{
				MTree<Sequent> subpr = proveMin(subGoal);
				if (subpr != null) 
				{
					pr.addSub(subpr);
				}
				else
				{
					pr = null;
					break;
				}
			}
			if (pr != null)
			{
				if (prMin == null || pr.getMaxDepth() < prMin.getMaxDepth())
				{
					prMin = pr;
				}
			}
		}
		return prMin;
	}

	private static void showProof(MTree<Sequent> tree, int depth)
	{
		System.out.println(StringUtils.repeat(depth, "  ") + tree.get());
		for (MTree<Sequent> subtree : tree.getSubtrees())
		{
			showProof(subtree, depth + 1);
		}
	}
}
