package com.example.inspe.adaptador

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inspe.R
import com.example.inspe.datos.datos1
import com.example.inspe.datos.formucode
import com.google.firebase.database.FirebaseDatabase


class adaptadrformucode(private val formucode1: ArrayList<formucode>) : RecyclerView.Adapter<adaptadrformucode.Myholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.datos2,parent,false)
        return Myholder(itemView)
    }
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val formucode=formucode1[position]

        holder.departamento.text = formucode.pregunta
         //val valor:Int? = formulariof1.valor
        //val enlace: String? = formulariof1.enlace
        //val posision:Int? = formulariof1.posision
        val databaseRef = FirebaseDatabase.getInstance().reference.child("/usuario/001")

        holder.si.setOnClickListener {
            // Cambiar color del botón a blanco
            holder.si.setBackgroundColor(Color.WHITE)

            // Guardar respuesta en Firebase Realtime Database
            val preguntaId = formucode1[position].pregunta // Obtén el ID de la pregunta (si tienes uno)
            val respuesta = "si" // Opcionalmente, puedes guardar el valor "si" o "no" como un campo de respuesta en la base de datos

            if (preguntaId != null) {
                databaseRef.child("respuestas").child(preguntaId).setValue(respuesta)
            }
        }

        holder.no.setOnClickListener {
            // Cambiar color del botón a blanco
            holder.no.setBackgroundColor(Color.WHITE)

            // Guardar respuesta en Firebase Realtime Database
            val preguntaId = formucode1[position].pregunta // Obtén el ID de la pregunta (si tienes uno)
            val respuesta = "no" // Opcionalmente, puedes guardar el valor "si" o "no" como un campo de respuesta en la base de datos

            if (preguntaId != null) {
                databaseRef.child("respuestas").child(preguntaId).setValue(respuesta)
            }
        }

    }



    override fun getItemCount(): Int {
        return  formucode1.size
    }
    class Myholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val departamento: TextView =itemView.findViewById(R.id.departamento)
        val si: Button =itemView.findViewById(R.id.respuestasi)
        val no: Button =itemView.findViewById(R.id.respuestano)

    }
}