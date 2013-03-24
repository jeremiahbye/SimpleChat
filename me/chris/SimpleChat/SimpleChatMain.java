package me.chris.SimpleChat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import me.chris.SimpleChat.CommandHandler.SimpleChatCommandHandler;
import me.chris.SimpleChat.PartyHandler.Party;
import me.chris.SimpleChat.PartyHandler.PartyAPI;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleChatMain extends JavaPlugin
{
    @Override
	public void onEnable()
	{
    	new Variables(this);
    	
    	try
		{
			firstRun();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	SimpleChatYamlUpdater.updateExtraYaml();
    	
    	loadYamls();
    	
    	if (!setupPermissions())
		{
			Variables.log.log(Level.SEVERE, "[SimpleChat] No Permission found! Disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		Variables.matching = SimpleChatHelperMethods.makeSureMatch(Variables.perms.getGroups(), SimpleChatAPI.getGroups());
		
		getServer().getPluginManager().registerEvents(new SimpleChatListener(), this);
		
		SimpleChatCommandHandler commandHandler = new SimpleChatCommandHandler();
		getCommand("simplechat").setExecutor(commandHandler);
		getCommand("scconfig").setExecutor(commandHandler);
		getCommand("scextra").setExecutor(commandHandler);
		getCommand("chat").setExecutor(commandHandler);
		getCommand("chaton").setExecutor(commandHandler);
		getCommand("chatoff").setExecutor(commandHandler);
		getCommand("cmdrdrct").setExecutor(commandHandler);
				
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
		
		Variables.log.log(Level.INFO, "[SimpleChat] Version " + Variables.version.substring(10));
		Variables.log.log(Level.INFO, "[SimpleChat] Started successfully.");	
		
		for(Player p : getServer().getOnlinePlayers())
		{
			if(PartyAPI.isPlayerInAParty(p))
			{
				Party pty = PartyAPI.findPartyofPlayer(p);
				pty.addOnlineMember(p);
			}
			
			if(Variables.perms.has(p, "SimpleChat.SocialSpy") || Variables.perms.has(p, "SimpleChat.*"))
			{
				Variables.onlineSocialSpy.add(p);
			}
			
			if(Variables.perms.has(p, "SimpleChat.AdminChat") || Variables.perms.has(p, "SimpleChat.*"))
			{
				Variables.onlineAdminChat.add(p);
			}
		}
	}
	
	@Override
	public void onDisable()
	{
		Variables.log.log(Level.INFO, "[SimpleChat] Stopped.");	
	}
	
	private void firstRun() throws Exception
	{
		if (!Variables.configFile.exists())
		{
			Variables.log.log(Level.INFO, "[SimpleChat] No config.yml file found. Attempting to make one. ");
			Variables.configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), Variables.configFile);
			Variables.log.log(Level.INFO, "[SimpleChat] File Made Successfully ");
		}
		else
		{
			Variables.log.log(Level.INFO, "[SimpleChat] Config Found. Using it.  ");
		}
		
		if (!Variables.extraFile.exists())
		{
			Variables.log.log(Level.INFO, "[SimpleChat] No extra.yml file found. Attempting to make one. ");
			Variables.extraFile.getParentFile().mkdirs();
			copy(getResource("extra.yml"), Variables.extraFile);
			Variables.log.log(Level.INFO, "[SimpleChat] File Made Successfully ");
		}
		else
		{
			Variables.log.log(Level.INFO, "[SimpleChat] Extra Found. Using it.  ");
		}
		
	}
	
	public void copy(InputStream in, File file)
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
			Variables.config.load(Variables.configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			Variables.extra.load(Variables.extraFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Variables.importVariables();
	}
	
	public void saveYamls()
	{
		Variables.exportVariables();
		
		try
		{
			Variables.config.save(Variables.configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			Variables.extra.save(Variables.extraFile);
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
			Variables.perms = permissionProvider.getProvider();
		}
		return (Variables.perms != null);
	}	
	
}
