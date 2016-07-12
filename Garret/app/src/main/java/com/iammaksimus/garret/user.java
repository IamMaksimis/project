package com.iammaksimus.garret;

/**
 * Represents an item in a ToDo list
 */
public class user {

    /**
     * Item text
     */


    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("nick")
    private String nick;

    /**
     * Indicates if the item is completed
     */
    @com.google.gson.annotations.SerializedName("name")
    private String name;

    @com.google.gson.annotations.SerializedName("firstname")
    private String firstname;


    @com.google.gson.annotations.SerializedName("password")
    private String password;

    @com.google.gson.annotations.SerializedName("tvshow")
    private String tvshow;

    @com.google.gson.annotations.SerializedName("friends")
    private String friends;


    /**
     * ToDoItem constructor
     */
    public user() {

    }

  /*  @Override
    public String toString() {
        return getText();
    }
*/
    /**
     * Initializes a new ToDoItem
     *
     *
     *            The item text
     * @param id
     *            The item id
     */
    public user(String id, String nick, String name, String firstname, String password, String tvshow, String friends) {
        this.setId(id);
        this.setNick(nick);
        this.setName(name);
        this.setFirstname(firstname);
        this.setPassword(password);
        this.setTvshow(tvshow);
        this.setFriends(friends);
    }

    /**
     * Returns the item text
     */
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getNick() {
        return nick;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getPassword() {
        return password;
    }

    public String getFriends() {
        return friends;
    }

    public String getTvshow() {
        return tvshow;
    }

    /**
     * Sets the item text
     *
     * @param text
     *            text to set
     */


    /**
     * Returns the item id
     */


    /**
     * Sets the item id
     *
     * @param id
     *            id to set
     */
    public final void setId(String id) {
        this.id = id;
    }

    public final void setNick(String nick) {
        this.nick = nick;
    }
    public final void setName(String name) {
        this.name = name;
    }

    public final void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public void setTvshow(String tvshow) {
        this.tvshow = tvshow;
    }

    /**
     * Indicates if the item is marked as completed
     */


    /**
     * Marks the item as completed or incompleted
     */


    @Override
    public boolean equals(Object o) {
        return o instanceof user && ((user) o).id == id;
    }
}