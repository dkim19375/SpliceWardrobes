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

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor

enum class ErrorMessages(message: String) {
    NO_PERMISSION("You do not have permission!"),
    MUST_BE_PLAYER("You must be a player!"),
    INVENTORY_FULL("Your inventory is full!"),
    NOT_OWNED("You do not own this item!"),
    ALREADY_HAS_COSMETIC("You already have this cosmetic type!"),
    INVALID_ARG("Invalid argument!"),
    ;

    val message: TextComponent = Component.text(message).color(NamedTextColor.RED)
}