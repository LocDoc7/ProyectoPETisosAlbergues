package com.ay.proyectopetisosalbergue.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ay.proyectopetisosalbergue.Model.CategoriaCanes;
import com.ay.proyectopetisosalbergue.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

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
