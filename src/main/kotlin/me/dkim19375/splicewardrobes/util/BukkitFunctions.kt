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

package me.dkim19375.splicewardrobes.util

import me.dkim19375.dkimbukkitcore.data.HelpMessage
import me.dkim19375.dkimbukkitcore.data.HelpMessageFormat
import me.dkim19375.dkimbukkitcore.function.showHelpMessage
import me.dkim19375.dkimcore.extension.runCatchingOrNull
import me.dkim19375.splicewardrobes.SpliceWardrobes
import me.dkim19375.splicewardrobes.data.CosmeticTagData
import me.dkim19375.splicewardrobes.enumclass.ArmorType
import me.dkim19375.splicewardrobes.enumclass.ErrorMessages
import me.dkim19375.splicewardrobes.enumclass.Permissions
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.permissions.Permissible
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

private val plugin by lazy { JavaPlugin.getPlugin(SpliceWardrobes::class.java) }
private val COSMETIC_KEY by lazy { NamespacedKey(plugin, "cosmetic") }
private val COSMETIC_TAG_TYPE by lazy { CosmeticTagType() }

private val format = HelpMessageFormat(
    topBar = null,
    header = "${ChatColor.GREEN}%name% v%version% Help Page: %page%/%maxpages%",
    bottomBar = null
)

private val commands = listOf(
    HelpMessage("help [page]", "Shows this help page", Permissions.COMMAND.perm),
    HelpMessage("reload", "Reloads the config files", Permissions.RELOAD.perm),
)

fun CommandSender.sendHelpMessage(
    label: String,
    page: Int = 1,
) = showHelpMessage(
    label = label,
    error = null,
    page = page,
    commands = commands,
    plugin = plugin,
    format = format
)

fun ItemStack.serializeToBase64(): String = if (type.isAir) {
    ""
} else {
    ByteArrayOutputStream().use { outputStream ->
        BukkitObjectOutputStream(outputStream).use { dataOutput ->
            dataOutput.writeObject(serializeAsBytes())
        }
        Base64Coder.encodeLines(outputStream.toByteArray())
    }
}

fun String.deserializeToItemStack(): ItemStack? = if (isBlank()) null else runCatchingOrNull {
    ByteArrayInputStream(Base64Coder.decodeLines(this)).use { input ->
        BukkitObjectInputStream(input).use { dataInput ->
            ItemStack.deserializeBytes(dataInput.readObject() as ByteArray)
        }
    }
}

fun Player.equip(item: ItemStack): Boolean {
    fun putArmor(alreadyItem: ItemStack?, setItem: (ItemStack?) -> Unit): Boolean {
        if (alreadyItem?.type?.isAir == false) {
            if (inventory.firstEmpty() == -1) {
                sendMessage(ErrorMessages.INVENTORY_FULL)
                return false
            }
            inventory.addItem(alreadyItem)
        }
        setItem(item.clone())
        return true
    }
    return when (ArmorType.fromMaterial(item.type) ?: ArmorType.HELMET) {
        ArmorType.HELMET -> putArmor(inventory.helmet, inventory::setHelmet)
        ArmorType.CHESTPLATE -> putArmor(inventory.chestplate, inventory::setChestplate)
        ArmorType.LEGGINGS -> putArmor(inventory.leggings, inventory::setLeggings)
        ArmorType.BOOTS -> putArmor(inventory.boots, inventory::setBoots)
    }
}

fun CommandSender.sendMessage(error: ErrorMessages) = sendMessage(error.message)

fun ItemStack.putCosmeticKey(owner: CosmeticTagData): ItemStack = apply {
    itemMeta = itemMeta?.putCosmeticKey(owner)
}

fun ItemMeta.putCosmeticKey(owner: CosmeticTagData): ItemMeta = apply {
    persistentDataContainer.putCosmeticKey(owner)
}

fun PersistentDataContainer.putCosmeticKey(owner: CosmeticTagData): PersistentDataContainer = apply {
    set(COSMETIC_KEY, COSMETIC_TAG_TYPE, owner)
}

fun ItemStack.getCosmeticKey(): CosmeticTagData? = itemMeta?.getCosmeticKey()

fun ItemMeta.getCosmeticKey(): CosmeticTagData? = persistentDataContainer.getCosmeticKey()

fun PersistentDataContainer.getCosmeticKey(): CosmeticTagData? = get(COSMETIC_KEY, COSMETIC_TAG_TYPE)

fun Permissible.hasPermission(permission: Permissions): Boolean = hasPermission(permission.perm)