package lovelogic.prover;

import java.util.ArrayList;
import java.util.List;

import lovelogic.sequent.Deducer;
import lovelogic.sequent.Sequent;
import lovelogic.sequent.SequentList;
import lovelogic.syntax.Formula;
import util.MTree;

public class Prover
{
	private Prover() { }

	public static MTree<ProofStep> findProof(Sequent goal)
	{
		return searchProof(goal, false, false);
	}

	public static MTree<ProofStep> findMinimumProof(Sequent goal)
	{
		return searchProof(goal, false, true);
	}

	public static MTree<ProofStep> searchProof(Sequent goal, boolean intuition, boolean minimize)
	{
		if (intuition && !goal.isIntuitionistic())
		{
			return null;
		}

		if (goal.isAxiom())
		{
			return MTree.of(ProofStep.of(goal, ""));
		}

		MTree<ProofStep> prMin = null;
		List<Deduction> deductions = new ArrayList<Deduction>();
		for (Formula a : goal.getAllFormulae())
		{
			Deducer.getDeductionList(deductions, goal, a);
		}

		for (Deduction deduction : deductions)
		{
			SequentList subGoals = deduction.getSubGoals();
			MTree<ProofStep> pr = MTree.of(ProofStep.of(goal, deduction.getDeductionName()));
			for (Sequent subGoal : subGoals.getSubGoals())
			{
				MTree<ProofStep> subpr = searchProof(subGoal, intuition, minimize);
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
					if (!minimize)
					{
						break;
					}
				}
			}
		}
		return prMin;
	}
}
