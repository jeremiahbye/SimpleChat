package me.chris.SimpleChat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

import me.chris.SimpleChat.PartyHandler.Party;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Christopher Pybus
 * @date Mar 25, 2012
 * @file SimpleChatVariables.java
 * @package me.chris.SimpleChat
 * @purpose
 */

public class Variables
{
	public static FileConfiguration			config;
	public static FileConfiguration			extra;
	public static Permission				perms;
	public static Logger					log;
	public static SimpleChatMain			plugin;
	
	public static File						configFile;
	public static File						extraFile;
	
	public static File						configExampleFile;
	public static File						extraExampleFile;
	
	public static SimpleChatAPI				api		= new SimpleChatAPI();
	public static SimpleChatHelperMethods	schm	= new SimpleChatHelperMethods();
	public static SimpleChatChatState		sccs	= new SimpleChatChatState();
	
	public static HashMap<Player, Player>	messaging;
	public static HashMap<Player, Player>	lockedPM;
	
	public static boolean					matching;
	
	public static final String				version	= "SimpleChat 5.0";
	
	public static ArrayList<Player>			adminChat;
	public static boolean					consoleAdminChat;
	
	public static ArrayList<Player>			onlineSocialSpy;
	public static ArrayList<Player>			onlineAdminChat;
	
	// PARTY VARIABLES
	public static HashMap<Player, Party>	lockedPartyChat;
	public static HashMap<Player, Party>	inviteToParty;
	
	// Past this point are the yaml variables
	
	public static String					MessageFormat;
	
	public static HashMap<String, String>	groupPrefixes;
	public static HashMap<String, String>	groupSuffixes;
	
	public static HashMap<String, String>	userPrefixes;
	public static HashMap<String, String>	userSuffixes;
	
	public static String					defaultPrefix;
	public static String					defaultSuffix;
	public static String					defaultGroup;
	
	public static boolean					UseSimpleChatCensor;
	public static ArrayList<String>			CurseWords;
	
	public static boolean					UseSimpleChatCapsPreventer;
	public static int						MaxNumberOfCapitalLetters;
	public static boolean					PunishmentKick;
	public static boolean					PunishmentMsgToPlayer;
	public static boolean					PunishmentReplaceMsg;
	public static String					MsgToPlayer;
	public static ArrayList<String>			ReplaceWith;
	
	public static boolean					UseSimpleChatOtherMessages;
	public static String					OtherMessagesJoin;
	public static String					OtherMessagesLeave;
	public static String					OtherMessagesKick;
	
	public static boolean					UseSimpleChatDieMessages;
	public static HashMap<String, String>	DieMessages;
	
	public static boolean					UseSimpleChatJoinMsg;
	public static ArrayList<String>			JoinMsgToPlayer;
	
	public static boolean					UseSimpleChatGeneralFormatting;
	public static String					MeFormat;
	public static String					SayFormat;
	public static String					BroadcastFormat;
	
	public static boolean					UseSimpleChatMsgAndReplyFormatting;
	public static String					SendingMessage;
	public static String					ReceivingMessage;
	
	public static boolean					UseSimpleChatAdminChat;
	public static String					AdminChatFormat;
	
	public static boolean					UseSimpleChatPartyChat;
	public static String					PartyChatFormat;
	public static HashMap<String, Party>	Parties;
	
	public static ArrayList<String>			socialSpyOffPM;
	public static ArrayList<String>			socialSpyOffParty;
	
