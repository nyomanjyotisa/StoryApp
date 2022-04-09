package id.jyotisa.storyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.jyotisa.storyapp.model.Story

@Database(entities = [Story::class], version = 1, exportSchema = false)

abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoryDatabase::class.java, "db_storyapp"
                    )
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}