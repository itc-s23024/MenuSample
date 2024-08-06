package jp.ac.it_college.std.s23024.menusample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23024.menusample.databinding.SetMealRowBinding

// RecyclerView が扱うデータを管理するクラス。
// 必ず RecyclerView.Adapter を継承する必要がある。
class FoodMenuAdapter(
    private val data: List<FoodMenu>,
    private val onItemSelected: (item: FoodMenu) -> Unit
) : RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {

    var  currentItem: FoodMenu? = null
        private set

    // RecyclerView が表示時に使う1件分のデータを表示するためのビューを管理するクラス
    // 必ず RecyclerView.ViewHolder を継承する必要がある。
    // ViewBinding と役割が似ているので、 可能な限り ViewBingding に乗っかった形で実装したパターン。
    class ViewHolder(private  val binding: SetMealRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onItemClick: (item: FoodMenu) -> Unit = {}
        var onItemLongClick: (item: FoodMenu) -> Unit = {}

        fun bind(item: FoodMenu) {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.root.setOnClickListener{
                // アダプタにコールバック
                onItemClick(item)
            }

            binding.root.setOnLongClickListener {
                // アダプタにコールバック
                onItemLongClick(item)
                // ここで　true を返してイベントが完結すると、
                // アクティビティ側でコンテキストメニューが出ません。
                false
            }
        }
    }

    // ViewHolder のインスタンスを作る必要が出てきたときに呼ばれるメソッド。
    // 一度作られた ViewHolder は使い回すので、 一定回数呼ばれたらそれ以上は呼ばれない。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // parent(ConstraintLayout)の中に詰まっているアプリ情報を基に、layoutInflater を取り出す
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SetMealRowBinding.inflate(inflater,  parent, false)).apply {
            onItemClick = onItemSelected
            onItemLongClick = { item ->
                currentItem = item
            }
        }
    }

    // 要素数を返すメソッド
    override fun getItemCount(): Int = data.size

    // 用意が整った ViewHolder へ実際にデータを反映させるタイミングで呼ばれるメソッド
    // パラメータの position に何番目のデータを表示するべきかの値が渡される。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}