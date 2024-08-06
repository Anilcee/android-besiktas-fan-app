package com.example.besiktasdeneme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TeamFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList;



    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        playerList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playerList);
        recyclerView.setAdapter(playerAdapter);

        ApiRequestTask apiRequestTask = new ApiRequestTask();
        apiRequestTask.execute();

        return view;
    }

    class ApiRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            String apiKey = "93faf53b37msh992bc4f6d3cba84p157a38jsn68ba9368a6a2";
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/players?team=549&league=203&season=2024&page=2")
                    .get()
                    .addHeader("X-RapidAPI-Key", apiKey)
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (response != null) {
                Log.d("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray responseArray = jsonObject.optJSONArray("response");

                    if (responseArray != null) {
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject playerObject = responseArray.getJSONObject(i);

                            JSONObject playerInfo = playerObject.getJSONObject("player");
                            String photoUrl = playerInfo.getString("photo");
                            String firstName = playerInfo.getString("firstname");
                            String lastName = playerInfo.getString("lastname");
                            int age = playerInfo.getInt("age");
                            String nationality = playerInfo.getString("nationality");

                            JSONArray statisticsArray = playerObject.getJSONArray("statistics");
                            if (statisticsArray.length() > 0) {
                                JSONObject statisticsObject = statisticsArray.getJSONObject(0);
                                JSONObject gamesObject = statisticsObject.getJSONObject("games");
                                int gamesPlayed = gamesObject.optInt("appearences");
                                int goalsScored = statisticsObject.getJSONObject("goals").optInt("total");
                                int assists = statisticsObject.getJSONObject("goals").optInt("assists");
                                String position = statisticsObject.getJSONObject("games").getString("position");

                                Player player = new Player();
                                player.setPhotoUrl(photoUrl);
                                player.setFirstName(firstName);
                                player.setLastName(lastName);
                                player.setAge(age);
                                player.setNationality(nationality);
                                player.setGamesPlayed(gamesPlayed);
                                player.setGoalsScored(goalsScored);
                                player.setAssists(assists);
                                player.setPosition(position);

                                playerList.add(player);
                            }
                        }
                    }

                    playerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
