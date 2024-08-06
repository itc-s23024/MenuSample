package jp.ac.it_college.std.s23024.menusample

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23024.menusample.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var setMealList: List<FoodMenu>
    private lateinit var curryList: List<FoodMenu>
    private val foodMenuList = mutableListOf<FoodMenu>()
    private val adapter = FoodMenuAdapter(foodMenuList, ::order)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 定食メニューのデータを読み込んでリストにしてプロパティに保存しておく
        setMealList = Json.decodeFromString(
            resources.openRawResource(R.raw.set_meal).reader().readText()
        )
        // カレーメニューのデータを読み込んでリストにしてプロパティに保存しておく
        curryList = Json.decodeFromString(
            resources.openRawResource(R.raw.curry).reader().readText()
        )

        // デフォルトでは定食リストを出したいので
        foodMenuList.addAll(setMealList)


        // ToolBar を アクションバーとして使ってもらうお願い
        setSupportActionBar(binding.toolbar)

        // setup RecyclerView
        binding.lvMenu.apply {
            val manager = LinearLayoutManager(this@MainActivity)
            layoutManager = manager
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, manager.orientation)
            )
            //　アダプタ
            adapter = this@MainActivity.adapter

            // RecyclerView でコンテキストメニューを有効化する
            registerForContextMenu(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuListOptionTeishoku -> {
                // 定食に差し替え
                binding.lvMenu.adapter?.let { adapter ->
                    // 削除と通知
                    val beforeCount = adapter.itemCount
                    foodMenuList.clear()
                    adapter.notifyItemRangeRemoved(0, beforeCount)
                    // 追加と通知adapter
                    foodMenuList.addAll(setMealList)
                    adapter.notifyItemRangeInserted(0, setMealList.size)
                }
                true
            }
            R.id.menuListOptionCurry -> {
                //カレーに差し替え
                binding.lvMenu.adapter?.let { adapter ->
                    //削除と通知
                    val beforeCount = adapter.itemCount
                    foodMenuList.clear()
                    adapter.notifyItemRangeRemoved(0, beforeCount)
                    //追加と通知
                    foodMenuList.addAll(curryList)
                    adapter.notifyItemRangeInserted(0, curryList.size)
                }
                true
            }
           else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        menu?.setHeaderTitle(R.string.menu_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuListContextDesc -> {
                // 「詳細を表示」メニューが選ばれた
                adapter.currentItem?.let(::showDescription)
                true
            }

            R.id.menuListContextOrder -> {
                // 「ご注文」メニューが選ばれた
                adapter.currentItem?.let(::order)
                                  //.let { order(it) }
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
    private fun order(item: FoodMenu) {
        val menuName = item.name
        val menuPrice = item.price
        // Intentオブジェクトを作る
        val intent2MenuThanks = Intent(
            this@MainActivity,
            MenuThanksActivity::class.java
        )
        intent2MenuThanks.putExtra("menuName", menuName)
        intent2MenuThanks.putExtra("menuPrice", menuPrice)

        // 注文完了画面を起動
        startActivity(intent2MenuThanks)
    }
    private fun showDescription(item: FoodMenu) {
       Toast.makeText(this, item.desc, Toast.LENGTH_LONG).show()
    }
}
