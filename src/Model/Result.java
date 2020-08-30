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
public class Result {

    private int id;
    private int studentId;
    private float gpa;
    private String hocki;
    private String hocluc;
    private int tcQua;
    private int tcNo;
    private String status;
    
    public Result(int id,int student_id,float gpa,String hocki,String hocluc,int tcQua,int tcNo,String status){
        this.id = id;
        this.studentId = student_id;
        this.gpa = gpa;
        this.hocki = hocki;
        this.hocluc = hocluc;
        this.tcQua = tcQua;
        this.tcNo = tcNo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int age) {
        this.studentId = studentId;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getHocKi() {
        return hocki;
    }

    public void setHocKi(String grades) {
        this.hocki = hocki;
    }
    
    public String getHocLuc() {
        return hocluc;
    }

    public void setHocLuc(String hocluc) {
        this.hocluc = hocluc;
    }
    
        public int getTcQua() {
        return tcQua;
    }

    public void setTcQua(int tcQua) {
        this.tcQua = tcQua;
    }
    
        public int getTcNo() {
        return tcNo;
    }

    public void setTcNo(int tcNo) {
        this.tcNo = tcNo;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
}
