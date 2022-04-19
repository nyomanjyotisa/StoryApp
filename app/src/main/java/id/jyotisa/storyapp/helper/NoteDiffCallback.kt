package id.jyotisa.storyapp.helper

import androidx.recyclerview.widget.DiffUtil
import id.jyotisa.storyapp.model.Story

class NoteDiffCallback(private val mOldStoryList: List<Story>, private val mNewStoryList: List<Story>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldStoryList.size
    }

    override fun getNewListSize(): Int {
        return mNewStoryList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldStoryList[oldItemPosition].id == mNewStoryList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldStoryList[oldItemPosition]
        val newEmployee = mNewStoryList[newItemPosition]
        return oldEmployee.name == newEmployee.name && oldEmployee.description == newEmployee.description
    }
}