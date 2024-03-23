package com.example.jaxschoolpro.model;

import com.example.jaxschoolpro.dto.TeacherInsertDTO;

public class Teacher {
    private Long id;
    private String ssn;
    private String firstname;
    private String lastname;

    public Teacher(){}

    public Teacher(Long id, String ssn, String firstname, String lastname){
        this.id = id;
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public TeacherInsertDTO toDTO(){
        return new TeacherInsertDTO(ssn,firstname,lastname);
    }

    public void fromDTO(TeacherInsertDTO dto){
        this.ssn = dto.getSsn();
        this.firstname = dto.getFirstname();
        this.lastname = dto.getLastname();
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", ssn='" + ssn + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
