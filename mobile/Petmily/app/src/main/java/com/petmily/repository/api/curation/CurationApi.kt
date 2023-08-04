package com.petmily.repository.api.curation

import com.petmily.repository.dto.CurationResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CurationApi {
    /**
     * all 전체 수신
     * 강아지
     * 고양이
     */
    @GET("/curation/getNewsData")
    suspend fun requestCurationData(@Query("species") species: String): CurationResult
}
