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

public class AdaptadorM extends RecyclerView.Adapter<AdaptadorM.MHolder> {

    private Context mcontext;
    private ArrayList<ArchivosM> mFiles;

    AdaptadorM(Context mcontext, ArrayList<ArchivosM> mFiles){
        this.mFiles = mFiles;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.mitems,parent,false);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        holder.ncancion.setText(mFiles.get(position).getCancion());
        Glide.with(mcontext).load(R.drawable.ic_cancion).into(holder.icancion);
        /*byte[] image = getAlbumsArt(mFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.icancion);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_cancion).into(holder.icancion);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Reproductor.class);
                intent.putExtra("position",position);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{
        TextView ncancion;
        ImageView icancion;
        public MHolder(@NonNull View itemView) {
            super(itemView);
            ncancion = itemView.findViewById(R.id.textView4);
            icancion = itemView.findViewById(R.id.imageView);
        }
    }

    /*private byte[] getAlbumsArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }*/
}
