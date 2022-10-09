package com.ay.proyectopetisosalbergue.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisosalbergue.Model.Consejos;
import com.ay.proyectopetisosalbergue.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

public class ConsejosAdapter extends RecyclerView.Adapter<ConsejosAdapter.ConsejosHolder> {
    Context context;
    List<Consejos> consejosList;

    public ConsejosAdapter(Context context, List<Consejos> consejosList) {
        this.context = context;
        this.consejosList = consejosList;
    }

    @NonNull
    @NotNull
    @Override
    public ConsejosAdapter.ConsejosHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_consejos_salud,parent,false);
        return new ConsejosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ConsejosAdapter.ConsejosHolder holder, int position) {
        Glide.with(context).load(String.valueOf(RUTA+consejosList.get(position).getImgConsejo())).into(holder.imgConsejo);
        Glide.with(context).load(String.valueOf(RUTA+consejosList.get(position).getIcAlbergue())).into(holder.icAlbergue);
        holder.tvNombreAlbergue.setText(String.valueOf(consejosList.get(position).getNomAlbergue()));
        holder.tvTituloConsejo.setText(String.valueOf(consejosList.get(position).getTituloConsejo()));
        holder.tvDescConsejo.setText(String.valueOf(consejosList.get(position).getDescConsejo()));
        holder.btnConoceMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(consejosList.get(position).getVinculoConsejo()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return consejosList.size();
    }

    public class ConsejosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreAlbergue,tvTituloConsejo, tvDescConsejo;
        Button btnConoceMas;
        ImageView icAlbergue,imgConsejo;

        public ConsejosHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNombreAlbergue = itemView.findViewById(R.id.tv_nomAlbergueConsejo);
            tvTituloConsejo = itemView.findViewById(R.id.tvTituloConsejo);
            tvDescConsejo = itemView.findViewById(R.id.tvdescConsejoAlbergue);
            btnConoceMas = itemView.findViewById(R.id.btn_conocer_mas_consejo);
            icAlbergue = itemView.findViewById(R.id.ic_albergueConsejo);
            imgConsejo = itemView.findViewById(R.id.img_consejo);
        }
    }
}
