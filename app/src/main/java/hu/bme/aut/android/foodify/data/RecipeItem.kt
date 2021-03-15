package hu.bme.aut.android.foodify.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "recipeitem")
data class RecipeItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "category") val category: Category,

) {
    enum class Category {
        Breakfast, Lunch, Dinner, Dessert, Drink;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                return values().find{it.ordinal == ordinal}
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
}