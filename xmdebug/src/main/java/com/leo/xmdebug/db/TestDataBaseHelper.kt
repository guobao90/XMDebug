package com.leo.litang.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TestDataBaseHelper : SQLiteOpenHelper {
    companion object {
        val TABLE_NAME = "testDb"
        private val obj = Any()
        private var sPostTagDataBaseHelper: TestDataBaseHelper? = null

        fun getInstance(context: Context): TestDataBaseHelper {
            if (sPostTagDataBaseHelper == null) {
                synchronized(obj) {
                    if (sPostTagDataBaseHelper == null) {
                        sPostTagDataBaseHelper = TestDataBaseHelper(context.applicationContext)
                    }
                }
            }
            return sPostTagDataBaseHelper!!
        }
    }

    constructor(context: Context) : super(context, "test.sqlite", null, 1)



    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            db.beginTransaction()
            db.execSQL("CREATE TABLE $TABLE_NAME ('id' INTEGER PRIMARY KEY NOT NULL ,'name' VARCHAR,'year' VARCHAR ,'month' VARCHAR,'day' VARCHAR,'hour' VARCHAR,'minute' VARCHAR ,'time' VARCHAR,'male' INTEGER)")
            db.setTransactionSuccessful()
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}