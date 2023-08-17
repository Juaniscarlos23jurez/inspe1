package com.example.inspe.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.inspe.MainActivity
import com.example.inspe.R
import com.example.inspe.datos.datos2
import com.google.firebase.database.ValueEventListener


class datosadaptador1(private val pedido2: ArrayList<datos2>) : RecyclerView.Adapter<datosadaptador1.MyHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.datos1, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val datos2 = pedido2[position]
        holder.boton.text = datos2.formulario

        holder.boton.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return pedido2.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boton: Button = itemView.findViewById(R.id.textView3)
    }
}
