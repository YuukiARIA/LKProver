package util;

public class Pair<X, Y>
{
	public final X fst;
	public final Y snd;

	public Pair(X fst, Y snd)
	{
		this.fst = fst;
		this.snd = snd;
	}

	public Pair<X, Y> fst(X fst)
	{
		return of(fst, snd);
	}

	public Pair<X, Y> snd(Y snd)
	{
		return of(fst, snd);
	}

	public static <X, Y> Pair<X, Y> of(X fst, Y snd)
	{
		return new Pair<X, Y>(fst, snd);
	}
}
