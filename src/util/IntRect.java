package util;

public final class IntRect
{
	public final int left;
	public final int top;
	public final int right;
	public final int bottom;
	public final int width;
	public final int height;

	public IntRect()
	{
		this(0, 0, 0, 0);
	}

	public IntRect(int x, int y, int width, int height)
	{
		this.left = x;
		this.top = y;
		this.right = x + width;
		this.bottom = y + height;
		this.width = width;
		this.height = height;
	}

	public static IntRect of(int x, int y, int height, int width)
	{
		return new IntRect(x, y, width, height);
	}

	public static IntRect ofBounds(int left, int top, int right, int bottom)
	{
		return new IntRect(left, top, right - left, bottom - top);
	}
}
