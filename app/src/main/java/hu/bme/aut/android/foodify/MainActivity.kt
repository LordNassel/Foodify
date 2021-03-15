package hu.bme.aut.android.foodify

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.bme.aut.android.foodify.adapter.RecipeAdapter
import hu.bme.aut.android.foodify.data.RecipeItem
import hu.bme.aut.android.foodify.data.RecipeListDatabase
import hu.bme.aut.android.foodify.fragments.NewRecipeItemDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), RecipeAdapter.RecipeItemClickListener,
    NewRecipeItemDialogFragment.NewRecipeItemDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var database: RecipeListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        try{
            Thread.sleep(2000)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        setTheme(R.style.AppTheme)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener{
            NewRecipeItemDialogFragment().show(
                supportFragmentManager,
                NewRecipeItemDialogFragment.TAG
            )
        }

        database = Room.databaseBuilder(
            applicationContext,
            RecipeListDatabase::class.java,
            "recipe-list"
        ).build()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = RecipeAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.recipeItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onItemChanged(item: RecipeItem) {
        thread {
            database.recipeItemDao().update(item)
            Log.d("MainActivity", "RecipeItem update was successful")
        }
    }

    override fun onItemDeleted(item: RecipeItem){
        thread{
            database.recipeItemDao().deleteItem(item)
            Log.d("MainActivity", "RecipeItem delete was successful")
        }
    }

    override fun onRecipeItemCreated(newItem: RecipeItem) {
        thread {
            val newId = database.recipeItemDao().insert(newItem)
            val newRecipeItem = newItem.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addItem(newRecipeItem)
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.are_you_sure_want_to_exit)
            .setPositiveButton(R.string.ok) { dialogInterface, i -> onExit() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun onExit() {
        finish()
    }
}

