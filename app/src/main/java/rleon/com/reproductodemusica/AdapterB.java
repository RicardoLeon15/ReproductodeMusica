package rleon.com.reproductodemusica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterB extends RecyclerView.Adapter<AdapterB.MHolder> implements SearchView.OnQueryTextListener {
    private Context mcontext;
    private ArrayList<ArchivosM> mFiles;

    public AdapterB(Context context, ArrayList<ArchivosM> archivosMS) {
        this.mFiles = archivosMS;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public AdapterB.MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.mitems,parent,false);
        return new AdapterB.MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterB.MHolder holder, int position) {
        holder.buscar.setOnQueryTextListener(this);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MHolder extends RecyclerView.ViewHolder{
        TextView ncancion;
        ImageView icancion;
        SearchView buscar;
        public MHolder(@NonNull View itemView) {
            super(itemView);
            ncancion = itemView.findViewById(R.id.textView4);
            icancion = itemView.findViewById(R.id.imageView);
            buscar = itemView.findViewById(R.id.searchC);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        AdaptadorM adaptadorM = new AdaptadorM();
        adaptadorM.filtrar(s);
        return false;
    }
}
