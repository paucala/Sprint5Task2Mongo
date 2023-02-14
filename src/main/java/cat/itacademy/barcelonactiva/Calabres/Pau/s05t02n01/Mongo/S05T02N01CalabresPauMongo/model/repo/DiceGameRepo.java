package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.repo;


import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Game;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Player;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.GameDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DiceGameRepo extends MongoRepository<Player, String> {
    boolean existsByName(String name);

}
