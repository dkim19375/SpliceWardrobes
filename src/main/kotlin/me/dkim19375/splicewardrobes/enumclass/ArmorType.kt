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

package me.dkim19375.splicewardrobes.enumclass

import org.bukkit.Material

enum class ArmorType {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS
    ;

    companion object {
        fun fromMaterial(material: Material): ArmorType? = when (material) {
            Material.LEATHER_HELMET -> HELMET
            Material.GOLDEN_HELMET -> HELMET
            Material.CHAINMAIL_HELMET -> HELMET
            Material.IRON_HELMET -> HELMET
            Material.DIAMOND_HELMET -> HELMET
            Material.NETHERITE_HELMET -> HELMET
            Material.TURTLE_HELMET -> HELMET

            Material.LEATHER_CHESTPLATE -> CHESTPLATE
            Material.GOLDEN_CHESTPLATE -> CHESTPLATE
            Material.CHAINMAIL_CHESTPLATE -> CHESTPLATE
            Material.IRON_CHESTPLATE -> CHESTPLATE
            Material.DIAMOND_CHESTPLATE -> CHESTPLATE
            Material.NETHERITE_BOOTS -> CHESTPLATE

            Material.LEATHER_LEGGINGS -> LEGGINGS
            Material.GOLDEN_LEGGINGS -> LEGGINGS
            Material.CHAINMAIL_LEGGINGS -> LEGGINGS
            Material.IRON_LEGGINGS -> LEGGINGS
            Material.DIAMOND_LEGGINGS -> LEGGINGS
            Material.NETHERITE_CHESTPLATE -> LEGGINGS

            Material.LEATHER_BOOTS -> BOOTS
            Material.GOLDEN_BOOTS -> BOOTS
            Material.CHAINMAIL_BOOTS -> BOOTS
            Material.IRON_BOOTS -> BOOTS
            Material.DIAMOND_BOOTS -> BOOTS
            Material.NETHERITE_LEGGINGS -> BOOTS

            else -> null
        }
    }
}