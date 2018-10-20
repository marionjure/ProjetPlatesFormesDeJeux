package src.application;

import src.application.User;

public class Couple<T, V> {

    private Integer id, posLig, posCol;
    private User user;

    T elem1;
    V elem2;

    public Couple(Integer i, User u) {
        this.id = i;
        this.user = u;
    }

    public Couple(Integer posLig, Integer posCol) {
        this.posLig = posLig;
        this.posCol = posCol;
    }

    public Couple(T elem1, V elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
    }


    public T getElem1() {
        return elem1;
    }

    public V getElem2() {
        return elem2;
    }

    /**
     * Permet d'obtenir l'ID donné à l'utilisateur
     *
     * @return un Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Permet d'obtenir l'utilisateur
     *
     * @return un User
     */
    public User getUser() {
        return user;
    }

    /**
     * Permet de donner un ID à un utilisateur
     *
     * @param id Integer, ID a donné à l'utilisateur
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Permet de modifié l'utilisateur
     *
     * @param user un utilisateur
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPosLig() {
        return posLig;
    }

    public Integer getPosCol() {
        return posCol;
    }

    public void setPosCol(Integer posCol) {
        this.posCol = posCol;
    }

    public void setPosLig(Integer posLig) {
        this.posLig = posLig;
    }
}
