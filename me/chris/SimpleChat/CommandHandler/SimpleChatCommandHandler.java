package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.Variables;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleChatCommandHandler implements CommandExecutor
{
	private static final String NOPERMS = "�a[SimpleChat] �4You do not have perms to access the following command:";
	private static final String INVALIDCOMMAND = "�a[SimpleChat] �4The following command is invalid:";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String idk, String[] args)
	{
		int length = args.length;
		Player p = null;
		if (!(sender instanceof Player))
		{
			return true;
		}

		p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("simplechat"))
		{
			if (length == 0)
			{
				if (Variables.perms.has(p, "simplechat.welcome") || Variables.perms.has(p, "simplechat.*"))
				{
					SimpleChatOtherCommands.welcome(p);
				}
				else
				{
					noPerms(p, cmd.getName(), args);
				}
			}
			else if (length == 1)
			{
				if (args[0].equalsIgnoreCase("help"))
				{
					if (Variables.perms.has(p, "simplechat.help"))
					{
						SimpleChatOtherCommands.help(p);

					}
					else
					{
						noPerms(p, cmd.getName(), args);

					}
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					if (Variables.perms.has(p, "simplechat.reload") || Variables.perms.has(p, "simplechat.*"))
					{
						SimpleChatOtherCommands.reload(p);

					}
					else
					{
						noPerms(p, cmd.getName(), args);

					}
				}
				else
				{
					invalidCommand(p, cmd.getName(), args);

				}
			}
			else if (length > 1)
			{
				invalidCommand(p, cmd.getName(), args);

			}
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("chat"))
		{
			if (Variables.perms.has(p, "simplechat.chat") || Variables.perms.has(p, "simplechat.*"))
			{
				if (length == 0)
				{
					SimpleChatOtherCommands.chat(p);

				}
				else
				{
					invalidCommand(p, cmd.getName(), args);

				}
			}
			else
			{
				noPerms(p, cmd.getName(), args);

			}
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("chaton"))
		{
			if (Variables.perms.has(p, "simplechat.chaton") || Variables.perms.has(p, "simplechat.*"))
			{
				if (length == 0)
				{
					SimpleChatOtherCommands.chatOn(p);

				}
				else
				{
					invalidCommand(p, cmd.getName(), args);

				}
			}
			else
			{
				noPerms(p, cmd.getName(), args);

			}
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("chatoff"))
		{
			if (Variables.perms.has(p, "simplechat.chatoff") || Variables.perms.has(p, "simplechat.*"))
			{
				if (length == 0)
				{
					SimpleChatOtherCommands.chatOff(p);

				}
				else
				{
					invalidCommand(p, cmd.getName(), args);

				}
			}
			else
			{
				noPerms(p, cmd.getName(), args);

			}
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("scconfig"))
		{
			if (Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
			{
				if (length == 0)
				{
					SimpleChatConfigCommands.help(p);

				}
				else if (length == 1)
				{
					String arg1 = args[0];
					if (arg1.equalsIgnoreCase("msgFormat") || arg1.equalsIgnoreCase("messageFormat"))
					{
						SimpleChatConfigCommands.msgFormatHelp(p);

					}
					else if (arg1.equalsIgnoreCase("groups"))
					{
						SimpleChatConfigCommands.groupsHelp(p);

					}
					else if (arg1.equalsIgnoreCase("users"))
					{
						SimpleChatConfigCommands.usersHelp(p);

					}
					else if (arg1.equalsIgnoreCase("defaults"))
					{
						SimpleChatConfigCommands.defaultsHelp(p);

					}
					else
					{
						invalidCommand(p, cmd.getName(), args);

					}
				}
				else if (length == 2)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					if (arg1.equalsIgnoreCase("msgFormat") || arg1.equalsIgnoreCase("messageFormat"))
					{
						if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatConfigCommands.msgFormatVariables(p);

						}
						else if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatConfigCommands.msgFormatView(p);

						}
						else
						{
							invalidCommand(p, cmd.getName(), args);

						}
					}
					else if (arg1.equalsIgnoreCase("groups"))
					{
						if (arg2.equalsIgnoreCase("sync"))
						{
							SimpleChatConfigCommands.groupsSync(p);

						}
						else if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatConfigCommands.groupsView(p);

						}
						else
						{
							invalidCommand(p, cmd.getName(), args);

						}
					}
					else if (arg1.equalsIgnoreCase("users"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatConfigCommands.usersView(p);

						}
						else
						{
							invalidCommand(p, cmd.getName(), args);

						}
					}
					else if (arg1.equalsIgnoreCase("defaults"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatConfigCommands.defaultsView(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
				else if (length == 3)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					if (arg1.equalsIgnoreCase("msgFormat") || arg1.equalsIgnoreCase("messageFormat"))
					{
						if (arg2.equalsIgnoreCase("setformat"))
						{
							SimpleChatConfigCommands.msgFormatSetFormat(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("groups"))
					{
						if (arg2.equalsIgnoreCase("addgroup"))
						{
							SimpleChatConfigCommands.groupsAddGroup(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("removegroup"))
						{
							SimpleChatConfigCommands.groupsRemoveGroup(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("users"))
					{
						if (arg2.equalsIgnoreCase("adduser"))
						{
							SimpleChatConfigCommands.usersAddUser(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("removeuser"))
						{
							SimpleChatConfigCommands.usersRemoveUser(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("defaults"))
					{
						if (arg2.equalsIgnoreCase("setprefix"))
						{
							SimpleChatConfigCommands.defaultsSetPrefix(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setsuffix"))
						{
							SimpleChatConfigCommands.defaultsSetSuffix(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setgroup"))
						{
							SimpleChatConfigCommands.defaultsSetGroup(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
				else if (length >= 4)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					String arg3 = args[2];

					if (arg1.equalsIgnoreCase("msgFormat") || arg1.equalsIgnoreCase("messageFormat"))
					{
						if (arg2.equalsIgnoreCase("setformat"))
						{
							SimpleChatConfigCommands.msgFormatSetFormat(p, combineArgs(2, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("groups"))
					{
						if (arg2.equalsIgnoreCase("setprefix"))
						{
							SimpleChatConfigCommands.groupsSetPrefix(p, arg3, combineArgs(3, args));
						}
						else if (arg2.equalsIgnoreCase("setsuffix"))
						{
							SimpleChatConfigCommands.groupsSetSuffix(p, arg3, combineArgs(3, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("users"))
					{
						if (arg2.equalsIgnoreCase("setprefix"))
						{
							SimpleChatConfigCommands.usersSetPrefix(p, arg3, combineArgs(3, args));
						}
						else if (arg2.equalsIgnoreCase("setsuffix"))
						{
							SimpleChatConfigCommands.usersSetSuffix(p, arg3, combineArgs(3, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("defaults"))
					{
						SimpleChatConfigCommands.groupsHelp(p);
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
			}
			else
			{
				noPerms(p, cmd.getName(), args);
			}
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("scextra"))
		{
			if (Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
			{
				if (length == 0)
				{
					SimpleChatExtraCommands.help(p);
				}
				else if (length == 1)
				{
					String arg1 = args[0];
					if (arg1.equalsIgnoreCase("chatcensor") || arg1.equalsIgnoreCase("cc"))
					{
						SimpleChatExtraCommands.ccHelp(p);
					}
					else if (arg1.equalsIgnoreCase("capspreventor") || arg1.equalsIgnoreCase("cp"))
					{
						SimpleChatExtraCommands.cpHelp(p);
					}
					else if (arg1.equalsIgnoreCase("otherMessages") || arg1.equalsIgnoreCase("om") || arg1.equalsIgnoreCase("otherMsgs"))
					{
						SimpleChatExtraCommands.omHelp(p);
					}
					else if (arg1.equalsIgnoreCase("diemessage") || arg1.equalsIgnoreCase("dm") || arg1.equalsIgnoreCase("dieMsg"))
					{
						SimpleChatExtraCommands.dmHelp(p);
					}
					else if (arg1.equalsIgnoreCase("joinmessage") || arg1.equalsIgnoreCase("jm") || arg1.equalsIgnoreCase("joinMsg"))
					{
						SimpleChatExtraCommands.jmHelp(p);
					}
					else if (arg1.equalsIgnoreCase("meformat") || arg1.equalsIgnoreCase("me"))
					{
						SimpleChatExtraCommands.meHelp(p);
					}
					else if (arg1.equalsIgnoreCase("messageAndReply") || arg1.equalsIgnoreCase("mr") || arg1.equalsIgnoreCase("msgAndReply"))
					{
						SimpleChatExtraCommands.mrHelp(p);
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
				else if (length == 2)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					if (arg1.equalsIgnoreCase("chatcensor") || arg1.equalsIgnoreCase("cc"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.ccView(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("capspreventor") || arg1.equalsIgnoreCase("cp"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.cpView(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("otherMessages") || arg1.equalsIgnoreCase("om") || arg1.equalsIgnoreCase("otherMsg"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.omView(p);
						}
						else if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatExtraCommands.omVariables(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("diemessage") || arg1.equalsIgnoreCase("dm") || arg1.equalsIgnoreCase("dieMsg"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.dmView(p);
						}
						else if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatExtraCommands.dmVariables(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("joinmessage") || arg1.equalsIgnoreCase("jm") || arg1.equalsIgnoreCase("joinMsg"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.jmView(p);
						}
						else if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatExtraCommands.jmVariables(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("meformat") || arg1.equalsIgnoreCase("me"))
					{
						if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.meView(p);
						}
						else if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatExtraCommands.meVariables(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("messageAndReply") || arg1.equalsIgnoreCase("mr") || arg1.equalsIgnoreCase("msgAndReply"))
					{
						if (arg2.equalsIgnoreCase("variables"))
						{
							SimpleChatExtraCommands.mrVariables(p);
						}
						else if (arg2.equalsIgnoreCase("view"))
						{
							SimpleChatExtraCommands.mrView(p);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
				else if (length == 3)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					if (arg1.equalsIgnoreCase("chatcensor") || arg1.equalsIgnoreCase("cc"))
					{
						if (arg2.equalsIgnoreCase("usechatcensor"))
						{
							SimpleChatExtraCommands.ccUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("addword"))
						{
							SimpleChatExtraCommands.ccAddWord(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("removeword"))
						{
							SimpleChatExtraCommands.ccRemoveWord(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("capspreventor") || arg1.equalsIgnoreCase("cp"))
					{
						if (arg2.equalsIgnoreCase("usecapspreventor"))
						{
							SimpleChatExtraCommands.cpUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setmaxcapitalletters"))
						{
							SimpleChatExtraCommands.cpSetMaxCapitalLetters(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setmsgtoplayer") || arg2.equalsIgnoreCase("setMessageToPlayer"))
						{
							SimpleChatExtraCommands.cpSetMsgToPlayer(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("otherMessages") || arg1.equalsIgnoreCase("om") || arg1.equalsIgnoreCase("otherMsgs"))
					{
						if (arg2.equalsIgnoreCase("useothermessages") || arg2.equalsIgnoreCase("useOtherMsgs"))
						{
							SimpleChatExtraCommands.omUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setjoinmsg") || arg2.equalsIgnoreCase("setJoinMessage"))
						{
							SimpleChatExtraCommands.omSetJoinMsg(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setleavemsg") || arg2.equalsIgnoreCase("setLeaveMessage"))
						{
							SimpleChatExtraCommands.omSetLeaveMsg(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setkickmsg") || arg2.equalsIgnoreCase("setKickMessage"))
						{
							SimpleChatExtraCommands.omSetKickMsg(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("diemessage") || arg1.equalsIgnoreCase("dm") || arg1.equalsIgnoreCase("dieMsg"))
					{
						if (arg2.equalsIgnoreCase("usediemessage") || arg2.equalsIgnoreCase("useDieMsg"))
						{
							SimpleChatExtraCommands.dmUse(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("joinmessage") || arg1.equalsIgnoreCase("jm") || arg1.equalsIgnoreCase("joinMsg"))
					{
						if (arg2.equalsIgnoreCase("usejoinmessage") || arg2.equalsIgnoreCase("useJoinMsg"))
						{
							SimpleChatExtraCommands.jmUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("removeline"))
						{
							SimpleChatExtraCommands.jmRemoveLine(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("meformat") || arg1.equalsIgnoreCase("me"))
					{
						if (arg2.equalsIgnoreCase("usemeformat"))
						{
							SimpleChatExtraCommands.meUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setmeformat"))
						{
							SimpleChatExtraCommands.meSetMeFormat(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("messageAndReply") || arg1.equalsIgnoreCase("mr") || arg1.equalsIgnoreCase("msgAndReply"))
					{
						if (arg2.equalsIgnoreCase("useMessageAndReply") || arg2.equalsIgnoreCase("useMsgAndReply"))
						{
							SimpleChatExtraCommands.mrUse(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setSendingFormat"))
						{
							SimpleChatExtraCommands.mrSetSendingMsg(p, arg3);
						}
						else if (arg2.equalsIgnoreCase("setReceivingFormat"))
						{
							SimpleChatExtraCommands.mrSetReceivingMsg(p, arg3);
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
				else if (length >= 4)
				{
					String arg1 = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					String arg4 = args[3];
					if (arg1.equalsIgnoreCase("chatcensor") || arg1.equalsIgnoreCase("cc"))
					{
						if (arg2.equalsIgnoreCase("addword"))
						{
							SimpleChatExtraCommands.ccAddWord(p, combineArgs(args));
						}
						else if (arg2.equalsIgnoreCase("removeword"))
						{
							SimpleChatExtraCommands.ccRemoveWord(p, combineArgs(args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("capspreventor") || arg1.equalsIgnoreCase("cp"))
					{
						if (arg2.equalsIgnoreCase("setpunishment"))
						{
							SimpleChatExtraCommands.cpSetPunishment(p, arg3, arg4);
						}
						else if (arg2.equalsIgnoreCase("setmsgtoplayer") || arg2.equalsIgnoreCase("setMessageToPlayer"))
						{
							SimpleChatExtraCommands.cpSetMsgToPlayer(p, combineArgs(2, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("otherMessages") || arg1.equalsIgnoreCase("om") || arg1.equalsIgnoreCase("otherMsgs"))
					{
						if (arg2.equalsIgnoreCase("setjoinmsg") || arg2.equalsIgnoreCase("setJoinMessage"))
						{
							SimpleChatExtraCommands.omSetJoinMsg(p, combineArgs(2, args));
						}
						else if (arg2.equalsIgnoreCase("setleavemsg") || arg2.equalsIgnoreCase("setLeaveMessage"))
						{
							SimpleChatExtraCommands.omSetLeaveMsg(p, combineArgs(2, args));
						}
						else if (arg2.equalsIgnoreCase("setkickmsg") || arg2.equalsIgnoreCase("setKickMessage"))
						{
							SimpleChatExtraCommands.omSetKickMsg(p, combineArgs(2, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("diemessage") || arg1.equalsIgnoreCase("dm") || arg1.equalsIgnoreCase("dieMsg"))
					{
						if (arg2.equalsIgnoreCase("setdiemessage") || arg2.equalsIgnoreCase("setDieMsg"))
						{
							SimpleChatExtraCommands.dmSetDieMsg(p, arg3, combineArgs(3, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("joinmessage") || arg1.equalsIgnoreCase("jm") || arg1.equalsIgnoreCase("joinMsg"))
					{
						if (arg2.equalsIgnoreCase("setline"))
						{
							SimpleChatExtraCommands.jmSetLine(p, arg3, combineArgs(3, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("meformat") || arg1.equalsIgnoreCase("me"))
					{
						if (arg2.equalsIgnoreCase("setmeformat"))
						{
							SimpleChatExtraCommands.meSetMeFormat(p, combineArgs(2, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else if (arg1.equalsIgnoreCase("messageAndReply") || arg1.equalsIgnoreCase("mr") || arg1.equalsIgnoreCase("msgAndReply"))
					{
						if (arg2.equalsIgnoreCase("setSendingFormat"))
						{
							SimpleChatExtraCommands.mrSetSendingMsg(p, combineArgs(2, args));
						}
						else if (arg2.equalsIgnoreCase("setReceivingFormat"))
						{
							SimpleChatExtraCommands.mrSetReceivingMsg(p, combineArgs(2, args));
						}
						else
						{
							invalidCommand(p, cmd.getName(), args);
						}
					}
					else
					{
						invalidCommand(p, cmd.getName(), args);
					}
				}
			}
			else
			{
				noPerms(p, cmd.getName(), args);
			}
			return true;
		}
		else
		{
			return false;
		}

	}

	public static void noPerms(Player p, String cmd, String[] args)
	{
		String command = cmd;
		for (String arg : args)
		{
			cmd += " " + arg;
		}
		p.sendMessage(NOPERMS);
		p.sendMessage("�a[SimpleChat] �o�c/" + command);

	}

	public static void invalidCommand(Player p, String cmd, String[] args)
	{
		String command = cmd;
		for (String arg : args)
		{
			command += " " + arg;
		}
		p.sendMessage(INVALIDCOMMAND);
		p.sendMessage("�a[SimpleChat] �o�c/" + command);

	}

	public static String combineArgs(int startAt, String[] args)
	{
		String combined = "";
		for (int index = startAt; index < args.length; index++)
		{
			combined += args[index] + " ";
		}
		combined.trim();
		return combined;
	}

	public static String[] combineArgs(String[] args)
	{
		String[] newArgs = new String[args.length - 2];
		for (int index = 2; index < args.length; index++)
		{
			newArgs[index - 2] = args[index];
		}
		return newArgs;
	}
}
