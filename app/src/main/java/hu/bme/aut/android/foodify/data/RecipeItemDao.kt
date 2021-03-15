package hu.bme.aut.android.foodify.data

import androidx.room.*

@Dao
interface RecipeItemDao {
    @Query("SELECT * FROM recipeitem")
    fun getAll(): List<RecipeItem>

    @Insert
    fun insert(recipeItems: RecipeItem): Long

    @Update
    fun update(recipeItem: RecipeItem)

    @Delete
    fun deleteItem(recipeItem: RecipeItem)
}