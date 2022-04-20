package id.jyotisa.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.jyotisa.storyapp.databinding.ItemStoryBinding
import id.jyotisa.storyapp.helper.Utils.loadImage
import id.jyotisa.storyapp.model.Story

class StoryAdapter(private val callback: StoryCallback) :
    PagingDataAdapter<Story, StoryAdapter.UserViewHolder>(DIFF_CALLBACK) {
    private val listStory = ArrayList<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userBinding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = listStory.size

    inner class UserViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            with(binding) {
                username.text = story.name
                desc.text = story.description
                imgStory.loadImage(story.photoUrl)
                root.setOnClickListener { callback.onStoryClick(story) }
            }
        }
    }

    interface StoryCallback {
        fun onStoryClick(story: Story)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}