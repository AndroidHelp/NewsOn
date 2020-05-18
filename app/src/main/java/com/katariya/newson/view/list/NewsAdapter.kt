package com.katariya.newson.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katariya.newson.R
import com.katariya.newson.network.model.Article
import com.katariya.newson.utils.fromHtml
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_news_item.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ItemViewHolder> {

    private val context: Context
    private val dataList: ArrayList<Article>
    private val itemClickListener: NewsItemClickListener

    constructor(
        context: Context,
        dataList: ArrayList<Article>,
        itemClickListener: NewsItemClickListener
    ) : super() {
        this.context = context
        this.dataList = dataList
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val newsItem = dataList[position]
        holder.bindItem(newsItem)
        holder.itemView.setOnClickListener { itemClickListener.onItemClick(position) }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(newsItem: Article) {
            itemView.apply {
                if (newsItem.title != null) {
                    tvArticleTitle.visibility = View.VISIBLE
                    tvArticleTitle.text = fromHtml(newsItem.title)
                } else tvArticleTitle.visibility = View.GONE
                if (newsItem.description != null) {
                    tvDescription.visibility = View.VISIBLE
                    tvDescription.text =
                        fromHtml(newsItem.description)
                } else tvDescription.visibility = View.GONE
                if (newsItem.content != null) {
                    val separated= newsItem.content.substringBefore("[")
                    tvContent.visibility = View.VISIBLE
                    tvContent.text = fromHtml(separated+ "Read More")
                } else tvContent.visibility = View.GONE
                if (newsItem.urlToImage != null) {
                    ivArticle.visibility = View.VISIBLE
                    Picasso.get().load(newsItem.urlToImage).into(ivArticle)
                } else ivArticle.visibility = View.GONE

            }
        }
    }
}