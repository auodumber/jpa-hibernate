package org.demo.inheritance;

import jakarta.persistence.Entity;

/**
 * @author Auodumbar
 */
@Entity
public class Developer extends AbstractEmployee{
    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "module='" + module + '\'' +
                '}';
    }
}