	public Variables(SimpleChatMain plugin)
	{
		Variables.plugin = plugin;
		log = Logger.getLogger("Minecraft");
		
		configFile = new File(plugin.getDataFolder(), "config.yml");
		extraFile = new File(plugin.getDataFolder(), "extra.yml");
		configExampleFile = new File(plugin.getDataFolder(), "config-example.yml");
		extraExampleFile = new File(plugin.getDataFolder(), "extra-example.yml");
		
		config = new YamlConfiguration();
		extra = new YamlConfiguration();
		
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
		
		adminChat = new ArrayList<Player>();
		
		socialSpyOffPM = new ArrayList<String>();
		socialSpyOffParty = new ArrayList<String>();
		
		Parties = new HashMap<String, Party>();
		
		lockedPartyChat = new HashMap<Player, Party>();
		inviteToParty = new HashMap<Player, Party>();
		
		onlineSocialSpy = new ArrayList<Player>();
		onlineAdminChat = new ArrayList<Player>();
		
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
		
		String useGeneralFormatting = Variables.extra.getString("UseSimpleChatGeneralFormatting");
		if (useGeneralFormatting.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatGeneralFormatting = true;
		}
		else
		{
			Variables.UseSimpleChatGeneralFormatting = false;
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
		
		String useAdminChat = Variables.extra.getString("UseSimpleChatAdminChat");
		if (useAdminChat.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatAdminChat = true;
		}
		else
		{
			Variables.UseSimpleChatAdminChat = false;
		}
		
		String partyChat = Variables.extra.getString("UseSimpleChatPartyChat");
		if (partyChat.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatPartyChat = true;
		}
		else
		{
			Variables.UseSimpleChatPartyChat = false;
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
		
		if (Variables.extra.contains("Punishment.kick"))
		{
			punshKick = Variables.extra.getString("Punishment.kick");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Punishment --> Kick. (Extra.yml)");
			punshKick = "false";
		}
		
		if (Variables.extra.contains("Punishment.msgToPlayer"))
		{
			punshMsg = Variables.extra.getString("Punishment.msgToPlayer");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Punishment --> msgToPlayer. (Extra.yml)");
			punshMsg = "false";
		}
		
		if (Variables.extra.contains("Punishment.replaceMsg"))
		{
			punshReplace = Variables.extra.getString("Punishment.replaceMsg");
		}
		else
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
		if (Variables.extra.contains("MsgToPlayer"))
		{
			Variables.MsgToPlayer = Variables.extra.getString("MsgToPlayer");
		}
		else
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
		if (Variables.extra.contains("OtherMessages.join"))
		{
			Variables.OtherMessagesJoin = Variables.extra.getString("OtherMessages.join");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Join. (Extra.yml)");
			Variables.OtherMessagesJoin = "&a+pname &ehas joined the game.";
		}
		
		if (Variables.extra.contains("OtherMessages.leave"))
		{
			Variables.OtherMessagesLeave = Variables.extra.getString("OtherMessages.leave");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Leave. (Extra.yml)");
			Variables.OtherMessagesLeave = "&a+pname &ehas left the game.";
		}
		
		if (Variables.extra.contains("OtherMessages.kick"))
		{
			Variables.OtherMessagesKick = Variables.extra.getString("OtherMessages.kick");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the OtherMessages --> Kick. (Extra.yml)");
			Variables.OtherMessagesKick = "&a+pname &ehas been kicked from the game.";
		}
		
		// Die Messages
		String[] death = { "deathInFire", "deathOnFire", "deathLava", "deathInWall", "deathDrowned", "deathStarve",
				"deathCactus", "deathFall", "deathOutOfWorld", "deathGeneric", "deathExplosion", "deathMagic",
				"deathSlainBy", "deathArrow", "deathFireball", "deathThrown" };
		
		for (String d : death)
		{
			if (Variables.extra.contains("DieMessages." + d))
			{
				Variables.DieMessages.put(d, Variables.extra.getString("DieMessages." + d));
			}
			else
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
		if (Variables.extra.contains("MeFormat"))
		{
			Variables.MeFormat = Variables.extra.getString("MeFormat");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MeFormat. (Extra.yml)");
			Variables.MeFormat = "* &c+dname &7+msg";
		}
		
		// Get Say Format
		if (Variables.extra.contains("SayFormat"))
		{
			Variables.SayFormat = Variables.extra.getString("SayFormat");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the SayFormat. (Extra.yml)");
			Variables.SayFormat = "&6[&4Server] &a+msg";
		}
		
		// Get Broadcast Format
		if (Variables.extra.contains("BroadcastFormat"))
		{
			Variables.BroadcastFormat = Variables.extra.getString("BroadcastFormat");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the BroadcastFormat. (Extra.yml)");
			Variables.BroadcastFormat = "&6[&4Broadcast&6] &a+msg";
		}
		
		// Get Msg and Reply
		if (Variables.extra.contains("SendingMessage"))
		{
			Variables.SendingMessage = Variables.extra.getString("SendingMessage");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the SendingMessage. (Extra.yml)");
			Variables.SendingMessage = "&8&l[&r&7+pname &e-> &7+otherpname&8&l] &r&b&o+msg";
		}
		
		if (Variables.extra.contains("ReceivingMessage"))
		{
			Variables.ReceivingMessage = Variables.extra.getString("ReceivingMessage");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the ReceivingMessage. (Extra.yml)");
			Variables.ReceivingMessage = "&8&l[&r&7+otherpname &e-> &7+pname&8&l] &r&b&o+msg";
		}
		
		if (Variables.extra.contains("SocialSpyOffPM"))
		{
			List<String> fake = Variables.extra.getStringList("SocialSpyOffPM");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			Variables.socialSpyOffPM = other;
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ThisPlayer");
			other.add("Player123456789");
			Variables.socialSpyOffPM = other;
		}
		
		// Admin Chat
		if (Variables.extra.contains("AdminChatFormat"))
		{
			Variables.AdminChatFormat = Variables.extra.getString("AdminChatFormat");
		}
		else
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the AdminChat. (Extra.yml)");
			Variables.AdminChatFormat = "&3[AdminChat] &7+pname: &4+msg";
		}
		
		// Party Chat
		if (extra.contains("PartyChatFormat"))
		{
			PartyChatFormat = extra.getString("PartyChatFormat");
			
		}
		else
		{
			PartyChatFormat = "&3[+partyname] &7+pname: &2+msg";
			log.log(Level.SEVERE, "[SimpleChat] Error on the PartyChat (Extra.yml)");
		}
		
		try
		{
			Set<String> partyHeaders = extra.getConfigurationSection("Parties.").getKeys(false);
			for (String groupName : partyHeaders)
			{
				try
				{
					String owner = extra.getString("Parties." + groupName + ".Owner");
					ArrayList<String> members2 = new ArrayList<String>();
					ArrayList<String> bannedplayers = new ArrayList<String>();
					String password = "";
					
					if (extra.contains("Parties." + groupName + ".Password"))
					{
						password = extra.getString("Parties." + groupName + ".Password");
					}
					
					if (extra.contains("Parties." + groupName + ".BannedPlayers"))
					{
						List<String> bannedPlayers = extra.getStringList("Parties." + groupName + ".BannedPlayers");
						for (String player : bannedPlayers)
						{
							bannedplayers.add(player);
						}
					}
					
					if (extra.contains("Parties." + groupName + ".Members"))
					{
						List<String> members = extra.getStringList("Parties." + groupName + ".Members");
						for (String member : members)
						{
							members2.add(member);
						}
					}
					
					Party party = new Party(groupName, owner, password, members2, bannedplayers);
					
					Parties.put(groupName, party);
				}
				catch (Throwable t)
				{
					Variables.log.log(Level.SEVERE, "[SimpleChat] There was a problem with one of your Parties.");
				}
			}
		}
		catch (Throwable t)
		{
			ArrayList<String> members2 = new ArrayList<String>();
			members2.add("SupremeOverlord");
			members2.add("Player1");
			members2.add("Player2");
			ArrayList<String> bannedplayers = new ArrayList<String>();
			Party party = new Party("ExampleGroup", "SupremeOverlord", "password", members2, bannedplayers);
			Parties.put("ExampleGroup", party);
		}
		
		if (Parties.isEmpty())
		{
			ArrayList<String> members2 = new ArrayList<String>();
			members2.add("SupremeOverlord");
			members2.add("Player1");
			members2.add("Player2");
			ArrayList<String> bannedplayers = new ArrayList<String>();
			Party party = new Party("ExampleGroup", "SupremeOverlord", "password", members2, bannedplayers);
			Parties.put("ExampleGroup", party);
			log.log(Level.SEVERE, "[SimpleChat] Error on the Parties. It was empty. Added a default group. (Extra.yml)");
		}
		
		if (Variables.extra.contains("SocialSpyOffParty"))
		{
			List<String> fake = Variables.extra.getStringList("SocialSpyOffParty");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			Variables.socialSpyOffParty = other;
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ThisPlayer");
			other.add("Player123456789");
			Variables.socialSpyOffParty = other;
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
		Variables.extra.set("UseSimpleChatGeneralFormatting", Variables.UseSimpleChatGeneralFormatting);
		Variables.extra.set("UseSimpleChatMsgAndReplyFormatting", Variables.UseSimpleChatMsgAndReplyFormatting);
		Variables.extra.set("UseSimpleChatAdminChat", Variables.UseSimpleChatAdminChat);
		Variables.extra.set("UseSimpleChatPartyChat", Variables.UseSimpleChatPartyChat);
		
		Variables.extra.set("CurseWords", Variables.CurseWords);
		
		Variables.extra.set("MaxNumberOfCapitalLetters", Variables.MaxNumberOfCapitalLetters);
		
		Variables.extra.set("Punishment.kick", Variables.PunishmentKick);
		Variables.extra.set("Punishment.msgToPlayer", Variables.PunishmentMsgToPlayer);
		Variables.extra.set("Punishment.replaceMsg", Variables.PunishmentReplaceMsg);
		
		Variables.extra.set("MsgToPlayer", Variables.MsgToPlayer);
		
		Variables.extra.set("ReplaceWith", Variables.ReplaceWith);
		
		Variables.extra.set("OtherMessages.join", Variables.OtherMessagesJoin);
		Variables.extra.set("OtherMessages.leave", Variables.OtherMessagesLeave);
		Variables.extra.set("OtherMessages.kick", Variables.OtherMessagesKick);
		
		for (Entry<String, String> entry : Variables.DieMessages.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.extra.set("DieMessages." + key, value);
		}
		
		Variables.extra.set("JoinMsgToPlayer", Variables.JoinMsgToPlayer);
		
		Variables.extra.set("MeFormat", Variables.MeFormat);
		Variables.extra.set("SayFormat", Variables.SayFormat);
		Variables.extra.set("BroadcastFormat", Variables.BroadcastFormat);
		
		Variables.extra.set("SendingMessage", Variables.SendingMessage);
		Variables.extra.set("ReceivingMessage", Variables.ReceivingMessage);
		Variables.extra.set("SocialSpyOffPM", socialSpyOffPM);
		
		Variables.extra.set("AdminChatFormat", Variables.AdminChatFormat);
		
		Variables.extra.set("PartyChatFormat", Variables.PartyChatFormat);
		
		Variables.extra.set("Parties", "Filler");
		
		for (Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			Variables.extra.set("Parties." + entry.getKey() + ".Owner", entry.getValue().owner);
			if (entry.getValue().hasPassword())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".Password", entry.getValue().password);
			}
			if (entry.getValue().hasMembers())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".Members", entry.getValue().members);
			}
			if (entry.getValue().hasBannedPlayers())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".BannedPlayers", entry.getValue().bannedplayers);
			}
			
		}
		
		Variables.extra.set("SocialSpyOffParty", socialSpyOffParty);
	}
}
