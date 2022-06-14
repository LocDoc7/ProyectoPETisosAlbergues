package com.ay.proyectopetisos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisos.Model.CodigoIntercambio;
import com.ay.proyectopetisos.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class CodigoIntercambioAdapter extends RecyclerView.Adapter<CodigoIntercambioAdapter.CodigoIntercambioHolder> {
    Context context;
    List<CodigoIntercambio> codigoIntercambioList;
    ItemClickListener onItemClickListener;

    public CodigoIntercambioAdapter( List<CodigoIntercambio> codigoIntercambioList, Context context, ItemClickListener onItemClickListener) {
        this.context = context;
        this.codigoIntercambioList = codigoIntercambioList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public CodigoIntercambioHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_codigos_intercambio,parent,false);
        return new CodigoIntercambioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CodigoIntercambioAdapter.CodigoIntercambioHolder holder, int position) {
        Glide.with(context).load(String.valueOf(RUTA+codigoIntercambioList.get(position).getImgProductoIntercambio())).into(holder.imgProducto);
        holder.tvnomProducto.setText(String.valueOf(codigoIntercambioList.get(position).getNomProductoIntercambio()));
        holder.tvnomAlbergue.setText(String.valueOf(codigoIntercambioList.get(position).getNomAlbergueIntercambio()));
        holder.tvfechaValida.setText(String.valueOf(codigoIntercambioList.get(position).getFechaValidaProductoIntercambio()));
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(codigoIntercambioList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return codigoIntercambioList.size();
    }
    public interface ItemClickListener{
        void onItemClick(CodigoIntercambio codigoIntercambio);
    }

    public class CodigoIntercambioHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView tvnomProducto,tvnomAlbergue,tvfechaValida;

        public CodigoIntercambioHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto_codigo_intercambio);
            tvnomProducto = itemView.findViewById(R.id.tvNomProducto_codigo_intercambio);
            tvnomAlbergue = itemView.findViewById(R.id.tv_nombre_albergue_codigo_intercambio);
            tvfechaValida = itemView.findViewById(R.id.tv_fecha_valida_codigo_intercambio);
        }
    }
}
