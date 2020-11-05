package eu.endermite.serverbasics.chat;

import eu.endermite.serverbasics.ServerBasics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        // Staffchat
        if (ServerBasics.getConfigCache().STAFFCHAT_ENABLED && player.hasPermission("serverbasics.staffchat") && event.getMessage().startsWith("!")) {
            // Make sure only players with staffchat perms get the message
            event.getRecipients().removeIf(recipent -> !recipent.hasPermission("serverbasics.staffchat"));

            // Remove the staffchat symbol
            String message = event.getMessage();
            message = message.substring(1);
            event.setMessage(message);

            String format = ServerBasics.getConfigCache().STAFFCHAT_FORMAT;
            format = format.replaceAll("%nickname%", player.getDisplayName());
            format = format+"%2$s";
            format = ChatColor.translateAlternateColorCodes('&', format);
            event.setFormat(format);
            return;
        }

        // Chat format
        if (!ServerBasics.getConfigCache().CHAT_FORMAT_ENABLED)
            return;

        String format = ServerBasics.getConfigCache().CHAT_FORMAT;
        format = format.replaceAll("%nickname%", player.getDisplayName());
        format = format+"%2$s";
        format = ChatColor.translateAlternateColorCodes('&', format);
        event.setFormat(format);





    }
}