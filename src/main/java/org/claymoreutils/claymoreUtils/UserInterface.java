package org.claymoreutils.claymoreUtils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getServer;

public class UserInterface {

    public static String sendText(String text) {
        return text.replaceAll("&", "§");
    }

    public static void sendClickableLink(Player player, String url, String message) {
        // Create the text component with the message text
        TextComponent textComponent = new TextComponent(message);

        // Set the click event to open a URL
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

        // Send the message to the player
        player.spigot().sendMessage(textComponent);
    }

    public static void executeConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void SendActionBar(Player p, String m) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(m));
    }

    public static void PlaySound(Sound s, Entity e, float volume, float pitch) {
        e.getWorld().playSound(e.getLocation(), s, volume, pitch);
    }

    public static void PlaySoundAt(Sound sound, Location location, float volume, float pitch) {
        if (location.getWorld() != null) {
            location.getWorld().playSound(location, sound, volume, pitch);
        }
    }

    public static String uncolouredText(String text) {
        return text.replaceAll("§.|[^\\x00-\\x7F]|\\d+|[^a-zA-Z_ ]", "").trim();
    }

    public static String numberInText(String text) {
        String cleaned = text.replaceAll("§.|[^\\x00-\\x7F]", "").trim();
        // Replace text
        String pattern = "\\d+";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(cleaned);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            // Append matched substring to result
            result.append(matcher.group());
        }
        return result.toString().trim();
    }

    public static String intToRoman(int number) {
        if (number <= 0 || number > 3999) {
            return "-";
        }

        String[] romans = {
                "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] values = {
                1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(romans[i]);
            }
        }
        return result.toString();
    }

    public static void consoleLog(String message) {
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static final String defaultFormat = "#,###.##";

    public static String FormatDouble(double d){
        DecimalFormat formatter = new DecimalFormat(defaultFormat);
        return formatter.format(d);

    }

    public static Inventory OpenGUI(Player p, int size, String name) {
        Inventory gui = Bukkit.createInventory(new CustomInventoryHolder(null), size*9, sendText("&n"+name));

        return gui;
    }

    public static Inventory OpenChest(Player p, int size, String name) {
        Inventory gui = Bukkit.createInventory(p, size*9, sendText("&n"+name));

        return gui;
    }

    public static ItemStack getHeaderFooter(){
        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);

        return border;
    }

    public static void SetHeaderFooter(Inventory inventory){
        int size = inventory.getSize();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, getHeaderFooter());
        }
        for (int i = size-9; i < size; i++) {
            inventory.setItem(i, getHeaderFooter());
        }
    }

    public static void SetFooter(Inventory inventory){
        int size = inventory.getSize();
        for (int i = size-9; i < size; i++) {
            inventory.setItem(i, getHeaderFooter());
        }
    }

    public static ItemStack getBackground(Material material){
        ItemStack border = new ItemStack(material);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);

        return border;
    }
    public static void SetBackground(Inventory inventory, Material material){
        int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, getBackground(material));
            }
        }
    }

    public static void APITest(Player player){
        player.sendMessage("responded");
    }

}
