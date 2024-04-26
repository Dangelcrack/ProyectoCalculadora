package com.github.dangelcrack.model.entity;
import java.util.Objects;

public class Move {
    private String nameMove;
    private String typeMove;
    private String category;
    private int power;

    public Move(String nameMove, String typeMove, String category, int power) {
        this.nameMove = nameMove;
        this.typeMove = typeMove;
        this.category = category;
        this.power = power;
    }

    public Move() {
    }

    public String getNameMove() {
        return nameMove;
    }

    public void setNameMove(String nameMove) {
        this.nameMove = nameMove;
    }

    public String getTypeMove() {
        return typeMove;
    }

    public void setTypeMove(String typeMove) {
        this.typeMove = typeMove;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(nameMove, move.nameMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameMove);
    }

    @Override
    public String toString() {
        return "Move{" +
                "nameMove='" + nameMove + '\'' +
                ", typeMove='" + typeMove + '\'' +
                ", category='" + category + '\'' +
                ", power=" + power +
                '}';
    }
}