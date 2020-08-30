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
public class Student {

    private int id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String mssv;
    private String sex;
    private String address;
    private float cpa;
    private String hocluc;
    private String mess;

    public Student(int id, String name, int age, String email, String phone, String mssv, String sex, String address, float cpa, String hocluc) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.mssv = mssv;
        this.sex = sex;
        this.address = address;
        this.cpa = cpa;
        this.hocluc = hocluc;
    }

    public Student(String name, int age, String email, String phone, String mssv, String sex, String address, float cpa, String hocluc) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.mssv = mssv;
        this.sex = sex;
        this.address = address;
        this.cpa = cpa;
        this.hocluc = hocluc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getCpa() {
        return cpa;
    }

    public void setCpa(float cpa) {
        this.cpa = cpa;
    }

    public String getHocLuc() {
        return hocluc;
    }

    public void setHocLuc(String hocluc) {
        this.hocluc = hocluc;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
