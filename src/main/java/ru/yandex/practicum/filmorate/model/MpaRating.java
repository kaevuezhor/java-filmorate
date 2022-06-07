package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MpaRating {
    @NotNull
    private int id;
    private String name;

    public MpaRating (int id, String name) {
        this.id = id;
        switch (id) {
            case 1:
                this.name = "G";
                break;
            case 2:
                this.name = "PG";
                break;
            case 3:
                this.name = "PG-13";
                break;
            case 4:
                this.name = "R";
                break;
            case 5:
                this.name = "NC-17";
                break;
            default:
                this.name = name;
        }
    }

    public MpaRating (String name) {
        this.name = name;
        switch (name) {
            case "G":
                this.id = 1;
                break;
            case "PG":
                this.id = 2;
                break;
            case "PG-13":
                this.id = 3;
                break;
            case "R":
                this.id = 4;
                break;
            case "NC-17":
                this.id = 5;
                break;
            default:
                this.id = 0;
        }
    }
}
