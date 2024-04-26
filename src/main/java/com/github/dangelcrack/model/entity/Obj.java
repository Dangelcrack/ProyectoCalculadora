package com.github.dangelcrack.model.entity;

import java.util.Objects;

public class Obj {
    private String nameObject;
    private String description;

    public Obj(String nameObject, String description) {
        this.nameObject = nameObject;
        this.description = description;
    }

    public Obj() {
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return Objects.equals(nameObject, obj.nameObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameObject);
    }

    @Override
    public String toString() {
        return "Object{" +
                "nameObject='" + nameObject + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
