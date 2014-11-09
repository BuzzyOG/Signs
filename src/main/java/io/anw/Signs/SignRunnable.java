package io.anw.Signs;

import io.anw.AuroraUtils.Bukkit.Utils.BukkitUtils;
import io.anw.Signs.Objects.AuroraSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

public class SignRunnable {

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.getInstance().Config.getInt("Number-Of-Signs") > 0) {
                    for (int x = 1; x <= Main.getInstance().Config.getInt("Number-Of-Signs"); x++) {
                        AuroraSign s = new AuroraSign(Main.getInstance().Config.getString("Signs.Sign" + x + ".Name"));
                        Sign aSign = (Sign) new Location(
                                Bukkit.getWorld(Main.getInstance().Config.getString("Signs.Sign" + x + ".World")),
                                Main.getInstance().Config.getDouble("Signs.Sign" + x + ".X"),
                                Main.getInstance().Config.getDouble("Signs.Sign" + x + ".Y"),
                                Main.getInstance().Config.getDouble("Signs.Sign" + x + ".Z")
                        ).getBlock().getState();

                        if (aSign != null) {
                            String state = s.getState();

                            if (!(state.equalsIgnoreCase("restarting") || state.equalsIgnoreCase("in_game"))) {
                                aSign.setLine(0, !(s.getPlayers() >= (s.getMaxPlayers() - (s.getMaxPlayers() / 4)) || s.getPlayers() == s.getMaxPlayers()) ? BukkitUtils.colorizeString("&a[JOIN]") : s.getPlayers() == s.getMaxPlayers() ? BukkitUtils.colorizeString("&c[FULL]") : BukkitUtils.colorizeString("&b[VIP]"));
                                aSign.setLine(1, BukkitUtils.colorizeString("&6" + s.getPlayers() + "/" + s.getMaxPlayers()));
                                aSign.setLine(2, BukkitUtils.colorizeString("&b" + s.getName()));
                                aSign.setLine(3, BukkitUtils.colorizeString("&o" + s.getState()));
                            } else {
                                aSign.setLine(0, state.equalsIgnoreCase("in_game") ? BukkitUtils.colorizeString("&6[IN-GAME]") : BukkitUtils.colorizeString("&6[Loading]"));
                                aSign.setLine(1, state.equalsIgnoreCase("in_game") ? BukkitUtils.colorizeString("&6" + s.getPlayers() + "/" + s.getMaxPlayers()) : null);
                                aSign.setLine(2, state.equalsIgnoreCase("in_game") ? BukkitUtils.colorizeString("&b" + s.getName()) : null);
                                aSign.setLine(3, state.equalsIgnoreCase("in_game") ? BukkitUtils.colorizeString("&oBack soon! :)") : BukkitUtils.colorizeString("&oRestarting..."));
                            }

                            aSign.update(true);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);
    }

}
