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

import me.dkim19375.splicewardrobes.data.AllGUIsConfigData
import me.dkim19375.splicewardrobes.data.CategoryGUIData
import me.dkim19375.splicewardrobes.data.GUIItemStackData
import me.dkim19375.splicewardrobes.data.MainGUIData

enum class CosmeticType(
    val mainItemConfig: (MainGUIData) -> GUIItemStackData,
    val config: (AllGUIsConfigData) -> CategoryGUIData,
    val autoEquip: Boolean,
) {
    HEAD(MainGUIData::head, AllGUIsConfigData::head, true),
    CHESTPLATE(MainGUIData::chestplate, AllGUIsConfigData::chestplate, true),
    LEGGINGS(MainGUIData::leggings, AllGUIsConfigData::leggings, true),
    BOOTS(MainGUIData::boots, AllGUIsConfigData::boots, true),
    TOOLS(MainGUIData::tools, AllGUIsConfigData::tools, false),
    HANDS(MainGUIData::hands, AllGUIsConfigData::hands, false),
}