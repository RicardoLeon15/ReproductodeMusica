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

public class ArtistaC extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView artistP;
    String artistNom;
    ArrayList<ArchivosM> cancionesAr = new ArrayList<>();
    ArtistaCAdapter artistaCAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista_c);
        recyclerView = findViewById(R.id.recycleview);
        artistP = findViewById(R.id.artisP);
        artistNom = getIntent().getStringExtra("nombreAr");
        int j = 0;
        for (int i=0; i<archivosMS.size(); i++){
            if (artistNom.equals(archivosMS.get(i).getArtista())){
                cancionesAr.add(j, archivosMS.get(i));
                j++;
            }
        }
        //Glide.with(this).load(R.drawable.ic_artista).into(artistP);
        byte[] image = getArtistArt(cancionesAr.get(0).getPath());
        if (image != null){
            Glide.with(this).load(image).into(artistP);
        }else {
            Glide.with(this).load(R.drawable.ic_artista).into(artistP);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!(cancionesAr.size() < 1)){
            artistaCAdapter = new ArtistaCAdapter(this, cancionesAr);
            recyclerView.setAdapter(artistaCAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
    }

    private byte[] getArtistArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}