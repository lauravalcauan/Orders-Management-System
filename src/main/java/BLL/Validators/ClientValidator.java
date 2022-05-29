package BLL.Validators;

import Model.Client;

public class ClientValidator implements Validator<Client> {

    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 60;

    /**
     * @param client
     */
    public void validate(Client client) {
        String error = "";
        if (client.getName().length() == 0)
            error += "invalid name!\n";
        if (client.getAddress().length() == 0)
            error += "invalid address!\n";
        if (client.getEmail().length() == 0)
            error += "invalid email!\n";
        if (client.getAge() < MIN_AGE || client.getAge() > MAX_AGE)
            error += "invalid age!\n";
        if (error.length()!=0)
            throw new IllegalArgumentException(error);
    }

}
