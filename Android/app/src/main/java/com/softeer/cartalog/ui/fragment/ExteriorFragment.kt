package com.softeer.cartalog.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.DecodeResult
import coil.decode.Decoder
import coil.disk.DiskCache
import coil.imageLoader
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import coil.transition.Transition
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.FragmentExteriorBinding
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ExteriorFragment : Fragment() {
    private val exteriorViewModel: ExteriorViewModel by viewModels()

    private var _binding: FragmentExteriorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExteriorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exteriorViewModel.setExteriorColorData()

        binding.viewModel = exteriorViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 360img test
        val img360List = mutableListOf<Drawable>()

        val imageLoader = ImageLoader.Builder(requireContext())
            .memoryCache {
                MemoryCache.Builder(requireContext())
                    .maxSizePercent(0.3)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(requireContext().cacheDir.resolve("car_360_img"))
                    .maxSizePercent(0.3)
                    .build()
            }
            .build()

        lifecycleScope.launch {
            for (i in 1..60) {
                val request = ImageRequest.Builder(requireContext())
                    .data(
                        "https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/exterior/A2B/${
                            String.format(
                                "%03d",
                                i
                            )
                        }.png"
                    )
                    .build()
                imageLoader.execute(request).drawable?.let {
                    img360List.add(it)
                }
            }
        }

        var startX = 0f
        binding.iv360.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    true
                }

                MotionEvent.ACTION_MOVE -> {

                    var dragIdx = (((startX - event.x) / view.width * 60).toInt() + 60) % 60
                    val request = ImageRequest.Builder(requireContext())
                        .data(img360List[dragIdx])
                        .crossfade(false)
                        .target(binding.iv360)
                        .placeholder(img360List[dragIdx])
                        .build()
                    try {
                        imageLoader.enqueue(request)
                    } catch (e: NullPointerException) {
                    }

                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}