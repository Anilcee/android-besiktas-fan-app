package com.example.besiktasdeneme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.besiktasdeneme.FixtureData;
import com.example.besiktasdeneme.FixtureDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FixtureFragment extends Fragment {

    private RecyclerView recyclerView;
    private FixtureDataAdapter matchAdapter;

    public static FixtureFragment newInstance() {
        FixtureFragment fragment = new FixtureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiRequestTask apiRequestTask = new ApiRequestTask();
        apiRequestTask.execute();

        return view;
    }

    private class ApiRequestTask extends AsyncTask<Void, Void, List<FixtureData>> {

        @Override
        protected List<FixtureData> doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            String apiKey ="";
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=203&season=2024&team=549&last=6")
                    .get()
                    .addHeader("X-RapidAPI-Key", apiKey)
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    List<FixtureData> matchList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject matchObject = jsonArray.getJSONObject(i);
                        JSONObject fixtureObject = matchObject.getJSONObject("fixture");

                        String date = fixtureObject.getString("date").substring(0, 10);
                        String teamLogo1 = matchObject.getJSONObject("teams").getJSONObject("home").getString("logo");
                        String teamLogo2 = matchObject.getJSONObject("teams").getJSONObject("away").getString("logo");
                        String teamName1 = matchObject.getJSONObject("teams").getJSONObject("home").getString("name");
                        String teamName2 = matchObject.getJSONObject("teams").getJSONObject("away").getString("name");
                        String score1 = matchObject.getJSONObject("goals").getString("home");
                        String score2 = matchObject.getJSONObject("goals").getString("away");

                        FixtureData match = new FixtureData(date, teamLogo1, teamLogo2, teamName1, teamName2, score1, score2);
                        matchList.add(match);
                    }

                    return matchList;
                } else {
                    Log.e("API Request", "İstek başarısız: " + response.code());
                }
            } catch (IOException | JSONException e) {
                Log.e("API Request", "Hata oluştu: " + e.getMessage());
            }

            return null;
        }

        protected void onPostExecute(List<FixtureData> matchList) {
            if (matchList != null) {
                matchAdapter = new FixtureDataAdapter(matchList, getActivity());
                recyclerView.setAdapter(matchAdapter);
            }
        }
    }
}
