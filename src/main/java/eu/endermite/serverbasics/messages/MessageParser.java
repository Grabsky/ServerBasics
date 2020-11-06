package eu.endermite.serverbasics.messages;

import eu.endermite.serverbasics.ServerBasics;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class MessageParser {

    public static final Pattern HEX_PATTERN = Pattern.compile("(#[A-Fa-f0-9]{6})");

    /**
     * Parses message string into message
     *
     * @param recipent Recipent of the message
     * @param message  String to parse into message
     */
    public static void sendMessage(CommandSender recipent, String message) {

        MessageType messageType = MessageType.TEXT;

        //TODO different message types based on string prefix



        if (!(recipent instanceof Player)) {
            recipent.sendMessage(message);
            return;
        }
        Player player = (Player) recipent;

        if (ServerBasics.isHooked("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        message = ChatColor.translateAlternateColorCodes('&', message);

        if (messageType.equals(MessageType.TEXT)) {
            player.sendMessage(message);
            return;
        }
        if (messageType.equals(MessageType.ACTIONBAR)) {
            player.sendActionBar(message);
            return;
        }
        if (messageType.equals(MessageType.SUBTITLE)) {
            player.sendTitle("", message, 5, 60, 5);
            return;
        }
    }

    /**
     * For sending TranslatableComponent errors that already exist in the client
     *
     * @param player
     * @param translationString
     * @param color
     */
    public static void sendDefaultTranslatedError(CommandSender player, String translationString, TextColor color) {
        Component component = Component.translatable().key(translationString).colorIfAbsent(color).build();
        BukkitAudiences bukkitAudiences = ServerBasics.getCommandManager().bukkitAudiences;
        bukkitAudiences.sender(player).sendMessage(component);

    }

    @Nullable
    public static String replaceHex(@Nullable String str) {
        if (str != null) {
            java.util.regex.Matcher matcher = HEX_PATTERN.matcher(str);
            while (matcher.find()) {
                String group = matcher.group(1);
                str = str.replace(group, net.md_5.bungee.api.ChatColor.of(group).toString());
            }
        }
        return str;
    }

    @Nullable
    public static String color(@Nullable String str) {
        return color(str, true);
    }

    @Nullable
    public static String color(@Nullable String str, boolean parseHex) {
        return str != null ? net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', parseHex ? replaceHex(str) : str) : str;
    }


}


