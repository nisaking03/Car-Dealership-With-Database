package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    public Dealership dealership;
    DealershipFileManager dealershipFileManager;

    public UserInterface(){

        DealershipFileManager dealFileManager = new DealershipFileManager();
        dealership =  dealFileManager.getDealership();
    }

    public void display(){

        String mainMenu =
                        "1 - Find vehicles within a price range\n" +
                        "2 - Find vehicles by make / model\n" +
                        "3 - Find vehicles by year range\n" +
                        "4 - Find vehicles by color\n" +
                        "5 - Find vehicles by mileage range\n" +
                        "6 - Find vehicles by type (car, truck, SUV, van)\n" +
                        "7 - List ALL vehicles\n" +
                        "8 - Add a vehicle\n" +
                        "9 - Remove a vehicle\n" +
                        "99 - Sell/Lease a vehicle\n" +
                        "0 - Quit \n";


        while (true) {
            System.out.print(mainMenu);
            int command;

            command = ConsoleHelper.promptForInt("Enter here"); //prompt for main menu

            switch (command) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 99:
                    processSellOrLease();
                    break;
                case 0: //exit
                    return;
                default:
                    System.out.println("Invalid Entry!"); //Error message
                    break;

            }
        }
    }

    // Helper to display any list of vehicles
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.println("\n--- Vehicles ---");
        for (Vehicle v : vehicles) {
            System.out.println(v); // calls to print out each vehicle
        }
    }

    private void processGetByPriceRequest(){
        System.out.println("What is the minimum and maximum price?");
        double minPrice = ConsoleHelper.promptForDouble("Enter minimum price");
        double maxPrice = ConsoleHelper.promptForDouble("Enter maximum price");

        List<Vehicle> result = dealership.getVehicleByPrice(minPrice, maxPrice);
        //displayVehicles(result);
    }
    private void processGetByMakeModelRequest(){
        System.out.println("What is the Make and Model you are looking for?");
        String make = ConsoleHelper.promptForString("Enter Make");
        String model = ConsoleHelper.promptForString("Enter Model");

        //ArrayList<Vehicle> vehiclesByMakeModel = (ArrayList<Vehicle>) dealership.getVehicleByMakeModel(make,model);
        List<Vehicle> result = dealership.getVehicleByMakeModel(make, model);
        displayVehicles(result);
    }
    private void processGetByYearRequest(){
        System.out.println("What is the year you are looking for?");
        int minYear = ConsoleHelper.promptForInt("Enter Minimum Year (YYYY)");
        int maxYear = ConsoleHelper.promptForInt("Enter Maximum Year (YYYY)");

        //ArrayList<Vehicle>vehiclesByYear = (ArrayList<Vehicle>) dealership.getVehicleByYear(minYear,maxYear);
        List<Vehicle> result = dealership.getVehicleByYear(minYear, maxYear);
        displayVehicles(result);
    }
    private void processGetByColorRequest(){
        System.out.println("What vehicle color you are looking for?");
        String color = ConsoleHelper.promptForString("Enter color");

//       ArrayList<Vehicle>vehiclesByColor = (ArrayList<Vehicle>) dealership.getVehicleByColor(color);
//        System.out.println(vehiclesByColor);

        List<Vehicle> result = dealership.getVehicleByColor(color);
        displayVehicles(result);
    }
    private void processGetByMileageRequest(){
        System.out.println("What vehicle mileage you are looking for?");
        int minMileage = ConsoleHelper.promptForInt("Enter minimum mileage");
        int maxMileage = ConsoleHelper.promptForInt("Enter maximum mileage");

        //ArrayList<Vehicle>vehiclesByMileage = (ArrayList<Vehicle>) dealership.getVehicleByMileage(minMileage, maxMileage);
        List<Vehicle> result = dealership.getVehicleByMileage(minMileage, maxMileage);
        displayVehicles(result);
    }
    private void processGetByVehicleTypeRequest(){
        System.out.println("What Type of vehicle are you searching for?");
        String vehicleType = ConsoleHelper.promptForString("Enter vehicle type");

        //ArrayList<Vehicle>vehiclesByType = (ArrayList<Vehicle>) dealership.getVehicleByType(vehicleType);
        List<Vehicle> result = dealership.getVehicleByType(vehicleType);
        displayVehicles(result);
    }
    private void processGetAllVehiclesRequest(){
        displayVehicles(dealership.getAllVehicles());
    }
    private void processAddVehicleRequest(){
        int VIN  = ConsoleHelper.promptForInt("What is the vehicle VIN number");
        int year = ConsoleHelper.promptForInt("What is the Year of your vehicle");
        String make = ConsoleHelper.promptForString("What is the vehicle make?");
        String model = ConsoleHelper.promptForString("What is the vehicle model?");
        String vehicleType = ConsoleHelper.promptForString("What is the vehicle type");
        String color = ConsoleHelper.promptForString("What is the color of the vehicle");
        int odometer = ConsoleHelper.promptForInt("What is the mileage of the vehicle");
        double price = ConsoleHelper.promptForDouble("What is your asking price for the vehicle");

        Vehicle vehicleToAdd = new Vehicle(VIN,year,make,model,vehicleType,color,odometer,price);
        dealership.addVehicle(vehicleToAdd);

        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        DealershipFileManager.saveDealership(dealership);
    }
    private void processRemoveVehicleRequest(){
        int vin  = ConsoleHelper.promptForInt("What is the vehicle VIN number");

        Vehicle found = null; // empty box with placeholder

        for(Vehicle v : dealership.getAllVehicles()){
            if(vin == v.getVin()){
                found = v;

                break;
                //dealership.removeVehicle(v);
                //System.out.println("Vehicle Removed!");
               // this.dealershipFileManager.saveDealership(dealership);

            }
        }

        // Check if matching vehicle Vin was found
        if (found != null) {
            // removes the vehicle with found VIN number
            dealership.getAllVehicles().remove(found);

            // save the updated dealership to the CSV
            DealershipFileManager.saveDealership(dealership);

            System.out.println("Vehicle removed successfully finally......");
        }
        else {
            System.out.println("Could not find that Vehicles VIN");
        }
    }

    //Sell and lease info
    private void processSellOrLease(){
        String sellOrLease =
                "S - Sell your vehicle \n" +
                "L - Lease your vehicle \n" +
                "B - Back to Main menu \n";
        while (true){
            System.out.println(sellOrLease);
            char command;

            command = ConsoleHelper.promptForChar("Enter \"Sell\" or \"Lease\"");

            switch (command){
                case 'S':
                    processSell();
                    break;
                case 'L':
                    processSLease();
                    break;
                case 'B':
                    return;
                default:
                    System.out.println("Invalid Entry!");
                    break;
            }
        }
    }
    private void processSell(){

        //• collect basic sales information from the user
        int VIN  = ConsoleHelper.promptForInt("What is the vehicle VIN number");
//        int year = ConsoleHelper.promptForInt("What is the Year of your vehicle");
//        String make = ConsoleHelper.promptForString("What is the vehicle make?");
//        String model = ConsoleHelper.promptForString("What is the vehicle model?");
//        String vehicleType = ConsoleHelper.promptForString("What is the vehicle type");
//        String color = ConsoleHelper.promptForString("What is the color of the vehicle");
//        int odometer = ConsoleHelper.promptForInt("What is the mileage of the vehicle");
//        double price = ConsoleHelper.promptForDouble("What is your asking price for the vehicle");

        //• add the vehicle information to the contract
        //• ask if it is a sale or lease  (Note: you can't lease a vehicle over 3 years old)
        //• calculate pricing
    }
    private void processSLease(){

        //• collect basic sales information from the user
        int VIN  = ConsoleHelper.promptForInt("What is the vehicle VIN number");
//        int year = ConsoleHelper.promptForInt("What is the Year of your vehicle");
//        String make = ConsoleHelper.promptForString("What is the vehicle make?");
//        String model = ConsoleHelper.promptForString("What is the vehicle model?");
//        String vehicleType = ConsoleHelper.promptForString("What is the vehicle type");
//        String color = ConsoleHelper.promptForString("What is the color of the vehicle");
//        int odometer = ConsoleHelper.promptForInt("What is the mileage of the vehicle");
//        double price = ConsoleHelper.promptForDouble("What is your asking price for the vehicle");

        //• add the vehicle information to the contract
        //• ask if it is a sale or lease  (Note: you can't lease a vehicle over 3 years old)
        //• calculate pricing
    }
}
