package com.example.android_architecture_example.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android_architecture_example.R
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.ui.overview.UserGridAdapter
import java.lang.StringBuilder
import java.util.*

@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("fullName")
fun getFullName(textView: TextView, user: User) {
    val sb = StringBuilder()
    if (user.title.isEmpty()) {
        textView.text = sb.append(user.firstName + " ").append(user.lastName)
    } else {
        textView.text = sb.append(user.title.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }+ ".").append(user.firstName + " ").append(user.lastName)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserGridAdapter
    adapter.submitList(data)
}