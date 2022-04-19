package id.jyotisa.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.model.Story
import kotlinx.coroutines.flow.Flow

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1

        const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVptUVFrek5Nd1ljZW9EMEMiLCJpYXQiOjE2NTAzNDQxNTl9.lubZzUc4Nmri5ruC7zGmpDmiJ9k-4AjEyVG7iovziBE"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {

            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStoriesPaging(TOKEN, page, params.loadSize)

            LoadResult.Page(
                data = responseData.body()?.listStory ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}