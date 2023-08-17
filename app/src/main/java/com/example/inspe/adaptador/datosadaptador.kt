package com.example.inspe.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datos.formulariof1
import com.example.inspe.R
import com.example.inspe.datos.datos1


class datosadaptador(private val pedido1: ArrayList<datos1>) : RecyclerView.Adapter<datosadaptador.Myholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.datos,parent,false)
        return Myholder(itemView)
    }
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val datos1=pedido1[position]

        holder.textView3.text = datos1.contacto
        holder.textView4.text  = datos1.tien
        //val valor:Int? = formulariof1.valor
        //val enlace: String? = formulariof1.enlace
        //val posision:Int? = formulariof1.posision

    }



    override fun getItemCount(): Int {
        return  pedido1.size
    }
    class Myholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView3: TextView =itemView.findViewById(R.id.textView3)
        val textView4: TextView =itemView.findViewById(R.id.textView4)

    }
}