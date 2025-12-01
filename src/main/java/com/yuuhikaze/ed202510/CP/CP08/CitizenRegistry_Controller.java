package com.yuuhikaze.ed202510.CP.CP08;

import com.yuuhikaze.ed202510.TDA.ProbeHashMap;

class CitizenRegistryController {
    private ProbeHashMap<String, Citizen> registry;
    private static final int REGISTRY_SIZE = 100;

    public CitizenRegistryController() {
        this.registry = new ProbeHashMap<>(REGISTRY_SIZE);
    }

    /**
     * Computes the hash key from the cedula (last 3 digits)
     */
    private String computeKey(String cedula) {
        if (cedula.length() < 3) {
            return cedula;
        }
        return cedula.substring(cedula.length() - 3);
    }

    /**
     * Adds a citizen to the registry
     * Returns true if successful, false if cedula already exists
     */
    public boolean addCitizen(Citizen citizen) {
        String key = computeKey(citizen.getCedula());

        // Check if citizen already exists
        if (registry.get(key) != null) {
            Citizen existing = registry.get(key);
            if (existing.getCedula().equals(citizen.getCedula())) {
                System.out.println("ERROR: Citizen with cedula " + citizen.getCedula() + " already registered");
                return false;
            }
        }

        registry.put(key, citizen);
        System.out.println("SUCCESS: Citizen " + citizen.getFullName() + " registered with key " + key);
        return true;
    }

    /**
     * Retrieves and prints a citizen's record by cedula
     */
    public void printCitizenRecord(String cedula) {
        String key = computeKey(cedula);
        Citizen citizen = registry.get(key);

        if (citizen == null) {
            System.out.println("ERROR: No citizen found with cedula " + cedula);
            return;
        }

        // Verify it's the correct citizen (in case of collision)
        if (!citizen.getCedula().equals(cedula)) {
            System.out.println("ERROR: No citizen found with cedula " + cedula);
            return;
        }

        System.out.println("\nCitizen Record for Cedula: " + cedula);
        System.out.println(citizen);
    }

    /**
     * Displays all citizens in the registry
     */
    public void displayAllCitizens() {
        System.out.println("\n=== ALL REGISTERED CITIZENS ===");
        int count = 0;
        for (Citizen citizen : registry.values()) {
            if (citizen != null) {
                System.out.println((++count) + ". " + citizen.getFullName() +
                                 " (Cedula: " + citizen.getCedula() + ")");
            }
        }
        System.out.println("Total citizens: " + count);
    }
}
