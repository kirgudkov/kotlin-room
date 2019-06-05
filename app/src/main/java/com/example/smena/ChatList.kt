package com.example.smena

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smena.adapter.ChatListAdapter
import com.example.smena.dao.ChatsDao
import com.example.smena.model.Chat
import com.example.smena.model.ChatsWithUsersAndLastMessage
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatList : AppCompatActivity() {

    private var chatsDao: ChatsDao? = App.instance.appDatabase!!.chatsDao()

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
    }

    override fun onDestroy() {
        App.instance.destroyDatabase()
        super.onDestroy()
    }

    private fun fetchData() {
        println("------------------------------------------")
        println(chatsDao!!.getAll())
        println("------------------------------------------")
        val chats = chatsDao!!.getChatsWithUser()
        println(chats)
        println("------------------------------------------")

        chats.observe(this, Observer {
            chatListAdapter!!.replaceAll(it)
        })
    }

//    private fun initData() {
//        val task = Runnable {
//            val elon = User(
//                1,
//                "Elon Musk",
//                "https://s.yimg.com/ny/api/res/1.2/lseK7hX2FbGJM94BuezbHg--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/http://media.zenfs.com/en-US/homerun/businessinsider.com/9ea607142652ea3bbd2f0792b3f6b821"
//            )
//            val tony = User(
//                2,
//                "Tony Stark",
//                "https://s.yimg.com/ny/api/res/1.2/lseK7hX2FbGJM94BuezbHg--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/http://media.zenfs.com/en-US/homerun/businessinsider.com/9ea607142652ea3bbd2f0792b3f6b821"
//            )
//            val chat = Chat(1, tony.id)
//            val message1 = Message(1, chat.id, "Sap bitch?", tony.id)
//            val message2 = Message(2, chat.id, "Hi, it's Tony", tony.id)
//
//            userDao!!.insert(elon)
//            userDao!!.insert(tony)
//            chatsDao!!.insert(chat)
//            messageDao!!.insert(message1)
//            messageDao!!.insert(message2)
//        }
//        mDbWorkerThread.postTask(task)
//    }
}
