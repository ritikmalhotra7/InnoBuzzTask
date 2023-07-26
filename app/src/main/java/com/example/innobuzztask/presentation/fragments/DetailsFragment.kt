package com.example.innobuzztask.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.innobuzztask.R
import com.example.innobuzztask.databinding.FragmentDetailsBinding
import com.example.innobuzztask.domain.models.Post
import com.example.innobuzztask.utils.Utils.KEY_POST
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding by lazy {
        _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        setViews()
        return binding.root
    }

    private fun setViews() {
        val post = Gson().fromJson(requireArguments().getString(KEY_POST), Post::class.java)
        binding.apply {
            post.apply {
                fragmentDetailsTvUserId.text = buildString {
                    append(getString(R.string.userid))
                    append(userId)
                }
                fragmentDetailsTvId.text = buildString {
                    append(getString(R.string.id))
                    append(id)
                }
                fragmentDetailsTvTitle.text = title
                fragmentDetailsTvBody.text = body
            }
            fragmentDetailsIvBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}