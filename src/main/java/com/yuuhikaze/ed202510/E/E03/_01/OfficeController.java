package com.yuuhikaze.ed202510.E.E03._01;

class OfficeController {

    // ProbeHashMap is used since for archival purposes it is not desirable
    // to have nesting (ChainHashMap's behavior)
    private ProbeHashMap_C<String, ClientEntity> registry;

    public OfficeController() {
        this.registry = new ProbeHashMap_C<>();
    }

    public void addBatchOfClients(ClientEntity[] clients) {
        // Check if registry conditions is met
        if (clients.length != 11) {
            System.err.println("Can't add batch of clients which size is not eleven.");
            return;
        }
        // Add clients
        for (ClientEntity client : clients) {
            registry.put(client.getID(), client);
        }
    }
}
