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
        holder.nomAl.setText(albumFiles.get(position).getCancion());
        Glide.with(mcontext).load(R.drawable.ic_cancion).into(holder.imgAl); //debes comentar esto antes de descomentar lo de abajo
        /*byte[] image = getAlbumsArt(albumFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.imgAl);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_cancion).into(holder.imgAl);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Reproductor.class);
                intent.putExtra("sender", "cancionesA");
                intent.putExtra("position", position);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgAl;
        TextView nomAl;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            imgAl = itemView.findViewById(R.id.imageView);
            nomAl = itemView.findViewById(R.id.textView4);
        }
    }

    /*private byte[] getAlbumsArt(String uri){  debes descomentarlo
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }*/
}
