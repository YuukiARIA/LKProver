package util.strconv;

public class ToString implements IStringConverter<Object>
{
	private static ToString instance;

	private ToString() { }

	public String toString(Object x)
	{
		return x.toString();
	}

	public static ToString getInstance()
	{
		if (instance == null)
		{
			instance = new ToString();
		}
		return instance;
	}
}
