package rleon.com.reproductodemusica;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterLists extends RecyclerView.Adapter<AdapterLists.MyHolder> {
    private Context mcontext;
    private ArrayList<String> playlists;
    View view;
    public AdapterLists(Context mcontext, ArrayList<String> playlists) {
        this.mcontext = mcontext;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public AdapterLists.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.albumes, parent, false);
        return new AdapterLists.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.nomAl.setText(playlists.get(position));
        Glide.with(mcontext).load(R.drawable.ic_round_music_note_24).into(holder.imgAl);
        /*byte[] image = getAlbumsArt(albumFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.imgAl);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_round_music_note_24).into(holder.imgAl);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgAl;
        TextView nomAl;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            imgAl = itemView.findViewById(R.id.imageA);
            nomAl = itemView.findViewById(R.id.nombreA);
        }
    }

    private byte[] getAlbumsArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}

