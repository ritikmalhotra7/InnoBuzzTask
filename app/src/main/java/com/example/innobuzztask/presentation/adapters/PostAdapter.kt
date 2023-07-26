package com.example.innobuzztask.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzztask.databinding.PostItemBinding
import com.example.innobuzztask.domain.models.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var itemClickListener: ((Int) -> Unit)? = null

    private val callback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.body == newItem.body
        }
    }
    private val differ = AsyncListDiffer(this@PostAdapter, callback)

    fun setList(list: List<Post>) {
        differ.submitList(list)
    }

    fun setClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    inner class ViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Post, position: Int) {
            binding.apply {
                item.apply {
                    postItemTvTitle.text = title
                }
                root.setOnClickListener {
                    itemClickListener?.let {
                        it(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.setData(item, position)
    }
}