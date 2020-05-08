package com.mulganov.job.kotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mulganov.job.kotlin.list.Product
import com.mulganov.job.kotlin.rest.Category
import com.mulganov.job.kotlin.rest.NetworkService
import com.mulganov.job.kotlin.rest.Post
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class Load : AppCompatActivity() {

    private lateinit var mContentView: View

    private var process = 0



    companion object{
        public lateinit var text: TextView
        public var products = ArrayList<Product>()
        public var category = ArrayList<String>()
        public var productMap = HashMap<String, ArrayList<Product>>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        text = findViewById(R.id.text)

        mContentView = findViewById<View>(R.id.main)

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        setSupportActionBar(findViewById(R.id.toolbar))

        text.text = "Загрузка"

        NetworkService.getInstance().getListCategory(this)

    }

    fun write(text: String){
        Load.text.post {
            Load.text.text = text
        }
    }

    fun setCategory(category: Category){
        write("Загрузка: Получили категории")

        System.out.println("Category + Size: " + category.list.size)
        for (c in category.list){
            Filtr.checkMap.put(c.category, false);
            Load.category.add(c.category)
            setProducts(c.category)
        }

        Filtr.checkMap.put(Load.category[0], true);


        Thread(Runnable {
            while(true){
                if (process >= category.list.size){
                    runOnUiThread { findViewById<View>(R.id.progressBar2).visibility = View.INVISIBLE }
                    write("Завершено")

                    Thread.sleep(250)

                    setBitmap();

                    break
                }
            }
        }).start()

    }

    private fun setBitmap() {
        write("Синхронизация ресурсов") // круто звучит
        var i = 0
        for (product in products){
            write("Синхронизация ресурсов:\n" + i + "/" + (products.size-1))
            try {
//                Assets.addBitmapToMemoryCache(product.url, Assets.loadImageFromAsset(File(product.url)))
                product.bitmap = decodeFile(File(product.url)) as Bitmap
            }catch (ex: Exception){
                product.bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_xz)
                println("Error: index: " + product.url.replace(filesDir.absolutePath+ "/", ""))
            }
            i++
        }

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun setProducts(category: String){
        NetworkService.getInstance().getList(this, category)
    }

    fun getPost(posts: Post, category: String){
        println("Size: "  + posts.list.size)

        Thread(Runnable {
            var array = ArrayList<Product>()

            for (a in posts.list){

                write("Загрузка: Оброботка предметов: " + a.name)

                var product = Product()
                product.name = a.name
                product.url = filesDir.absolutePath+ "/" + a.name.replace("/", "|") + ".jpg"

//                product.url = filesDir.absolutePath+ "/" + index + ".jpg"

                File(filesDir.absolutePath + "/").mkdir()


                while(true){
                    if (!File(product.url).isFile)
                        InternetHelper.getFile(a.img, product.url);
                    else
                        break;
                }
                println(product.url + " | " + File(product.url).isFile)


                products.add(product)

                array.add(product)
            }

            productMap.put(category, array);

            process++
            System.out.println(process)

        }).start()
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


    fun decodeFile(f: File): Bitmap? {
        try { // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)
            // The new size we want to scale to
            val REQUIRED_SIZE = 70
            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            // Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }
        return null
    }
}

