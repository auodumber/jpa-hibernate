package org.demo.onetone;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int serialNo;

    private LocalDate expDate;

    @OneToOne(mappedBy = "passport",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    //Passport also know about the relationship but the owner of the relationship is Person
    //cascade = CascadeType.REMOVE if child object removed then parent also removed
    private Person person;

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "serialNo=" + serialNo +
                ", expDate=" + expDate +
                ", person=" + person +
                '}';
    }
}
