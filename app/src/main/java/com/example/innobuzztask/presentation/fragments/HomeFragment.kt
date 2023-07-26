package com.example.innobuzztask.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innobuzztask.R
import com.example.innobuzztask.databinding.FragmentHomeBinding
import com.example.innobuzztask.domain.models.Post
import com.example.innobuzztask.presentation.activties.MainActivity
import com.example.innobuzztask.presentation.adapters.PostAdapter
import com.example.innobuzztask.presentation.services.MyAccessibilityService
import com.example.innobuzztask.presentation.viewmodels.AppViewModel
import com.example.innobuzztask.utils.Utils.KEY_POST
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding by lazy {
        _binding!!
    }
    private lateinit var postAdapter: PostAdapter
    private val viewModel: AppViewModel by viewModels()
    private lateinit var serviceIntent:Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        showProgress()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() {
        postAdapter = PostAdapter()
        serviceIntent = Intent(requireActivity().applicationContext,MyAccessibilityService::class.java)
        requireActivity().startService(serviceIntent)
        lifecycleScope.launch {
            viewModel.mPostsState.collectLatest { state ->
                val posts: List<Post> = state.posts
                val containsError: String? = state.containsError
                val isLoading: Boolean? = state.isLoading

                isLoading?.let {
                    if (it) showProgress()
                    else hideProgress()
                }
                containsError?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                    hideProgress()
                }
                posts.let { list ->
                    postAdapter.apply {
                        setList(list)
                        setClickListener { position ->
                            findNavController().navigate(
                                R.id.action_homeFragment_to_detailsFragment,
                                Bundle().apply {
                                    putString(KEY_POST, Gson().toJson(list[position]))
                                })
                        }
                    }
                    hideProgress()
                }
            }
        }
        binding.apply {
            fragmentHomeRvPosts.apply {
                adapter = postAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            fragmentHomeBtAccessibilityPermissions.setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
    }

    private fun showProgress() = (requireActivity() as MainActivity).showProgress()

    private fun hideProgress() = (requireActivity() as MainActivity).hideProgress()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}