package rleon.com.reproductodemusica;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

public class AdapterB implements SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}
