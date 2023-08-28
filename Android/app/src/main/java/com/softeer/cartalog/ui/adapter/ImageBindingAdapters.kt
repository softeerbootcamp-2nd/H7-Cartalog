package com.softeer.cartalog.ui.adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.transition.CrossfadeTransition
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.util.UtilManager
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("imgUrl")
fun setImageWithUrl(
    imageView: ImageView,
    imgUrl: String?
) {
    imageView.load(imgUrl)
}
@BindingAdapter("imgUrlAnimation")
fun setImageWithAnimation(
    imageView: ImageView,
    imgUrl: String?
){
    imageView.load(imgUrl) {
        transitionFactory { target, result -> CrossfadeTransition(target, result, 400, false) }
    }
}

@BindingAdapter("colorCode")
fun setImage360Init(
    imageView: ImageView,
    colorCode: String?
) {
    imageView.load(colorCode?.let { UtilManager.get360Image(it, 1) }) {
        transitionFactory { target, result -> CrossfadeTransition(target, result, 400, false) }
    }
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("viewModel")
fun setImage360WithUrl(
    imageView: ImageView,
    viewModel: ExteriorViewModel
) {
    val imageLoader = ImageLoader.Builder(imageView.context)
        .memoryCache {
            MemoryCache.Builder(imageView.context)
                .maxSizePercent(0.3)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(imageView.context.cacheDir.resolve("car_360_img"))
                .maxSizePercent(0.3)
                .build()
        }
        .build()

    imageView.setOnTouchListener { _, event ->

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                viewModel.setStart360X(event.x)
                true
            }

            MotionEvent.ACTION_MOVE -> {
                val dragIdx =
                    (((viewModel.start360X.value!! - event.x) / imageView.width * 60).toInt() + 60) % 60

                val imgUrl = UtilManager.get360Image(
                    viewModel.colorList.value!![viewModel.selectedColorIdx.value!!].code,
                    dragIdx
                )
                val request = ImageRequest.Builder(imageView.context)
                    .data(imgUrl)
                    .build()

                CoroutineScope(Dispatchers.IO).launch {
                    val drawable = imageLoader.execute(request).drawable
                    drawable?.let {
                        imageLoader.enqueue(
                            ImageRequest.Builder(imageView.context)
                                .data(it)
                                .crossfade(false)
                                .target(imageView)
                                .placeholder(it)
                                .build()
                        )
                    }
                }
                true
            }

            else -> false
        }
    }
}

@BindingAdapter("optionType")
fun setImageViewScaleType(
    imageView: ImageView,
    optionType: PriceDataType
){
    if(optionType == PriceDataType.POWERTRAIN || optionType == PriceDataType.BODYTYPE ||  optionType == PriceDataType.WHEELDRIVE){
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
    } else {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    }
}