package com.mulganov.job.kotlin

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class InternetHelper {

    companion object{
        fun getFile(uurl: String, file: String?): File? {
            var bis: BufferedInputStream? = null
            var bos: BufferedOutputStream? = null
            try {
                val url = URL(uurl)
                bis = BufferedInputStream(url.openStream())
                bos = BufferedOutputStream(FileOutputStream(File(file)))
                var c: Int
                while (bis.read().also { c = it } != -1) {
                    bos.write(c)
                }
                println("$uurl OK")
                bos.close()
                bis.close()
                return File(file)
            } catch (e: Exception) {
                println(e.toString())
                println("$uurl NOT")
            }
            return null
        }
    }



}