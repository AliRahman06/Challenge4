package com.binar.challenge4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.challenge4.data.model.Student
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    @InternalCoroutinesApi
    companion object {
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase? {
            if (INSTANCE == null){
                synchronized(StudentDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java, "Student.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}