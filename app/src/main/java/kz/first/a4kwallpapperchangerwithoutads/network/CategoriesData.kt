package kz.first.a4kwallpapperchangerwithoutads.network


data class CategoriesData(
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



