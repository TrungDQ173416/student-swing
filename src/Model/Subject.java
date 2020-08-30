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
public class Subject {
    private int id;
    private String name;
    private int tin;
    private float heso;
    
    public Subject(int id, String name,int tin, float heso){
        this.id = id;
        this.name = name;
        this.tin = tin;
        this.heso = heso;
    }
     public Subject(String name,int tin, float heso){
        this.name = name;
        this.tin = tin;
        this.heso = heso;
     }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getTin(){
        return tin;
    }
    
    public void setTin(int  tin){
        this.tin = tin;
    } 
    
       public float getHeso(){
        return heso;
    }
    
    public void setHeso(float heso){
        this.heso = heso;
    } 
    
    @Override
	public String toString() {
		return this.name;
	}
}
