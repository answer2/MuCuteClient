package com.mucheng.mucute.client.game.entity

import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket
import java.util.UUID

@Suppress("MayBeConstant")
open class Player(
    runtimeEntityId: Long,
    uniqueEntityId: Long,
    open val uuid: UUID,
    open val username: String
) : Entity(runtimeEntityId, uniqueEntityId) {

    companion object {
        val EYE_HEIGHT = 1.62f
    }

    val vec3PositionFeet: Vector3f
        get() = Vector3f.from(posX, posY - EYE_HEIGHT, posZ)

    val displayName: String
        get() = (metadata[EntityDataTypes.NAME] as? String?)?.ifEmpty { username } ?: username

    override fun beforePacketBound(packet: BedrockPacket): Boolean {
        super.beforePacketBound(packet)
        if (packet is MovePlayerPacket && packet.runtimeEntityId == runtimeEntityId) {
            move(packet.position.x, packet.position.y, packet.position.z)
            rotate(packet.rotation)
            tickExists++
        }

        return false
    }

    override fun toString(): String {
        return "EntityPlayer(entityId=$runtimeEntityId, uniqueId=$uniqueEntityId, username=$username, uuid=$uuid, posX=$posX, posY=$posY, posZ=$posZ)"
    }

}