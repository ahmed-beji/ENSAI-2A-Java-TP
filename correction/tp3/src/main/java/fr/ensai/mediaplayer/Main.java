package fr.ensai.mediaplayer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String lyrics = """
                If you wanna run away with me, I know a galaxy
                And I can take you for a ride
                I had a premonition that we fell into a rhythm
                Where the music don't stop for life
                Glitter in the sky, glitter in my eyes
                Shining just the way I like
                If you're feeling like you need a little bit of company
                You met me at the perfect time
                You want me, I want you, baby
                My sugarboo, I'm levitating
                The Milky Way, we're renegading
                Yeah, yeah, yeah, yeah, yeah
                I got you, moonlight, you're my starlight
                I need you all night, come on, dance with me
                I'm levitating
                You, moonlight, you're my starlight (you're the moonlight)
                I need you all night, come on, dance with me
                I'm levitating""";

        Artist duaLipa = new Artist("Dua", "Lipa", null);
        Artist stephenKozmeniuk = new Artist("Kozmeniuk", "Stephen", null);

        Song levitating = new Song(
                "Levitating",
                duaLipa,
                2020,
                207,
                lyrics,
                duaLipa,
                stephenKozmeniuk,
                List.of(MusicalGenre.POP, MusicalGenre.ELECTRONIC));

        // levitating.play();

        Song jump = new Song(
                "Jump",
                new Artist("Madonna", "Ciccone", "American"),
                2005,
                240,
                """
                        There's only so much you can learn in one place
                        The more that I wait, the more time that I waste
                        I haven't got much time to waste, it's time to make my way
                        I'm not afraid of what I'll face, but I'm afraid to stay
                        I'm going down my own road and I can make it alone
                        All work and no fighting, I'll find a place of my own
                        Are you ready to jump?
                        Get ready to jump
                        Don't ever look back, oh baby
                        Yes, I'm ready to jump
                        Just take my hands
                        Get ready to jump""",
                null,
                null,
                List.of(MusicalGenre.POP, MusicalGenre.DISCO));

        Podcast wangariPodcast = new Podcast(
                "Wangari Maathai: The Tree Champion",
                "Eco Heroes",
                "Environmental Activism",
                2023,
                600,
                """
                        Welcome to Eco Heroes.
                        Today, we honor Wangari Maathai, a Kenyan environmentalist.
                        She founded the Green Belt Movement, planting millions of trees.
                        Her work fought deforestation and empowered women.
                        Maathai won the Nobel Peace Prize for her efforts.
                        She proved that environmental protection and peace go hand in hand.
                        Her legacy inspires us to protect our planet.
                        Through her courage, she showed the world that one person can make a difference.
                        Plant trees, empower communities, and honor Wangari Maathai's vision.
                        """);

        Playlist p1 = new Playlist("p1");
        p1.addMedia(levitating);
        p1.addMedia(jump);
        p1.addMedia(wangariPodcast);

        Playlist p2 = new Playlist(p1);

        p1.removeMedia(jump);

        System.out.println("p1 duration : " + p1.getTotalDuration());
        System.out.println("p2 duration : " + p2.getTotalDuration());

        p2.play(false);

        System.out.println("\n" + "-".repeat(100) + "\n");

        Playlist p = new Playlist("ludo");
        List<Media> medias = MediaJsonLoader.load("medias.json");

        p.addMedia(medias);

        p.play(false);

    }
}