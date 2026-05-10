package domain;

public abstract class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return user.getUsername().equals(username);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}


