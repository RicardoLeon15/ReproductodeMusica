package rleon.com.reproductodemusica;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MHolder> {

    private Context mcontext;
    private ArrayList<ArchivosM> mFiles;
    private String nomList;
    private SharedPreferences spPlaylists;
    private ArrayList<ArchivosM> arraylist = new ArrayList<ArchivosM>();

    AdapterList(Context mcontext, ArrayList<ArchivosM> mFiles,String nomList){
        this.mFiles = mFiles;
        this.mcontext = mcontext;
        this.nomList = nomList;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        spPlaylists = mcontext.getSharedPreferences(nomList, Context.MODE_PRIVATE);
        String lists = spPlaylists.getString("Canciones","");
        if (!lists.equals("")){
            TypeToken<ArrayList<ArchivosM>> token = new TypeToken<ArrayList<ArchivosM>>(){};
            Gson gson = new Gson();
            arraylist = gson.fromJson(lists,token.getType());
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.itemaddlist,parent,false);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        int tiempoT = Integer.parseInt(mFiles.get(position).getTiempo()) / 1000;
        holder.datosCancion.setText(mFiles.get(position).getArtista()+" • "+formattedTime(tiempoT));
        holder.ncancion.setText(mFiles.get(position).getCancion());
        byte[] image = getAlbumsArt(mFiles.get(position).getPath());
        if (image != null){
            Glide.with(mcontext).asBitmap().load(image).into(holder.icancion);
        }else{
            Glide.with(mcontext).load(R.drawable.ic_round_music_note_24).into(holder.icancion);
        }
        holder.btnAdd.setOnCheckedChangeListener(null);
        holder.btnAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    arraylist.add(mFiles.get(position));
                }
                else {
                    arraylist.remove(mFiles.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{
        TextView ncancion,datosCancion;
        ImageView icancion;
        CheckBox btnAdd;
        public MHolder(@NonNull View itemView) {
            super(itemView);
            ncancion = itemView.findViewById(R.id.tvSongAddList);
            datosCancion = itemView.findViewById(R.id.tvADAddList);
            icancion = itemView.findViewById(R.id.imageAddList);
            btnAdd = itemView.findViewById(R.id.btnOptAddList);
            freeMemory();
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

    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public ArrayList<ArchivosM> getArraylist() {
        return arraylist;
    }
}
