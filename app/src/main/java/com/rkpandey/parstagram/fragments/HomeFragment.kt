package com.rkpandey.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.rkpandey.parstagram.MainActivity
import com.rkpandey.parstagram.Post
import com.rkpandey.parstagram.PostAdapter
import com.rkpandey.parstagram.R
open class HomeFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allposts: MutableList<Post> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we set up our views and click listeners

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postRecycleView)

        // Steps to populate RecycleView
        // 1. Create layout for each row in list
        // 2. Create data source for each row
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecycleView
        adapter = PostAdapter(requireContext(), allposts)
        postsRecyclerView.adapter = adapter
        // 5. Set layout manager on RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        queryPost()

    }

    // Send a post object to our parse server

    open fun queryPost() {
        // Specify which class to query

        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        // return posts in descending order
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(
                                TAG,
                                "Post:" + post.getDescription() + ", username: " + post.getUser()?.username
                            )


                        }
                        allposts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }


    companion object {
        const val TAG = "HomeFragment"
    }
}