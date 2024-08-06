package jp.ac.it_college.std.s23024.menusample

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23024.menusample.databinding.ActivityMenuThanksBinding

class MenuThanksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuThanksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuThanksBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // MainActivity からデータを受け取る
        val menuName = intent.getStringExtra("menuName")
        val menuPrice = intent.getIntExtra("menuPrice", 0)

        // ビューの初期化
        binding.apply {
            //ツールバー
            setSupportActionBar(toolbar2)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // ビューに定食情報をセットする
            binding.tvMenuName.text = menuName
            binding.tvMenuPrice.text = menuPrice.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
