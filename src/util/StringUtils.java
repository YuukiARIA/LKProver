package util;

import java.util.Arrays;
import java.util.Collection;

public final class StringUtils
{
	private StringUtils() { }

	public static String repeat(int n, String s)
	{
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < n; i++)
		{
			buf.append(s);
		}
		return buf.toString();
	}

	public static String repeat(int n, char c)
	{
		char[] cs = new char[n];
		Arrays.fill(cs, c);
		return new String(cs);
	}

	public static <T> String join(Collection<T> items, String delim)
	{
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (T item : items)
		{
			if (!first)
			{
				buf.append(delim);
			}
			first = false;
			buf.append(item);
		}
		return buf.toString();
	}

	public static <T> String join(Collection<T> items)
	{
		StringBuilder buf = new StringBuilder();
		for (T item : items)
		{
			buf.append(item);
		}
		return buf.toString();
	}
}
