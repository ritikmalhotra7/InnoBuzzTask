package com.example.innobuzztask.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innobuzztask.data.mappers.toPost
import com.example.innobuzztask.data.remote.dtos.PostDto
import com.example.innobuzztask.domain.models.Post
import com.example.innobuzztask.domain.usecases.FetchPosts
import com.example.innobuzztask.domain.usecases.GetPosts
import com.example.innobuzztask.domain.usecases.InsertPosts
import com.example.innobuzztask.utils.ResponseState
import com.example.innobuzztask.utils.Utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val fetchPost: FetchPosts,
    private val insertPosts: InsertPosts,
    private val getPosts: GetPosts,
    private val context: Application
) : ViewModel() {
    private val _mPostsState = MutableStateFlow(PostState())
    val mPostsState get() = _mPostsState

    init {
        if (isConnected(context, null, null)) displayPosts()
        else {
            displayPosts()
            viewModelScope.launch {
                _mPostsState.emit(
                    PostState(
                        posts = listOf(),
                        isLoading = false,
                        containsError = "No Internet Network"
                    )
                )
            }
        }
    }

    data class PostState(
        val posts: List<Post> = listOf(),
        val containsError: String? = null,
        val isLoading: Boolean? = null
    )

    private fun displayPosts() = viewModelScope.launch(Dispatchers.IO) {
        getPosts().collectLatest { result ->
            when (result) {
                is ResponseState.Success -> {
                    result.data?.let { data ->
                        if (data.isEmpty()) {
                            if (isConnected(context, { fetchFromApi() }, null)) fetchFromApi()
                            else _mPostsState.emit(
                                PostState(
                                    posts = listOf(),
                                    isLoading = false,
                                    containsError = "No Internet Network"
                                )
                            )
                        } else {
                            _mPostsState.emit(
                                PostState(
                                    posts = data.map { it.toPost() },
                                    isLoading = false,
                                    containsError = null
                                )
                            )
                        }
                    }
                }

                is ResponseState.Error -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = false,
                            containsError = result.message
                        )
                    )
                }

                is ResponseState.Loading -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = true,
                            containsError = null
                        )
                    )
                }
            }
        }
    }

    private fun fetchFromApi() = viewModelScope.launch(Dispatchers.IO) {
        fetchPost().collectLatest { result ->
            when (result) {
                is ResponseState.Success -> {
                    result.data?.let { data ->
                        insertToDb(data)
                    }
                }

                is ResponseState.Error -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = false,
                            containsError = result.message
                        )
                    )
                }

                is ResponseState.Loading -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = true,
                            containsError = null
                        )
                    )
                }
            }
        }
    }

    private suspend fun insertToDb(list: List<PostDto>) {
        insertPosts(list.map { it.toPost() }).collectLatest { isInserted ->
            when (isInserted) {
                is ResponseState.Success -> {
                    isInserted.data?.let {
                        if (isInserted.data) {
                            displayPosts()
                        }
                    }
                }

                is ResponseState.Error -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = false,
                            containsError = isInserted.message
                        )
                    )
                }

                is ResponseState.Loading -> {
                    _mPostsState.emit(
                        PostState(
                            posts = listOf(),
                            isLoading = true,
                            containsError = null
                        )
                    )
                }
            }
        }
    }

}