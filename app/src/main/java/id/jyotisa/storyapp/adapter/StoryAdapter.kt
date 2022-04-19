package id.jyotisa.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.jyotisa.storyapp.databinding.ItemStoryBinding
import id.jyotisa.storyapp.helper.NoteDiffCallback
import id.jyotisa.storyapp.helper.Utils.loadImage
import id.jyotisa.storyapp.model.Story

class StoryAdapter(private val callback: StoryCallback) :
    RecyclerView.Adapter<StoryAdapter.UserViewHolder>() {
    private val listStory = ArrayList<Story>()

    fun setData(stories: ArrayList<Story>) {
        val diffCallback = NoteDiffCallback(this.listStory, stories)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listStory.clear()
        this.listStory.addAll(stories)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userBinding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listStory[position])
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
}