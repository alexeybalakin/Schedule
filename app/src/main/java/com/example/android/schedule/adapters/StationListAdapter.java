package com.example.android.schedule.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.schedule.R;
import com.example.android.schedule.model.Station;

import java.util.List;



public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Station station);
    }

    private List<Station> stations;
    private final OnItemClickListener listener;


    public StationListAdapter(List<Station> stations, OnItemClickListener listener) {
        this.stations = stations;
        this.listener = listener;;
    }

    @Override
    public StationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_list_item, parent,false);
        return new StationListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StationListAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView tvStation = cardView.findViewById(R.id.tvStation);
        TextView tvCountry = cardView.findViewById(R.id.tvCountry);
        TextView tvCity = cardView.findViewById(R.id.tvCity);
        tvStation.setText(stations.get(position).getStationTitle());
        tvCountry.setText(stations.get(position).getCountryTitle());
        tvCity.setText(stations.get(position).getCityTitle());
        holder.bind(stations.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public void updateList(List<Station> stations) {
        this.stations = stations;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.cvStation);
        }
        public void bind(final Station item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
