package com.android.bloomshows.network.model.mapper

import com.android.bloomshows.network.model.BloomShowsErrorResponse

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message


object ErrorResponseMapper : ApiErrorModelMapper<BloomShowsErrorResponse> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): BloomShowsErrorResponse {
        return BloomShowsErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}