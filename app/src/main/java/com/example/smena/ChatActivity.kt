package com.example.smena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smena.model.Message
import com.example.smena.adapter.MessagesAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.smena.dao.ChatsDao
import com.example.smena.dao.MessagesDao
import com.example.smena.model.Chat
import com.example.smena.model.ChatsWithUsersAndLastMessage
import kotlinx.coroutines.*


class ChatActivity : AppCompatActivity() {

    private val messages: ArrayList<Message> = ArrayList()
    private var chatsDao: ChatsDao? = App.instance.appDatabase!!.chatsDao()
    private var messagesDao: MessagesDao? = App.instance.appDatabase!!.messagesDao()
    private var messageListAdapter: MessagesAdapter? = MessagesAdapter(messages, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chat: Chat = intent.extras!!["chat"] as Chat

        runBlocking { fetchData(chat) }
//        runBlocking { messagesDao!!.deleteAll() }

        val last = messagesDao!!.getLast(chat.id)
        last.observe(this, Observer {
            if (last.value != null)
                messageListAdapter!!.add(last.value!!)
        })

        messagesRV.layoutManager = LinearLayoutManager(this)
        messagesRV.adapter = messageListAdapter

        back.setOnClickListener { finish() }

        button.setOnClickListener {
            runBlocking {
                messagesDao!!.insert(
                    Message(
                        if (messages.size != 0) messages[messages.size - 1].id + 1 else 0,
                        chat.id,
                        editText.text.toString(),
                        chat.userId
                    )
                )
                editText.text = null
            }
        }

    }

    private fun bindViews(chat: List<ChatsWithUsersAndLastMessage>) {
        toolbarTitle.text = chat[0].users[0].name
        Glide.with(this)
            .load(chat[0].users[0].avatar)
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(avatar)
        messageListAdapter!!.addAll(chat[0].messages.toCollection(ArrayList()))
    }

    private suspend fun fetchData(chat: Chat) {
        val chats = chatsDao!!.getChatWithUserById(chat.id)
        bindViews(chats)
    }
}
