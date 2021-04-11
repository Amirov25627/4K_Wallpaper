package kz.first.a4kwallpapperchangerwithoutads.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PicturesService {
    @GET("topics/?client_id=ONP7jkHj4xX3kCUOq5wESG1LOH0VRgJ8RxW7alGvpes")
    suspend fun categoryList(): List<CategoriesData>


    @GET("topics/{topic}/photos?client_id=ONP7jkHj4xX3kCUOq5wESG1LOH0VRgJ8RxW7alGvpes")
    suspend fun pictureList(@Path("topic") name: String): List<PicturesData>

}



object PicturesAPI {
    val service: PicturesService by lazy {
        retrofit.create(
            PicturesService::class.java)
    }
}

val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()


val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


