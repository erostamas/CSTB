package com.erostamas.cstb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erostamas.cstb.ui.main.GameListFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class GameListRecyclerViewAdapter extends RecyclerView.Adapter<GameListRecyclerViewAdapter.ViewHolder> {

    private LeagueDataBase leagueDataBase;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private GameListFragment gameListFragment;
    private Context context;
    private String myTeam = "";
    private ArrayList<Integer> filterIndexes = new ArrayList<>();

    public GameListRecyclerViewAdapter(Context context, LeagueDataBase leagueDataBase, GameListFragment gameListFragment, String myTeam) {
        this.mInflater = LayoutInflater.from(context);
        this.leagueDataBase = leagueDataBase;
        this.gameListFragment = gameListFragment;
        this.context = context;
        this.myTeam = myTeam;
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.i("erostamasdebug", "onChanged filtered");
                super.onChanged();
                reIndex();
            }
        });

    }

    public GameListRecyclerViewAdapter(Context context, final LeagueDataBase leagueDataBase, GameListFragment gameListFragment) {
        this(context, leagueDataBase, gameListFragment, "");
    }

    private void reIndex() {
        Log.i("erostamasdebug", "reIndex database size: " + Integer.toString(leagueDataBase.getGames().size()) + " myteam: " + myTeam);
        filterIndexes.clear();
        for (int index = 0; index < leagueDataBase.getGames().size(); index++) {
            Log.i("erostamasdebug", "home team: " + leagueDataBase.getGames().get(index)._home);
            if (myTeam.isEmpty() || leagueDataBase.getGames().get(index)._home.equals(myTeam) || leagueDataBase.getGames().get(index)._away.equals(myTeam)) {
                Log.i("erostamasdebug", "adding to filteredIndexes: " + Integer.toString(index));
                filterIndexes.add(index);
            }
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int realPosition = filterIndexes.get(position);
        ((TextView)holder.rootView.findViewById(R.id.home)).setText(leagueDataBase.getGames().get(realPosition)._home);
        ((TextView)holder.rootView.findViewById(R.id.away)).setText(leagueDataBase.getGames().get(realPosition)._away);
        ((TextView)holder.rootView.findViewById(R.id.date)).setText(DateFormat.format("yyyy MMM dd", leagueDataBase.getGames().get(realPosition)._dateTime).toString());
        ((TextView)holder.rootView.findViewById(R.id.time)).setText(DateFormat.format("HH:mm", leagueDataBase.getGames().get(realPosition)._dateTime).toString());
        if (!leagueDataBase.getGames().get(realPosition)._result.isEmpty()) {
            Log.i("erostamasdebug", "result: " + leagueDataBase.getGames().get(realPosition)._result + " size: " + leagueDataBase.getGames().get(realPosition)._result.length());
            ((TextView) holder.rootView.findViewById(R.id.result)).setText(leagueDataBase.getGames().get(realPosition)._result);
        } else {
            ((TextView) holder.rootView.findViewById(R.id.result)).setText(" - ");
        }

        if (!this.myTeam.isEmpty() && (leagueDataBase.getGames().get(realPosition)._result.isEmpty() && (!leagueDataBase.getGames().get(filterIndexes.get(position - 1))._result.isEmpty() || position == 0))) {
            ((TextView) holder.rootView.findViewById(R.id.time)).setTextColor(ContextCompat.getColor(context, R.color.colorNextDateText));
            ((TextView) holder.rootView.findViewById(R.id.date)).setTextColor(ContextCompat.getColor(context, R.color.colorNextDateText));
        }
        holder.position = realPosition;

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return filterIndexes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rootView;
        public int position;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = (RelativeLayout)itemView.findViewById(R.id.game_list_item_root);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (leagueDataBase.getGames() != null) {
                    MenuItem myActionItem = menu.add("Delete");
                    myActionItem.setOnMenuItemClickListener(mOnMyActionClickListener);
                }
            }
        };

        private final MenuItem.OnMenuItemClickListener mOnMyActionClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //GameListFragment.favourites.remove(position);
                //favouritesFragment.adapter.notifyDataSetChanged();
                return true;
            }
        };


    }

    // convenience method for getting data at click position
    public Game getItem(int id) {
        return leagueDataBase.getGames().get(filterIndexes.get(id));
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
