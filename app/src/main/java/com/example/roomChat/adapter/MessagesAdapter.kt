package com.example.roomChat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomChat.R
import com.example.roomChat.model.Message
import kotlinx.android.synthetic.main.outgoing_message_list_item.view.*

class MessagesAdapter(private val items: ArrayList<Message>, private val context: Context) :
    RecyclerView.Adapter<MessagesViewHolder>() {

    companion object {
        private const val INCOMING = 0
        private const val OUTGOING = 1
    }

    fun addAll(items: ArrayList<Message>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Message) {
        if (items.size > 0) {
            if (items.last().id != item.id) {
                this.items.add(item)
                notifyItemChanged(items.size)
            }
        } else {
            this.items.add(item)
            notifyItemChanged(items.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return MessagesViewHolder(
            LayoutInflater.from(context).inflate(
                if (viewType == INCOMING) R.layout.incoming_message_list_item else R.layout.outgoing_message_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.text.text = items[position].text
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].incoming) {
            INCOMING
        } else {
            OUTGOING
        }
    }
}

class MessagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text = view.text!!
}