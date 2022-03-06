package com.nedushny.test_task.network

import retrofit2.Response

class Repository {

    suspend fun loadImages(): Response<ArrayList<String>> = RetrofitInstance().api.loadImages()

}