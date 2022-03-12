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

package me.dkim19375.splicewardrobes.data

import me.dkim19375.dkimbukkitcore.function.formatAll
import me.dkim19375.dkimcore.extension.*
import me.dkim19375.splicewardrobes.util.deserializeToItemStack
import me.dkim19375.splicewardrobes.util.toComponent
import me.mattstudios.config.annotations.Name
import net.kyori.adventure.text.*
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.*
import org.bukkit.inventory.ItemStack
import java.util.*

data class ItemStackData(
    var material: String = "BARRIER",
    @Name("display-name")
    var displayName: String = "",
    var lore: List<String> = emptyList(),
) {
    fun getItemStack(
        player: OfflinePlayer? = null,
    ): ItemStack = (material.deserializeToItemStack() ?: ItemStack(runCatchingOrNull {
        Material.matchMaterial(material.uppercase())
    } ?: Material.BARRIER)).apply {
        itemMeta = itemMeta?.apply {
            this@ItemStackData.displayName.takeIf(String::isNotEmpty)?.formatAll(player)?.toComponent()
                ?.decoration(TextDecoration.ITALIC, false)
                ?.let { name ->
                    displayName(name)
                }
            this@ItemStackData.lore.takeIf(List<String>::isNotEmpty)?.map {
                it.formatAll(player).toComponent().decoration(TextDecoration.ITALIC, false)
            }?.let { lore ->
                lore(lore)
            }
        }
    }
}