/**
 * 
 */
package me.chris.SimpleChat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.Set;

import me.chris.SimpleChat.PartyHandler.Party;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Chris
 */
public class SimpleChatYamlUpdater
{
	static boolean					UseSimpleChatCensor;
	static ArrayList<String>		CurseWords;
	
	static boolean					UseSimpleChatCapsPreventer;
	static int						MaxNumberOfCapitalLetters;
	static boolean					PunishmentKick;
	static boolean					PunishmentMsgToPlayer;
	static boolean					PunishmentReplaceMsg;
	static String					MsgToPlayer;
	static ArrayList<String>		ReplaceWith;
	
	static boolean					UseSimpleChatOtherMessages;
	static String					OtherMessagesJoin;
	static String					OtherMessagesLeave;
	static String					OtherMessagesKick;
	
	static boolean					UseSimpleChatDieMessages;
	static HashMap<String, String>	DieMessages;
	
	static boolean					UseSimpleChatJoinMsg;
	static ArrayList<String>		JoinMsgToPlayer;
	
	static boolean					UseSimpleChatGeneralFormatting;
	static String					MeFormat;
	static String					SayFormat;
	static String					BroadcastFormat;
	
	static boolean					UseSimpleChatMsgAndReplyFormatting;
	static String					SendingMessage;
	static String					ReceivingMessage;
	
	static boolean					UseSimpleChatAdminChat;
	static String					AdminChatFormat;
	
	static boolean					UseSimpleChatPartyChat;
	static String					PartyChatFormat;
	static HashMap<String, Party>	Parties;
	
	static ArrayList<String>		socialSpyOffPM;
	static ArrayList<String>		socialSpyOffParty;
	
