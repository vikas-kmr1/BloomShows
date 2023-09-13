package com.android.bloomshows.network.model.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvResponse(
  @field:Json(name = "page")
    val page: Int,
  @field:Json(name = "results")
    val results: List<TvResultResponse>,
  @field:Json(name = "total_pages")
    val totalPages: Int,
  @field:Json(name = "total_results")
    val totalResults: Int
)