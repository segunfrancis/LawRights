package com.segunfrancis.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.segunfrancis.common.util.loadImage
import com.segunfrancis.feature.home.model.ContentItem

class ContentAdapter(
    private val contentItem: List<ContentItem>
) : PagerAdapter() {

    override fun getCount(): Int = contentItem.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.content_item, container, false)

        val contentImageView = view.findViewById<ImageView>(R.id.content_image)
        val contentTitle = view.findViewById<TextView>(R.id.content_title)

        contentImageView.loadImage(contentItem[position].contentImage)
        contentTitle.text = contentItem[position].contentTitle
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        return container.removeView(`object` as View)
    }
}
