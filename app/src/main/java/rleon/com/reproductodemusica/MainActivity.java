package rleon.com.reproductodemusica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    static ArrayList<ArchivosM> archivosMS;
    static boolean aleatorio = false;
    static ArrayList<ArchivosM> albums = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        permission();
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
            , REQUEST_CODE);
        }else{
            archivosMS = getAllAudio(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                archivosMS = getAllAudio(this);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , REQUEST_CODE);
            }
        }
    }

    public static ArrayList<ArchivosM> getAllAudio(Context context){
        ArrayList<String> albums2 = new ArrayList<>();
        ArrayList<ArchivosM> listSong = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String title = cursor.getString(0);
                String artist = cursor.getString(1);
                String album = cursor.getString(2);
                String duration = cursor.getString(3);
                String data = cursor.getString(4);
                String id = cursor.getString(5);
                ArchivosM archivosM = new ArchivosM(title, artist, album, duration, data, id);
                listSong.add(archivosM);
                if (!albums2.contains(album)){
                    albums.add(archivosM);
                    albums2.add(album);
                }
            }
            //cursor.close();
        }
        return listSong;
    }
}