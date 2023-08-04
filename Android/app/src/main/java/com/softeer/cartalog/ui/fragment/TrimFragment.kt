package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.FragmentTrimBinding
import com.softeer.cartalog.model.data.Trim
import com.softeer.cartalog.model.data.TrimOption
import com.softeer.cartalog.ui.adapter.TrimCardAdapter

class TrimFragment : Fragment() {

    private var _binding: FragmentTrimBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 임시 데이터 설정
        val tmpOption = listOf(TrimOption("a",12))
        val tmpData = Trim("name","desc",10,tmpOption)
        val trimItemAdapter = TrimCardAdapter(listOf(tmpData,tmpData,tmpData))

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.vpSlideContainer.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        binding.vpSlideContainer.setPadding(70,0,70,0)
        binding.vpSlideContainer.offscreenPageLimit = 2
        binding.vpSlideContainer.adapter = trimItemAdapter
        binding.dotsIndicator.attachTo(binding.vpSlideContainer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}