package com.katariya.newson.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.katariya.newson.R
import com.katariya.newson.network.model.Article
import com.katariya.newson.network.viewmodel.NewsViewModel
import com.katariya.newson.network.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.layout_error_view.view.*

class NewsFragment : Fragment(),
    NewsItemClickListener {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var newsDataList = ArrayList<Article>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        val newsViewModelFactory = NewsViewModelFactory()
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        newsAdapter = NewsAdapter(
            context = requireContext(),
            dataList = newsDataList,
            itemClickListener = this
        )
        view.rcvNews.layoutManager = LinearLayoutManager(requireContext())
        view.rcvNews.hasFixedSize()
        view.rcvNews.adapter = newsAdapter

        newsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { successData(it) })
        newsViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { showLoading(it) })
        newsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { updateView(it) })

        view.tvRetry.setOnClickListener {
            newsViewModel.fetchNewsFromRepository()
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val navController = Navigation.findNavController(requireView())
        val action =
            NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(
                newsDataList[position].url!!
            )

         navController.navigate(action)
    }

    private fun successData(it: ArrayList<Article>?) {
        if (it!!.size > 0) {
            updateView(null)
            if (newsDataList.isNotEmpty()) newsDataList.clear()
            newsDataList.addAll(it!!)
            newsAdapter.notifyDataSetChanged()
        } else updateView(getString(R.string.message_empty_is_not_available))
    }

    private fun updateView(message: String?) {
        if (message.isNullOrEmpty()) {
            requireView().errorView.visibility = View.GONE
            requireView().rcvNews.visibility = View.VISIBLE
        } else {
            requireView().errorView.visibility = View.VISIBLE
            requireView().rcvNews.visibility = View.GONE
            if (message.contains("Unable to resolve host", ignoreCase = true))
                requireView().tvError.text = getString(R.string.message_check_your_internet_connection)
            else requireView().tvError.text = message
        }
    }

    private fun showLoading(it: Boolean) {
        if (it) {
            requireView().pbLoading.visibility = View.VISIBLE
            requireView().errorView.visibility = View.GONE
            requireView().rcvNews.visibility = View.GONE
        } else {
            requireView().pbLoading.visibility = View.GONE
            requireView().errorView.visibility = View.GONE
            requireView().rcvNews.visibility = View.VISIBLE
        }
    }
}