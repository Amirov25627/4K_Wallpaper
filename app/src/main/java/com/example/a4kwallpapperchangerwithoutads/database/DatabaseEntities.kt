//package com.example.a4kwallpapperchangerwithoutads.database
//
//import androidx.navigation.NavType
//import androidx.room.Embedded
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.a4kwallpapperchangerwithoutads.network.PicturesData
//import com.example.a4kwallpapperchangerwithoutads.network.Urls
//
//@Entity
//data class DatabasePicture constructor(
//        @PrimaryKey
//        val id: String,
//        @Embedded val urls: Urls
//)
//
//
//fun List<DatabasePicture>.asDomainModel(): List<PicturesData> {
//    return map {
//        PicturesData(
//                id = it.id,
//                urls = it.urls )
//         }
//}

