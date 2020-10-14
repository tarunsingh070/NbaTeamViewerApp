package com.tarun.nbateamviewerapp.data.model

/**
 * Base class for Players list items.
 */
sealed class PlayersListDataItem {
    abstract val id: Int
    abstract val rowType: PlayerRowType

    enum class PlayerRowType(val type: Int) {PLAYER(0), HEADER(1)}

    data class PlayerItem(val player: Player): PlayersListDataItem() {
        override val id: Int = player.id
        override val rowType = PlayerRowType.PLAYER
    }

    object HeaderItem : PlayersListDataItem() {
        override val id: Int = Int.MIN_VALUE
        override val rowType = PlayerRowType.HEADER
    }
}