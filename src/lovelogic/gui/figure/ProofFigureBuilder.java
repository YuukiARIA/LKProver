package lovelogic.gui.figure;

import lovelogic.prover.ProofStep;
import lovelogic.sequent.Sequent;
import util.MTree;

public final class ProofFigureBuilder
{
	private ProofFigureBuilder() { }

	public static ProofFigure build(MTree<ProofStep> prtree)
	{
		ProofStep pr = prtree.get();
		Sequent sequent = pr.getSequent();
		ProofFigure pf = new ProofFigure(sequent.toString(), pr.getDeductionName());
		for (MTree<ProofStep> sub : prtree.getSubtrees())
		{
			pf.add(build(sub));
		}
		return pf;
	}
}
