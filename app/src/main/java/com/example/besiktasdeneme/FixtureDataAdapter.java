package com.example.besiktasdeneme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FixtureDataAdapter extends RecyclerView.Adapter<FixtureDataAdapter.MatchViewHolder> {
    private List<FixtureData> matchList;
    private Context context;

    public FixtureDataAdapter(List<FixtureData> matchList, Context context) {
        this.matchList = matchList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fixture, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        FixtureData match = matchList.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private ImageView teamLogo1ImageView;
        private ImageView teamLogo2ImageView;
        private TextView teamName1TextView;
        private TextView teamName2TextView;
        private TextView score1TextView;
        private TextView score2TextView;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            teamLogo1ImageView = itemView.findViewById(R.id.teamLogo1ImageView);
            teamLogo2ImageView = itemView.findViewById(R.id.teamLogo2ImageView);
            teamName1TextView = itemView.findViewById(R.id.teamName1TextView);
            teamName2TextView = itemView.findViewById(R.id.teamName2TextView);
            score1TextView = itemView.findViewById(R.id.score1TextView);
            score2TextView = itemView.findViewById(R.id.score2TextView);
        }

        public void bind(FixtureData match) {
            dateTextView.setText(match.getDate());
            Picasso.get().load(match.getTeamLogo1()).into(teamLogo1ImageView);
            Picasso.get().load(match.getTeamLogo2()).into(teamLogo2ImageView);
            teamName1TextView.setText(match.getTeamName1());
            teamName2TextView.setText(match.getTeamName2());
            if(match.getScore1()=="null"){
                score1TextView.setText("3");
                score2TextView.setText("0");
            }
            else{
                score1TextView.setText(match.getScore1());
                score2TextView.setText(match.getScore2());
            }

        }
    }
}
