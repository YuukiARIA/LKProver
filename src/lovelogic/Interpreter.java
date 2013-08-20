package lovelogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lovelogic.prover.ProofStep;
import lovelogic.prover.Prover;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.ParserException;
import util.MTree;
import util.StringUtils;

public class Interpreter
{
	public static void main(String[] args)
	{
		repl();
	}

	public static void repl()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Input a formula.");
		try
		{
			String line;
			while (true)
			{
				System.out.print("> ");
				line = reader.readLine();

				if (line == null)
				{
					break;
				}

				line = line.trim();
				if (line.isEmpty())
				{
					break;
				}

				try
				{
					Formula e = Formula.parse(line);
					prove(e);
				}
				catch (ParserException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void prove(Formula e)
	{
		MTree<ProofStep> proof = Prover.findMinimumProof(Sequent.createGoal(e));
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
