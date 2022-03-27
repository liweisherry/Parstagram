package com.rkpandey.parstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.parse.*

import java.io.File
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rkpandey.parstagram.fragments.Compose
import com.rkpandey.parstagram.fragments.HomeFragment
import com.rkpandey.parstagram.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {


    var miActionProgressItem: MenuItem? = null


// run a background job and once complete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager: FragmentManager = supportFragmentManager
        getSupportActionBar()?.hide();


        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener{

        // Return true to say that we have handled this user interaction on the item
            item ->
            var fragmentToShow: Fragment ?= null
            when(item.itemId){
                R.id.action_home -> {
                    fragmentToShow = HomeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = Compose()
                }

            }
            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow)
                    .commit()
            }
            true
        }
        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.home

    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress)

        // Return to finish
        return super.onPrepareOptionsMenu(menu)
    }

    fun showProgressBar() {
        // Show progress item
        miActionProgressItem!!.isVisible = true
    }

    fun hideProgressBar() {
        // Hide progress item
        miActionProgressItem!!.isVisible = false
    }








    companion object {
        const val TAG = "MainActivity"
    }
}
