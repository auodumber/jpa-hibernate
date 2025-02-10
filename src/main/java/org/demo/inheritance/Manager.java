package org.demo.inheritance;

import jakarta.persistence.Entity;

/**
 * @author Auodumbar
 */
@Entity
public class Manager extends AbstractEmployee{

    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "department='" + department + '\'' +
                '}';
    }
}
