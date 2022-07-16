package server;

import shared.model.Book;
import shared.model.Credentials;
import shared.model.User;

import java.util.ArrayList;

public class Library {
    private ArrayList<User> users;
    private ArrayList<Book> books;

    private int lastId = 0;

    public Library() {
        users = new ArrayList<>();
        books = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username) && user.getCredentials().getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username)) {
                return false;
            }
        }
        addUser(username, password);
        return true;
    }

    private void addUser(String username, String password) {
        users.add(new User(lastId++, new Credentials(username, password)));
    }
}
