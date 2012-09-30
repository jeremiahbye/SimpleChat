package me.chris.SimpleChat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


import me.chris.SimpleChat.CommandHandler.SimpleChatCommandHandler;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleChatMain extends JavaPlugin
{
    static Logger log = Logger.getLogger("Minecraft");
    
    File configFile;
	File extraFile;
	
	File configExampleFile;
	File extraExampleFile;
	
	public static FileConfiguration config;
	public static FileConfiguration extra;	
	public static FileConfiguration configExample;
	public static FileConfiguration extraExample;	
	public static Permission perms;
		
	@Override
	public void onEnable()
	{
		if (!setupPermissions())
		{
			log.log(Level.SEVERE, "[SimpleChat] No Permission found! Disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		configFile = new File(getDataFolder(), "config.yml");
		extraFile = new File(getDataFolder(), "extra.yml");
		configExampleFile = new File(getDataFolder(), "config-example.yml");
		extraExampleFile = new File(getDataFolder(), "extra-example.yml");
		
		try
		{
			firstRun();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		config = new YamlConfiguration();
		extra = new YamlConfiguration();
		loadYamls();
		
		Variables.matching = SimpleChatHelperMethods.makeSureMatch(Variables.perms.getGroups(), SimpleChatAPI.getGroups());
		
		getServer().getPluginManager().registerEvents(new SimpleChatListener(), this);
		
		SimpleChatCommandHandler commandHandler = new SimpleChatCommandHandler();
		getCommand("simplechat").setExecutor(commandHandler);
		getCommand("scconfig").setExecutor(commandHandler);
		getCommand("scextra").setExecutor(commandHandler);
		getCommand("chat").setExecutor(commandHandler);
		getCommand("chaton").setExecutor(commandHandler);
		getCommand("chatoff").setExecutor(commandHandler);
				
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			public void run()
			{
				Variables.matching = SimpleChatHelperMethods.makeSureMatch(Variables.perms.getGroups(), SimpleChatAPI.getGroups());
				if(Variables.matching == false)
				{
					getServer().broadcastMessage("§a[SimpleChat]§4 Config does not match with permission groups. ");
				}
			}

		}, 0L, 200L);
		
		
		
		copy(getResource("config-example.yml"), configExampleFile);
		copy(getResource("extra-example.yml"), extraExampleFile);
		
		log.log(Level.INFO, "[SimpleChat] Version " + Variables.version.substring(10));
		log.log(Level.INFO, "[SimpleChat] Started successfully.");		
	}
	
	@Override
	public void onDisable()
	{
		log.log(Level.INFO, "[SimpleChat] Stopped.");	
	}
	
	private void firstRun() throws Exception
	{
		if (!configFile.exists())
		{
			log.log(Level.INFO, "[SimpleChat] No config.yml file found. Attempting to make one. ");
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
			log.log(Level.INFO, "[SimpleChat] File Made Successfully ");
		}
		else
		{
			log.log(Level.INFO, "[SimpleChat] Config Found. Using it.  ");
		}
		
		if (!extraFile.exists())
		{
			log.log(Level.INFO, "[SimpleChat] No extra.yml file found. Attempting to make one. ");
			extraFile.getParentFile().mkdirs();
			copy(getResource("extra.yml"), extraFile);
			log.log(Level.INFO, "[SimpleChat] File Made Successfully ");
		}
		else
		{
			log.log(Level.INFO, "[SimpleChat] Extra Found. Using it.  ");
		}
		
		if (!configExampleFile.exists())
		{
			configExampleFile.getParentFile().mkdirs();
			copy(getResource("config-example.yml"), configExampleFile);
		}
		
		if (!extraExampleFile.exists())
		{
			extraExampleFile.getParentFile().mkdirs();
			copy(getResource("extra-example.yml"), extraExampleFile);
		}
	}
	
	private void copy(InputStream in, File file)
	{
		try
		{
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadYamls()
	{
		try
		{
			config.load(configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			extra.load(extraFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		new Variables(this,  perms, config,  extra,  log);
	}
	
	public void saveYamls()
	{
		Variables.exportVariables();
		
		try
		{
			config.save(configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			extra.save(extraFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	private Boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
		{
			perms = permissionProvider.getProvider();
		}
		return (perms != null);
	}	
	
}
