package com.vitocuaderno.maj.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vitocuaderno.maj.data.local.HomeContentDao
import com.vitocuaderno.maj.data.model.HomeContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(HomeContent::class), version = 1, exportSchema = false)
public abstract class AppRoomDatabase : RoomDatabase(), HomeContentDao {
    abstract fun homeContentDao(): HomeContentDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope
        ) : AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.homeContentDao())
                }
            }
        }

        suspend fun populateDatabase(homeContentDao: HomeContentDao) {
            var homeContent = HomeContent(1)
//            homeContentDao.insert()
        }
    }
}