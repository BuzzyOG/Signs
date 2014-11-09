package io.anw.Signs.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import io.anw.AuroraUtils.Bukkit.Utils.MessageUtils;
import io.anw.AuroraUtils.Bukkit.Utils.Misc.SoundPlayer;
import io.anw.Signs.Main;
import io.anw.Signs.Utils.SQL.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {

    @Command(
            aliases = {"addsign"},
            desc = "Add a new game sign!",
            max = 0
    )
    @CommandPermissions({"aurora.staff.addsign"})
    public static void addSign(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getTargetBlock(null, 5).getState() instanceof Sign) {
                Sign s = (Sign) player.getTargetBlock(null, 5).getState();
                if (DatabaseManager.getInstance().doesSignExist(ChatColor.stripColor(s.getLine(2)))) {
                    int nos = Main.getInstance().Config.getInt("Number-Of-Signs") + 1;
                    Main.getInstance().Config.set("Signs.Sign" + nos + ".Name", ChatColor.stripColor(s.getLine(2)));
                    Main.getInstance().Config.set("Signs.Sign" + nos + ".World", s.getWorld().getName());
                    Main.getInstance().Config.set("Signs.Sign" + nos + ".X", s.getLocation().getX());
                    Main.getInstance().Config.set("Signs.Sign" + nos + ".Y", s.getLocation().getY());
                    Main.getInstance().Config.set("Signs.Sign" + nos + ".Z", s.getLocation().getZ());
                    Main.getInstance().Config.set("Number-Of-Signs", nos);

                    Main.getInstance().getConfigManager().save();
                    MessageUtils.messagePrefix(player, MessageUtils.MessageType.GOOD, "Added sign &6#" + nos + "&7!");
                } else {
                    MessageUtils.messagePrefix(player, MessageUtils.MessageType.BAD, "The data for this sign could not be found in the database, please try again!");
                    SoundPlayer.play(player, Sound.NOTE_BASS, 10, 1);
                }
            }
        }
    }

}
