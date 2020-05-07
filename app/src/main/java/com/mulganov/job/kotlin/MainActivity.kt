package com.mulganov.job.kotlin

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.mulganov.job.kotlin.list.MainAdaptor
import com.mulganov.job.kotlin.list.Product
import com.mulganov.job.kotlin.rest.NetworkService
import com.mulganov.job.kotlin.rest.Post
import retrofit2.Call
import retrofit2.Callback
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mContentView: View

    private var title = ""

    private var list = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContentView = findViewById<View>(R.id.main)

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<View>(R.id.progressBar).visibility = View.INVISIBLE

    }

    fun setTitle(title: String){
        runOnUiThread {
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).title = title
        }
    }

    override fun onResume() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        super.onResume()

        list = ArrayList()
        title = ""

        System.out.println(list.size)

        for (c in Load.category){
            if (Filtr.checkMap.get(c) as Boolean){
                title += c
                title += "; "
                getList(c)

                System.out.println(list.size.toString() + " d")
            }
        }

        reloadAdapter()
        setTitle(title)
    }

    fun getList(category: String){
        Load.productMap.get(category)?.let { list.addAll(it) }
        reloadAdapter()
    }

    fun reloadAdapter(){
        setTitle(title)
        val lv = findViewById(R.id.list) as ListView

        lv.post { lv.adapter = MainAdaptor(this, list) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent = Intent(this, Filtr::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

    }
}

