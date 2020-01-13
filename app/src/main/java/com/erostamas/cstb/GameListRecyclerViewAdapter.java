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

import com.erostamas.cstb.ui.main.FilteredGameListFragment;

public class GameListRecyclerViewAdapter extends RecyclerView.Adapter<GameListRecyclerViewAdapter.ViewHolder> {

    private LeagueDataBase leagueDataBase;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private FilteredGameListFragment filteredGameListFragment;
    private Context context;

    // data is passed into the constructor
    public GameListRecyclerViewAdapter(Context context, LeagueDataBase leagueDataBase, FilteredGameListFragment filteredGameListFragment) {
        this.mInflater = LayoutInflater.from(context);
        this.leagueDataBase = leagueDataBase;
        this.filteredGameListFragment = filteredGameListFragment;
        this.context = context;
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
        ((TextView)holder.rootView.findViewById(R.id.home)).setText(leagueDataBase.getGames().get(position)._home);
        ((TextView)holder.rootView.findViewById(R.id.away)).setText(leagueDataBase.getGames().get(position)._away);
        ((TextView)holder.rootView.findViewById(R.id.date)).setText(DateFormat.format("yyyy MMM dd", leagueDataBase.getGames().get(position)._dateTime).toString());
        ((TextView)holder.rootView.findViewById(R.id.time)).setText(DateFormat.format("HH:mm", leagueDataBase.getGames().get(position)._dateTime).toString());
        if (!leagueDataBase.getGames().get(position)._result.isEmpty()) {
            Log.i("erostamasdebug", "result: " + leagueDataBase.getGames().get(position)._result + " size: " + leagueDataBase.getGames().get(position)._result.length());
            ((TextView) holder.rootView.findViewById(R.id.result)).setText(leagueDataBase.getGames().get(position)._result);
        } else {
            ((TextView) holder.rootView.findViewById(R.id.result)).setText(" - ");
        }

        if (leagueDataBase.getGames().get(position)._result.isEmpty() && (!leagueDataBase.getGames().get(position - 1)._result.isEmpty() || position == 0)) {
            ((TextView) holder.rootView.findViewById(R.id.time)).setTextColor(ContextCompat.getColor(context, R.color.colorNextDateText));
            ((TextView) holder.rootView.findViewById(R.id.date)).setTextColor(ContextCompat.getColor(context, R.color.colorNextDateText));
        }
        holder.position = position;

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return leagueDataBase.getGames().size();
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
                //FilteredGameListFragment.favourites.remove(position);
                //favouritesFragment.adapter.notifyDataSetChanged();
                return true;
            }
        };


    }

    // convenience method for getting data at click position
    public Game getItem(int id) {
        return leagueDataBase.getGames().get(id);
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
