package lovelogic.sequent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.StringUtils;

public class SequentList
{
	private List<Sequent> list = new ArrayList<Sequent>();

	public SequentList()
	{
		this(new ArrayList<Sequent>());
	}

	private SequentList(List<Sequent> list)
	{
		this.list = list;
	}

	public SequentList addSequent(Sequent s)
	{
		list.add(s);
		return this;
	}

	public SequentList copy()
	{
		List<Sequent> newList = new ArrayList<Sequent>();
		for (Sequent s : list)
		{
			newList.add(s.copy());
		}
		return new SequentList(newList);
	}

	public Iterable<Sequent> getSubGoals()
	{
		return Collections.unmodifiableCollection(list);
	}

	public String toString()
	{
		return StringUtils.join(list, ", ");
	}
}
