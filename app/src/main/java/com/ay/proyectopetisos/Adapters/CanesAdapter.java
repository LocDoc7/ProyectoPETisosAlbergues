package com.ay.proyectopetisos.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.Model.Canes;
import com.ay.proyectopetisos.Model.CategoriaCanes;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;
import com.ay.proyectopetisos.TabItems.Inicio.RegistrarVisitaFragment;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.util.List;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class CanesAdapter extends RecyclerView.Adapter<CanesAdapter.CanesHolder> {
    List<Canes> canesList;
    Context context;
    //Bitmap bitmapimg;
    onClick onClick;
    Dialog mydialog;

    public CanesAdapter(List<Canes> canesList, Context context, onClick onClick) {
        this.canesList = canesList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @NotNull
    @Override
    public CanesAdapter.CanesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_perros_albergue,parent,false);
        return new CanesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CanesAdapter.CanesHolder holder, int position) {
        holder.canNombre.setText(String.valueOf(canesList.get(position).getNombreCanino()));
        //holder.canHistoria.setText(String.valueOf(canesList.get(position).getHistoriaCanino()));
        holder.canSexo.setText(String.valueOf(canesList.get(position).getSexoCanino()));
        holder.canEdad.setText(String.valueOf(canesList.get(position).getEdadCanino()));
        holder.canRaza.setText(String.valueOf(canesList.get(position).getRazaCanino()));
        holder.canEstado.setText(String.valueOf(canesList.get(position).getEstadoCanino()));
        Glide.with(context).load(String.valueOf(RUTA+canesList.get(position).getImgCanino())).into(holder.imgCanes);
        //new GetImageFromURL(holder.imgCanes).execute(String.valueOf(RUTA+canesList.get(position).getImgCanino()));

        holder.imgCanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog = new Dialog(context);
                mydialog.setContentView(R.layout.dialog_canino);
                TextView dialog_name = (TextView) mydialog.findViewById(R.id.tvNombreCaninoDetalle);
                TextView dialog_historia = (TextView) mydialog.findViewById(R.id.tvHistoriaCaninoDetalle);
                ImageView dialog_imag = (ImageView) mydialog.findViewById(R.id.img_canino_detalle);
                dialog_name.setText(String.valueOf(canesList.get(position).getNombreCanino()));
                dialog_historia.setText(String.valueOf(canesList.get(position).getHistoriaCanino()));
                Glide.with(context).load(String.valueOf(RUTA+canesList.get(position).getImgCanino())).into(dialog_imag);
                //new GetImageFromURL(dialog_imag).execute(String.valueOf(RUTA+canesList.get(position).getImgCanino()));
                mydialog.show();
            }
        });
        holder.btnRegistrar.setOnClickListener(view -> {
            onClick.onClick(canesList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return canesList.size();
    }

    /*@Override
    public void onClick(View view) {
        Toast.makeText(context, "Registro", Toast.LENGTH_SHORT).show();
    }*/

    public interface onClick{
        void onClick(Canes canes);
    }

    public class CanesHolder extends RecyclerView.ViewHolder {
        ImageView imgCanes;
        TextView canNombre,canHistoria,canSexo,canEdad,canRaza,canEstado;
        Button btnRegistrar;

        public CanesHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCanes = itemView.findViewById(R.id.img_canino_lista);
            canNombre = itemView.findViewById(R.id.tvNombreCanino);
            //canHistoria = itemView.findViewById(R.id.tvHistoriaCanino);
            btnRegistrar = itemView.findViewById(R.id.btn_registar_visita_canino);
            canSexo = itemView.findViewById(R.id.tv_sexo_canino);
            canEdad = itemView.findViewById(R.id.tv_edad_canino);
            canRaza = itemView.findViewById(R.id.tv_raza_canino);
            canEstado = itemView.findViewById(R.id.tv_estado_canino);

        }
    }
    /*public class GetImageFromURL extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;
        public GetImageFromURL(ImageView imgv){
            this.imageView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmapimg = null;
            try {
                InputStream ist = new java.net.URL(urldisplay).openStream();
                bitmapimg = BitmapFactory.decodeStream(ist);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return bitmapimg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }*/
}
