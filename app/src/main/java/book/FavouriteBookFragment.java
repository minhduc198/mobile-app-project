package book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.openlibraryapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteBookFragment extends Fragment {

    private RecyclerView rcvFavourite;
    private FavouriteAdapter adapter;
    private List<Favourite> favouriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_book, container, false);

        rcvFavourite = view.findViewById(R.id.recv_favour);
        adapter = new FavouriteAdapter(requireContext());

        favouriteList = new ArrayList<>();
        favouriteList.add(new Favourite(R.drawable.rin,getString(R.string.title1)));
        favouriteList.add(new Favourite(R.drawable.qveene,getString(R.string.title2)));
        favouriteList.add(new Favourite(R.drawable.shanna,getString(R.string.title3)));
        favouriteList.add(new Favourite(R.drawable.tower,getString(R.string.title4)));
        favouriteList.add(new Favourite(R.drawable.hobbit,getString(R.string.title5)));
        favouriteList.add(new Favourite(R.drawable.ring,getString(R.string.title6)));
        favouriteList.add(new Favourite(R.drawable.throne,getString(R.string.title7)));

        adapter.setData(favouriteList);

        rcvFavourite.setLayoutManager(new GridLayoutManager(requireContext(),2));
        rcvFavourite.setAdapter(adapter);

        return view;
    }
}