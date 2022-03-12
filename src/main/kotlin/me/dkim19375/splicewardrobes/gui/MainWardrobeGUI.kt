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

package me.dkim19375.splicewardrobes.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import me.dkim19375.dkimbukkitcore.function.formatAll
import me.dkim19375.splicewardrobes.SpliceWardrobes
import me.dkim19375.splicewardrobes.data.MainConfigData
import me.dkim19375.splicewardrobes.enumclass.CosmeticType
import me.dkim19375.splicewardrobes.util.addAllFlags
import me.dkim19375.splicewardrobes.util.asNewGuiItem
import me.dkim19375.splicewardrobes.util.toComponent
import org.bukkit.entity.Player

class MainWardrobeGUI(
    private val player: Player,
    private val plugin: SpliceWardrobes,
) {
    private val config = plugin.mainConfig.get(MainConfigData.GUIS).main
    private val menu = Gui.gui().rows(config.rows)
        .title(config.title.formatAll(player).toComponent())
        .disableAllInteractions()
        .create()

    private fun reset() {
        for (i in 0 until menu.rows * 9) {
            menu.removeItem(i)
        }
    }

    fun showPlayer() {
        setup()
        menu.open(player)
    }

    private fun setup() {
        reset()
        val allConfig = plugin.mainConfig.get(MainConfigData.GUIS)
        for (type in CosmeticType.values()) {
            val item = type.mainItemConfig(config)
            val config = type.config(allConfig)
            val autoEquip = type.autoEquip
            menu.setItem(
                item.row,
                item.column,
                ItemBuilder.from(item.item.getItemStack(player)).addAllFlags().asNewGuiItem {
                    CategoryGUI(config, type, autoEquip, player, plugin).showPlayer()
                }
            )
        }
    }
}