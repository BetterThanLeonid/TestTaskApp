package com.nedushny.test_task.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nedushny.test_task.R
import com.nedushny.test_task.network.NetworkConstants


class OpenImageActivity : AppCompatActivity() {

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        image = findViewById(R.id.image)

        val url = intent.getStringExtra("url")

        Glide.with(this)
            .load(url)
            .error(NetworkConstants.IMAGE_FAILED_URL)
            .into(image)
    }
}