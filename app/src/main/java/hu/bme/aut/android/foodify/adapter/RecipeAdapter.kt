package hu.bme.aut.android.foodify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.foodify.R
import hu.bme.aut.android.foodify.data.RecipeItem

class RecipeAdapter(private val listener: RecipeItemClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val items = mutableListOf<RecipeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recipe_list, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description
        holder.instructionsTextView.text = item.instructions
        holder.ingredientsTextView.text = item.ingredients
        //holder.categoryTextView.text = item.category.name
        holder.iconImageView.setImageResource(getImageResource(item.category))
        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface RecipeItemClickListener {
        fun onItemChanged(item: RecipeItem)
        fun onItemDeleted(item: RecipeItem)
        /*fun onItemSee(item: RecipeItem)*/
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView
        val nameTextView: TextView
        val descriptionTextView: TextView
        val instructionsTextView: TextView
        val ingredientsTextView: TextView
        //val categoryTextView: TextView
        val editButton: ImageButton
        val removeButton: ImageButton
        val seeButton: ImageButton

        var item: RecipeItem? = null

        init {
            iconImageView = itemView.findViewById(R.id.RecipeItemIconImageView)
            nameTextView = itemView.findViewById(R.id.RecipeItemNameTextView)
            descriptionTextView = itemView.findViewById(R.id.RecipeItemDescriptionTextView)
            instructionsTextView = itemView.findViewById(R.id.RecipeItemInstructionsTextView)
            ingredientsTextView = itemView.findViewById(R.id.RecipeItemIngredientsTextView)
            //categoryTextView = itemView.findViewById(R.id.RecipeItemCategoryTextView)
            editButton = itemView.findViewById(R.id.RecipeItemEditButton)
            removeButton = itemView.findViewById(R.id.RecipeItemRemoveButton)
            seeButton = itemView.findViewById(R.id.RecipeItemSeeButton)

            removeButton.setOnClickListener {
                if (item != null) {
                    items.remove(item)
                    listener.onItemDeleted(item!!)
                    notifyDataSetChanged()
                    Toast.makeText(
                        removeButton.context,
                        item!!.name.toString() + " deleted",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            editButton.setOnClickListener {
                if (item != null) {
                    listener.onItemChanged(item!!)
                    notifyDataSetChanged()
                    Toast.makeText(
                        removeButton.context,
                        item!!.name.toString() + " changed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            seeButton.setOnClickListener {
                if (item != null) {
                    //listener.onItemSee(item!!)
                    Toast.makeText(
                        seeButton.context,
                        item!!.name.toString() + " visited",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

        @DrawableRes
        private fun getImageResource(category: RecipeItem.Category) = when (category) {
            RecipeItem.Category.Breakfast -> R.drawable.ic_breakfast_48dp
            RecipeItem.Category.Lunch -> R.drawable.ic_lunch_48dp
            RecipeItem.Category.Dinner -> R.drawable.ic_dinner_48dp
            RecipeItem.Category.Dessert -> R.drawable.ic_dessert_48dp
            RecipeItem.Category.Drink -> R.drawable.ic_drinks_48dp
        }

        fun addItem(item: RecipeItem) {
            items.add(item)
            notifyItemInserted(items.size - 1)
        }

        fun update(shoppingItems: List<RecipeItem>) {
            items.clear()
            items.addAll(shoppingItems)
            notifyDataSetChanged()
        }
}



