package rleon.com.reproductodemusica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static rleon.com.reproductodemusica.MainActivity.archivosMS;

public class AlbumC extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView albumP;
    String albumNom;
    ArrayList<ArchivosM> cancionesA = new ArrayList<>();
    AlbumCAdapter albumCAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_c);
        recyclerView = findViewById(R.id.recycleview);
        albumP = findViewById(R.id.albumP);
        albumNom = getIntent().getStringExtra("nombreA");
        int j = 0;
        for (int i=0; i<archivosMS.size(); i++){
            if (albumNom.equals(archivosMS.get(i).getAlbum())){
                cancionesA.add(j, archivosMS.get(i));
                j++;
            }
        }
        //Glide.with(this).load(R.drawable.ic_cancion).into(albumP);
        byte[] image = getAlbumsArt(cancionesA.get(0).getPath());
        if (image != null){
            Glide.with(this).load(image).into(albumP);
        }else {
            Glide.with(this).load(R.drawable.ic_cancion).into(albumP);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(cancionesA.size() < 1)){
            albumCAdapter = new AlbumCAdapter(this, cancionesA);
            recyclerView.setAdapter(albumCAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
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