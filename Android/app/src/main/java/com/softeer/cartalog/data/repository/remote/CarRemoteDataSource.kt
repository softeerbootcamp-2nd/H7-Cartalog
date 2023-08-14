package com.softeer.cartalog.data.repository.remote

import android.graphics.drawable.Drawable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.remote.api.CarApi
import kotlinx.coroutines.launch
import retrofit2.Response

class CarRemoteDataSource(
    private val carApi: CarApi
){
    suspend fun getTrims(): Response<Trims>{
        return carApi.getTrims(1)
    }

}