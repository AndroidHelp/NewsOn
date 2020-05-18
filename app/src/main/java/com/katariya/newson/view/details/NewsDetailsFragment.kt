package com.katariya.newson.view.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.katariya.newson.R
import kotlinx.android.synthetic.main.fragment_news_details.view.*

class NewsDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)

        requireArguments().apply {
            val safeArgs =
                NewsDetailsFragmentArgs.fromBundle(
                    this
                )
            val url = safeArgs.sourceUrl

            loadData(view, url)
        }

        return view
    }

    private fun loadData(view: View, url: String) {
        view.webView.loadUrl(url)
        /*     view.webView.webChromeClient = object : WebChromeClient() {
              //   private var mProgress: ProgressDialog? = null
                 override fun onProgressChanged(webView: WebView, progress: Int) {
                     view.progressBar.visibility = View.VISIBLE
                    *//* if (mProgress == null) {
                    mProgress = ProgressDialog(activity)
                    mProgress!!.show()
                }
                mProgress!!.setMessage("Loading $progress%")*//*
                if (progress == 100) {
                    view.progressBar.visibility = View.GONE
                //    mProgress!!.dismiss()
                //    mProgress = null
                }
            }
        }*/
        view.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                webView: WebView,
                url: String,
                favicon: Bitmap?
            ) {
                super.onPageStarted(webView, url, favicon)
                view.progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(webView: WebView, url: String) {
                super.onPageFinished(webView, url)
                view.progressBar.visibility = View.GONE
            }

            override fun onLoadResource(webView: WebView, url: String) {
                super.onLoadResource(webView, url)
            }

            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                return super.shouldOverrideUrlLoading(webView, url)
            }
        }
    }
}