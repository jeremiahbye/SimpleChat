package me.chris.SimpleChat;

import java.util.ArrayList;
import java.util.logging.Level;

import me.chris.SimpleChat.CommandHandler.SimpleChatCommandHandler;
import me.chris.SimpleChat.CommandHandler.SimpleChatOtherCommands;
import me.chris.SimpleChat.CommandHandler.SimpleChatPrivateMessaging;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SimpleChatListener implements Listener
{
	String chatState = "";

	public SimpleChatListener()
	{

	}

	// +------------------------------------------------------+ //
	// | Player Join | //
	// | | //
	// | For when a player joins. Ton of stuff going on in | //
	// | This listener, look through code carefully. | //
	// +------------------------------------------------------+ //
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();

		if (p.isOp())
		{
			String lastestVersion = "";
			try
			{
				lastestVersion = SimpleChatHelperMethods.updateCheck();
			}
			catch (Throwable t)
			{

			}

			if (!lastestVersion.equalsIgnoreCase(Variables.version))
			{
				p.sendMessage("§5=====================================================");
				p.sendMessage("§4 Warning!§f This isnt the lastest version of SimpleChat!");
				p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Variables.version + "§f)");
				p.sendMessage("§5=====================================================");
			}
		}

		if (Variables.UseSimpleChatOtherMessages == true)
		{
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
			String joinMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesJoin, vars, replace);
			event.setJoinMessage(joinMsg);
		}

		if (Variables.UseSimpleChatJoinMsg == true)
		{
			ArrayList<String> joinMsgs = Variables.JoinMsgToPlayer;

			for (int counter = 0; counter < joinMsgs.size(); counter++)
			{
				String msg = joinMsgs.get(counter);
				String originalMsg = msg;

				ArrayList<Integer> angleBrackets = new ArrayList<Integer>();

				for (int index = 0; index < msg.length(); index++)
				{
					if (msg.charAt(index) == '<')
					{
						angleBrackets.add(index);
						angleBrackets.add(msg.indexOf((char) 62, index));
					}
				}

				if (angleBrackets.isEmpty())
				{
					String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
					String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
					msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);

					p.sendMessage(msg);
					continue;
				}

				if (angleBrackets.size() % 2 != 0 || angleBrackets.contains(-1))
				{
					Variables.log.log(Level.WARNING, "The onlineplayers variable on Line " + (counter + 1) + " in the JoinMsgToPlayer section of extra.yml is NOT in the proper format. Please fix.");
					String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
					String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
					msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);

					p.sendMessage(msg);
					continue;
				}

				for (int index = 0; index < angleBrackets.size(); index += 2)
				{
					String substring = originalMsg.substring((angleBrackets.get(index) + 1), (angleBrackets.get(index + 1)));
					String nameColor = "&f";
					String commaColor = "&f";
					String group = "all";
					if (substring.length() >= 13 && substring.substring(0, 13).equalsIgnoreCase("onlineplayers"))
					{
						if (substring.length() == 13 && !substring.contains(":"))
						{
							msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(nameColor, commaColor, group));
						}
						else if (substring.contains(":"))
						{
							String[] split1 = substring.split(":");
							if (split1[1].contains(","))
							{
								String[] variables = split1[1].split(",");
								if (variables.length == 1)
								{
									msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(variables[0], commaColor, group));
								}
								else if (variables.length == 2)
								{
									msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(variables[0], variables[1], group));
								}
								else if (variables.length == 3)
								{
									msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(variables[0], variables[1], variables[2]));
								}
							}
							else
							{
								msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(split1[1], commaColor, group));
							}
						}
					}
					else
					{
						Variables.log.log(Level.WARNING, "The onlineplayers variable on Line " + (counter + 1) + " in the JoinMsgToPlayer section of extra.yml is NOT in the proper format. Please fix.");
					}
				}

				String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
				String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
				msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);

				p.sendMessage(msg);
			}
		}

	}

	// +------------------------------------------------------+ //
	// | Player Chat | //
	// | | //
	// | For when a player chats. Ton of stuff going on in | //
	// | This listener, look through code carefully. | //
	// +------------------------------------------------------+ //

	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent event)
	{
		Player p = event.getPlayer();
		String msg = event.getMessage();
		
		if(Variables.lockedPM.containsKey(p))
		{
			event.setCancelled(true);
			SimpleChatPrivateMessaging.lockedPMChat(p, msg);
			return;
		}
		
		msg = msg.replace("%", "%%");

		if (SimpleChatChatState.getChatState().equalsIgnoreCase("off") && !Variables.perms.has(p, "simplechat.chatoffbypass"))
		{
			event.setCancelled(true);
			p.sendMessage("§a[SimpleChat]§4 An admin has turned off chat.");
			return;
		}

		if (Variables.matching == false)
		{
			return;
		}

		// Replaces variables.
		String playerChat = SimpleChatHelperMethods.replaceVars(Variables.MessageFormat, new String[] { "+pre", "+suf", "+pname", "+dname", "+gro", "&" }, new String[] { SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), event.getPlayer().getName(), event.getPlayer().getDisplayName(), SimpleChatAPI.getGroup(p), "§" });

		// Checks to see if sever uses the censor.
		if (Variables.UseSimpleChatCensor == true)
		{
			if (!Variables.perms.has(p, "simplechat.cancurse"))
			{
				msg = SimpleChatHelperMethods.censorMessage(msg, '*', Variables.CurseWords);
			}
		}

		// Checks to see if the server uses the caps preventer
		if (Variables.UseSimpleChatCapsPreventer == true)
		{
			if (!Variables.perms.has(p, "simplechat.canusecaps"))
			{
				msg = SimpleChatHelperMethods.noCaps(msg, p);
				if (msg.equals("_KICKED_"))
				{
					event.setCancelled(true);
					return;
				}
			}
		}

		// Checks to see if player can use color in chat.
		if (msg.contains("&") && Variables.perms.has(p, "simplechat.color"))
		{
			msg = msg.replace("&", "§");
		}
		else if (msg.contains("&") && !Variables.perms.has(p, "simplechat.color"))
		{
			p.sendMessage("§a[SimpleChat] §4You dont have perms for colored chat!");
		}

		playerChat = playerChat.replace("+msg", msg);
		event.setFormat(playerChat);

		p = null;
		msg = null;
		playerChat = null;

	}

	// +------------------------------------------------------+ //
	// | Player Quit and Player Kick | //
	// | | //
	// | Here are the two listeners for when a player quits | //
	// | And when a player is kicked. Enjoy. | //
	// +------------------------------------------------------+ //

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (Variables.UseSimpleChatOtherMessages == true)
		{
			Player p = event.getPlayer();
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };

			String leaveMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesLeave, vars, replace);

			event.setQuitMessage(leaveMsg);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event)
	{
		Player p = event.getPlayer();
		String reason = event.getReason();
		if (Variables.UseSimpleChatOtherMessages == true)
		{
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+reason", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), reason, "§" };

			String kickMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesKick, vars, replace);
			event.setLeaveMessage(kickMsg);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (!(event.getEntity() instanceof Player))
			return;
		if (!(event instanceof PlayerDeathEvent))
			return;
		if (Variables.UseSimpleChatDieMessages == true)
		{
			Player player = (Player) event.getEntity();

			String pName = player.getName();
			String dName = player.getDisplayName();
			String damager = "";

			PlayerDeathEvent subEvent = (PlayerDeathEvent) event;
			EntityDamageEvent dEvent = player.getLastDamageCause();

			if (dEvent instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent dEEvent = (EntityDamageByEntityEvent) dEvent;
				if (dEEvent.getDamager() instanceof Player)
					damager = ((Player) dEEvent.getDamager()).getName();
				else
				{
					damager = "a" + SimpleChatHelperMethods.parseEntityName(dEEvent.getDamager());
				}
			}

			String dMsg = subEvent.getDeathMessage();

			String finalDeathMsg = "";

			if (dMsg.contains("went up in flames"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathInFire");
			}
			else if (dMsg.contains("burned to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathOnFire");
			}
			else if (dMsg.contains("tried to swim in lava"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathLava");
			}
			else if (dMsg.contains("suffocated in a wall"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathInWall");
			}
			else if (dMsg.contains("drowned"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathDrowned");
			}
			else if (dMsg.contains("starved to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathStarve");
			}
			else if (dMsg.contains("was pricked to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathCactus");
			}
			else if (dMsg.contains("hit the ground too hard"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathFall");
			}
			else if (dMsg.contains("fell out of the world"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathOutOfWorld");
			}
			else if (dMsg.contains("died"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathGeneric");
			}
			else if (dMsg.contains("blew up"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathExplosion");
			}
			else if (dMsg.contains("was killed by magic"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathMagic");
			}
			else if (dMsg.contains("was slain by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathSlainBy");
			}
			else if (dMsg.contains("was shot by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathArrow");
			}
			else if (dMsg.contains("was fireballed by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathFireball");
			}
			else if (dMsg.contains("was pummeled by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathThrown");
			}
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+other", "&" };
			String[] replace = { pName, dName, SimpleChatAPI.getPrefix(player), SimpleChatAPI.getSuffix(player), SimpleChatAPI.getGroup(player), damager, "§" };

			subEvent.setDeathMessage(SimpleChatHelperMethods.replaceVars(finalDeathMsg, vars, replace));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
	{
		String message = event.getMessage();
		String[] tempArgs = message.split(" ");
		String cmd = tempArgs[0].replace("/", "");
		String[] args = new String[tempArgs.length - 1];

		if (args.length > 0)
		{
			for (int index = 1; index < tempArgs.length; index++)
			{
				args[index - 1] = tempArgs[index];
			}
		}

		Player p = event.getPlayer();

		if ((cmd.equalsIgnoreCase("message") || cmd.equalsIgnoreCase("msg") || cmd.equalsIgnoreCase("m") || cmd.equalsIgnoreCase("tell") || cmd.equalsIgnoreCase("whisper") || cmd.equalsIgnoreCase("pm")) && Variables.UseSimpleChatMsgAndReplyFormatting == true)
		{
			if (Variables.perms.has(p, "simplechat.msg") || Variables.perms.has(p, "simplechat.*"))
			{
				
				if (args.length > 1)
				{
					SimpleChatPrivateMessaging.msg(p, args[0], SimpleChatCommandHandler.combineArgs(1, args));
				}
				else if (args.length == 1)
				{
					SimpleChatPrivateMessaging.lockPM(p, args[0]);
				}
				else if (args.length == 0 && Variables.lockedPM.containsKey(p))
				{
					SimpleChatPrivateMessaging.unlockPM(p);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}

			event.setCancelled(true);
		}

		else if ((cmd.equalsIgnoreCase("reply") || cmd.equalsIgnoreCase("r")) && Variables.UseSimpleChatMsgAndReplyFormatting == true)
		{
			if (Variables.perms.has(p, "simplechat.reply") || Variables.perms.has(p, "simplechat.*"))
			{
				if (args.length >= 1)
				{
					SimpleChatPrivateMessaging.reply(p, SimpleChatCommandHandler.combineArgs(0, args));
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			event.setCancelled(true);
		}
		else if (cmd.equalsIgnoreCase("me") && Variables.UseSimpleChatMeFormatting == true)
		{
			if (Variables.perms.has(p, "simplechat.me") || Variables.perms.has(p, "simplechat.*"))
			{
				if (args.length >= 1)
				{
					SimpleChatOtherCommands.me(p, args);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);

			}
			event.setCancelled(true);
		}
	}

}
