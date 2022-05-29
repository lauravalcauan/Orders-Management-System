package BLL;

import BLL.Validators.ClientValidator;
import BLL.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientValidator validator;
    private ClientDAO clientDAO;

    public ClientBLL() {
        this.validator = new ClientValidator();
        this.clientDAO = new ClientDAO();
    }

    /**
     * @param id
     * @return
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if(client == null) {
            throw new NoSuchElementException("The client with id= " + id + " was not found!");
        }

        return client;
    }

    /**
     * @param client
     */
    public void insertClient(Client client) {
        validator.validate(client);
        clientDAO.insert(client);
    }

    /**
     * @param id
     */
    public void deleteClient(int id){
        clientDAO.delete(id);
    }

    /**
     * @param client
     */
    public void editClient(Client client) {
        validator.validate(client);
        clientDAO.edit(client);
    }

    /**
     * @return
     */
    public List<Client> findAllClient(){
        return clientDAO.findAll();
    }
}
