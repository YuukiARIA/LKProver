package lovelogic.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Config
{
	private static final Object LOCK = new Object();

	private static Config instance;

	private Map<String, String> props = new HashMap<String, String>();

	private Config() { }

	public String getDefault(String key, String defval)
	{
		String value = props.get(key);
		if (value == null)
		{
			props.put(key, defval);
			value = defval;
		}
		return value;
	}

	public void load(final String fileName)
	{
		try
		{
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileName)));

			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] ss = line.split("\\s+=\\s+");
				if (ss.length == 2)
				{
					String k = ss[0], v = ss[1];
					props.put(k, v);
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				save(fileName);
			}
		});
	}

	private void save(String fileName)
	{
		try
		{
			PrintWriter writer = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName))));

			for (Map.Entry<String, String> ent : props.entrySet())
			{
				writer.println(ent.getKey() + "=" + ent.getValue());
			}
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static Config getInstance()
	{
		synchronized (LOCK)
		{
			if (instance == null)
			{
				instance = new Config();
			}
		}
		return instance;
	}
}
