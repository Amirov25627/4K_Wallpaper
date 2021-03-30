package com.example.a4kwallpapperchangerwithoutads.network

data class PicturesData(
        //val id: String,
        //val urls: Urls,
        val urls: PictureUrls,
        val links: Links,
        val id: String
)

data class PictureUrls(
        val full: String
)

data class Links(
        val download: String
)