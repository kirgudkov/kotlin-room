package com.example.roomChat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.roomChat.R
import com.example.roomChat.model.ChatsWithUsersAndLastMessage
import kotlinx.android.synthetic.main.chat_list_item.view.*

class ChatListAdapter(
    private val items: ArrayList<ChatsWithUsersAndLastMessage>,
    private val context: Context,
    private val onItemClick: ((ChatsWithUsersAndLastMessage) -> Unit)
) :
    RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    fun addAll(items: List<ChatsWithUsersAndLastMessage>) {
        this.items.addAll(items.toCollection(ArrayList()))
        notifyDataSetChanged()
    }

    fun replaceAll(items: List<ChatsWithUsersAndLastMessage>) {
        this.items.clear()
        this.addAll(items.toCollection(ArrayList()))
        notifyDataSetChanged()
    }

    fun add(item: ChatsWithUsersAndLastMessage) {
        this.items.add(item)
        notifyItemChanged(items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.name.text = items[position].users[0].name
        if (items[position].messages.isNotEmpty()) {
            holder.message.text = items[position].messages.last().text
        }
        Glide.with(context)
            .load(items[position].users[0].avatar)
            .apply(RequestOptions.circleCropTransform())
            .transition(withCrossFade())
            .into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { onItemClick.invoke(items[adapterPosition]) }
        }

        val name = view.name!!
        val avatar = view.avatar!!
        val message = view.message!!
    }
}