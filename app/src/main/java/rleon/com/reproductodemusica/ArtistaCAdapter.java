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

public class ArtistaCAdapter extends RecyclerView.Adapter<ArtistaCAdapter.MyHolder>{
    private Context mcontext;
    static ArrayList<ArchivosM> artistFiles;
    View view;
    public ArtistaCAdapter(Context mcontext, ArrayList<ArchivosM> artistFiles) {
        this.mcontext = mcontext;
        this.artistFiles = artistFiles;
    }
    @NonNull
    @Override
    public ArtistaCAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.mitems, parent, false);
        return new ArtistaCAdapter.MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ArtistaCAdapter.MyHolder holder, final int position) {
        holder.nomAr.setText(artistFiles.get(position).getCancion());
        Glide.with(mcontext).load(R.drawable.ic_cancion).into(holder.imgAr); //debes comentar esto antes de descomentar lo de abajo
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
                intent.putExtra("sender", "cancionesAr");
                intent.putExtra("position", position);
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
            imgAr = itemView.findViewById(R.id.imageView);
            nomAr = itemView.findViewById(R.id.textView4);
        }
    }
}
