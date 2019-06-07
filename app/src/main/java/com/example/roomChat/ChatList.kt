package com.example.roomChat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomChat.adapter.ChatListAdapter
import com.example.roomChat.dao.ChatsDao
import com.example.roomChat.dao.MessagesDao
import com.example.roomChat.dao.UserDao
import com.example.roomChat.model.Chat
import com.example.roomChat.model.ChatsWithUsersAndLastMessage
import com.example.roomChat.model.Message
import com.example.roomChat.model.User
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.coroutines.runBlocking

class ChatList : AppCompatActivity() {

    private var chatsDao: ChatsDao? = App.instance.appDatabase!!.chatsDao()
    private var userDao: UserDao? = App.instance.appDatabase!!.userDao()
    private var messageDao: MessagesDao? = App.instance.appDatabase!!.messagesDao()

    private val chatList: ArrayList<ChatsWithUsersAndLastMessage> = ArrayList()
    private var chatListAdapter: ChatListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)


        val layoutManager = LinearLayoutManager(this)
        chat_list.layoutManager = layoutManager
        chatListAdapter = ChatListAdapter(chatList, this) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("chat", Chat(it.id, it.userId))
            startActivity(intent)
        }

        chat_list.adapter = chatListAdapter
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        chat_list.addItemDecoration(dividerItemDecoration)

        fetchData()

//        runBlocking { initData() }
    }

    override fun onDestroy() {
        App.instance.destroyDatabase()
        super.onDestroy()
    }

    private fun fetchData() {
        chatsDao!!.getChatsWithUser().observe(this, Observer { chatListAdapter!!.replaceAll(it) })
    }

    private suspend fun initData() {
        val elon = User(
            1,
            "Elon Musk",
            "https://s.yimg.com/ny/api/res/1.2/lseK7hX2FbGJM94BuezbHg--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/http://media.zenfs.com/en-US/homerun/businessinsider.com/9ea607142652ea3bbd2f0792b3f6b821"
        )
        val tony = User(
            2,
            "Tony Stark",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwt9hi7mfItFYX0nk11LBOWM5WY0p1O_t6tu7UUuSokFBQDBxC"
        )
        val chat = Chat(1, tony.id)
        val chat2 = Chat(2, elon.id)
        val message1 = Message(1, chat.id, "Hey! It's me, Tony!", tony.id, true)
        val message2 = Message(2, chat.id, "So whats up man?)", tony.id, true)
        val message3 = Message(3, chat2.id, "Hi, it's Elon", elon.id, true)

        userDao!!.insert(elon)
        userDao!!.insert(tony)
        chatsDao!!.insert(chat)
        chatsDao!!.insert(chat2)
        messageDao!!.insert(message1)
        messageDao!!.insert(message2)
        messageDao!!.insert(message3)
    }
}
