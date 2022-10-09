package com.ay.proyectopetisosalbergue.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisosalbergue.Model.VisitasRegistradas;
import com.ay.proyectopetisosalbergue.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

public class VisitasRegistradasAdapter extends RecyclerView.Adapter<VisitasRegistradasAdapter.VisitasRegistradasHolder> {
    Context context;
    List<VisitasRegistradas> visitasRegistradasList;
    ItemClickListener onItemClickListener;

    public VisitasRegistradasAdapter(List<VisitasRegistradas> visitasRegistradasList,Context context, ItemClickListener onItemClickListener) {
        this.context = context;
        this.visitasRegistradasList = visitasRegistradasList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public VisitasRegistradasHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_visitas_registradas,parent,false);
        return new VisitasRegistradasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VisitasRegistradasHolder holder, int position) {
        Glide.with(context).load(String.valueOf(RUTA+visitasRegistradasList.get(position).getCaninoImg())).into(holder.imgCanino);
        holder.nomAlbergue.setText(String.valueOf(visitasRegistradasList.get(position).getNomAlbergue()));
        holder.nomCanino.setText(String.valueOf(visitasRegistradasList.get(position).getCaninoNombre()));
        holder.fechaRegistro.setText(String.valueOf(visitasRegistradasList.get(position).getVisitaFecha()));
        holder.horaRegistro.setText(String.valueOf(visitasRegistradasList.get(position).getVisitaHora()));
        holder.visitaEstado.setText(String.valueOf(visitasRegistradasList.get(position).getVisitaEstado()));
        String estado = String.valueOf(visitasRegistradasList.get(position).getVisitaEstado());
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(visitasRegistradasList.get(position));
        });
        if(estado.equals("EN ESPERA")){
            holder.lnEstado.setBackground(context.getResources().getDrawable(R.color.primaryLightColor));
        }else if (estado.equals("CONFIRMADO")){
            holder.lnEstado.setBackground(context.getResources().getDrawable(R.color.secondaryColor));
        }

    }
    public interface ItemClickListener{
        void onItemClick(VisitasRegistradas visitasRegistradas);
    }

    @Override
    public int getItemCount() {
        return visitasRegistradasList.size();
    }

    public class VisitasRegistradasHolder extends RecyclerView.ViewHolder {
        ImageView imgCanino;
        TextView nomAlbergue,nomCanino,fechaRegistro,horaRegistro,visitaEstado;
        LinearLayout lnEstado;

        public VisitasRegistradasHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCanino = itemView.findViewById(R.id.imgAlbergueList);
            nomAlbergue = itemView.findViewById(R.id.tvNombreAlbergueVisitaRegistrada);
            nomCanino = itemView.findViewById(R.id.tv_nombre_animal_visita_registrado);
            fechaRegistro = itemView.findViewById(R.id.tv_fecha_registrado);
            horaRegistro = itemView.findViewById(R.id.tv_hora_registrado);
            visitaEstado = itemView.findViewById(R.id.tv_estado_reg_visita);
            lnEstado = itemView.findViewById(R.id.back_color);
        }
    }

}
