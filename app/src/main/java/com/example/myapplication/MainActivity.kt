package com.example.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.ItemAdapter
import com.example.myapplication.networking.LilyApi
import com.example.myapplication.networking.LilyApiService
import com.example.myapplication.networking.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var allPosts: MutableList<Post>

    lateinit var mRecyclerView: RecyclerView
    lateinit var mFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFab = findViewById(R.id.fab)
        mFab.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(it.context)
            val dialogLayout: View = layoutInflater.inflate(R.layout.dialog_create_new_post, null)
            dialogBuilder.setView(dialogLayout)
            dialogBuilder.setNegativeButton(
                "Close",
                { dialog, id ->
                    dialog.dismiss()
                }
            )
            dialogBuilder.setPositiveButton(
                "Save",
                { dialog, id ->
                    val title: String = dialogLayout.findViewById<EditText>(R.id.new_title).getText().toString()
                    val description: String = dialogLayout.findViewById<EditText>(R.id.new_body).getText().toString()
                    CoroutineScope(Dispatchers.Main).launch {
                        savePost(
                            title,
                            description
                        )
                    }
                    dialog.dismiss()
                })
            dialogBuilder.show()
        }
        lifecycleScope.launchWhenStarted {
            mRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
            mRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            mRecyclerView.setHasFixedSize(true)
            refreshData()
        }

    }

    suspend fun savePost(title: String, description: String) {
        val newPost: Post = LilyApi.retrofitService.createPost(Post(id = 0, title = title, description = description))
        allPosts.add(newPost)
        mRecyclerView.adapter?.notifyDataSetChanged()
    }

    suspend fun refreshData() {
        allPosts = LilyApi.retrofitService.getPosts() as MutableList<Post>
        mRecyclerView.adapter = ItemAdapter(applicationContext, allPosts)
    }
}