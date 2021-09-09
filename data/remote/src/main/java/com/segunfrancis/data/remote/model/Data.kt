package com.segunfrancis.data.remote.model

data class Data(
    val current_page: Int,
    val `data`: List<DataX>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val path: String,
    val per_page: String,
    val prev_page_url: Any,
    val to: Int,
    val total: Int
)