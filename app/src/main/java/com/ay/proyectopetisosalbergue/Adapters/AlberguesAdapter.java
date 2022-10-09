package com.ay.proyectopetisosalbergue.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ay.proyectopetisosalbergue.Model.Albergues;
import com.ay.proyectopetisosalbergue.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

public class AlberguesAdapter extends RecyclerView.Adapter<AlberguesAdapter.AlberguesHolder> {

    List<Albergues> listaAlbergues;
    Context context;
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
        Glide.with(context).load(String.valueOf(RUTA+listaAlbergues.get(position).getImgAlbergue())).into(holder.img_albergue);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(listaAlbergues.get(position));
        });
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
}
