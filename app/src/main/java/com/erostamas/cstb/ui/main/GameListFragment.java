package com.erostamas.cstb.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erostamas.cstb.GameListRecyclerViewAdapter;
import com.erostamas.cstb.MainActivity;
import com.erostamas.cstb.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class GameListFragment extends Fragment implements GameListRecyclerViewAdapter.ItemClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_MY_TEAM_NAME = "my_team";

    private PageViewModel pageViewModel;
    public GameListRecyclerViewAdapter adapter;

    private String _myTeam = "";

    public static GameListFragment newInstance(int index) {
        Log.i("erostamasdebug", "GameListFragment newinstance without myteam");
        GameListFragment fragment = new GameListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameListFragment newInstance(int index, String myTeam) {
        Log.i("erostamasdebug", "GameListFragment newinstance with myteam: " + myTeam);
        GameListFragment fragment = new GameListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_MY_TEAM_NAME, myTeam);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("erostamasdebug", "GameListFragment oncreate");
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (getArguments().containsKey(ARG_MY_TEAM_NAME)) {
                _myTeam = getArguments().getString(ARG_MY_TEAM_NAME);
            }
            Log.i("erostamasdebug", "GameListFragment oncreate myteam arg: " + _myTeam);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filtered_game_list, container, false);

        // set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.game_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        int numberOfColumns = 1;
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        Log.i("erostamasdebug", "new GameListRecyclerViewAdapter with myteam: " + _myTeam);
        adapter = new GameListRecyclerViewAdapter(getActivity(), ((MainActivity)getActivity()).leagueDataBase, this, _myTeam);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        /*SeekBar intensityBar = (SeekBar) rootView.findViewById(R.id.seekBarIntensity);
        final ImageView colorPalette = (ImageView) rootView.findViewById(R.id.colorPalette);
        intensityBar.setMax(100);

        intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("ledcontrol", "Intensity progress changed to : " + seekBar.getProgress());

                CommandSender.setIntensity(getActivity(), seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        View.OnTouchListener colorPaletteClickListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(colorPalette)) {
                    // Write your awesome code here
                    float eventX = event.getX();
                    float eventY = event.getY();
                    float[] eventXY = new float[] {eventX, eventY};

                    Matrix invertMatrix = new Matrix();
                    ((ImageView)v).getImageMatrix().invert(invertMatrix);

                    invertMatrix.mapPoints(eventXY);
                    int x = Integer.valueOf((int)eventXY[0]);
                    int y = Integer.valueOf((int)eventXY[1]);


                    Drawable imgDrawable;
                    imgDrawable = ((ImageView)v).getDrawable();
                    Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

                    //Limit x, y range within bitmap
                    if(x < 0){
                        x = 0;
                    }else if(x > bitmap.getWidth()-1){
                        x = bitmap.getWidth()-1;
                    }

                    if(y < 0){
                        y = 0;
                    }else if(y > bitmap.getHeight()-1){
                        y = bitmap.getHeight()-1;
                    }

                    int touchedRGB = bitmap.getPixel(x, y);

                    CommandSender.setColor(getActivity(), Color.red(touchedRGB), Color.green(touchedRGB), Color.blue(touchedRGB));
                }
                return true;
            }
        };
        colorPalette.setOnTouchListener(colorPaletteClickListener);*/
        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        //RGBColor item = adapter.getItem(position);
        //CommandSender.setColor(getActivity(), item._red, item._green, item._blue);
        //Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}