package com.nedushny.test_task.network

import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("task-m-001/list.php")
    suspend fun loadImages() : Response<ArrayList<String>>
}