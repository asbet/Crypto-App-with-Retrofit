package com.betulas.retrofitkotlincryptoapp.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betulas.retrofitkotlincryptoapp.databinding.RecyclerRowBinding
import com.betulas.retrofitkotlincryptoapp.model.CryptoModel
import com.betulas.retrofitkotlincryptoapp.views.MainActivity

class CryptoAdapter(private val cryptoList:ArrayList<CryptoModel>, private val listener: Listener):RecyclerView.Adapter<CryptoAdapter.cryptoHolder>() {
    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }
    private val colors:Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#AA7EC5","#7EC5C5","#a1fb93","#0d9de3","#ffe48f","#C5B37E","#C57E95")


    class cryptoHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cryptoHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return cryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: cryptoHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position %12]))
        holder.binding.textName.text=cryptoList.get(position).currency
        holder.binding.textPrice.text=cryptoList.get(position).price
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}