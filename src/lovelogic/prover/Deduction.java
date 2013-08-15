package lovelogic.prover;

import lovelogic.sequent.Sequent;

public class Deduction
{
	private Sequent sequent;
	private String deductionName;

	public Deduction(Sequent sequent, String deductionName)
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

	public static Deduction of(Sequent sequent, String deductionName)
	{
		return new Deduction(sequent, deductionName);
	}
}
