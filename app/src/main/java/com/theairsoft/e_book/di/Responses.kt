package com.theairsoft.e_book.di

import com.google.gson.annotations.SerializedName
import com.theairsoft.e_book.database.BookEntity
import com.theairsoft.e_book.database.NewsLocal

data class NewsResponse(
    val status: String?,
    @SerializedName("num_results")
    val numResults: Int,
    val results: List<NewsEmailedArticle>?
) {
    fun newsEntityList(): List<NewsLocal> {
        return results?.map { e -> e.toNewsEntity() } ?: arrayListOf()
    }
}


data class NewsEmailedArticle(
    val url: String?,
    val id: Long?,
    @SerializedName("asset_id")
    val assetsId: Long?,
    val source: String?,
    @SerializedName("published_date")
    val publishedDate: String?,
    val updated: String?,
    val title: String?,
    val abstract: String?,
    val media: List<NewsMedia>?
) {
    private fun getImage():String{
        return media?.let { media->
            if (media.isNotEmpty()){
                media[0].metadata?.let { meta->
                    if (meta.isNotEmpty()){
                        meta[0].url
                    }else{
                        ""
                    }
                }
            }else{
                ""
            }
        }.toString()
    }

    fun toNewsEntity(): NewsLocal {
        return NewsLocal(
            id = id,
            publishedDate = publishedDate?:"",
            title = title,
            subTitle = abstract,
            image = getImage()
        )
    }
}

data class NewsMedia(
    val type: String?,
    val subtype: String?,
    @SerializedName("media-metadata")
    val metadata: List<MediaMetadatas>?
)

data class MediaMetadatas(
    val url: String?,
    val format: String?
)

data class BookResponse(
    val status: String?,
    @SerializedName("num_results")
    val numResults: Int,
    val results: ResultData?
)

data class ResultData(
    val lists: List<Lists>?
)

data class Lists(
    @SerializedName("list_id")
    val id: Long?,
    val books: List<Books>?
) {
    fun booksEntityList(): List<BookEntity> {
        return books?.map { e -> e.toBookEntity() } ?: arrayListOf()
    }
}

data class Books(
    @SerializedName("book_image")
    val bookImage: String?,
    val author: String?,
    val title: String?,
    @SerializedName("amazon_product_url")
    val url: String?
) {
    fun toBookEntity(): BookEntity {
        return BookEntity(null, title, author, url,bookImage)
    }
}
