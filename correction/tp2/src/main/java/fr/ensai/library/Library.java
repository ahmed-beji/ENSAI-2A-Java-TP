package fr.ensai.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    // Attributes
    private String name;
    private List<Item> items;
    private List<Loan> activeLoans;
    private List<Loan> completedLoans;

    /**
     * Constructs a new Library object.
     */
    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.activeLoans = new ArrayList<>();
        this.completedLoans = new ArrayList<>();
    }

    // Method to add a book to the library's collection
    public void addIem(Item item) {
        this.items.add(item);
    }

    public List<Loan> getActiveLoans() {
        return this.activeLoans;
    }

    /**
     * Displays all items currently present in the library.
     */
    public void displayItems() {
        if (this.items.isEmpty()) {
            System.out.println("\n[!] The library is empty.");
            return;
        }

        String rowFormat = "| %-10s | %-50s |%n";
        String separator = "+------------+----------------------------------------------------+";

        System.out.println("\n" + separator);
        System.out.printf(rowFormat, "Type", "Title");
        System.out.println(separator);

        for (Item item : this.items) {
            System.out.printf(rowFormat,
                    item.getClass().getSimpleName(),
                    item.getTitle());
        }

        System.out.println(separator);
    }

    /**
     * Retrieves a list of books written by a specific author.
     *
     * @param author The author whose books are to be found.
     * @return A list of books written by the given author.
     */
    public List<Book> getBooksByAuthor(Author author) {
        ArrayList<Book> result = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Book book && book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Finds the active loan for a given item, if it exists.
     *
     * @param item The item to check for an active loan.
     * @return The Loan object if the item is currently loaned out, otherwise null.
     */
    public Loan findActiveLoanForItem(Item item) {
        if (this.activeLoans != null) {
            for (Loan loan : this.activeLoans) {
                if (loan.getItem().equals(item)) {
                    return loan;
                }
            }
        }
        return null;
    }

    /**
     * Loans an item to a student, adding a new Loan to activeLoans.
     *
     * @param item    The item to be loaned.
     * @param student The student borrowing the item.
     * @return true if the loan was successful, false otherwise.
     */
    public boolean loanItem(Item item, Student student) {
        if (item == null || student == null || this.findActiveLoanForItem(item) != null) {
            return false;
        }

        Loan newLoan = new Loan(item, student, new Date());
        this.activeLoans.add(newLoan);
        return true;
    }

    /**
     * Renders an item, moving the corresponding loan from activeLoans to
     * completedLoans and adding an end date.
     *
     * @param item The item to be rendered.
     * @return true if the item was successfully rendered, false otherwise (e.g.,
     *         item not found in active loans).
     */
    public boolean renderItem(Item item) {
        if (item == null) {
            return false;
        }

        Loan loanToRender = this.findActiveLoanForItem(item);

        if (loanToRender == null) {
            return false;
        }

        // Add returnDate and Move loan from activeLoans to completedLoans
        loanToRender.setReturnDate(new Date());
        this.activeLoans.remove(loanToRender);
        this.completedLoans.add(loanToRender);

        return true;
    }

    /**
     * Loads books from a CSV file and adds them to the library.
     * 
     * @param filePath The path to the CSV file containing book data.
     * @throws IOException If there is an error reading the file, an
     *                     {@link IOException} will be thrown.
     */
    public void loadBooksFromCSV(String filePath) {

        URL url = getClass().getClassLoader().getResource(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(url.getFile()))) {
            Map<String, Author> authors = new HashMap<>();
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 5) {
                    String isbn = data[0].trim();
                    String title = data[1].trim();
                    String authorName = data[2].trim();
                    int year = Integer.parseInt(data[3].trim());
                    int pageCount = Integer.parseInt(data[4].trim());

                    // Check if author already exists in the map
                    Author author = authors.get(authorName);
                    if (author == null) {
                        author = new Author(authorName);
                        authors.put(authorName, author);
                        // System.out.println(String.format("Create %s", author));
                    }
                    Book book = new Book(isbn, title, author, year, pageCount);

                    this.addIem(book);
                }
            }
        } catch (

        IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

}