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

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.annotations.Name
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property
import org.bukkit.Material

object MainConfigData : SettingsHolder {
    @Path("gui")
    val GUIS = Property.create(AllGUIsConfigData())

    @Path("owned-modifications")
    val OWNED_MODIFICATIONS = Property.create(OwnedModificationsConfigData())
}

data class OwnedModificationsConfigData(
    @Path("owned")
    var owned: ModificationConfigData = ModificationConfigData(
        name = "&a%name%",
        lore = listOf(
            "%lore%",
            " ",
            "&aOwned"
        )
    ),
    @Path("unowned")
    var unowned: ModificationConfigData = ModificationConfigData(
        name = "&c%name%",
        lore = listOf(
            "%lore%",
            " ",
            "&cUnowned"
        )
    )
)

data class ModificationConfigData(
    @Path("name")
    var name: String = "%name%",
    @Path("lore")
    var lore: List<String> = listOf(
        "%lore%"
    ),
)

data class SpecialItemsConfigData(
    var back: GUIItemStackData = GUIItemStackData(
        row = 3,
        item = ItemStackData(
            material = Material.MAGENTA_GLAZED_TERRACOTTA.name,
            displayName = "&cBack",
            lore = listOf("&cClick to go back")
        )
    ),
)

data class AllGUIsConfigData(
    @Name("special-items")
    var specialItems: SpecialItemsConfigData = SpecialItemsConfigData(),
    var main: MainGUIData = MainGUIData(),
    var head: CategoryGUIData = CategoryGUIData(),
    var chestplate: CategoryGUIData = CategoryGUIData(
        title = "Chestplate Wardrobe",
        items = mapOf(
            "example" to WardrobeItemData(
                displayItem = ItemStackData(
                    material = Material.DIAMOND_CHESTPLATE.name,
                    displayName = "&6&lDiamond Chestplate",
                    lore = listOf(
                        "&aSpecial Diamond Chestplate",
                        "&aYou should get this Chestplate!"
                    )
                ),
                giveItem = ItemStackData(
                    material = Material.DIAMOND_CHESTPLATE.name,
                    displayName = "&6&lDiamond Chestplate",
                    lore = listOf(
                        "&aSpecial Diamond Chestplate"
                    )
                )
            )
        )
    ),
    var leggings: CategoryGUIData = CategoryGUIData(
        title = "Leggings Wardrobe",
        items = mapOf(
            "example" to WardrobeItemData(
                displayItem = ItemStackData(
                    material = Material.DIAMOND_LEGGINGS.name,
                    displayName = "&6&lDiamond Leggings",
                    lore = listOf(
                        "&aSpecial Diamond Leggings",
                        "&aYou should get this Leggings!"
                    )
                ),
                giveItem = ItemStackData(
                    material = Material.DIAMOND_LEGGINGS.name,
                    displayName = "&6&lDiamond Leggings",
                    lore = listOf(
                        "&aSpecial Diamond Leggings"
                    )
                )
            )
        )
    ),
    var boots: CategoryGUIData = CategoryGUIData(
        title = "Boots Wardrobe",
        items = mapOf(
            "example" to WardrobeItemData(
                displayItem = ItemStackData(
                    material = Material.DIAMOND_BOOTS.name,
                    displayName = "&6&lDiamond Boots",
                    lore = listOf(
                        "&aSpecial Diamond Boots",
                        "&aYou should get this Boots!"
                    )
                ),
                giveItem = ItemStackData(
                    material = Material.DIAMOND_BOOTS.name,
                    displayName = "&6&lDiamond Boots",
                    lore = listOf(
                        "&aSpecial Diamond Boots"
                    )
                )
            )
        )
    ),
    var tools: CategoryGUIData = CategoryGUIData(
        title = "Tools Wardrobe",
        items = mapOf(
            "example" to WardrobeItemData(
                displayItem = ItemStackData(
                    material = Material.DIAMOND_PICKAXE.name,
                    displayName = "&6&lDiamond Pickaxe",
                    lore = listOf(
                        "&aSpecial Diamond Pickaxe",
                        "&aYou should get this Pickaxe!"
                    )
                ),
                giveItem = ItemStackData(
                    material = Material.DIAMOND_PICKAXE.name,
                    displayName = "&6&lDiamond Pickaxe",
                    lore = listOf(
                        "&aSpecial Diamond Pickaxe"
                    )
                )
            )
        )
    ),
    var hands: CategoryGUIData = CategoryGUIData(
        title = "Hands Wardrobe",
        items = mapOf(
            "example" to WardrobeItemData(
                displayItem = ItemStackData(
                    material = Material.PAPER.name,
                    displayName = "&6&lMagical Paper",
                    lore = listOf(
                        "&aSpecial Paper",
                        "&aYou should get this paper!"
                    )
                ),
                giveItem = ItemStackData(
                    material = Material.PAPER.name,
                    displayName = "&6&lMagical Paper",
                    lore = listOf(
                        "&aSpecial paper"
                    )
                )
            )
        )
    )
)

data class CategoryGUIData(
    var title: String = "Head Wardrobe",
    var rows: Int = 3,
    var items: Map<String, WardrobeItemData> = mapOf(
        "example" to WardrobeItemData(
            displayItem = ItemStackData(
                material = Material.DIAMOND_HELMET.name,
                displayName = "&6&lDiamond Helmet",
                lore = listOf(
                    "&aSpecial Diamond Helmet",
                    "&aYou should get this helmet!"
                )
            ),
            giveItem = ItemStackData(
                material = Material.DIAMOND_HELMET.name,
                displayName = "&6&lDiamond Helmet",
                lore = listOf(
                    "&aSpecial Diamond Helmet"
                )
            )
        )
    ),
)

data class MainGUIData(
    var title: String = "Wardrobes",
    var rows: Int = 4,
    var head: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.PLAYER_HEAD.name,
            displayName = "&6Head"
        ),
        row = 2,
        column = 3
    ),
    var chestplate: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.IRON_CHESTPLATE.name,
            displayName = "&6Chestplate"
        ),
        row = 2,
        column = 5
    ),
    var leggings: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.IRON_LEGGINGS.name,
            displayName = "&6Leggings"
        ),
        row = 2,
        column = 7
    ),
    var boots: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.IRON_BOOTS.name,
            displayName = "&6Boots"
        ),
        row = 3,
        column = 3
    ),
    var tools: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.IRON_AXE.name,
            displayName = "&6Tools"
        ),
        row = 3,
        column = 5
    ),
    var hands: GUIItemStackData = GUIItemStackData(
        item = ItemStackData(
            material = Material.PAPER.name,
            displayName = "&6Hands"
        ),
        row = 3,
        column = 7
    ),
)