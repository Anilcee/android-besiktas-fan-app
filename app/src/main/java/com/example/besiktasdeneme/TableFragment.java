package com.example.besiktasdeneme;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TableFragment extends Fragment {

    private TableLayout tableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        TableFragment.ApiRequestTask apiRequestTask = new TableFragment.ApiRequestTask();
        apiRequestTask.execute();
        return view;
    }

    private class ApiRequestTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            String apiKey = "";
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=2024&league=203")
                    .get()
                    .addHeader("X-RapidAPI-Key",apiKey)
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    return jsonObject.getJSONArray("response");
                } else {
                    Log.e("API Request", "İstek başarısız: " + response.code());
                }
            } catch (IOException | JSONException e) {
                Log.e("API Request", "Hata oluştu: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray responseArray) {
            if (responseArray != null && getActivity() != null) {
                try {
                    tableLayout.removeAllViews();

                    TableRow titleRow = new TableRow(getActivity());
                    titleRow.setPadding(9, 10, 10, 10);

                    TextView logoTitle = createTextView("");
                    TextView teamTitle = createTextView("Takım");
                    TextView winsTitle = createTextView("G");
                    TextView drawsTitle = createTextView("B");
                    TextView lossesTitle = createTextView("M");
                    TextView goalDifferenceTitle = createTextView("A");
                    TextView pointsTitle = createTextView("P");

                    titleRow.addView(logoTitle);
                    titleRow.addView(teamTitle);
                    titleRow.addView(winsTitle);
                    titleRow.addView(drawsTitle);
                    titleRow.addView(lossesTitle);
                    titleRow.addView(goalDifferenceTitle);
                    titleRow.addView(pointsTitle);

                    tableLayout.addView(titleRow);

                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject responseObj = responseArray.getJSONObject(i);
                        JSONArray standingsArray = responseObj.getJSONObject("league").getJSONArray("standings");

                        for (int j = 0; j < standingsArray.length(); j++) {
                            JSONArray teamArray = standingsArray.getJSONArray(j);

                            for (int k = 0; k < teamArray.length(); k++) {
                                JSONObject teamObj = teamArray.getJSONObject(k);

                                String teamName = teamObj.getJSONObject("team").getString("name");
                                String teamLogo = teamObj.getJSONObject("team").getString("logo");
                                int wins = teamObj.getJSONObject("all").getInt("win");
                                int draws = teamObj.getJSONObject("all").getInt("draw");
                                int losses = teamObj.getJSONObject("all").getInt("lose");
                                int points = teamObj.getInt("points");
                                int goalDifference = teamObj.getInt("goalsDiff");

                                TableRow tableRow = new TableRow(getActivity());
                                tableRow.setBackgroundColor(Color.WHITE);
                                tableRow.setPadding(10, 10, 10, 10);

                                ImageView logoImageView = new ImageView(getActivity());
                                Picasso.get().load(teamLogo).resize(80, 80).into(logoImageView);

                                tableRow.addView(logoImageView);

                                TextView teamNameTextView = createTextView(teamName);
                                TextView winsTextView = createTextView(String.valueOf(wins));
                                TextView drawsTextView = createTextView(String.valueOf(draws));
                                TextView lossesTextView = createTextView(String.valueOf(losses));
                                TextView goalDifferenceTextView = createTextView(String.valueOf(goalDifference));
                                TextView pointsTextView = createTextView(String.valueOf(points));

                                tableRow.addView(teamNameTextView);
                                tableRow.addView(winsTextView);
                                tableRow.addView(drawsTextView);
                                tableRow.addView(lossesTextView);
                                tableRow.addView(goalDifferenceTextView);
                                tableRow.addView(pointsTextView);

                                tableLayout.addView(tableRow);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private TextView createTextView(String text) {
            if (getActivity() == null) {
                return null;
            }
            TextView textView = new TextView(getActivity());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10, 10, 10, 10);
            textView.setText(text);
            return textView;
        }
    }
}
