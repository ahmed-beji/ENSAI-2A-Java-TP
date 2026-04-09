package fr.ensai.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MediaFactory {

    public static Media create(JSONObject json) {
        String type = json.getString("type");

        switch (type.toLowerCase()) {

            case "song":
                JSONObject singerJson = json.getJSONObject("singer");
                Artist singer = new Artist(
                        singerJson.getString("firstName"),
                        singerJson.optString("lastName", null),
                        singerJson.optString("nationality", null));
                String lyrics = json.optString("lyrics", null);

                List<MusicalGenre> genres = new ArrayList<>();
                JSONArray genresJson = json.optJSONArray("genres");

                if (genresJson != null) {
                    for (int i = 0; i < genresJson.length(); i++) {
                        String genreStr = genresJson.getString(i);

                        try {
                            genres.add(MusicalGenre.valueOf(genreStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println(String.format("Unknown genre: %s", genreStr));
                        }
                    }
                }

                return new Song(
                        json.getString("title"),
                        singer,
                        json.getInt("year"),
                        json.getInt("duration"),
                        lyrics,
                        null, // author
                        null, // composer
                        genres);

            case "podcast":
                return new Podcast(
                        json.getString("title"),
                        json.getString("host"),
                        json.getString("topic"),
                        json.getInt("year"),
                        json.getInt("duration"),
                        json.optString("subtitles"));

            default:
                throw new IllegalArgumentException("Unknown media type: " + type);
        }
    }
}