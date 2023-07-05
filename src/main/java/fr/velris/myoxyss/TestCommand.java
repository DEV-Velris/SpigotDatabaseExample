package fr.velris.myoxyss;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    private final Myoxyss plugin = Myoxyss.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args[0].equalsIgnoreCase("add")) {
                    plugin.getDatabase().addMoney(player.getName());
                    return true;
                }

                if (args[0].equalsIgnoreCase("show")) {
                    player.sendMessage("Votre money est de: " + plugin.getDatabase().getMoney(player.getName()));
                    return true;
                }
            }
        }

        return false;
    }
}
