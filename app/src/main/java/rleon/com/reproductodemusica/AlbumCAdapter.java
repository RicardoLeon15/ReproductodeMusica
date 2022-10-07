package rleon.com.reproductodemusica;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class AlbumCAdapter extends RecyclerView.Adapter<AlbumCAdapter.MyHolder>{
    private Context mcontext;
    static ArrayList<ArchivosM> albumFiles;
    View view;
    public AlbumCAdapter(Context mcontext, ArrayList<ArchivosM> albumFiles) {
        this.mcontext = mcontext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.mitems, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        int tiempoT = Integer.parseInt(albumFiles.get(position).getTiempo()) / 1000;
        holder.dcancion.setText(albumFiles.get(position).getArtista()+" • "+formattedTime(tiempoT));
        holder.ncancion.setText(albumFiles.get(position).getCancion());
        byte[] image = getAlbumsArt(albumFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.icancion);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_round_album_24).into(holder.icancion);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Reproductor.class);
                intent.putExtra("position",position);
                mcontext.startActivity(intent);
            }
        });
        holder.ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(mcontext, view);
                popupMenu.getMenuInflater().inflate(R.menu.ajustesc, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener((item) -> {
                    switch (item.getItemId()){
                        case R.id.eliminar:
                            deleteCancion(position, view);
                            break;
                    }
                    return true;
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    private void deleteCancion(int position, View view) {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(albumFiles.get(position).getId()));
        File file = new File(albumFiles.get(position).getPath());
        boolean deleted = file.delete();
        if (deleted){
            mcontext.getContentResolver().delete(contentUri, null, null);
            albumFiles.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,albumFiles.size());
            Snackbar.make(view, "Cancion eliminada: ", Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(view, "No se pudo eliminar: ", Snackbar.LENGTH_LONG).show();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView ncancion, dcancion;
        ImageView icancion;
        ImageButton ajustes;
        Button button;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            ncancion = itemView.findViewById(R.id.tvSong);
            dcancion = itemView.findViewById(R.id.tvSongDatos);
            icancion = itemView.findViewById(R.id.imageView);
            ajustes = itemView.findViewById(R.id.ajustesC);
            button = itemView.findViewById(R.id.btnSong);
        }
    }

    private byte[] getAlbumsArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut;
        String totalNew;
        String segundos = String.valueOf(mCurrentPosition % 60);
        String minutos = String.valueOf(mCurrentPosition / 60);
        totalOut = minutos+":"+segundos;
        totalNew = minutos+":"+"0"+segundos;
        if (segundos.length() == 1){
            return totalNew;
        }else{
            return totalOut;
        }
    }
}
