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

package me.dkim19375.splicewardrobes

import me.dkim19375.dkimbukkitcore.javaplugin.CoreJavaPlugin
import me.dkim19375.dkimcore.file.YamlFile
import me.dkim19375.itemmovedetectionlib.ItemMoveDetectionLib
import me.dkim19375.splicewardrobes.command.MainCommand
import me.dkim19375.splicewardrobes.data.MainConfigData
import me.dkim19375.splicewardrobes.listener.ItemMoveListeners
import java.io.File

class SpliceWardrobes : CoreJavaPlugin() {
    override val defaultConfig = false
    val mainConfig by lazy { YamlFile(MainConfigData, File(dataFolder, "config.yml")) }

    override fun onEnable() {
        registerConfig(mainConfig)
        registerCommand("splicewardrobes", MainCommand(this))
        ItemMoveDetectionLib.register(this)
        registerListener(ItemMoveListeners(this))
    }
}