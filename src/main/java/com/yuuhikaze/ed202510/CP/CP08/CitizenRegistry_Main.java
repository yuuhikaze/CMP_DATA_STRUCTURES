package com.yuuhikaze.ed202510.CP.CP08;

import com.yuuhikaze.ed202510.enums.Gender;

public class CitizenRegistry_Main {
    public static void main(String[] args) {
        System.out.println("=== CITIZEN REGISTRY SYSTEM (Hash-based Storage) ===\n");

        CitizenRegistryController registry = new CitizenRegistryController();

        // Register citizens
        System.out.println("--- Registering Citizens ---\n");

        registry.addCitizen(new Citizen("1327182359", "John Anderson Smith", Gender.Male,
                "123 Main Street, Quito, Pichincha"));

        registry.addCitizen(new Citizen("1327182352", "Alice Marie Johnson", Gender.Female,
                "456 Elm Avenue, Guayaquil, Guayas"));

        registry.addCitizen(new Citizen("1327182353", "Bob Carlos Martinez", Gender.Male,
                "789 Oak Boulevard, Cuenca, Azuay"));

        registry.addCitizen(new Citizen("1327182356", "Jack Robert Williams", Gender.Male,
                "321 Pine Road, Ambato, Tungurahua"));

        registry.addCitizen(new Citizen("1705234891", "Maria Elena Garcia", Gender.Female,
                "654 Cedar Lane, Manta, Manabi"));

        registry.addCitizen(new Citizen("0912345678", "Carlos Alberto Mendez", Gender.Male,
                "987 Birch Street, Loja, Loja"));

        // Try to add duplicate
        System.out.println("\n--- Attempting to add duplicate citizen ---\n");
        registry.addCitizen(new Citizen("1327182359", "Duplicate Person", Gender.Male,
                "Should not be registered"));

        // Display all citizens
        registry.displayAllCitizens();

        // Query specific citizens
        System.out.println("\n--- Querying Citizen Records ---\n");

        registry.printCitizenRecord("1327182359");
        registry.printCitizenRecord("1705234891");
        registry.printCitizenRecord("0912345678");

        // Try to query non-existent citizen
        System.out.println("\n--- Querying non-existent citizen ---\n");
        registry.printCitizenRecord("9999999999");

        System.out.println("\n=== END OF DEMONSTRATION ===");
    }
}
