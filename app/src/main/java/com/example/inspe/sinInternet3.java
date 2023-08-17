package com.example.inspe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inspe.dininternet.datos;
import com.example.inspe.dininternet.datos1;
import com.example.inspe.dininternet.datos2;
import com.example.inspe.dininternet.datos3;
import com.example.inspe.dininternet.datos4;
import com.example.inspe.sininternetadaptador.adaptadorsi;
import com.example.inspe.sininternetadaptador.adaptadorsi1;
import com.example.inspe.sininternetadaptador.adaptadorsi2;
import com.example.inspe.sininternetadaptador.adaptadorsi3;
import com.example.inspe.sininternetadaptador.adaptadorsi4;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class sinInternet3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adaptadorsi notaAdapter;
    private List<datos> notasList = new ArrayList<>();


    private RecyclerView recyclerView1;
    private adaptadorsi1 notaAdapter1;
    private List<datos1> notasList1 = new ArrayList<>();
    private static final int CAMERA_REQUEST_CODE =101 ;
    private Bitmap capturedImage = null;



    private RecyclerView recyclerView2;
    private adaptadorsi2 notaAdapter2;
    private List<datos2> notasList2 = new ArrayList<>();
    private RecyclerView recyclerView3;
    private adaptadorsi3 notaAdapter3;
    private List<datos3> notasList3 = new ArrayList<>();
    private RecyclerView recyclerView4;
    private adaptadorsi4 notaAdapter4;
    private List<datos4> notasList4 = new ArrayList<>();
    TextView Frutas,slc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_internet2);
        String datos2 = getIntent().getStringExtra("dato");
        String datos1 = getIntent().getStringExtra("dato1");
        String valor1dato = datos1;

        String zona1 = getIntent().getStringExtra("zona1");
        String primera = zona1;

        String zona2 = getIntent().getStringExtra("zona2");
        String segunda = zona2;

        String zona3 = getIntent().getStringExtra("zona3");
        String tercera = zona3;

        String zona4 = getIntent().getStringExtra("zona4");
        String cuarta = zona4;

        Frutas=findViewById(R.id.Frutas);
        slc=findViewById(R.id.slc);
        Frutas.setVisibility(View.INVISIBLE); // Para hacerlo visible
        slc.setVisibility(View.INVISIBLE); // Para hacerlo visible

        recyclerView = findViewById(R.id.tiendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosDesdeSQLite();
        notaAdapter = new adaptadorsi(notasList, sinInternet3.this);
        recyclerView.setAdapter(notaAdapter);
////////////////////////////////////////////////////////////////////////////////////
        recyclerView1 = findViewById(R.id.formularios);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosDesdeSQLite1();
        notaAdapter1 = new adaptadorsi1(notasList1, sinInternet3.this);
        recyclerView1.setAdapter(notaAdapter1);

        ////////////////////////////////////////////////////////////////////////////////////
        recyclerView2 = findViewById(R.id.formulariostor);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosDesdeSQLite3();
        notaAdapter2 = new adaptadorsi2(notasList2, sinInternet3.this);
        recyclerView2.setAdapter(notaAdapter2);
////////////////////////////////////////////////////////////////////////////////////
        recyclerView3 = findViewById(R.id.formulariospesc);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosDesdeSQLite4();
        notaAdapter3 = new adaptadorsi3(notasList3, sinInternet3.this);
        recyclerView3.setAdapter(notaAdapter3);
////////////////////////////////////////////////////////////////////////////////////
        recyclerView4 = findViewById(R.id.formulariosCa);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        cargarDatosDesdeSQLite5();
        notaAdapter4 = new adaptadorsi4(notasList4, sinInternet3.this);
        recyclerView4.setAdapter(notaAdapter4);


    }

    private void cargarDatosDesdeSQLite1() {

        notasList1.add(new datos1( "Panadería", 21, "¿El establecimiento cuenta con instalaciones que evitan la contaminación de las materias primas y los productos?", 6 ));
        notasList1.add(new datos1( "Panadería", 22, "¿Las instalaciones del establecimiento, incluidos techos, puertas, paredes, pisos, baños, cisternas, tinacos u otros depósitos de agua; y mobiliario están limpias?", 6 ));
        notasList1.add(new datos1( "Panadería", 23, "¿Las instalaciones del establecimiento se encuentran en buenas condiciones de mantenimiento?", 6 ));
        notasList1.add(new datos1( "Panadería", 24, "¿Los pisos, paredes, techos  las uniones en las superficies de pisos o paredes recubiertas con materiales ", 6 ));
        notasList1.add(new datos1( "Panadería", 25, "¿Existe equipo en desuso, desperdicios, chatarra, o encharcamiento por drenaje insuficiente o inadecuado?", 6 ));
        notasList1.add(new datos1( "Panadería", 26, "¿Los equipos están instalados en forma tal que el espacio entre estos, la pared, el techo y el piso permite su limpieza y desinfección?", 6 ));
        notasList1.add(new datos1( "Panadería", 27, "¿El equipo, utensilios y materiales en contacto con materias primas y productos, son lisos, lavables, sin roturas y permiten su desinfección?", 6));
        notasList1.add(new datos1( "Panadería", 28, "¿El equipo, utensilios y materiales que se emplean en la producción o elaboración, son inocuos y resistentes a la corrosión?", 6 ));
        notasList1.add(new datos1( "Panadería", 29, "¿En los equipos de refrigeración o congelación se evita la acumulación de agua?", 6 ));
        notasList1.add(new datos1( "Panadería", 30, "¿Los equipos de refrigeración  congelación están provistos de termómetros o dispositivos para el registro de temperatura funcionando correctamente?", 6));
        notasList1.add(new datos1( "Panadería", 31, "¿Se cuenta con un termómetro con la escala apropiada para el monitoreo de temperaturas en los alimentos?", 6));
        notasList1.add(new datos1( "Panadería", 33, "¿La temperatura de los alimentos fríos se mantiene a 6º C o menos?", 6));
        notasList1.add(new datos1( "Panadería", 34, "¿La temperatura de los alimentos congelados se mantiene mínimo a -12º C?", 6));
    }

    private void cargarDatosDesdeSQLite3() {
        notasList2.add(new datos2( "Panadería", 35, "¿Cuenta con registros de monitoreo de temperaturas frías, congeladas y calientes?", 6));
        notasList2.add(new datos2( "Panadería", 36, "¿El drenaje cuenta con trampa contra olores, coladeras y o canaletas con rejillas, libres de basura, sin estancamiento y en buen estado; y en su caso trampas para grasa?", 6));
        notasList2.add(new datos2( "Panadería", 37, "¿Cuenta con un sistema de evacuación de efluentes o aguas residuales libres de reflujo, fugas, residuos, desechos y fauna nociva?", 6));
        notasList2.add(new datos2( "Panadería", 38, "¿La ventilación evita el calor, condensación de vapor, acumulación de humo y polvo?", 6));
        notasList2.add(new datos2( "Panadería", 39, "¿Las instalaciones de aire acondicionado no presentan goteos sobre las áreas donde las materias primas y productos están expuestos?", 6));
        notasList2.add(new datos2( "Panadería", 40, "¿La iluminación permite llevar a cabo la realización de las operaciones de manera higiénica?", 6));
        notasList2.add(new datos2( "Panadería", 41, "¿En áreas donde los productos se encuentren sin envasar, los focos y lámparas están protegidos o son de material que impide su astillamiento?", 6));
    }
    private void cargarDatosDesdeSQLite4() {

        notasList3.add(new datos3( "Panadería", 42, "¿Las condiciones de almacenamiento son adecuadas al tipo de materia prima y o producto que se maneja?", 6));
        notasList3.add(new datos3( "Panadería", 43, "¿Los agentes de limpieza, químicos y sustancias tóxicas, se encuentran almacenados en un espacio separado y delimitado de las áreas de almacenamiento y manipulación de materias primas y o producto?", 6));
        notasList3.add(new datos3( "Panadería", 44, "¿Los recipientes con agentes de limpieza, químicos y sustancias tóxicas se encuentran cerrados e identificados?", 6));
        notasList3.add(new datos3( "Panadería", 45, "¿Las materias primas  o productos se colocan en mesas, estibas, tarimas, anaqueles, entrepaños, estructura o cualquier superficie limpia que evite su contaminación?", 6));
        notasList3.add(new datos3( "Panadería", 46, "¿La colocación de materias primas y productos permite la circulación del aire?", 6));
        notasList3.add(new datos3( "Panadería", 47, "¿La estiba de los productos se realiza evitando el rompimiento y exudación de empaques o envolturas?", 6));
        notasList3.add(new datos3( "Panadería", 48, "¿Cuenta con un área específica para almacenar los implementos o utensilios de limpieza evitando la contaminación de materias primas, material de empaque y productos?", 6));
        notasList3.add(new datos3( "Panadería", 49, "¿Las materias primas y productos están identificados de tal manera que permite aplicar un sistema Primeras Entradas Primeras Salidas?", 6));
        notasList3.add(new datos3( "Panadería", 50, "¿Las materias primas y productos ostentan etiqueta en español?", 6));
        notasList3.add(new datos3( "Panadería", 51, "¿Los envases y recipientes en contacto directo con la materia prima y productos se almacenan protegidos de polvo, lluvia, fauna nociva y materia extraña?", 6));
        notasList3.add(new datos3( "Panadería", 52, "¿Cuenta con áreas específicas para almacenamiento de producto de devoluciones, producto rechazado y caduco?", 6));
    }
    private void cargarDatosDesdeSQLite5() {
        notasList4.add(new datos4( "Panadería", 53, "¿Se evita la contaminación cruzada entre la materia prima, producto en elaboración y producto terminado?", 6));
        notasList4.add(new datos4( "Panadería", 54, "¿Los materiales de empaque y envase de materias primas no son empleados para fines diferentes a los que fueron destinados originalmente, a menos que se eliminen las etiquetas, leyendas y se habiliten para el nuevo uso en forma correcta?", 6));
        notasList4.add(new datos4( "Panadería", 55, "¿Los recipientes y envases vacíos que contuvieron medicamentos, plaguicidas, agentes de limpieza, agentes de desinfección o cualquier sustancia toxica son reutilizados?", 6));
        notasList4.add(new datos4( "Panadería", 56, "¿El equipo y utensilios se encuentran en buenas condiciones de funcionamiento?", 6));
        notasList4.add(new datos4( "Panadería", 57, "¿El equipo y utensilios están limpios y desinfectados?", 6));
        notasList4.add(new datos4( "Panadería", 58, "¿Cuenta con programas de limpieza y desinfección de las áreas, equipos y utensilios?", 6));
        notasList4.add(new datos4( "Panadería", 59, "¿Son de grado alimenticio los lubricantes utilizados en equipos o partes que están en contacto directo con materias primas, envase primario, producto en proceso o terminado sin envasar?", 6));
        notasList4.add(new datos4( "Panadería", 60, "¿Al lubricar los equipos se evita la contaminación de los productos en proceso?", 6));
        notasList4.add(new datos4( "Panadería", 61, "¿El personal se presenta aseado al área de trabajo, con ropa y calzado limpios e íntegros?", 6));
        notasList4.add(new datos4( "Panadería", 62, "¿El personal que trabaja en producción o elaboración o accesa a las áreas, presenta signos como: tos frecuente, secreción nasal, diarrea, vómito, fiebre, ictericia o heridas en áreas corporales que entran en contacto directo con las materias primas o productos?", 6));
        notasList4.add(new datos4( "Panadería", 63, "¿El personal de las áreas de producción o elaboración, o que se encuentra en contacto directo con materias primas, envases primarios o productos, se lava las manos al inicio de las labores y cada vez que sea necesario?", 6));
        notasList4.add(new datos4( "Panadería", 64, "¿Utilizan guantes para el manejo de alimentos listos para su consumo?", 6));
        notasList4.add(new datos4( "Panadería", 65, "¿La ropa u objetos personales se guardan fuera de las áreas de trabajo, producción o elaboración?", 6));
        notasList4.add(new datos4( "Panadería", 66, "¿En las áreas donde el personal entra en contacto directo con materias primas, productos y envases primarios no existe evidencia de que come, bebe, fuma, masca, escupe, tose y o estornuda?", 6));
        notasList4.add(new datos4( "Panadería", 67, "¿El personal que elabora o está en contacto con alimentos o bebidas tiene el cabello corto o recogido, uñas recortadas y sin esmalte, no usa joyas, utiliza guantes o protección de plástico cuando manipula dinero, utiliza protección que cubre totalmente cabello, bigote y patillas?", 6));
    }
    // Método para cargar datos de la base de datos (simulado en este ejemplo)
    private void cargarDatosDesdeSQLite() {
// Simulación: Agregar algunas notas de ejemplo
        notasList.add(new datos( "Panadería", 1, "Lavado de manos", 5 ));
        notasList.add(new datos( "Panadería", 10, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 11, "Descongelación de alimentos", 5 ));
        notasList.add(new datos( "Panadería", 12, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 13, "Método para tomar temperatura en alimentos", 5 ));
        notasList.add(new datos( "Panadería", 14, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 15, "Ajuste de termómetro", 5 ));
        notasList.add(new datos( "Panadería", 16, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 19, "Determinación de cloro residual en cisterna", 5 ));
        notasList.add(new datos( "Panadería", 2, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 20, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 3, "Lavado y desinfección de utensilios", 5 ));
        notasList.add(new datos( "Panadería", 4, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 5, "Lavado y desinfección de frutas y verduras", 5 ));
        notasList.add(new datos( "Panadería", 6, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 7, "Lavado, desinfección y blanqueamiento de tablas de corte", 5 ));
        notasList.add(new datos( "Panadería", 8, "¿Cuentan con los materiales necesarios?", 5 ));
        notasList.add(new datos( "Panadería", 9, "Enfriamiento rápido de alimentos", 5 ));
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lector cancelado", Toast.LENGTH_SHORT).show();
            }
            String qrContent = result.getContents();

            if ("-2".equals(qrContent)) {
                startActivity(new Intent(sinInternet3.this,sinInternet.class));

            } else {
                Toast.makeText(this, "codigo incorrecto", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            capturedImage = (Bitmap) data.getExtras().get("data");

        }
    }

}
