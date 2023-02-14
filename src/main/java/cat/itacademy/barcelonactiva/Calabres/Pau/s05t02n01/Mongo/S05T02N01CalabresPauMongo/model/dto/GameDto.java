package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GameDto {

    private String gameresult;
    private int dice1;

    private int dice2;

    private int result;
}
