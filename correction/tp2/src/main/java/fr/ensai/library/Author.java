package fr.ensai.library;

import java.util.Objects;

public class Author extends Person {

    // Attributes
    private String nationality;

    /**
     * Constructs a new Author.
     */
    public Author(String name) {
        super(name);
    }

    // Methods
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Author author = (Author) obj;
        return Objects.equals(this.name, author.name);
    }

    @Override
    public String toString() {
        return String.format("Author %s", this.name);
    }
}
