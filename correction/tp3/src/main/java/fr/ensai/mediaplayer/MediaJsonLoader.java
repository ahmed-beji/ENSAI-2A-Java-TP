package fr.ensai.mediaplayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MediaJsonLoader {

    public static List<Media> load(String filename) {

        List<Media> medias = new ArrayList<>();

        try (InputStream is = MediaJsonLoader.class
                .getClassLoader()
                .getResourceAsStream(filename)) {

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + filename);
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);

                // Use MediaFactory to create Media objects
                Media media = MediaFactory.create(json);
                medias.add(media);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }

        return medias;
    }
}