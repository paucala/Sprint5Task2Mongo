package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.controllers;


import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.PlayerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DiceGameController {
    //POST: /players: crea un jugador/a.
    ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto);
    //PUT /players: modifica el nom del jugador/a.
    ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") String id, @RequestBody PlayerDto playerDto);
    //POST /players/{id}/games/ : un jugador/a específic realitza una tirada dels daus.
    ResponseEntity<GameDto> createGame(@PathVariable("id") String id);
    //DELETE /players/{id}/games: elimina les tirades del jugador/a.
    ResponseEntity<HttpStatus> deleteGames(@PathVariable("id") String id);
    //GET /players/: retorna el llistat de tots els jugadors/es del sistema amb el seu percentatge mitjà d’èxits.
    ResponseEntity<List<PlayerDto>> getAllPlayers();
    //GET /players/{id}/games: retorna el llistat de jugades per un jugador/a.
    ResponseEntity<List<GameDto>> getPlayerGamesById(@PathVariable("id") String id);
    //GET /players/ranking: retorna el ranking mig de tots els jugadors/es del sistema. És a dir, el  percentatge mitjà d’èxits.
    ResponseEntity<Float> getTotalRanking();
    //GET /players/ranking/loser: retorna el jugador/a  amb pitjor percentatge d’èxit.
    ResponseEntity<PlayerDto> getWorstPlayer();
    //GET /players/ranking/winner: retorna el  jugador amb pitjor percentatge d’èxit.
    ResponseEntity<PlayerDto> getBestPlayer();










}
