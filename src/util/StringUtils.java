package util;

import java.util.Arrays;
import java.util.Collection;

import util.strconv.IStringConverter;
import util.strconv.ToString;

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
		return join(items, delim, ToString.getInstance());
	}

	public static <T> String join(Collection<T> items, String delim, IStringConverter<? super T> converter)
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
			buf.append(converter.toString(item));
		}
		return buf.toString();
	}

	public static <T> String join(Collection<T> items)
	{
		return join(items, ToString.getInstance());
	}

	public static <T> String join(Collection<T> items, IStringConverter<? super T> converter)
	{
		StringBuilder buf = new StringBuilder();
		for (T item : items)
		{
			buf.append(converter.toString(item));
		}
		return buf.toString();
	}
}
