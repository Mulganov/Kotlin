package com.mulganov.job.kotlin

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.mulganov.job.kotlin.list.FiltrAdapter
import com.mulganov.job.kotlin.list.FiltrInfo
import com.mulganov.job.kotlin.list.MainAdaptor
import com.mulganov.job.kotlin.list.Product
import com.mulganov.job.kotlin.rest.NetworkService
import com.mulganov.job.kotlin.rest.Post
import retrofit2.Call
import retrofit2.Callback
import java.io.File

class Filtr : AppCompatActivity() {

    private lateinit var mContentView: View


    companion object{
        public var checkMap = HashMap<String, Boolean>()
    }

    var filtrs = ArrayList<FiltrInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtr)

        mContentView = findViewById<View>(R.id.main)

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        setSupportActionBar(findViewById(R.id.toolbar))

        (findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar).setNavigationOnClickListener {
            super.onBackPressed()
        }

        (findViewById(R.id.button) as Button).setOnClickListener {
            super.onBackPressed()
        }

        title = Load.category[0]

        setTitle("Filtrs")

        for (c in Load.category){
            filtrs.add(FiltrInfo(c))
        }

        reloadAdapter()
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
    }


    fun reloadAdapter(){
        val lv = findViewById(R.id.list) as ListView

        lv.post { lv.adapter = FiltrAdapter(this, filtrs) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        var intent = Intent(this, Filtr::class.java)

        return super.onOptionsItemSelected(item)
    }
}

