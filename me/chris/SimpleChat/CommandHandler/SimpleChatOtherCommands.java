package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatChatState;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class SimpleChatOtherCommands
{

	SimpleChatOtherCommands()
	{
	}

	public static void welcome(Player p)
	{
		String lastestVersion = "";
		try
		{
			lastestVersion = SimpleChatHelperMethods.updateCheck();
		}
		catch (Throwable t)
		{

		}

		p.sendMessage("§5=====================================================");
		p.sendMessage("§a Welcome to §cSimpleChat §aPlugin §9(" + Variables.version + ")");
		p.sendMessage("§a Designed and Programmed by §9Hotshot2162");
		p.sendMessage("§5=====================================================");
		if (!lastestVersion.equalsIgnoreCase(Variables.version))
		{
			p.sendMessage("§4 Warning!§f This isnt the lastest version of SimpleChat!");
			p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Variables.version + "§f)");
			p.sendMessage("§5=====================================================");
		}
	}

	public static void help(Player p)
	{
		p.sendMessage("§5==================§c [ SimpleChat Help ] §5==================");
		p.sendMessage("§c/simplechat §e- States the general info.");
		p.sendMessage("§c/simplechat help §e- Brings up the help menu. ");
		p.sendMessage("§c/simplechat reload §e- Reloads all the files. ");
		p.sendMessage("§c/chat §e- Toggles current chat state. ");
		p.sendMessage("§c/chaton §e- Turns server-wide chat on. ");
		p.sendMessage("§c/chatoff §e- Turns server-wide chat off. ");
		p.sendMessage("§c/scconfig §e- Edits config.yml values ");
		p.sendMessage("§c/scextra §e- Edits extra.yml values ");
		p.sendMessage("§3§oNOTE: A \"...\" signifies that the respective value can");
		p.sendMessage("§3§ohave multiple words/phrases.");
		p.sendMessage("§3§oNOTE: \"msg\" and \"message\" are interchangeable ");
		p.sendMessage("§3§othroughout the plugin. ");
	}

	public static void reload(Player p)
	{
		Variables.plugin.loadYamls();
		p.sendMessage("§aFile Reload Complete");
	}

	public static void me(Player p, String[] args)
	{
		String msg = "";
		for (String word : args)
		{
			msg += " " + word;
		}
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg.trim(), "§" };
		String meMsg = SimpleChatHelperMethods.replaceVars(Variables.MeFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(meMsg);

	}

	public static void chat(Player p)
	{
		String currentState = SimpleChatChatState.getChatState();
		if (currentState.equalsIgnoreCase("off"))
		{
			Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §2§lON§r§7 by §c" + p.getName());
			SimpleChatChatState.setChatState("on");
		}
		else
		{
			Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §4§lOFF§r§7 by §c" + p.getName());
			SimpleChatChatState.setChatState("off");
		}
	}

	public static void chatOn(Player p)
	{
		Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §2§lON§r§7 by §c" + p.getName());
		SimpleChatChatState.setChatState("on");
	}

	public static void chatOff(Player p)
	{
		Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §4§lOFF§r§7 by §c" + p.getName());
		SimpleChatChatState.setChatState("off");
	}

}
