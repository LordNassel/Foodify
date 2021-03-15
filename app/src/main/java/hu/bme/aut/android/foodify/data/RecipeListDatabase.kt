package hu.bme.aut.android.foodify.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeItem::class], version = 1)
@TypeConverters(value = [RecipeItem.Category::class])
abstract class RecipeListDatabase : RoomDatabase() {
    abstract fun recipeItemDao(): RecipeItemDao
}
