package rleon.com.reproductodemusica;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static rleon.com.reproductodemusica.MainActivity.archivosMS;

public class Reproductor extends AppCompatActivity {
    TextView nomC, nomA, duracion1, duracion2;
    ImageView regresar1, imgC, antesC, nextC, aleC, listRC;
    FloatingActionButton playP;
    SeekBar barra;
    int pos = -1;
    static ArrayList<ArchivosM> listMS = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        initViews();
        getIntenMethod();
        nomC.setText(listMS.get(pos).getCancion());
        nomA.setText(listMS.get(pos).getArtista());
        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b){
                    mediaPlayer.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Reproductor.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                    barra.setProgress(mCurrentPosition);
                    duracion1.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
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

    private void getIntenMethod() {
        pos = getIntent().getIntExtra("position",-1);
        listMS = archivosMS;
        if (listMS != null){
            playP.setImageResource(R.drawable.ic_pause);
            uri = uri.parse(listMS.get(pos).getPath());
        }
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }else{
            mediaPlayer =  MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        barra.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);
    }

    private void initViews() {
        nomC = findViewById(R.id.nomCancion);
        nomA = findViewById(R.id.nomArtista);
        duracion1 = findViewById(R.id.ceroTiempo);
        duracion2 = findViewById(R.id.dtiempo);
        regresar1 = findViewById(R.id.regresar);
        imgC = findViewById(R.id.imgCancion);
        antesC = findViewById(R.id.ante);
        nextC = findViewById(R.id.nexts);
        aleC = findViewById(R.id.aleatorio);
        listRC = findViewById(R.id.listVS);
        playP = findViewById(R.id.play_pause);
        barra = findViewById(R.id.barraM);
    }

    private void metaData (Uri uri){
        MediaMetadataRetriever retriever =  new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int tiempoT = Integer.parseInt(listMS.get(pos).getTiempo()) / 1000;
        duracion2.setText(formattedTime(tiempoT));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null){
            Glide.with(this).asBitmap().load(art).into(imgC);
        }else{
            Glide.with(this).asBitmap().load(R.drawable.ic_cancion).into(imgC);
        }
    }
}