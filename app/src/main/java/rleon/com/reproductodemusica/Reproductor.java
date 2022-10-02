package rleon.com.reproductodemusica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import static rleon.com.reproductodemusica.MainActivity.aleatorio;
import static rleon.com.reproductodemusica.MainActivity.archivosMS;

public class Reproductor extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    TextView nomC, nomA, duracion1, duracion2;
    ImageView regresar1, imgC, antesC, nextC, aleC, listRC, imgh;
    FloatingActionButton playP;
    SeekBar barra;
    int pos = -1;
    static ArrayList<ArchivosM> listMS = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playC, prevC, sigC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        initViews();
        getIntenMethod();
        nomC.setText(listMS.get(pos).getCancion());
        nomA.setText(listMS.get(pos).getArtista());
        mediaPlayer.setOnCompletionListener(this);
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
        aleC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aleatorio){
                    aleatorio = false;
                    aleC.setImageResource(R.drawable.ic_aleato);
                }else{
                    aleatorio = true;
                    aleC.setImageResource(R.drawable.ic_aleo2);
                }
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

    @Override
    protected void onResume(){
        playBtn(); //boton para pausar o tocar una canción
        sigBtn();  //boton para pasar a la siguiente cancion
        prevBtn(); //boton para regresar a la cancion
        super.onResume();
    }

    private void prevBtn() {
        prevC = new Thread(){
            @Override
            public void run(){
                super.run();
                antesC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevCBtn();
                    }
                });
            }
        };
        prevC.start();
    }

    private void prevCBtn() {  //funcion anterior cancion
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            if (aleatorio){
                pos = getRandom(listMS.size()-1);
            }else {
                pos = ((pos-1)<0 ? (listMS.size()-1) : (pos-1));
            }
            uri = Uri.parse(listMS.get(pos).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            nomC.setText(listMS.get(pos).getCancion());
            nomA.setText(listMS.get(pos).getArtista());
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playP.setBackgroundResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            if (aleatorio){
                pos = getRandom(listMS.size()-1);
            }else {
                pos = ((pos-1)<0 ? (listMS.size()-1) : (pos-1));
            }
            uri = Uri.parse(listMS.get(pos).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            nomC.setText(listMS.get(pos).getCancion());
            nomA.setText(listMS.get(pos).getArtista());
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playP.setBackgroundResource(R.drawable.ic_play);
        }
    }

    private void sigBtn() {
        sigC = new Thread(){
            @Override
            public void run(){
                super.run();
                nextC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextCBtn();
                    }
                });
            }
        };
        sigC.start();
    }

    private void nextCBtn() {  //funcion siguiente cancion
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            if (aleatorio){
                pos = getRandom(listMS.size()-1);
            }else {
                pos = ((pos+1)%listMS.size());
            }
            uri = Uri.parse(listMS.get(pos).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            nomC.setText(listMS.get(pos).getCancion());
            nomA.setText(listMS.get(pos).getArtista());
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playP.setBackgroundResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            if (aleatorio){
                pos = getRandom(listMS.size()-1);
            }else {
                pos = ((pos+1)%listMS.size());
            }
            uri = Uri.parse(listMS.get(pos).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            nomC.setText(listMS.get(pos).getCancion());
            nomA.setText(listMS.get(pos).getArtista());
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playP.setBackgroundResource(R.drawable.ic_play);
        }
    }

    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i+1);
    }

    private void playBtn() {
        playC = new Thread(){
            @Override
            public void run(){
                super.run();
                playP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playPauseBtn();
                    }
                });
            }
        };
        playC.start();
    }

    private void playPauseBtn(){  //funcion pausa o play a cancion
        if (mediaPlayer.isPlaying()){
            playP.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }else{
            playP.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            barra.setMax(mediaPlayer.getDuration() / 1000);
            Reproductor.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000;
                        barra.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
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
        imgh = findViewById(R.id.imgGr);
    }

    private void metaData (Uri uri){
        MediaMetadataRetriever retriever =  new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int tiempoT = Integer.parseInt(listMS.get(pos).getTiempo()) / 1000;
        duracion2.setText(formattedTime(tiempoT));
        byte[] art = retriever.getEmbeddedPicture();
        Bitmap bitmap = null;
        if (art != null){
            //Glide.with(this).asBitmap().load(art).into(imgC);
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
            AleatorioImg(this, imgC, bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override  //cambio el fondo del reproductor segun la imagen de la cancion en sintonia
                public void onGenerated(@Nullable Palette palette) {
                    Palette.Swatch swatch = palette.getDominantSwatch();
                    if (swatch != null){
                        ImageView ig = findViewById(R.id.imgGr);
                        RelativeLayout ly = findViewById(R.id.mreproductor);
                        ig.setBackgroundResource(R.drawable.gradient);
                        ly.setBackgroundResource(R.drawable.fondo);
                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{swatch.getRgb(), 0x00000000});
                        ig.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawableB = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{swatch.getRgb(), swatch.getRgb()});
                        ly.setBackground(gradientDrawableB);
                        nomC.setTextColor(swatch.getTitleTextColor());
                        nomA.setTextColor(swatch.getBodyTextColor());
                    }else{
                        ImageView ig = findViewById(R.id.imgGr);
                        RelativeLayout ly = findViewById(R.id.mreproductor);
                        ly.setBackgroundResource(R.drawable.gradient);
                        ig.setBackgroundResource(R.drawable.fondo);
                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xff000000, 0x00000000});
                        ig.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawableB = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xff000000, 0xff000000});
                        ly.setBackground(gradientDrawableB);
                        nomC.setTextColor(Color.WHITE);
                        nomA.setTextColor(Color.DKGRAY);
                    }
                }
            });
        }else{
            Glide.with(this).asBitmap().load(R.drawable.ic_cancion).into(imgC);
            ImageView ig = findViewById(R.id.imgGr);
            RelativeLayout ly = findViewById(R.id.mreproductor);
            ig.setBackgroundResource(R.drawable.gradient);
            ly.setBackgroundResource(R.drawable.fondo);
            nomC.setTextColor(Color.WHITE);
            nomA.setTextColor(Color.DKGRAY);
        }
    }

    public void AleatorioImg(Context contex, ImageView imageView, Bitmap bitmap){ //solo es el cambio de imagen
        Animation animout = AnimationUtils.loadAnimation(contex, android.R.anim.fade_out);
        Animation animin = AnimationUtils.loadAnimation(contex, android.R.anim.fade_in);
        animout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(contex).load(bitmap).into(imageView);
                animin.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animout);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextCBtn();
        if (mediaPlayer != null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }
    }
}