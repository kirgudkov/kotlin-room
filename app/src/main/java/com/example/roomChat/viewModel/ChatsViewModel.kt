package com.example.roomChat.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.roomChat.App
import com.example.roomChat.ChatActivity
import com.example.roomChat.dao.ChatsDao
import com.example.roomChat.dao.MessagesDao
import com.example.roomChat.dao.UserDao
import com.example.roomChat.model.Chat
import com.example.roomChat.model.ChatsWithUsersAndLastMessage
import com.example.roomChat.model.Message
import com.example.roomChat.model.User

class ChatsViewModel(private val context: Context) : ViewModel() {

    private var chatsDao: ChatsDao? = App.instance.appDatabase!!.chatsDao()
    private var userDao: UserDao? = App.instance.appDatabase!!.userDao()
    private var messageDao: MessagesDao? = App.instance.appDatabase!!.messagesDao()

    fun fetchData(): LiveData<List<ChatsWithUsersAndLastMessage>> {
        return chatsDao!!.getChatsWithUser()
    }

    fun onItemPress(it: ChatsWithUsersAndLastMessage) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("chat", Chat(it.id, it.userId))
        context.startActivity(intent)
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