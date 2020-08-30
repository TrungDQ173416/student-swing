/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import subComponent.MenuItemPane;

/**
 *
 * @author 84969
 */
public class Score {
    private int score_id;
    private int studentId;
    private String name;
    private int tin;
    private float mid_score;
    private float last_score;
    private String grades;
    private String hocki;
    private float heso;
    
        public Score(String name, int studentId, float mid_score, float last_score, String hocki, String grades, int tin,float heso) {
        this.name = name;
        this.studentId = studentId;
        this.mid_score = mid_score;
        this.last_score = last_score;
        this.hocki = hocki;
        this.heso = heso;
        this.grades = grades;
        this.tin = tin;
    }

    public Score(int score_id,String name, int studentId, float mid_score, float last_score, String hocki, String grades, int tin,float heso) {
        this.score_id = score_id;
        this.name = name;
        this.studentId = studentId;
        this.mid_score = mid_score;
        this.last_score = last_score;
        this.hocki = hocki;
        this.heso = heso;
        this.grades = grades;
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int age) {
        this.studentId = studentId;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }
    
        public float getMidScore() {
        return mid_score;
    }

    public void setMidScore(float mid_score) {
        this.mid_score = mid_score;
    }

    public float getHeSo() {
        return heso;
    }

    public void setHeSo(float heso) {
        this.heso = heso;
    }

    public float getLastScore() {
        return last_score;
    }

    public void setLastScore(float last_score) {
        this.last_score = last_score;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }
    
    public String getHocKi() {
        return hocki;
    }

    public void setHocKi(String hocki) {
        this.hocki = hocki;
    }
    
    public int getId(){
        return score_id;
    }
    
    public void setId(int score_id){
        this.score_id = score_id;
    }
    
    
    
    

    @Override
    public String toString() {
        return this.name;
    }
}
