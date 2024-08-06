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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> playerList;

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private TextView txtFullName;
        private TextView txtAge;
        private TextView txtNationality;
        private TextView txtGamesPlayed;
        private TextView txtGoalsScored;
        private TextView txtAssists;
        private TextView txtPosition;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtPosition=itemView.findViewById(R.id.txtPosition);
            txtNationality = itemView.findViewById(R.id.txtNationality);
            txtGamesPlayed = itemView.findViewById(R.id.txtGamesPlayed);
            txtGoalsScored = itemView.findViewById(R.id.txtGoalsScored);
            txtAssists = itemView.findViewById(R.id.txtAssists);
        }

        public void bind(Player player) {
            Picasso.get().load(player.getPhotoUrl()).into(imgPhoto);
            txtFullName.setText(player.getFirstName() + " " + player.getLastName());
            txtAge.setText("Yaş: " + player.getAge());
            txtPosition.setText("Pozisyon: "+player.getPosition());
            txtNationality.setText("Uyruk: " + player.getNationality());
            txtGamesPlayed.setText("Oynanan Maç: " + player.getGamesPlayed());
            txtGoalsScored.setText("Gol: " + player.getGoalsScored());
            txtAssists.setText("Asist: " + player.getAssists());
        }
    }

}

