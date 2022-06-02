package com.ay.proyectopetisos.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.ay.proyectopetisos.Model.CategoriaCanes;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.util.List;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class CategoriaCanesAdapter extends RecyclerView.Adapter<CategoriaCanesAdapter.CategoriaCanesHolder>{
    List<CategoriaCanes> categoriaCanesList;
    Context context;
    ItemClickListener onItemClickListener;

    public CategoriaCanesAdapter(List<CategoriaCanes> categoriaCanesList, Context context, ItemClickListener onItemClickListener) {
        this.categoriaCanesList = categoriaCanesList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public CategoriaCanesAdapter.CategoriaCanesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_categoria_perros,parent,false);
        return new CategoriaCanesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoriaCanesAdapter.CategoriaCanesHolder holder, int position) {
        holder.tvnombreCategoria.setText(String.valueOf(categoriaCanesList.get(position).getDescCategoria()));
        holder.tvrangoCategoria.setText(String.valueOf(categoriaCanesList.get(position).getRangoCategoria()));
        holder.tvcantCategoria.setText(String.valueOf(categoriaCanesList.get(position).getCantidadCanesCategoria()));
        Glide.with(context).load(String.valueOf(RUTA+categoriaCanesList.get(position).getImgCategoria())).into(holder.imgCategoria);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(categoriaCanesList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoriaCanesList.size();
    }

    public class CategoriaCanesHolder extends RecyclerView.ViewHolder {
        TextView tvnombreCategoria,tvrangoCategoria,tvcantCategoria;
        ImageView imgCategoria;

        public CategoriaCanesHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvnombreCategoria = itemView.findViewById(R.id.tvNombreCategoriaCanino);
            tvrangoCategoria = itemView.findViewById(R.id.tvDescCategoriaCanino);
            tvcantCategoria = itemView.findViewById(R.id.tv_num_animales_categoria);
            imgCategoria = itemView.findViewById(R.id.img_categoria_canino);
        }
    }
    public interface ItemClickListener{
        void onItemClick(CategoriaCanes categoriaCanes);
    }

}
