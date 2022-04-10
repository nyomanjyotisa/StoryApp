package id.jyotisa.storyapp.database


import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.jyotisa.storyapp.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    fun insert(story: Story)

    @Query("SELECT * FROM stories")
    fun getAll(): Cursor

    @Query("SELECT COUNT(*) FROM stories")
    fun getSum(): Int

    @Query("DELETE FROM stories")
    fun delete(): Int
}