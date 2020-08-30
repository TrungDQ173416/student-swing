/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author 84969
 */
public class User {
    private int id;
    private String userName;
    private String passWord;
    private String role;
    
    public User(int id, String username,String password, String role){
        this.id = id;
        this.userName = username;
        this.passWord = password;
        this.role = role;
    }
        public User(String username,String password, String role){
        this.userName = username;
        this.passWord = password;
        this.role = role;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getPassWord(){
        return passWord;
    }
    
    public void setPassWord(String passWord){
        this.passWord = passWord;
    } 
    
       public String getRole(){
        return role;
    }
    
    public void setRole(String role){
        this.role = role;
    } 
    
    @Override
	public String toString() {
		return this.userName;
	}
}
