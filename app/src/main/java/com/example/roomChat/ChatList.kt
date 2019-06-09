package com.example.roomChat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomChat.adapter.ChatListAdapter
import com.example.roomChat.databinding.ActivityChatListBinding
import com.example.roomChat.viewModel.ChatsViewModel

class ChatList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityChatListBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_list)

        binding.lifecycleOwner = this

        val layoutManager = LinearLayoutManager(this)
        binding.chatList.layoutManager = layoutManager

        val chatListAdapter = ChatListAdapter(this) {
            binding.viewModel?.onItemPress(it)
        }

        binding.chatList.adapter = chatListAdapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.chatList.addItemDecoration(dividerItemDecoration)

        val viewModel = ChatsViewModel(this)
        viewModel.fetchData().observe(this, Observer { chatListAdapter.replaceAll(it) })
        binding.setVariable(BR.viewModel, viewModel)

    }

    override fun onDestroy() {
        App.instance.destroyDatabase()
        super.onDestroy()
    }
}
