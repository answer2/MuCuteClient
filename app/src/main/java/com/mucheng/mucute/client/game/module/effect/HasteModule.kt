package com.mucheng.mucute.client.game.module.effect

import com.mucheng.mucute.client.game.Module
import com.mucheng.mucute.client.game.ModuleCategory
import com.mucheng.mucute.client.game.data.Effect
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket

class HasteModule : Module("haste", ModuleCategory.Effect) {

    override fun beforePacketBound(packet: BedrockPacket): Boolean {
        if (packet is PlayerAuthInputPacket && isEnabled) {
            if (localPlayer.tickExists % 20 == 0L) {
                session.clientBound(MobEffectPacket().apply {
                    runtimeEntityId = localPlayer.runtimeEntityId
                    event = MobEffectPacket.Event.ADD
                    effectId = Effect.HASTE
                    amplifier -= 1
                    isParticles = false
                    duration = 360000
                })
            }
        }
        return false
    }

    override fun onDisabled() {
        if (isSessionCreated) {
            session.clientBound(MobEffectPacket().apply {
                runtimeEntityId = localPlayer.runtimeEntityId
                event = MobEffectPacket.Event.REMOVE
                effectId = Effect.HASTE
            })
        }
    }
}
