package com.tarun.nbateamviewerapp.ui.teamDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.PlayersListDataItem
import com.tarun.nbateamviewerapp.databinding.ItemPlayerBinding
import com.tarun.nbateamviewerapp.databinding.ItemPlayerHeaderBinding

/**
 * The adapter class for showing the list of players.
 */
class PlayersListAdapter(playerListDiff: DiffUtil.ItemCallback<PlayersListDataItem>) :
    ListAdapter<PlayersListDataItem, RecyclerView.ViewHolder>(playerListDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PlayersListDataItem.PlayerRowType.HEADER.type -> HeaderViewHolder.from(parent)
            PlayersListDataItem.PlayerRowType.PLAYER.type -> PlayerViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlayerViewHolder -> {
                holder.bindTo(getItem(position) as PlayersListDataItem.PlayerItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).rowType.type
    }

    /**
     * View holder class for each player row.
     *
     * @param binding: The instance of [ItemPlayerBinding] for this instance of holder.
     */
    class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PlayerViewHolder {
                val itemPlayerBinding =
                    ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PlayerViewHolder(itemPlayerBinding)
            }
        }

        fun bindTo(playerItem: PlayersListDataItem.PlayerItem) {
            val player = playerItem.player
            binding.apply {
                playerName.text = itemView.context.getString(
                    R.string.full_name, player.firstName,
                    player.lastName
                )
                playerNumber.text = player.number.toString()
                playerPosition.text = player.position
            }
        }
    }

    /**
     * View holder class for header.
     *
     * @param binding: The instance of [ItemPlayerHeaderBinding] for this instance of holder.
     */
    class HeaderViewHolder(private val binding: ItemPlayerHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val itemPlayerHeaderBinding =
                    ItemPlayerHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return HeaderViewHolder(itemPlayerHeaderBinding)
            }
        }
    }
}