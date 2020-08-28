package com.ihariza.news.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryListConverter {

    @TypeConverter
    fun fromCategoryList(categories: List<String>?): String? {
        categories.let {
            val type = object : TypeToken<List<String?>?>() {}.type
            return Gson().toJson(categories, type)
        }
    }

    @TypeConverter
    fun toCategoryList(category: String?): List<String>? {
        category.let {
            val type = object : TypeToken<List<String?>?>() {}.type
            return Gson().fromJson<List<String>>(category, type)
        }
    }
}