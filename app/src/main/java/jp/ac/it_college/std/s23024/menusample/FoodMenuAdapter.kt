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

    // RecyclerView が表示時に使う1件分のデータを表示するためのビューを管理するクラス
    // 必ず RecyclerView.ViewHolder を継承する必要がある。
    // ViewBinding と役割が似ているので、 可能な限り ViewBingding に乗っかった形で実装したパターン。
    class ViewHolder(private  val binding: SetMealRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodMenu, callback: (item: FoodMenu) -> Unit) {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.root.setOnClickListener{
                // アイテム(定食)がタップされたらここが呼ばれる
                callback(item)
            }
        }
    }

    // ViewHolder のインスタンスを作る必要が出てきたときに呼ばれるメソッド。
    // 一度作られた ViewHolder は使い回すので、 一定回数呼ばれたらそれ以上は呼ばれない。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // parent(ConstraintLayout)の中に詰まっているアプリ情報を基に、layoutInflater を取り出す
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SetMealRowBinding.inflate(layoutInflater, parent,false)
        return ViewHolder(binding)
    }

    // 要素数を返すメソッド
    override fun getItemCount(): Int = data.size

    // 用意が整った ViewHolder へ実際にデータを反映させるタイミングで呼ばれるメソッド
    // パラメータの position に何番目のデータを表示するべきかの値が渡される。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onItemSelected)
    }
}