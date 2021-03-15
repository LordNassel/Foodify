package hu.bme.aut.android.foodify.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.foodify.R
import hu.bme.aut.android.foodify.data.RecipeItem

class NewRecipeItemDialogFragment : DialogFragment() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var instructionsEditText: EditText
    private lateinit var ingredientsEditText: EditText

    interface NewRecipeItemDialogListener {
        fun onRecipeItemCreated(newItem: RecipeItem)
    }

    private lateinit var listener: NewRecipeItemDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewRecipeItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewRecipeItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_recipe_item)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid()) {
                    listener.onRecipeItemCreated(getRecipeItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewRecipeItemDialogFragment"
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_recipe_item, null)
        nameEditText = contentView.findViewById(R.id.RecipeItemNameEditText)
        descriptionEditText = contentView.findViewById(R.id.RecipeItemDescriptionEditText)
        instructionsEditText = contentView.findViewById(R.id.RecipeItemInstructionsEditText)
        ingredientsEditText = contentView.findViewById(R.id.RecipeItemIngredientsEditText)
        categorySpinner = contentView.findViewById(R.id.RecipeItemCategorySpinner)
        categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )
        return contentView
    }

    private fun isValid() = nameEditText.text.isNotEmpty()

    private fun getRecipeItem() = RecipeItem(
        id = null,
        name = nameEditText.text.toString(),
        description = descriptionEditText.text.toString(),
        category = RecipeItem.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: RecipeItem.Category.Breakfast,
        instructions = instructionsEditText.text.toString(),
        ingredients = ingredientsEditText.text.toString(),
    )
}