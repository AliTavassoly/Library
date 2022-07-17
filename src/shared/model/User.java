package shared.model;

public class User {
    private String name;
    private final Credentials credentials;

    private final int id;

    public User(int id, Credentials credentials) {
        this.id = id;
        this.credentials = credentials;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
