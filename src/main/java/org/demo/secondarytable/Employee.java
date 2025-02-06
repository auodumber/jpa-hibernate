package org.demo.secondarytable;

import jakarta.persistence.*;
import org.demo.basics.SaveMethod;

/**
 * @author Auodumbar
 *
 * Use Case: When integrating with an existing database where related fields are stored
 * in separate tables but always retrieved together.

 * With @OneToOne mapping, you must create a separate entity for the second table.
 * SecondaryTable allows you to map multiple tables to a single entity, avoiding unnecessary complexity.
 *
 */

@Entity
@SecondaryTable(
        name = "other_details",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "empid")
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int empId;

    private String empName;

    @Column(table = "other_details")
    private String address; //Suppose this field is from different table


    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
