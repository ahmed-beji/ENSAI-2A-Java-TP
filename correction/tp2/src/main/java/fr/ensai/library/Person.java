package fr.ensai.library;

public class Person {

    // Attributes
    protected String name;
    protected int age;

    /**
     * Constructs a new Person.
     * @param name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Constructs a new Person.
     * @param name
     * @param age
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Methods
    public void birthday() {
        this.age += 1;
    }

    public String toString() {
        return String.format("%s %s",this.getClass().getSimpleName(), this.name);
    }
}
