package kz.first.a4kwallpapperchangerwithoutads.network

data class PicturesData(
    val urls: PictureUrls,
    val links: Links,
    val id: String
)

data class PictureUrls(
        val full: String,
        val regular: String
)

data class Links(
        val download: String
)