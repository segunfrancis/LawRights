package com.segunfrancis.feature.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.segunfrancis.common.util.LoginResult
import com.segunfrancis.feature.home.databinding.FragmentHomeBinding
import com.segunfrancis.feature.home.model.ContentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private var dotscount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.token.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.Loading -> handleLoading()
                is LoginResult.Error -> handleError(result.error)
            }
        }

        setupPagerAdapter()

        binding.middleFirstCardItem.setOnClickListener {
            findNavController().navigate("https://com.segunfrancis.feature/myrights".toUri())
        }
    }

    private fun setupPagerAdapter() = with(binding) {

        val contentAdapter = ContentAdapter(contentItem = ContentItem.getPagerItems())
        viewPagerContents.adapter = contentAdapter
        viewPagerContents.setPadding(30, 10, 30, 10)
        dotscount = contentAdapter.count

        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(requireContext())
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.non_active_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            sliderDots.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(), R.drawable.active_dot
            )
        )

        viewPagerContents.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(), R.drawable.non_active_dot
                        )
                    )
                }
                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.active_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun handleLoading() {

    }

    private fun handleError(error: Throwable) {

    }
}
