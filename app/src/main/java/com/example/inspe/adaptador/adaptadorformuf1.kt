package com.example.admin.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.datos.formulariof1
import com.example.inspe.R
import com.google.firebase.database.FirebaseDatabase

class adaptadorformuf1(private val pedido: ArrayList<formulariof1>) : RecyclerView.Adapter<adaptadorformuf1.Myholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.formulario,parent,false)
        return Myholder(itemView)
    }
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val formulariof1=pedido[position]
        holder.pregunta.hint = formulariof1.pregunta
        //val enlace: String? = formulariof1.enlace1
        val valor:Int? = formulariof1.valor
        val enlace: String? = formulariof1.enlace
        val posision:Int? = formulariof1.posision

    }



    override fun getItemCount(): Int {
        return  pedido.size
    }
    class Myholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val pregunta: TextView =itemView.findViewById(R.id.pregunta)
      }
}
