package com.ay.proyectopetisos.Adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ay.proyectopetisos.Model.Notificaciones;
import com.ay.proyectopetisos.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.NotificacionesHolder> {
    List<Notificaciones> notificacionesList;
    Context context;

    public NotificacionesAdapter(List<Notificaciones> notificacionesList, Context context) {
        this.notificacionesList = notificacionesList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public NotificacionesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_notificaciones,parent,false);
        return new NotificacionesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificacionesHolder holder, int position) {
        holder.tvTitulo.setText(String.valueOf(notificacionesList.get(position).getNotTituto()));
        holder.tvDescripcion.setText(String.valueOf(notificacionesList.get(position).getNotDescripcion()));
        String timeAgo = String.valueOf(notificacionesList.get(position).getNotHora());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            Date date = sdf.parse(timeAgo);
            holder.tvHora.setText(calculateTimeAgo(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context).load(String.valueOf(RUTA+notificacionesList.get(position).getImgAlbergueLogo())).into(holder.imgAlbergue);
    }

    private String calculateTimeAgo(long timeMs) {
        String convertTime;
        long currentTimeMs = System.currentTimeMillis();
        long differenceTime = currentTimeMs - timeMs;
        long second = TimeUnit.MILLISECONDS.toSeconds(differenceTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(differenceTime);
        long hour = TimeUnit.MILLISECONDS.toHours(differenceTime);
        long days = TimeUnit.MILLISECONDS.toDays(differenceTime);
        convertTime  = "Ahora";
        if (second >= 59 && minutes < 59){
            convertTime  = "Hace "+ minutes + " minutos";
        }else if (minutes >= 59 && hour < 24){
            convertTime  = "Hace "+ hour + " horas";
        }else if (hour >= 24 && days < 7){
            convertTime  = "Hace "+ days + " dias";
        }else if (days >= 7){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeMs);
            convertTime = format.format(calendar.getTime());
        }
        return convertTime;
    }

    @Override
    public int getItemCount() {
        return notificacionesList.size();
    }

    public class NotificacionesHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAlbergue;
        TextView tvTitulo,tvDescripcion,tvHora;

        public NotificacionesHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgAlbergue = itemView.findViewById(R.id.img_albergue_notificacion);
            tvTitulo = itemView.findViewById(R.id.tvNotTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvdescNotificacion);
            tvHora = itemView.findViewById(R.id.tvNotHora);
        }
    }
}
