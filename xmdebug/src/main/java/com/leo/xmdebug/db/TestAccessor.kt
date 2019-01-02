package com.leo.litang.db

import android.content.ContentValues
import android.content.Context

class TestAccessor {
    private var mPostTagDataBaseHelper: TestDataBaseHelper

    constructor(ctx: Context) {
        mPostTagDataBaseHelper = TestDataBaseHelper.getInstance(ctx)
    }

    fun insert() {
        val db = mPostTagDataBaseHelper.writableDatabase
        val values = ContentValues()
        values.put("name", "test")
        values.put("year", "2019")
        values.put("month", "1")
        values.put("day", "1")
        db.insert(TestDataBaseHelper.TABLE_NAME, null, values)
    }
}