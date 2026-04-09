package fr.ensai.mediaplayer;

/**
 * Represents a podcast.
 */
public class Podcast extends Media {
    private String host;
    private String topic;
    private String subtitles;

    /**
     * Constructs a new Podcast object.
     *
     * @param title     The title of the podcast.
     * @param host      The host of the podcast.
     * @param topic     The topic of the podcast.
     * @param duration  The duration of the podcast in seconds.
     * @param year      The year the podcast was released.
     * @param subtitles The subtitles of the podcast.
     */
    public Podcast(String title, String host, String topic, int year, int duration, String subtitles) {
        super(title, duration, year);
        this.host = host;
        this.topic = topic;
        this.subtitles = subtitles;
    }

    /**
     * String representation of the Song.
     */
    @Override
    public String toString() {
        return String.format("Podcast %s by %s", this.title, this.host);
    }

    @Override
    public String getText() {
        return this.subtitles;
    }
}