package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto;

import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Game;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PlayerDto {
    private String id;
    private String name;
    private LocalDateTime createDateTime;
    private List<Game> games;
    private float exitpercent;
    public PlayerDto(String name) {
        this.name = name;
        this.games = new ArrayList<>();
    }
    public PlayerDto(){
        this.games = new ArrayList<>();
    }
}