	public static void updateExtraYaml()
	{
		CurseWords = new ArrayList<String>();
		
		ReplaceWith = new ArrayList<String>();
		
		DieMessages = new HashMap<String, String>();
		
		JoinMsgToPlayer = new ArrayList<String>();
		
		Parties = new HashMap<String, Party>();
		
		socialSpyOffPM = new ArrayList<String>();
		socialSpyOffParty = new ArrayList<String>();
		
		FileConfiguration extraTemp = new YamlConfiguration();
		try
		{
			extraTemp.load(Variables.extraFile);
		}
		catch (Throwable t)
		{
			
		}
		
		if (extraTemp.contains("UseSimpleChatCensor"))
		{
			String useCensor = extraTemp.getString("UseSimpleChatCensor");
			if (useCensor.equalsIgnoreCase("true"))
			{
				UseSimpleChatCensor = true;
			}
			else
			{
				UseSimpleChatCensor = false;
			}
		}
		else
		{
			UseSimpleChatCensor = true;
		}
		
		if (extraTemp.contains("UseSimpleChatCapsPreventer"))
		{
			String useCapsLock = extraTemp.getString("UseSimpleChatCapsPreventer");
			if (useCapsLock.equalsIgnoreCase("true"))
			{
				UseSimpleChatCapsPreventer = true;
			}
			else
			{
				UseSimpleChatCapsPreventer = false;
			}
		}
		else
		{
			UseSimpleChatCapsPreventer = true;
		}
		
		if (extraTemp.contains("UseSimpleChatOtherMessages"))
		{
			String useOther = extraTemp.getString("UseSimpleChatOtherMessages");
			if (useOther.equalsIgnoreCase("true"))
			{
				UseSimpleChatOtherMessages = true;
			}
			else
			{
				UseSimpleChatOtherMessages = false;
			}
		}
		else
		{
			UseSimpleChatOtherMessages = true;
		}
		
		if (extraTemp.contains("UseSimpleChatDieMessages"))
		{
			String useDie = extraTemp.getString("UseSimpleChatDieMessages");
			if (useDie.equalsIgnoreCase("true"))
			{
				UseSimpleChatDieMessages = true;
			}
			else
			{
				UseSimpleChatDieMessages = false;
			}
		}
		else
		{
			UseSimpleChatDieMessages = true;
		}
		
		if (extraTemp.contains("UseSimpleChatJoinMsg"))
		{
			String useJoinMsg = extraTemp.getString("UseSimpleChatJoinMsg");
			if (useJoinMsg.equalsIgnoreCase("true"))
			{
				UseSimpleChatJoinMsg = true;
			}
			else
			{
				UseSimpleChatJoinMsg = false;
			}
		}
		else
		{
			UseSimpleChatJoinMsg = true;
		}
		
		if (extraTemp.contains("UseSimpleChatMeFormatting"))
		{
			String useGeneralFormatting = extraTemp.getString("UseSimpleChatMeFormatting");
			if (useGeneralFormatting.equalsIgnoreCase("true"))
			{
				UseSimpleChatGeneralFormatting = true;
			}
			else
			{
				UseSimpleChatGeneralFormatting = false;
			}
		}
		else if (extraTemp.contains("UseSimpleChatGeneralFormatting"))
		{
			String useGeneralFormatting = extraTemp.getString("UseSimpleChatGeneralFormatting");
			if (useGeneralFormatting.equalsIgnoreCase("true"))
			{
				UseSimpleChatGeneralFormatting = true;
			}
			else
			{
				UseSimpleChatGeneralFormatting = false;
			}
		}
		else
		{
			UseSimpleChatGeneralFormatting = true;
		}
		
		if (extraTemp.contains("UseSimpleChatMsgAndReplyFormatting"))
		{
			String useMsgReply = extraTemp.getString("UseSimpleChatMsgAndReplyFormatting");
			if (useMsgReply.equalsIgnoreCase("true"))
			{
				UseSimpleChatMsgAndReplyFormatting = true;
			}
			else
			{
				UseSimpleChatMsgAndReplyFormatting = false;
			}
		}
		else
		{
			UseSimpleChatMsgAndReplyFormatting = true;
		}
		
		if (extraTemp.contains("UseSimpleChatAdminChat"))
		{
			String useAdminChat = extraTemp.getString("UseSimpleChatAdminChat");
			if (useAdminChat.equalsIgnoreCase("true"))
			{
				UseSimpleChatAdminChat = true;
			}
			else
			{
				UseSimpleChatAdminChat = false;
			}
		}
		else
		{
			UseSimpleChatAdminChat = true;
		}
		
		if (extraTemp.contains("UseSimpleChatPartyChat"))
		{
			String useAdminChat = extraTemp.getString("UseSimpleChatPartyChat");
			if (useAdminChat.equalsIgnoreCase("true"))
			{
				UseSimpleChatPartyChat = true;
			}
			else
			{
				UseSimpleChatPartyChat = false;
			}
		}
		else
		{
			UseSimpleChatPartyChat = true;
		}
		
		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------
		
		// Get Curse Words
		try
		{
			List<String> fake = extraTemp.getStringList("CurseWords");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str.toLowerCase());
			}
			CurseWords = other;
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
			CurseWords = other;
		}
		
		// Get Max caps
		if (extraTemp.contains("MaxNumberOfCapitalLetters"))
		{
			MaxNumberOfCapitalLetters = extraTemp.getInt("MaxNumberOfCapitalLetters");
		}
		else
		{
			MaxNumberOfCapitalLetters = 5;
		}
		
		// Get Punishments
		String punshKick;
		String punshMsg;
		String punshReplace;
		
		if (extraTemp.contains("Punishment.kick"))
		{
			punshKick = extraTemp.getString("Punishment.kick");
		}
		else
		{
			punshKick = "false";
		}
		
		if (extraTemp.contains("Punishment.msgToPlayer"))
		{
			punshMsg = extraTemp.getString("Punishment.msgToPlayer");
		}
		else
		{
			punshMsg = "false";
		}
		
		if (extraTemp.contains("Punishment.kick"))
		{
			punshReplace = extraTemp.getString("Punishment.msgToPlayer");
		}
		else
		{
			punshReplace = "false";
		}
		
		if (punshKick.equalsIgnoreCase("true"))
			PunishmentKick = true;
		else
			PunishmentKick = false;
		
		if (punshMsg.equalsIgnoreCase("true"))
			PunishmentMsgToPlayer = true;
		else
			PunishmentMsgToPlayer = false;
		
		if (punshReplace.equalsIgnoreCase("true"))
			PunishmentReplaceMsg = true;
		else
			PunishmentReplaceMsg = false;
		
		// Get MsgToPlayer
		if (extraTemp.contains("MsgToPlayer"))
		{
			MsgToPlayer = extraTemp.getString("MsgToPlayer");
		}
		else
		{
			MsgToPlayer = "&4Please dont use caps, dude";
		}
		
		// Get replacers
		try
		{
			List<String> fake = extraTemp.getStringList("ReplaceWith");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			ReplaceWith = other;
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
			
			ReplaceWith = other;
		}
		
		// Other messages
		if (extraTemp.contains("OtherMessages.join"))
		{
			OtherMessagesJoin = extraTemp.getString("OtherMessages.join");
		}
		else
		{
			OtherMessagesJoin = "&a+pname &ehas joined the game.";
		}
		
		if (extraTemp.contains("OtherMessages.leave"))
		{
			OtherMessagesLeave = extraTemp.getString("OtherMessages.leave");
		}
		else
		{
			OtherMessagesLeave = "&a+pname &ehas left the game.";
		}
		
		if (extraTemp.contains("OtherMessages.leave"))
		{
			OtherMessagesKick = extraTemp.getString("OtherMessages.kick");
		}
		else
		{
			OtherMessagesKick = "&a+pname &ehas been kicked from the game.";
		}
		
		// Die Messages
		String[] death = { "deathInFire", "deathOnFire", "deathLava", "deathInWall", "deathDrowned", "deathStarve",
				"deathCactus", "deathFall", "deathOutOfWorld", "deathGeneric", "deathExplosion", "deathMagic",
				"deathSlainBy", "deathArrow", "deathFireball", "deathThrown" };
		
		for (String d : death)
		{
			try
			{
				DieMessages.put(d, extraTemp.getString("DieMessages." + d));
			}
			catch (Throwable t)
			{
				DieMessages.put(d, "+pname has died");
			}
		}
		
		// Get Join Msg To Player
		try
		{
			List<String> fake = extraTemp.getStringList("JoinMsgToPlayer");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			JoinMsgToPlayer = other;
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
			
			ReplaceWith = other;
			
		}
		
		// Get Me Format
		if (extraTemp.contains("MeFormat"))
		{
			MeFormat = extraTemp.getString("MeFormat");
		}
		else
		{
			MeFormat = "* &c+dname &7+msg";
		}
		
		// Get Say Format
		if (extraTemp.contains("SayFormat"))
		{
			SayFormat = extraTemp.getString("SayFormat");
		}
		else
		{
			SayFormat = "&6[&4Server&6] &a+msg";
		}
		
		// Get Broadcast Format=
		if (extraTemp.contains("BroadcastFormat"))
		{
			BroadcastFormat = extraTemp.getString("BroadcastFormat");
		}
		else
		{
			BroadcastFormat = "&6[&4Broadcast&6] &a+msg";
		}
		
		// Get Msg and Reply
		if (extraTemp.contains("SendingMessage"))
		{
			SendingMessage = extraTemp.getString("SendingMessage");
		}
		else
		{
			SendingMessage = "&8&l[&r&7+pname &e-> &7+otherpname&8&l] &r&b&o+msg";
		}
		
		if (extraTemp.contains("ReceivingMessage"))
		{
			ReceivingMessage = extraTemp.getString("ReceivingMessage");
		}
		else
		{
			ReceivingMessage = "&8&l[&r&7+otherpname &e-> &7+pname&8&l] &r&b&o+msg";
		}
		
		if (extraTemp.contains("SocialSpyOffPM"))
		{
			List<String> fake = extraTemp.getStringList("SocialSpyOffPM");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			socialSpyOffPM = other;
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ThisPlayer");
			other.add("Player123456789");
			socialSpyOffPM = other;
		}
		
		if (extraTemp.contains("AdminChatFormat"))
		{
			AdminChatFormat = extraTemp.getString("AdminChatFormat");
		}
		else
		{
			AdminChatFormat = "&3[AdminChat] &7+pname: &4+msg";
		}
		
		if (extraTemp.contains("PartyChatFormat"))
		{
			PartyChatFormat = extraTemp.getString("PartyChatFormat");
		}
		else
		{
			PartyChatFormat = "&3[+partyname] &7+pname: &2+msg";
		}
		
		if (extraTemp.contains("Parties"))
		{
			try
			{
				Set<String> partyHeaders = extraTemp.getConfigurationSection("Parties.").getKeys(false);
				for (String groupName : partyHeaders)
				{
					try
					{
						String owner = extraTemp.getString("Parties." + groupName + ".Owner");
						CustomStringList members2 = new CustomStringList();
						CustomStringList bannedplayers = new CustomStringList();
						String password = "";
						
						if (extraTemp.contains("Parties." + groupName + ".Password"))
						{
							password = extraTemp.getString("Parties." + groupName + ".Password");
						}
						
						if (extraTemp.contains("Parties." + groupName + ".BannedPlayers"))
						{
							List<String> bannedPlayers = extraTemp.getStringList("Parties." + groupName
									+ ".BannedPlayers");
							for (String player : bannedPlayers)
							{
								bannedplayers.add(player);
							}
						}
						
						if (extraTemp.contains("Parties." + groupName + ".Members"))
						{
							List<String> members = extraTemp.getStringList("Parties." + groupName + ".Members");
							for (String member : members)
							{
								if (bannedplayers.contains(member))
								{
									continue;
								}
								
								if (!members2.contains(member))
								{
									members2.add(member);
								}
								
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
		}
		else
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
		}
		
		if (extraTemp.contains("SocialSpyOffParty"))
		{
			List<String> fake = extraTemp.getStringList("SocialSpyOffParty");
			ArrayList<String> other = new ArrayList<String>();
			for (String str : fake)
			{
				other.add(str);
			}
			socialSpyOffParty = other;
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ThisPlayer");
			other.add("Player123456789");
			socialSpyOffParty = other;
		}
		
		Variables.extraFile.delete();
		
		Variables.extraFile = new File(Variables.plugin.getDataFolder(), "extra.yml");
		
		try
		{
			Variables.extraFile.createNewFile();
		}
		catch (Throwable t)
		{
			
		}
		
		extraTemp = new YamlConfiguration();
		
		try
		{
			extraTemp.load(Variables.extraFile);
		}
		catch (Throwable t)
		{
			
		}
		
		extraTemp.set("UseSimpleChatCensor", UseSimpleChatCensor);
		extraTemp.set("CurseWords", CurseWords);
		
		extraTemp.set("UseSimpleChatCapsPreventer", UseSimpleChatCapsPreventer);
		extraTemp.set("MaxNumberOfCapitalLetters", MaxNumberOfCapitalLetters);
		extraTemp.set("Punishment.kick", PunishmentKick);
		extraTemp.set("Punishment.msgToPlayer", PunishmentMsgToPlayer);
		extraTemp.set("Punishment.replaceMsg", PunishmentReplaceMsg);
		extraTemp.set("MsgToPlayer", MsgToPlayer);
		extraTemp.set("ReplaceWith", ReplaceWith);
		
		extraTemp.set("UseSimpleChatOtherMessages", UseSimpleChatOtherMessages);
		extraTemp.set("OtherMessages.join", OtherMessagesJoin);
		extraTemp.set("OtherMessages.leave", OtherMessagesLeave);
		extraTemp.set("OtherMessages.kick", OtherMessagesKick);
		
		extraTemp.set("UseSimpleChatDieMessages", UseSimpleChatDieMessages);
		for (Entry<String, String> entry : DieMessages.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			extraTemp.set("DieMessages." + key, value);
		}
		
		extraTemp.set("UseSimpleChatJoinMsg", UseSimpleChatJoinMsg);
		extraTemp.set("JoinMsgToPlayer", JoinMsgToPlayer);
		
		extraTemp.set("UseSimpleChatGeneralFormatting", UseSimpleChatGeneralFormatting);
		extraTemp.set("MeFormat", MeFormat);
		extraTemp.set("SayFormat", SayFormat);
		extraTemp.set("BroadcastFormat", BroadcastFormat);
		
		extraTemp.set("UseSimpleChatMsgAndReplyFormatting", UseSimpleChatMsgAndReplyFormatting);
		extraTemp.set("SendingMessage", SendingMessage);
		extraTemp.set("ReceivingMessage", ReceivingMessage);
		extraTemp.set("SocialSpyOffPM", socialSpyOffPM);
		
		extraTemp.set("UseSimpleChatAdminChat", Boolean.valueOf(UseSimpleChatAdminChat));
		extraTemp.set("AdminChatFormat", AdminChatFormat);
		
		extraTemp.set("UseSimpleChatPartyChat", UseSimpleChatPartyChat);
		extraTemp.set("PartyChatFormat", PartyChatFormat);
		Variables.config.set("Parties", " ");
		
		for (Entry<String, Party> entry : Parties.entrySet())
		{
			extraTemp.set("Parties." + entry.getKey() + ".Owner", entry.getValue().owner);
			if (entry.getValue().hasPassword())
			{
				extraTemp.set("Parties." + entry.getKey() + ".Password", entry.getValue().password);
			}
			if (entry.getValue().hasMembers())
			{
				extraTemp.set("Parties." + entry.getKey() + ".Members", entry.getValue().members);
			}
			if (entry.getValue().hasBannedPlayers())
			{
				extraTemp.set("Parties." + entry.getKey() + ".BannedPlayers", entry.getValue().bannedplayers);
			}
		}
		extraTemp.set("SocialSpyOffParty", socialSpyOffParty);
		
		try
		{
			extraTemp.save(Variables.extraFile);
		}
		catch (Throwable e)
		{
			
		}
	}
}
