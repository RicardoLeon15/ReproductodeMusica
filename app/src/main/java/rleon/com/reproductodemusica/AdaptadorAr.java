package rleon.com.reproductodemusica;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorAr extends RecyclerView.Adapter<AdaptadorAr.MyHolder> {
    private Context mcontext;
    private ArrayList<ArchivosM> artistFiles;
    View view;
    public AdaptadorAr(Context mcontext, ArrayList<ArchivosM> artistFiles) {
        this.mcontext = mcontext;
        this.artistFiles = artistFiles;
    }

    @NonNull
    @Override
    public AdaptadorAr.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.albumes, parent, false);
        return new AdaptadorAr.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAr.MyHolder holder, int position) {
        holder.nomAr.setText(artistFiles.get(position).getArtista());
        Glide.with(mcontext).load(R.drawable.ic_artista).into(holder.imgAr);//debes comentar esto antes de descomentar lo de abajo
        /*byte[] image = getArtistArt(artistFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.imgAr);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_artista).into(holder.imgAl);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ArtistaC.class);
                intent.putExtra("nombreAr", artistFiles.get(position).getArtista());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgAr;
        TextView nomAr;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            imgAr = itemView.findViewById(R.id.imageA);
            nomAr = itemView.findViewById(R.id.nombreA);
        }
    }

    /*private byte[] getArtistArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }*/
}
