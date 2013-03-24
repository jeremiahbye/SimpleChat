package me.chris.SimpleChat;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Christopher Pybus
 * @date Feb 29, 2012
 * @file SimpleChatHelperMethods.java
 * @package me.chris.SimpleChat
 * 
 * @purpose 
 */

public class SimpleChatHelperMethods
{
	
	public static String replaceVars(String format, String[] vars, String[] replace)
	{
		if (vars.length != replace.length)
			return format;
		
		for (int i = 0; i < vars.length; i++)
		{
			format = format.replace(vars[i], replace[i]);
		}
		
		return format;
	}
	
	public static String censorMessage(String msg, char censorChar, List<String> list)
	{
		Queue<Integer> nonAlphaNumIndexes = new LinkedList<Integer>();
		Queue<Character> nonAlphaNumChars = new LinkedList<Character>();
		
		int i = 0;
		for (char c : msg.toCharArray())
		{
			if ((!(Character.isDigit(c))) && (!(Character.isLetter(c))))
			{
				nonAlphaNumIndexes.add(Integer.valueOf(msg.indexOf(c, i)));
				nonAlphaNumChars.add(Character.valueOf(c));
			}
			++i;
		}
		msg = msg.replaceAll("[^a-zA-Z0-9]", "");
		
		for (String word : list)
		{
			char[] replace = new char[word.length()];
			Arrays.fill(replace, censorChar);
			
			msg = msg.replaceAll("(?i)" + word, String.copyValueOf(replace));
		}
		
		StringBuffer sb = new StringBuffer(msg);
		while ((!(nonAlphaNumIndexes.isEmpty())) && (!(nonAlphaNumChars.isEmpty())))
		{
			int index = ((Integer) nonAlphaNumIndexes.remove()).intValue();
			
			if (sb.length() <= index)
				sb.append(nonAlphaNumChars.remove());
			else
			{
				sb.insert(index, nonAlphaNumChars.remove());
			}
		}
		nonAlphaNumIndexes = null;
		nonAlphaNumChars = null;
		msg = null;
		
		
		return ((String) sb.toString());
	}
	
	public static boolean makeSureMatch(String[] permGroups, ArrayList<String> arrayList)
	{
		if (permGroups.length != arrayList.size())
		{
			return false;
		}
		
		for (int index = 0; index < permGroups.length; index++)
		{
			if (arrayList.contains(permGroups[index].toLowerCase()))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	public static String noCaps(String msg, Player p)
	{
		int maxNumCaps = Variables.MaxNumberOfCapitalLetters;
		int numberOfCaps = 0;
		String cleanMsg = "";

		ArrayList<String> replacerMsgs = Variables.ReplaceWith;
		
		for (char c : msg.toCharArray())
		{
			if (Character.isUpperCase(c))
			{
				numberOfCaps = numberOfCaps + 1;
			}
		}
		
		if (numberOfCaps > maxNumCaps)
		{
			if (Variables.PunishmentKick == true)
			{
				
				p.kickPlayer("taking in more than " + Variables.MaxNumberOfCapitalLetters + " capital letters.");
				cleanMsg = "_KICKED_";
			}
			else
			// Only does the other two things if player hasnt already been kicked.
			{
				if (Variables.PunishmentMsgToPlayer)
				{
					p.sendMessage(Variables.MsgToPlayer.replaceAll("&", "�"));
				}
				
				if (Variables.PunishmentReplaceMsg)
				{
					cleanMsg = replacerMsgs.get(new Random().nextInt(replacerMsgs.size()));
				}
			}
		}
		else
		{
			cleanMsg = msg;
		}
		
		return cleanMsg;
	}
	
	public static String updateCheck() throws Exception
	{
		String pluginUrlString = "http://dev.bukkit.org/server-mods/simplechat/files.rss";
		String lastestVersion = "";
		try
		{
			URL url = new URL(pluginUrlString);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1)
			{
				Element firstElement = (Element) firstNode;
				NodeList firstElementTagName = firstElement.getElementsByTagName("title");
				Element firstNameElement = (Element) firstElementTagName.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();
				lastestVersion = firstNodes.item(0).getNodeValue();
			}
		}
		catch (Exception localException)
		{
		}
		return lastestVersion;
	}
	
	public static String onlinePlayers(String nameColor, String commaColor, String group)
	{
		String onlinePlayers = "";
		Player[] players = Variables.plugin.getServer().getOnlinePlayers();
		
		if (group.equalsIgnoreCase("all"))
		{
			for (int index = 0; players.length > index; index++)
			{
				onlinePlayers = onlinePlayers + nameColor + players[index].getName() + commaColor + ", ";
			}
		}
		else
		{
			for (int index = 0; players.length > index; index++)
			{
				String groupgroup = Variables.perms.getPrimaryGroup(players[index]);
				if (groupgroup != null)
				{
					if (groupgroup.equalsIgnoreCase(group))
					{
						onlinePlayers = onlinePlayers + nameColor + players[index].getName() + commaColor + ", ";
					}
				}
			}
		}
		return onlinePlayers;
	}
	
	public static String parseEntityName(Entity entity)
	{
		String killer = "";
		if (entity instanceof CaveSpider)
			killer = " CaveSpider";

		else if (entity instanceof Chicken)
			killer = " Chicken";

		else if (entity instanceof Cow)
			killer = " Cow";

		else if (entity instanceof Creeper)
			killer = " Creeper";

		else if (entity instanceof Enderman)
			killer = "n Enderman";

		else if (entity instanceof Ghast)
			killer = " Ghast";

		else if (entity instanceof Giant)
			killer = " Giant";

		else if (entity instanceof Pig)
			killer = " Pig";

		else if (entity instanceof PigZombie)
			killer = " PigZombie";

		else if (entity instanceof Sheep)
			killer = " Sheep";

		else if (entity instanceof Silverfish)
			killer = " Silverfish";

		else if (entity instanceof Skeleton)
			killer = " Skeleton";

		else if (entity instanceof Slime)
			killer = " Slime";

		else if (entity instanceof Spider)
			killer = " Spider";

		else if (entity instanceof Squid)
			killer = " Squid";

		else if (entity instanceof Wolf)
			killer = " Wolf";

		else if (entity instanceof Zombie)
			killer = " Zombie";

		else if (entity instanceof Monster)
			killer = " Monster";
		else
			killer = "n unknown entitiy";

		return killer;
	}
	
}
