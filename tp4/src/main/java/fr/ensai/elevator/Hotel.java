package fr.ensai.elevator;

import java.util.List;

public class Hotel {

    private List<Floor> floors;
    private List<Elevator> elevators;
    private int deliveredPassengers;

    /**
     * Constructs a hotel with the given floors and elevators.
     *
     * @param floors    the list of floors in the building
     * @param elevators the list of elevators operating in the hotel
     */
    public Hotel(List<Floor> floors, List<Elevator> elevators) {
        this.floors = floors;
        this.elevators = elevators;
        this.deliveredPassengers = 0;
    }

    /**
     * Updates the simulation for one step:
     *
     * <li>Moves all elevators</li>
     * <li>Unloads passengers at their destination floor</li>
     * <li>Loads waiting passengers if capacity allows</li>
     */
    public void update() {
        for (Elevator e : this.elevators) {
            e.move();
        }
        for (Elevator e : this.elevators) {
            Floor currentFloor = this.floors.get(e.getCurrentFloor());
            this.deliveredPassengers += e.unloadPassengers(currentFloor);
            e.loadPassengers(currentFloor);
        }
    }

    /**
     * Attempts to spawn new passengers on each floor.
     */
    public void spawnPerson() {
        for (int i = 0; i < floors.size(); i++) {
            this.floors.get(i).trySpawnPerson(this.elevators);
        }
    }

    /**
     * Displays the current state of the hotel in the console.
     */
    public void display(int step) {
        int floorColWidth = Config.getInt("hotel.display.column-width.floor");
        int elevatorColWidth = Config.getInt("hotel.display.column-width.elevator");
        int personsColWidth = Config.getInt("hotel.display.column-width.persons");

        StringBuilder horizontalBorder = new StringBuilder();
        horizontalBorder.append("+").append("-".repeat(floorColWidth + 2));
        for (int i = 0; i < this.elevators.size(); i++) {
            horizontalBorder.append("+").append("-".repeat(elevatorColWidth + 2));
        }
        horizontalBorder.append("+").append("-".repeat(personsColWidth + 2)).append("+");

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(horizontalBorder);
        System.out.printf("| %-" + floorColWidth + "s ", "Floor");
        for (Elevator elevator : this.elevators) {
            System.out.printf("| %-" + elevatorColWidth + "s ", "Elevator" + (elevator.getId()));
        }
        System.out.printf("| %-" + personsColWidth + "s |\n", "waitingPersons");
        System.out.println(horizontalBorder);
        for (int i = this.floors.size() - 1; i >= 0; i--) {
            this.floors.get(i).display(elevators);
        }
        System.out.println(horizontalBorder);
        System.out.println("Step : " + step + "\tDelivered passengers: " + deliveredPassengers);
    }
}