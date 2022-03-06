package com.nedushny.test_task.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nedushny.test_task.GridSpacingItemDecoration
import com.nedushny.test_task.ImagesAdapter
import com.nedushny.test_task.R
import com.nedushny.test_task.TaskViewModel
import java.io.IOException

//    Приложение должно получать JSON-список ссылок на изображения с сервера по адресу
//    http://dev-tasks.alef.im/task-m-001/list.php
//
//    и отображать их в виде таблицы следующим образом:
//    1) все картинки отображаются квадратными.
//    2) на телефонах две картинки в ряд, на планшетах три картинки в ряд.
//
//    Приложение должно
//    — запускаться на Android начиная с версии 5.0
//    — поддерживать портретную и альмбомную ориентацию
//    — иметь любую иконку, отличную от иконки по умолчанию
//    — при свайпе вниз контент должен обновляться
//    — при клике на изображение оно должно открыться на весь экран.

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewImages: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewImages = findViewById(R.id.recyclerViewImages)
        swipeRefreshLayout = findViewById(R.id.swipeLayout)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        viewModel.loadImages()
        var spanCount = 2

        val mobileOrTableThroughWebView = WebView(this).settings.userAgentString
        if (!mobileOrTableThroughWebView.contains("Mobile")) {
            spanCount = 3
        }

        val adapter = ImagesAdapter()
        val layoutManager = GridLayoutManager(this, spanCount)
        val itemDecorator = GridSpacingItemDecoration(this, spanCount, 20, true)

        recyclerViewImages.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
            addItemDecoration(itemDecorator)
        }

        adapter.setOnItemClickListener(object : ImagesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        OpenImageActivity::class.java
                    ).putExtra("url", adapter.data[position])
                )
            }
        })

        viewModel.data.observe(this) {
            adapter.data = it
            Log.d("TAG", "onCreate: success")
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.error.observe(this) {
            when (it) {
                is IOException -> Log.d("TAG", "Failed: $it")
                is Int -> Toast.makeText(this, "Network code: $it", Toast.LENGTH_SHORT).show()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadImages()
        }

    }
}