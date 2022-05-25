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
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.CategoriaAnimalesAlbergueFragment;
import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.util.List;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class AlberguesAdapter extends RecyclerView.Adapter<AlberguesAdapter.AlberguesHolder> {

    List<Albergues> listaAlbergues;
    Context context;
    Bitmap bitmapimg;
    ItemClickListener onItemClickListener;


    public AlberguesAdapter(List<Albergues> listaAlbergues,Context context, ItemClickListener onItemClickListener) {
        this.listaAlbergues = listaAlbergues;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public AlberguesAdapter.AlberguesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_albergues,parent,false);
        return new AlberguesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlberguesAdapter.AlberguesHolder holder, int position) {
        holder.tv_nombre_albergue.setText(String.valueOf(listaAlbergues.get(position).getNombreAlbergue()));
        holder.tv_cant_perritos.setText(String.valueOf(listaAlbergues.get(position).getCantPerritosAlbergue()));
        holder.tv_ubicacion.setText(String.valueOf(listaAlbergues.get(position).getUbiAlbergue()));
        holder.tv_celular.setText(String.valueOf(listaAlbergues.get(position).getCellAlbergue()));
        new GetImageFromURL(holder.img_albergue).execute(String.valueOf(RUTA+listaAlbergues.get(position).getImgAlbergue()));
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(listaAlbergues.get(position));
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new CategoriaAnimalesAlbergueFragment();
                Bundle bundle = new Bundle();
                int idAlbergue = listaAlbergues.get(position).getIdAlbergue();
                String nombreAlbergue = String.valueOf(holder.tv_nombre_albergue.getText());
                bundle.putInt("idAlbrgue",idAlbergue);
                bundle.putString("nombreAlbergue",nombreAlbergue);
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.root_frame,fragment).addToBackStack(null).commit();
                //notifyDataSetChanged();

                //Navigation.findNavController(view).navigate(R.id.nav_detalle_tarjeta_imagen,bundle);

                //Navigation.findNavController(view).navigate(R.id.nav_detalle_tarjeta,bundle);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listaAlbergues.size();
    }

    public class AlberguesHolder extends RecyclerView.ViewHolder {
        ImageView img_albergue;
        TextView tv_nombre_albergue,tv_cant_perritos,tv_ubicacion,tv_celular;


        public AlberguesHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img_albergue = itemView.findViewById(R.id.imgAlbergueList);
            tv_nombre_albergue = itemView.findViewById(R.id.tvNombreAlbergue);
            tv_cant_perritos = itemView.findViewById(R.id.tv_num_animales);
            tv_ubicacion = itemView.findViewById(R.id.tv_ubicacion_albergue);
            tv_celular = itemView.findViewById(R.id.tv_celular_albergue);
        }
    }
    public interface ItemClickListener{
        void onItemClick(Albergues albergues);
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
}
