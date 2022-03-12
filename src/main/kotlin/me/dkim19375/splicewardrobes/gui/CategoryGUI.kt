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
import dev.triumphteam.gui.guis.GuiItem
import me.dkim19375.dkimbukkitcore.function.formatAll
import me.dkim19375.splicewardrobes.SpliceWardrobes
import me.dkim19375.splicewardrobes.data.CategoryGUIData
import me.dkim19375.splicewardrobes.data.CosmeticTagData
import me.dkim19375.splicewardrobes.data.MainConfigData
import me.dkim19375.splicewardrobes.enumclass.CosmeticType
import me.dkim19375.splicewardrobes.enumclass.ErrorMessages
import me.dkim19375.splicewardrobes.util.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

class CategoryGUI(
    private val config: CategoryGUIData,
    private val type: CosmeticType,
    private val autoEquip: Boolean,
    private val player: Player,
    private val plugin: SpliceWardrobes,
) {
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
        val specialItems = plugin.mainConfig.get(MainConfigData.GUIS).specialItems
        menu.setItem(
            specialItems.back.row,
            specialItems.back.column,
            GuiItem(specialItems.back.item.getItemStack(player)) {
                MainWardrobeGUI(player, plugin).showPlayer()
            }
        )
        for ((name, item) in config.items) {
            val hasPermission = player.hasPermission(item.permission)
            menu.addItem(
                ItemBuilder.from(item.displayItem.getItemStack(player))
                    .addAllFlags()
                    .let { builder ->
                        val modificationConfig = plugin.mainConfig.get(MainConfigData.OWNED_MODIFICATIONS)
                        val modificationData = if (hasPermission) {
                            modificationConfig.owned
                        } else {
                            modificationConfig.unowned
                        }
                        val meta = builder.build().itemMeta ?: return@let builder
                        val displayName = meta.displayName()?.componentToString() ?: ""
                        val lore = (meta.lore() ?: emptyList()).componentsToString().joinToString("\n")
                        builder.name(
                            modificationData.name.replace("%name%", displayName)
                                .formatAll(player)
                                .toComponent()
                                .decoration(TextDecoration.ITALIC, false)
                        )
                        builder.lore(
                            modificationData.lore.joinToString("\n")
                                .replace("%lore%", lore)
                                .formatAll(player)
                                .split('\n')
                                .toComponents()
                                .map { it.decoration(TextDecoration.ITALIC, false) }
                        )
                    }.asNewGuiItem {
                        if (!hasPermission) {
                            player.sendMessage(ErrorMessages.NOT_OWNED)
                            return@asNewGuiItem
                        }
                        if (player.inventory.filterNotNull().any { item -> item.getCosmeticKey()?.type == type }) {
                            player.sendMessage(ErrorMessages.ALREADY_HAS_COSMETIC)
                            return@asNewGuiItem
                        }
                        val message = Component.text("Successfully gave the item!").color(NamedTextColor.GREEN)
                        val giveItem by lazy {
                            item.giveItem.getItemStack(player).putCosmeticKey(CosmeticTagData(
                                owner = player.uniqueId,
                                name = name,
                                type = type
                            ))
                        }
                        if (autoEquip) {
                            if (player.equip(giveItem)) {
                                player.sendMessage(message)
                            }
                            return@asNewGuiItem
                        }
                        if (player.inventory.firstEmpty() == -1) {
                            player.sendMessage(ErrorMessages.INVENTORY_FULL)
                            return@asNewGuiItem
                        }
                        player.inventory.addItem(giveItem)
                        player.sendMessage(message)
                    }
            )
        }
    }
}