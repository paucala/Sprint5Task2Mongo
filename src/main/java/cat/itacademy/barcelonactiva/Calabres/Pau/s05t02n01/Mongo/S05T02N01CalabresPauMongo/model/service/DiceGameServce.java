package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.service;


import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Game;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Player;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.PlayerDto;

import java.util.List;

public interface DiceGameServce {

    //Metodes perr crear partides i jugadors/es
    Game createGame(PlayerDto playerDto);
    Player createPlayer(PlayerDto playerDto);

    //Metodes de comprobació
    boolean existsByIdPlayer (String id);
    boolean existingPlayerbyName (String name);
    boolean existsByIdGame (String id);
    //Metodes per obtenir informació
    PlayerDto getPlayerbyId(String id);
    List<PlayerDto> getAllPlayers();
    List<GameDto> getGamesByPlayer(String id);
    //Metodes d'actualització
    Player updatePlayer (PlayerDto playerDto);
    //Metodes de eliminació
    void deleteGamesFromPlayer(String id);
    //Ranking
    double totalRanking();
    PlayerDto worstPlayer();
    PlayerDto bestPlayer();
    //Metodes auxiliars
    int diceRoll();
    float setPlayerExitPercent(String id);


}
