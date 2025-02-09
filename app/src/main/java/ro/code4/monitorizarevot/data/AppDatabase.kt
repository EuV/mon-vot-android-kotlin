package ro.code4.monitorizarevot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ro.code4.monitorizarevot.data.dao.BranchDetailsDao
import ro.code4.monitorizarevot.data.dao.CountyDao
import ro.code4.monitorizarevot.data.dao.FormsDao
import ro.code4.monitorizarevot.data.model.*
import ro.code4.monitorizarevot.data.model.answers.AnsweredQuestion
import ro.code4.monitorizarevot.data.model.answers.SelectedAnswer

@Database(
//    entities = [ResponseAnswer::class, BranchDetails::class, BranchQuestionAnswer::class, County::class, FormDetails::class, Section::class],
    entities = [County::class, BranchDetails::class, FormDetails::class, Section::class, Question::class, Answer::class, AnsweredQuestion::class, SelectedAnswer::class],
    version = 1
)
//@TypeConverters(DateConverter::class, ResponseAnswerConverter::class, BranchDetailsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countyDao(): CountyDao
    abstract fun branchDetailsDao(): BranchDetailsDao
    abstract fun formDetailsDao(): FormsDao

    companion object {
        @JvmStatic
        private lateinit var INSTANCE: AppDatabase

        private fun isInitialized(): Boolean = ::INSTANCE.isInitialized
        fun getDatabase(context: Context): AppDatabase {
            if (!isInitialized()) {
                synchronized(AppDatabase::class.java) {
                    if (!isInitialized()) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
