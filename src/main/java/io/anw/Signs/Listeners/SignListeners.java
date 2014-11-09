package io.anw.Signs.Listeners;

import io.anw.AuroraUtils.Bukkit.Utils.MessageUtils;
import io.anw.AuroraUtils.Bukkit.Utils.Misc.SoundPlayer;
import io.anw.AuroraUtils.Bukkit.Utils.UUIDUtility;
import io.anw.Signs.Main;
import io.anw.Signs.Objects.AuroraSign;
import io.anw.Signs.Utils.BungeeManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SignListeners implements Listener {

    private List<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            final Player player = e.getPlayer();

            if (e.getClickedBlock().getState() instanceof Sign) {
                if (!cooldown.contains(UUIDUtility.getUUID(player.getName()))) {
                    Sign s = (Sign) e.getClickedBlock().getState();
                    AuroraSign server = new AuroraSign(ChatColor.stripColor(s.getLine(2)));
                    String c = ChatColor.stripColor(s.getLine(0));

                    if (c.contains("JOIN")) {
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
                        BungeeManager.teleportToServer(player, server.getName());
                    }

                    else if (c.contains("VIP")) {
                        if (player.hasPermission("aurora.vip")) {
                            SoundPlayer.play(player, Sound.LEVEL_UP, 10, 10);
                            BungeeManager.teleportToServer(player, server.getName());
                        } else {
                            MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "You must be a &6VIP &7to join VIP signs!");
                            SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                        }
                    }

                    else if (c.contains("IN-GAME")) {
                        MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "You can't join games that have already started! Find another sign!");
                        SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                    }

                    else if (c.contains("Loading")) {
                        MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "This server is currently restarting / loading!");
                        SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                    }

                    else if (c.contains("FULL")) {
                        MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "You may not join a full game!");
                        SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                    }

                    cooldown.add(UUIDUtility.getUUID(player.getName()));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            cooldown.remove(UUIDUtility.getUUID(player.getName()));
                        }
                    }.runTaskLater(Main.getInstance(), 20 * 3);
                } else {
                    MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "Please do not spam the signs!");
                    SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                }
            }
        }
    }
}
