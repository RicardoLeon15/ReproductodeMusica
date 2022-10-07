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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static rleon.com.reproductodemusica.MainActivity.archivosMS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class List extends Fragment {

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
    private RecyclerView recyclerView;
    private AdapterList adapterList;

    public List() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List.
     */
    // TODO: Rename and change types and number of parameters
    public static List newInstance(String param1, String param2) {
        List fragment = new List();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        if (arraylist.size()>-1){
            adapterList = new AdapterList(getContext(), arraylist,nomList);
            recyclerView.setAdapter(adapterList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                    false));
        }
        TextView tvNomList = view.findViewById(R.id.tvListNom);
        tvNomList.setText(nomList);
        view.findViewById(R.id.btnListBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_list_to_Listas);
            }
        });
        view.findViewById(R.id.btnAddSongList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Playlist",nomList);
                Navigation.findNavController(view).navigate(R.id.action_list_to_listAddSong,bundle);
            }
        });
        return view;
    }
}