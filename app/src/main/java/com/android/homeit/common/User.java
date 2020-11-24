package com.android.homeit.common;

public class User {

    public String first, lname, email, pass, dob, contactno,gen;

    public User(){}

    public User(String fname, String lname, String email, String pass, String dob, String contactno, String gen){
        this.first = fname;
        this.lname = lname;
        this.email = email;
        this.pass = pass;
        this.dob = dob;
        this.contactno = contactno;
        this.gen = gen;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getFirst() {
        return first;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getDob() {
        return dob;
    }

    public String getContactno() {
        return contactno;
    }

    public String getGen() {
        return gen;
    }
}
