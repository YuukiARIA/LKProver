package lovelogic.prover;

import lovelogic.sequent.SequentList;

public class Deduction
{
	private SequentList subGoals;
	private String deductionName;

	public Deduction(SequentList subGoals, String deductionName)
	{
		this.subGoals = subGoals;
		this.deductionName = deductionName;
	}

	public SequentList getSubGoals()
	{
		return subGoals;
	}

	public String getDeductionName()
	{
		return deductionName;
	}

	public static Deduction of(SequentList subGoals, String deductionName)
	{
		return new Deduction(subGoals, deductionName);
	}
}
