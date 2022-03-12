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

import me.dkim19375.splicewardrobes.data.CosmeticTagData
import me.dkim19375.splicewardrobes.enumclass.CosmeticType
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import java.nio.ByteBuffer
import java.util.UUID

class CosmeticTagType : PersistentDataType<ByteArray, CosmeticTagData> {
    override fun getPrimitiveType(): Class<ByteArray> = ByteArray::class.java

    override fun getComplexType(): Class<CosmeticTagData> = CosmeticTagData::class.java

    override fun toPrimitive(data: CosmeticTagData, context: PersistentDataAdapterContext): ByteArray {
        val buffer = ByteBuffer.wrap(ByteArray(16 + (data.name.length + 4) + (data.type.name.length + 4)))
        buffer.putString(data.name)
        buffer.putString(data.type.name)
        buffer.putLong(data.owner.mostSignificantBits)
        buffer.putLong(data.owner.leastSignificantBits)
        return buffer.array()
    }

    override fun fromPrimitive(array: ByteArray, context: PersistentDataAdapterContext): CosmeticTagData {
        val buffer = ByteBuffer.wrap(array)
        val name = buffer.getString()
        val type = CosmeticType.valueOf(buffer.getString())
        val mostSignificantBits = buffer.long
        val leastSignificantBits = buffer.long
        return CosmeticTagData(UUID(mostSignificantBits, leastSignificantBits), name, type)
    }

    private fun ByteBuffer.putString(string: String) {
        putInt(string.length)
        put(string.encodeToByteArray())
    }

    private fun ByteBuffer.getString(): String {
        val length = int
        val bytes = array().drop(position()).take(length).toByteArray()
        position(position() + length)
        return String(bytes)
    }
}