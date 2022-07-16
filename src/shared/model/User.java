package shared.model;

public class User {
    private String name, username;
    private int id;

    private Credentials credentials;

    public User(int id, Credentials credentials) {
        this.id = id;
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
