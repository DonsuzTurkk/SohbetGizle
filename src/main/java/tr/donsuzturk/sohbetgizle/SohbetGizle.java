package tr.donsuzturk.sohbetgizle;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class SohbetGizle extends JavaPlugin implements Listener
{
    Set<Player> susturOyuncu;

    public SohbetGizle() {
        this.susturOyuncu = new HashSet<>();
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("Eklenti aktif edildi!");
    }

    @EventHandler
    public void sohbet(final AsyncPlayerChatEvent event) {
        event.getRecipients().removeAll(this.susturOyuncu);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (label.equalsIgnoreCase("sohbet")) {
            final Player oyuncu = (Player)sender;
            if (this.susturOyuncu.contains(oyuncu)) {
                this.susturOyuncu.remove(oyuncu);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SohbetGizle.this.getConfig().getString("Prefix") + " " + getConfig().getString("GizlemeMesaj")));
            }
            else {
                this.susturOyuncu.add(oyuncu);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SohbetGizle.this.getConfig().getString("Prefix") + " " + getConfig().getString("AktifEtmeMesaj")));
            }
        }
        return super.onCommand(sender, command, label, args);
    }
}
