package com.example.besiktasdeneme;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class AnthemFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private ListView songList;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;

    private int[] songResources = {
            R.raw.gucune_guc,
            R.raw.hadi_hisset,
            R.raw.besiktasim,
            R.raw.gundogdu,
            R.raw.sen_benim
    };


    public static AnthemFragment newInstance() {
        return new AnthemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anthem, container, false);

        playButton = view.findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        songList = view.findViewById(R.id.song_list);
        songList.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, getSongTitles());
        songList.setAdapter(adapter);

        mediaPlayer = MediaPlayer.create(requireContext(), songResources[currentSongIndex]);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_button) {
            if (isPlaying) {
                mediaPlayer.pause();
                playButton.setText("Devam Et");
            } else {
                mediaPlayer.start();
                playButton.setText("Durdur");
            }
            isPlaying = !isPlaying;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSongIndex = position;
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(requireContext(), songResources[currentSongIndex]);
        mediaPlayer.start();
        playButton.setText("Durdur");
        isPlaying = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private String[] getSongTitles() {
        return new String[]{
                "Gücüne Güç Katmaya Geldik",
                "Hadi Hisset",
                "Beşiktaşım Sevmişiz Seni",
                "Gündoğdu",
                "Sen Benim Her Gece Efkarım",
        };
    }
}
