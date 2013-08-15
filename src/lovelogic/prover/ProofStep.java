package lovelogic.prover;

import lovelogic.sequent.Sequent;

// TODO: redesign Deduction and ProofStep (these have almost same meanings)
public class ProofStep
{
	private Sequent sequent;
	private String deductionName;

	public ProofStep(Sequent sequent, String deductionName)
	{
		this.sequent = sequent;
		this.deductionName = deductionName;
	}

	public Sequent getSequent()
	{
		return sequent;
	}

	public String getDeductionName()
	{
		return deductionName;
	}

	public static ProofStep of(Sequent sequent, String deductionName)
	{
		return new ProofStep(sequent, deductionName);
	}
}
