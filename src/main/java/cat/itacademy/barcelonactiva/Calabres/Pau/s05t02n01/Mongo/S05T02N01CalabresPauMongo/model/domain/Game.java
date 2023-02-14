package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Game {
    //columnes
    private String gameresult;
    private int dice1;
    private int dice2;
    private int result;

    public Game(String gameresult, int dice1, int dice2, int result) {
        this.gameresult = gameresult;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.result = result;
    }
}
