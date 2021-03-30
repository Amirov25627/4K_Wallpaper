package com.example.a4kwallpapperchangerwithoutads.network

//import com.example.a4kwallpapperchangerwithoutads.database.DatabasePicture
//import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.squareup.moshi.Json


//data class PicturesData (
//        //@SerializedName("id") val id: String,
//        @SerializedName("urls") val urls: List<Urls>
//)
//
//data class Urls (
//        @SerializedName("raw") val raw : String,
//        @SerializedName("full") val full : String
//)


//fun List<PicturesData>.asDatabaseModel(): List<DatabasePicture> {
//    return map {
//        DatabasePicture(
//                id = it.id,
//                urls = it.urls)
//    }
//}

data class CategoriesData(
    //val id: String,
    //val urls: Urls,
    val title: String,
    val slug: String,
    val preview_photos: List<Preview>
)

data class Preview(
    val urls: Urls
)

data class Urls(
        val small : String
)



