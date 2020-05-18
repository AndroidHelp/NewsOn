package com.katariya.newson.network.model


import androidx.room.*
import com.google.gson.annotations.SerializedName

data class NewsInfoData(
    @SerializedName("articles")
    val articles: ArrayList<Article>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
)

@Entity(tableName = "news", indices = [Index(value = ["title"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String? = null,
    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String? = null,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String? = null,
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @Embedded
    @SerializedName("source")
    val source: Source? = null,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String? = null,
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String? = null,
    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    val urlToImage: String? = null,
    @ColumnInfo(name = "providerName")
    var providerName: String = "",
    @ColumnInfo(name = "inserted_time_stamp")
    var lastInsertedTimestamp: Long = 0
){
    constructor() : this(0,null, null, null, null, null, null, null, null, "", 0)
}

data class Source(
    @SerializedName("id")
    val sourceId: String? = null,
    @SerializedName("name")
    val sourceName: String? = null
)
