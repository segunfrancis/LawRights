package com.segunfrancis.feature.my_rights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.segunfrancis.feature.my_rights.databinding.FragmentMyRightsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MyRightsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMyRightsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MyRightsViewModel>()

    private val rightsPagingAdapter: LawRightsPagingAdapter by lazy { LawRightsPagingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRightsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rightRecyclerView.adapter = rightsPagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.myRights
                .catch { Timber.e(it) }
                .collectLatest { pagingData ->
                    pagingData.map {
                        Timber.d("PagedData: $it")
                    }
                    rightsPagingAdapter.submitData(pagingData)
                    viewModel.updateTime()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
