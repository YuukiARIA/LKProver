package lovelogic.gui.figure;

import lovelogic.sequent.Sequent;
import util.MTree;

public final class ProofFigureBuilder
{
	private ProofFigureBuilder() { }

	public static ProofFigure build(MTree<Sequent> pr)
	{
		Sequent sequent = pr.get();
		ProofFigure pf = new ProofFigure(sequent.toString(), "");
		for (MTree<Sequent> sub : pr.getSubtrees())
		{
			pf.add(build(sub));
		}
		return pf;
	}
}
