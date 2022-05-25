package com.ay.proyectopetisos.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisos.Model.Productos;
import com.ay.proyectopetisos.R;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.List;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosHolder> {
    Context context;
    List<Productos> productosList;
    Bitmap bitmapimg;
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
        new GetImageFromURL(holder.imgProducto).execute(String.valueOf(RUTA+productosList.get(position).getImgProducto()));
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

    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap> {
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
