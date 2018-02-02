/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import server.db.UsersRepository;

/**
 *
 * @author Anthony
 */
public class User {
    private Integer id;
    private String pseudo;
    private String mdp;
    
    
    public User(String s, String s1){
        this.pseudo=s;
        this.mdp=s1;
    }
    public User(Integer i, String s, String s1){
        this(s,s1);
        this.id=i;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    
}
