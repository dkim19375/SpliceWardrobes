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

package me.dkim19375.splicewardrobes.listener

import me.dkim19375.itemmovedetectionlib.event.InventoryItemTransferEvent
import me.dkim19375.splicewardrobes.SpliceWardrobes
import me.dkim19375.splicewardrobes.enumclass.CosmeticType
import me.dkim19375.splicewardrobes.util.getCosmeticKey
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent

class ItemMoveListeners(private val plugin: SpliceWardrobes) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    private fun InventoryOpenEvent.onOpen() {
        val items = view.topInventory.toMutableList()
        val bottom = view.bottomInventory
        val playerItems = if (bottom.type == InventoryType.PLAYER) {
            bottom.toList()
        } else {
            items.addAll(bottom)
            emptyList()
        }

        for (item in items.filterNotNull()) {
            if (item.getCosmeticKey()?.owner != null) {
                item.amount = 0
            }
        }
        for (item in playerItems.filterNotNull()) {
            if (item.getCosmeticKey()?.owner?.equals(player.uniqueId) == false) {
                item.amount = 0
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private fun PlayerDropItemEvent.onDrop() {
        if (itemDrop.itemStack.getCosmeticKey() != null) {
            isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private fun InventoryItemTransferEvent.onTransfer() {
        if (items.filterNotNull().any { it.getCosmeticKey() != null }) {
            isCancelled = true
            Bukkit.getScheduler().runTask(plugin, Runnable {
                val found = mutableSetOf<CosmeticType>()
                for (item in player.inventory.contents?.filterNotNull() ?: return@Runnable) {
                    val type = item.getCosmeticKey()?.type ?: continue
                    if (type in found) {
                        item.amount = 0
                        continue
                    }
                    if (item.amount > 1) {
                        item.amount = 1
                    }
                    found.add(type)
                }
            })
        }
    }
}