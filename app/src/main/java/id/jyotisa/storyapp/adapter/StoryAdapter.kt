package id.jyotisa.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.jyotisa.storyapp.databinding.ItemStoryBinding
import id.jyotisa.storyapp.helper.loadImage
import id.jyotisa.storyapp.model.Story

class StoryAdapter(private val callback: StoryAdapter.StoryCallback) :
    RecyclerView.Adapter<StoryAdapter.UserViewHolder>() {
    private val _data = ArrayList<Story>()

    fun setData(stories: ArrayList<Story>) {
        _data.clear()
        _data.addAll(stories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userBinding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(_data[position])
    }

    override fun getItemCount(): Int = _data.size

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
}