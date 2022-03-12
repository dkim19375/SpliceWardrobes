/*
 *     SpliceWardrobes, a spigot plugin for the Splice minecraft server
 *     Copyright (C) 2022  dkim19375
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.dkim19375.splicewardrobes.command

import me.dkim19375.splicewardrobes.SpliceWardrobes
import me.dkim19375.splicewardrobes.enumclass.ErrorMessages
import me.dkim19375.splicewardrobes.enumclass.Permissions
import me.dkim19375.splicewardrobes.gui.MainWardrobeGUI
import me.dkim19375.splicewardrobes.util.hasPermission
import me.dkim19375.splicewardrobes.util.sendHelpMessage
import me.dkim19375.splicewardrobes.util.sendMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MainCommand(private val plugin: SpliceWardrobes) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission(Permissions.COMMAND)) {
            sender.sendMessage(ErrorMessages.NO_PERMISSION)
            return true
        }
        if (args.isEmpty()) {
            if (sender !is Player) {
                sender.sendMessage(ErrorMessages.MUST_BE_PLAYER)
                return true
            }
            MainWardrobeGUI(sender, plugin).showPlayer()
            return true
        }
        when (args[0].lowercase()) {
            "help" -> {
                sender.sendHelpMessage(label, args.getOrNull(1)?.toIntOrNull()?.coerceAtLeast(1) ?: 1)
                return true
            }
            "reload" -> {
                plugin.reloadConfig()
                sender.sendMessage(Component.text("Successfully reloaded the configs!").color(NamedTextColor.GREEN))
                return true
            }
            else -> {
                sender.sendMessage(ErrorMessages.INVALID_ARG)
                return true
            }
        }
    }
}