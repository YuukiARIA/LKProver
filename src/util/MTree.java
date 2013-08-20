package util;

import java.util.ArrayList;
import java.util.List;

public class MTree<T>
{
	private T item;
	private List<MTree<T>> subs = new ArrayList<MTree<T>>();

	public MTree(T item)
	{
		this.item = item;
	}

	public T get()
	{
		return item;
	}

	public void addSub(MTree<T> sub)
	{
		subs.add(sub);
	}

	public boolean hasNoSubtree()
	{
		return subs.isEmpty();
	}

	public Iterable<MTree<T>> getSubtrees()
	{
		return subs;
	}

	public int getMaxDepth()
	{
		int d = 0;
		for (MTree<T> subtree : subs)
		{
			d = Math.max(subtree.getMaxDepth(), d);
		}
		return 1 + d;
	}

	public static <T> MTree<T> of(T item)
	{
		return new MTree<T>(item);
	}
}
