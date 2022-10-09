package com.ay.proyectopetisosalbergue.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisosalbergue.Model.Productos;
import com.ay.proyectopetisosalbergue.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosHolder> {
    Context context;
    List<Productos> productosList;
    ItemClickListener onItemClickListener;

    public ProductosAdapter(List<Productos> productosList,Context context, ItemClickListener onItemClickListener) {
        this.context = context;
        this.productosList = productosList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ProductosAdapter.ProductosHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_productos,parent,false);
        return new ProductosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductosAdapter.ProductosHolder holder, int position) {
        Glide.with(context).load(String.valueOf(RUTA+productosList.get(position).getImgProducto())).into(holder.imgProducto);
        holder.tvnomPro.setText(String.valueOf(productosList.get(position).getProNombre()));
        holder.tvprecioReal.setText(String.valueOf(productosList.get(position).getProPrecioReal()));
        holder.tvprecioCollares.setText(String.valueOf(productosList.get(position).getProPrecioCollares()));
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(productosList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }
    public interface ItemClickListener{
        void onItemClick(Productos productos);
    }

    public class ProductosHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView tvnomPro,tvprecioReal, tvprecioCollares;

        public ProductosHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.img_producto);
            tvnomPro  =itemView.findViewById(R.id.tv_nombreProducto);
            tvprecioReal = itemView.findViewById(R.id.tv_precio_soles);
            tvprecioCollares = itemView.findViewById(R.id.tv_precio_collares);
        }
    }
}
