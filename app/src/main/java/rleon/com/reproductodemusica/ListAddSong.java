package rleon.com.reproductodemusica;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static rleon.com.reproductodemusica.MainActivity.archivosMS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAddSong#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAddSong extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String nomList;
    private SharedPreferences spPlaylists;
    private ArrayList<ArchivosM> arraylist = new ArrayList<ArchivosM>();
    RecyclerView recyclerView;
    AdapterList adapterList;

    public ListAddSong() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAddSong.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAddSong newInstance(String param1, String param2) {
        ListAddSong fragment = new ListAddSong();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nomList = getArguments().getString("Playlist");
        spPlaylists = getContext().getSharedPreferences(nomList, Context.MODE_PRIVATE);
        String lists = spPlaylists.getString("Canciones","");
        if (!lists.equals("")){
            TypeToken<ArrayList<ArchivosM>> token = new TypeToken<ArrayList<ArchivosM>>(){};
            Gson gson = new Gson();
            arraylist = gson.fromJson(lists,token.getType());
        }
        final View view = inflater.inflate(R.layout.fragment_list_add_song,container,false);
        recyclerView = view.findViewById(R.id.rvAddSongs);
        recyclerView.setHasFixedSize(true);
        if (archivosMS.size()>-1){
            adapterList = new AdapterList(getContext(), archivosMS,nomList);
            recyclerView.setAdapter(adapterList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                    false));
        }
        view.findViewById(R.id.btnReturnList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Playlist",nomList);
                Navigation.findNavController(view).navigate(R.id.action_listAddSong_to_list,bundle);
            }
        });
        view.findViewById(R.id.btnSaveList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(adapterList.getArraylist());
                SharedPreferences.Editor editor = spPlaylists.edit();
                editor.putString("Canciones", json);
                editor.apply();
                Toast toast = Toast.makeText(getContext(), "Canciones agregadas", Toast.LENGTH_SHORT);
                toast.show();
                Bundle bundle = new Bundle();
                bundle.putString("Playlist", nomList);
                if (nomList == "Favoritos") {
                    Navigation.findNavController(view).navigate(R.id.action_listAddSong_to_Favoritos, bundle);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_listAddSong_to_list, bundle);
                }
            }
        });
        return view;
    }


}