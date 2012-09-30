package me.chris.SimpleChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Christopher Pybus
 * @date Mar 25, 2012
 * @file SimpleChatVariables.java
 * @package me.chris.SimpleChat
 * 
 * @purpose
 */

public class Variables
{
	public static FileConfiguration config;
	public static FileConfiguration extra;
	public static Permission perms;
	public static Logger log;
	public static SimpleChatMain plugin;

	public static SimpleChatAPI api = new SimpleChatAPI();
	public static SimpleChatHelperMethods schm = new SimpleChatHelperMethods();
	public static SimpleChatChatState sccs = new SimpleChatChatState();

	public static HashMap<Player, Player> messaging;
	public static HashMap<Player, Player> lockedPM;
	
	public static boolean matching;

	public static final String version = "SimpleChat 4.1";

	// Past this point are the yaml variables

	public static String MessageFormat;

	public static HashMap<String, String> groupPrefixes;
	public static HashMap<String, String> groupSuffixes;

	public static HashMap<String, String> userPrefixes;
	public static HashMap<String, String> userSuffixes;

	public static String defaultPrefix;
	public static String defaultSuffix;
	public static String defaultGroup ;

	public static boolean UseSimpleChatCensor;
	public static ArrayList<String> CurseWords;

	public static boolean UseSimpleChatCapsPreventer;
	public static int MaxNumberOfCapitalLetters;
	public static boolean PunishmentKick ;
	public static boolean PunishmentMsgToPlayer ;
	public static boolean PunishmentReplaceMsg;
	public static String MsgToPlayer ;
	public static ArrayList<String> ReplaceWith;

	public static boolean UseSimpleChatOtherMessages;
	public static String OtherMessagesJoin ;
	public static String OtherMessagesLeave ;
	public static String OtherMessagesKick ;

	public static boolean UseSimpleChatDieMessages;
	public static HashMap<String, String> DieMessages;

	public static boolean UseSimpleChatJoinMsg;
	public static ArrayList<String> JoinMsgToPlayer;

	public static boolean UseSimpleChatMeFormatting;
	public static String MeFormat ;

	public static boolean UseSimpleChatMsgAndReplyFormatting ;
	public static String SendingMessage ;
	public static String ReceivingMessage ;

	public Variables(SimpleChatMain plugin, Permission perms, FileConfiguration config, FileConfiguration extra, Logger log)
	{
		Variables.plugin = plugin;
		Variables.config = config;
		Variables.extra = extra;
		Variables.perms = perms;
		Variables.log = log;
		
		messaging = new HashMap<Player, Player>();
		lockedPM = new HashMap<Player, Player>();
		
		groupPrefixes = new HashMap<String, String>();
		groupSuffixes = new HashMap<String, String>();

		userPrefixes = new HashMap<String, String>();
		userSuffixes = new HashMap<String, String>();
		
		CurseWords = new ArrayList<String>();
		
		ReplaceWith = new ArrayList<String>();
		
		DieMessages = new HashMap<String, String>();
		
		JoinMsgToPlayer = new ArrayList<String>();
		
		importVariables();
	}

