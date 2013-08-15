package lovelogic;

import lovelogic.prover.ProofStep;
import lovelogic.prover.Prover;
import lovelogic.sequent.Sequent;
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
			MTree<ProofStep> proof = Prover.findMinimumProof(s);
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

	private static void showProof(MTree<ProofStep> tree, int depth)
	{
		ProofStep pr = tree.get();
		System.out.println(StringUtils.repeat(depth, "  ") + pr.getSequent() + " " + pr.getDeductionName());
		for (MTree<ProofStep> subtree : tree.getSubtrees())
		{
			showProof(subtree, depth + 1);
		}
	}
}
