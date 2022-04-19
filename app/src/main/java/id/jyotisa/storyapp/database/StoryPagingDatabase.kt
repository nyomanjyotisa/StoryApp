package id.jyotisa.storyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.jyotisa.storyapp.model.Story

@Database(
    entities = [Story::class],
    version = 1,
    exportSchema = false
)
abstract class StoryPagingDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: StoryPagingDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryPagingDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryPagingDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}