	public static void importVariables()
	{
		// Assign Message Format
		try
		{
			MessageFormat = Variables.config.getString("MessageFormat").toString();
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MessageFormat variable. (Config.yml)");
			MessageFormat = "+pname +msg";
		}

		// Get groups
		Set<String> groups = Variables.config.getConfigurationSection("Groups.").getKeys(false);
		for (String g : groups)
		{
			String pre;
			String suf;
			try
			{
				pre = Variables.config.getString("Groups." + g + ".prefix");
				suf = Variables.config.getString("Groups." + g + ".suffix");
			}
			catch (Throwable t)
			{
				log.log(Level.SEVERE, "[SimpleChat] Error on the " + g + " group. (Config.yml)");
				log.log(Level.SEVERE, "[SimpleChat] No prefix and/or suffix found");
				pre = "[" + g + "]";
				suf = "";
			}
			Variables.groupPrefixes.put(g.toLowerCase(), pre);
			Variables.groupSuffixes.put(g.toLowerCase(), suf);
		}
		groups = null;

		// Get Users
		Set<String> users = null;
		try
		{
			users = Variables.config.getConfigurationSection("Users.").getKeys(false);
		}
		catch (Throwable t)
		{
			users = null;
		}

		if (users != null)
		{
			for (String u : users)
			{
				String pre;
				String suf;
				try
				{
					pre = Variables.config.getString("Users." + u + ".prefix");
					suf = Variables.config.getString("Users." + u + ".suffix");
				}
				catch (Throwable t)
				{
					log.log(Level.SEVERE, "[SimpleChat] Error on the " + u + " user. (Config.yml)");
					log.log(Level.SEVERE, "[SimpleChat] No prefix and/or suffix found");
					pre = "[" + u + "]";
					suf = "";
				}
				Variables.userPrefixes.put(u.toLowerCase(), pre);
				Variables.userSuffixes.put(u.toLowerCase(), suf);
			}
		}
		users = null;

		// Get Defaults
		try
		{
			Variables.defaultPrefix = Variables.config.getString("Defaults.prefix");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Prefix. (Config.yml)");
			Variables.defaultPrefix = "";
		}

		try
		{
			Variables.defaultSuffix = Variables.config.getString("Defaults.suffix");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Suffix. (Config.yml)");
			Variables.defaultSuffix = "";
		}

		try
		{
			Variables.defaultGroup = Variables.config.getString("Defaults.group");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Group. (Config.yml)");
			Variables.defaultGroup = "";
		}

		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------

		String useCensor = Variables.extra.getString("UseSimpleChatCensor");
		if (useCensor.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatCensor = true;
		}
		else
		{
			Variables.UseSimpleChatCensor = false;
		}

		String useCapsLock = Variables.extra.getString("UseSimpleChatCapsPreventer");
		if (useCapsLock.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatCapsPreventer = true;
		}
		else
		{
			Variables.UseSimpleChatCapsPreventer = false;
		}

		String useOther = Variables.extra.getString("UseSimpleChatOtherMessages");
		if (useOther.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatOtherMessages = true;
		}
		else
		{
			Variables.UseSimpleChatOtherMessages = false;
		}

		String useDie = Variables.extra.getString("UseSimpleChatDieMessages");
		if (useDie.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatDieMessages = true;
		}
		else
		{
			Variables.UseSimpleChatDieMessages = false;
		}

		String useJoinMsg = Variables.extra.getString("UseSimpleChatJoinMsg");
		if (useJoinMsg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatJoinMsg = true;
		}
		else
		{
			Variables.UseSimpleChatJoinMsg = false;
		}

		String useMe = Variables.extra.getString("UseSimpleChatMeFormatting");
		if (useMe.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatMeFormatting = true;
		}
		else
		{
			Variables.UseSimpleChatMeFormatting = false;
		}

		String useMsgReply = Variables.extra.getString("UseSimpleChatMsgAndReplyFormatting");
		if (useMsgReply.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatMsgAndReplyFormatting = true;
		}
		else
		{
			Variables.UseSimpleChatMsgAndReplyFormatting = false;
		}

		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------

		// Get Curse Words
		try
		{
			List<String> fake = Variables.extra.getStringList("CurseWords");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str.toLowerCase());
			}
			Variables.CurseWords = other;
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("Motherfucker");
			other.add("Shit");
			other.add("Penis");
			other.add("Bull");
			other.add("Cock");
			other.add("Vagina");
			other.add("Pussy");
			Variables.CurseWords = other;
			log.log(Level.SEVERE, "[SimpleChat] Error on the CurseWord list. (Extra.yml)");
		}

		// Get Max caps
		try
		{
			Variables.MaxNumberOfCapitalLetters = Variables.extra.getInt("MaxNumberOfCapitalLetters");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MaxNumberOfCapitalLetters. (Extra.yml)");
			Variables.MaxNumberOfCapitalLetters = 5;
		}

		// Get Punishments
		String punshKick;
		String punshMsg;
		String punshReplace;

		try
		{
			punshKick = Variables.extra.getString("Punishment.kick");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Punishment --> Kick. (Extra.yml)");
			punshKick = "false";
		}

		try
		{
			punshMsg = Variables.extra.getString("Punishment.msgToPlayer");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Punishment --> msgToPlayer. (Extra.yml)");
			punshMsg = "false";
		}

		try
		{
			punshReplace = Variables.extra.getString("Punishment.replaceMsg");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Punishment --> replaceMsg. (Extra.yml)");
			punshReplace = "false";
		}

		if (punshKick.equalsIgnoreCase("true"))
			Variables.PunishmentKick = true;
		else
			Variables.PunishmentKick = false;

		if (punshMsg.equalsIgnoreCase("true"))
			Variables.PunishmentMsgToPlayer = true;
		else
			Variables.PunishmentMsgToPlayer = false;

		if (punshReplace.equalsIgnoreCase("true"))
			Variables.PunishmentReplaceMsg = true;
		else
			Variables.PunishmentReplaceMsg = false;

		// Get MsgToPlayer
		try
		{
			Variables.MsgToPlayer = Variables.extra.getString("MsgToPlayer");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MsgToPlayer. (Extra.yml)");
			Variables.MsgToPlayer = "&4Please dont use caps, dude";
		}

		// Get replacers
		try
		{
			List<String> fake = Variables.extra.getStringList("ReplaceWith");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			Variables.ReplaceWith = other;
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("Look at my new dress!!");
			other.add("Has anyone seen my dolly?");
			other.add("This new makeup is amazing!");
			other.add("I have to go! My ballet class is in 15 minutes!");
			other.add("BRB, going to go try on my new dress!");
			other.add("New Victorias Secret bra came in today! Im so excited!!");
			other.add("My new favorite color has GOT to be pink!");

			Variables.ReplaceWith = other;
			log.log(Level.SEVERE, "[SimpleChat] Error on the ReplaceWith list. (Extra.yml)");
		}

		// Other messages
		try
		{
			Variables.OtherMessagesJoin = Variables.extra.getString("OtherMessages.join");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Join. (Extra.yml)");
			Variables.OtherMessagesJoin = "&a+pname &ehas joined the game.";
		}

		try
		{
			Variables.OtherMessagesLeave = Variables.extra.getString("OtherMessages.leave");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Leave. (Extra.yml)");
			Variables.OtherMessagesLeave = "&a+pname &ehas left the game.";
		}

		try
		{
			Variables.OtherMessagesKick = Variables.extra.getString("OtherMessages.kick");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Kick. (Extra.yml)");
			Variables.OtherMessagesKick = "&a+pname &ehas been kicked from the game.";
		}

		// Die Messages
		String[] death = { "deathInFire", "deathOnFire", "deathLava", "deathInWall", "deathDrowned", "deathStarve", "deathCactus", "deathFall", "deathOutOfWorld", "deathGeneric", "deathExplosion", "deathMagic", "deathSlainBy", "deathArrow", "deathFireball", "deathThrown" };

		for (String d : death)
		{
			try
			{
				Variables.DieMessages.put(d, Variables.extra.getString("DieMessages." + d));
			}
			catch (Throwable t)
			{
				log.log(Level.SEVERE, "[SimpleChat] Error on the DieMessages --> " + d + ". (Extra.yml)");
				Variables.DieMessages.put(d, "+pname has died");
			}
		}

		// Get Join Msg To Player
		try
		{
			List<String> fake = Variables.extra.getStringList("JoinMsgToPlayer");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			Variables.JoinMsgToPlayer = other;
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("&5=====================================================");
			other.add("&a          Welcome to the server, +pre +pname!");
			other.add("&a Please make sure youve read all the rules and such.");
			other.add("&4 Online Admins: <onlineplayers:&c,&4,admin>   ");
			other.add("&a Online Members: <onlineplayers:&c,&4,member>   ");
			other.add("&a Online Defaults: <onlineplayers:&c,&4,default>   ");
			other.add("&5=====================================================");

			Variables.ReplaceWith = other;
			log.log(Level.SEVERE, "[SimpleChat] Error on the ReplaceWith list. (Extra.yml)");

		}

		// Get Me Format
		try
		{
			Variables.MeFormat = Variables.extra.getString("MeFormat");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MeFormat. (Extra.yml)");
			Variables.MeFormat = "* &c+dname &7+msg";
		}

		// Get Msg and Reply
		try
		{
			Variables.SendingMessage = Variables.extra.getString("SendingMessage");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the SendingMessage. (Extra.yml)");
			Variables.SendingMessage = "&7[+pname -> +otherpname] +msg";
		}

		try
		{
			Variables.ReceivingMessage = Variables.extra.getString("ReceivingMessage");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the ReceivingMessage. (Extra.yml)");
			Variables.ReceivingMessage = "&7[+otherpname -> +pname] +msgg";
		}
	}

	public static void exportVariables()
	{
		Variables.config.set("MessageFormat", Variables.MessageFormat);
		Variables.config.set("Groups", " ");
		Variables.config.set("Users", " ");

		for (Entry<String, String> entry : Variables.groupPrefixes.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.config.set("Groups." + key + ".prefix", value);
		}

		for (Entry<String, String> entry : Variables.groupSuffixes.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.config.set("Groups." + key + ".suffix", value);
		}

		if (Variables.userPrefixes != null)
		{
			for (Entry<String, String> entry : Variables.userPrefixes.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				Variables.config.set("Users." + key + ".prefix", value);
			}
		}

		if (Variables.userSuffixes != null)
		{
			for (Entry<String, String> entry : Variables.userSuffixes.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				Variables.config.set("Users." + key + ".suffix", value);
			}
		}
		
		Variables.config.set("Defaults.prefix", Variables.defaultPrefix);
		Variables.config.set("Defaults.suffix", Variables.defaultSuffix);
		Variables.config.set("Defaults.group", Variables.defaultGroup);
		
		Variables.extra.set("UseSimpleChatCensor", Variables.UseSimpleChatCensor);
		Variables.extra.set("UseSimpleChatCapsPreventer", Variables.UseSimpleChatCapsPreventer);
		Variables.extra.set("UseSimpleChatOtherMessages", Variables.UseSimpleChatOtherMessages);
		Variables.extra.set("UseSimpleChatDieMessages", Variables.UseSimpleChatDieMessages);
		Variables.extra.set("UseSimpleChatJoinMsg", Variables.UseSimpleChatJoinMsg);
		Variables.extra.set("UseSimpleChatMeFormatting", Variables.UseSimpleChatMeFormatting);
		Variables.extra.set("UseSimpleChatMsgAndReplyFormatting", Variables.UseSimpleChatMsgAndReplyFormatting);
		
		Variables.extra.set("CurseWords", Variables.CurseWords);
		
		Variables.extra.set("MaxNumberOfCapitalLetters", Variables.MaxNumberOfCapitalLetters);
		
		Variables.extra.set("Punishment.kick", Variables.PunishmentKick);
		Variables.extra.set("Punishment.msgToPlayer", Variables.PunishmentMsgToPlayer);
		Variables.extra.set("Punishment.replaceMsg", Variables.PunishmentReplaceMsg);
		
		Variables.extra.set("MsgToPlayer", Variables.MsgToPlayer);
		
		Variables.extra.set("ReplaceWith", Variables.ReplaceWith);
		
		Variables.extra.set("OtherMessages.join", Variables.OtherMessagesJoin);
		Variables.extra.set("OtherMessages.leave", Variables.OtherMessagesLeave);
		Variables.extra.set("OtherMessages.kcik", Variables.OtherMessagesKick);
		
		
		for (Entry<String, String> entry : Variables.DieMessages.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.extra.set("DieMessages." + key,  value);
		}
		
		Variables.extra.set("JoinMsgToPlayer", Variables.JoinMsgToPlayer);
		
		Variables.extra.set("MeFormat", Variables.MeFormat);
		
		Variables.extra.set("SendingMessage", Variables.SendingMessage);
		Variables.extra.set("ReceivingMessage", Variables.ReceivingMessage);
	}
}
