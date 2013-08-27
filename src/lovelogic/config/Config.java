package lovelogic.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	private static final File SYSTEM_CONFIG_FILE = new File("lkp.config");
	private static Config systemConfig;

	private File file;
	private Map<String, String> props = new HashMap<String, String>();

	private Config(File file)
	{
		this.file = file;
	}

	public void clear()
	{
		props.clear();
	}

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

	public void set(String key, String value)
	{
		props.put(key, value);
	}

	public boolean getBoolean(String key)
	{
		return "true".equalsIgnoreCase(getDefault(key, "false"));
	}

	public void setBoolean(String key, boolean b)
	{
		set(key, String.valueOf(b));
	}

	public void load()
	{
		if (file.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(file)));
	
				String line;
				while ((line = reader.readLine()) != null)
				{
					String[] ss = line.split("\\s*=\\s*");
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
		}
	}

	public void save()
	{
		try
		{
			PrintWriter writer = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file))));

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

	public static Config load(File file)
	{
		Config config = new Config(file);
		config.load();
		return config;
	}

	public static Config getSystemConfig()
	{
		if (systemConfig == null)
		{
			systemConfig = load(SYSTEM_CONFIG_FILE);
		}
		return systemConfig;
	}

	public static void saveSystemConfig()
	{
		getSystemConfig().save();
	}
}
