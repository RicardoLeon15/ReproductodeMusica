package rleon.com.reproductodemusica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static rleon.com.reproductodemusica.MainActivity.albums;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Listas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Listas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences spPlaylists;
    private ArrayList<String> arrayPlaylists = new ArrayList<String>();
    private RecyclerView recyclerView;
    private AdapterLists adapterLists;

    public Listas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Listas.
     */
    // TODO: Rename and change types and number of parameters
    public static Listas newInstance(String param1, String param2) {
        Listas fragment = new Listas();
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
        spPlaylists = getContext().getSharedPreferences("Playlists", Context.MODE_PRIVATE);
        String lists = spPlaylists.getString("lists","");
        if (!lists.equals("")){
            TypeToken<ArrayList<String>> token = new TypeToken<ArrayList<String>>(){};
            Gson gson = new Gson();
            arrayPlaylists = gson.fromJson(lists,token.getType());
        }
        final View view = inflater.inflate(R.layout.fragment_listas,container,false);
        recyclerView = view.findViewById(R.id.rvListas);
        recyclerView.setHasFixedSize(true);
        if (!(arrayPlaylists.size()<1)){
            adapterLists = new AdapterLists(getContext(), arrayPlaylists);
            recyclerView.setAdapter(adapterLists);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        view.findViewById(R.id.btnAddList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Crear Lista de reproducción");
                final EditText listNom = new EditText(getContext());
                listNom.setInputType(InputType.TYPE_CLASS_TEXT);
                listNom.setHint("Nueva Lista");
                builder.setView(listNom);
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        arrayPlaylists.add(listNom.getText().toString());
                        Gson gson = new Gson();
                        String json = gson.toJson(arrayPlaylists);
                        SharedPreferences.Editor editor = spPlaylists.edit();
                        editor.putString("lists",json);
                        editor.apply();
                        adapterLists = new AdapterLists(getContext(), arrayPlaylists);
                        recyclerView.setAdapter(adapterLists);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}