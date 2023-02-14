package cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.service;

import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Game;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.domain.Player;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.Calabres.Pau.s05t02n01.Mongo.S05T02N01CalabresPauMongo.model.repo.DiceGameRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DiceGameServiceImp implements DiceGameServce{
    //region ATTRIBUTES
    @Autowired
    DiceGameRepo diceGameRepo;
    @Autowired
    private ModelMapper modelMapper;
    //endregion ATTRIBUTES

    //region CREATE
    public Game createGame(PlayerDto playerDto){
        int result1 = diceRoll();
        int result2 =diceRoll();
        int total = result1 + result2;
        Player player = modelMapper.map(playerDto,  Player.class);
        Game game;
        if(total == 7){
            game = new Game("WON", result1, result2, total);
        } else {
            game = new Game("LOST", result1, result2, total);
        }
        player.getGames().add(game);
        diceGameRepo.save(player);
        return game;
    }
    //comprobem que el nom es anonim o no existeix eln la base de dades
    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = modelMapper.map(playerDto, Player.class);
        if(player.getName().equals("ANONIMUS") || !existingPlayerbyName(player.getName())){
            return diceGameRepo.save(player);
        }
        else {
            return null;
        }
    }
    //endregion CREATE

    //region CHECK
    @Override
    public boolean existsByIdPlayer(String id) {
        return diceGameRepo.existsById(id);
    }

    @Override
    public boolean existingPlayerbyName(String name) {
        return diceGameRepo.existsByName(name);
    }

    @Override
    public boolean existsByIdGame(String id) {
        return diceGameRepo.existsById(id);
    }
    //endregion CHECK

    //region GET
    @Override
    public PlayerDto getPlayerbyId(String id) {
        Optional<Player> player = diceGameRepo.findById(id);
        PlayerDto playerDto = modelMapper.map(player.get(), PlayerDto.class);
        return playerDto;
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        List<PlayerDto> players = diceGameRepo.findAll().stream().map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());
        players.forEach(playerDto -> playerDto.setExitpercent(setPlayerExitPercent(playerDto.getId())));
        return players;
    }
    //primer recull tots els jugadors i després

    @Override
    public List<GameDto> getGamesByPlayer(String id) {
        List<GameDto> games = diceGameRepo.findById(id).get().getGames()
                .stream().map(game -> modelMapper.map(game, GameDto.class)).collect(Collectors.toList());
        return games;
    }

    //endregion GET

    //region UPDATE
    //rep un DTO, comproba que el nom és correcte (es anonim o no està repetit) i canvia l'única dada modificable d'un jugador, el nom
    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player existingPlayer = diceGameRepo.findById(playerDto.getId()).get();
        if(playerDto.getName().equals("ANONIMUS") || !existingPlayerbyName(playerDto.getName())){
            existingPlayer.setName(playerDto.getName());
            return diceGameRepo.save(existingPlayer);
        }
        else {
            return null;
        }
    }

    //endregion UPDATE

    //region DELETE
    @Override
    public void deleteGamesFromPlayer(String id) {
        Player player = modelMapper.map(getPlayerbyId(id), Player.class);
        player.getGames().clear();
        diceGameRepo.save(player);
    }
    //endregion DELETE

    //region RANKING
    //Agafa tots els usuaris que hagin jugat alguna partida i retorna el percentatge mig
    @Override
    public double totalRanking() {
        List<PlayerDto> players = getAllPlayers();
        double totalRanking = 0;
        totalRanking = players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0)
                .mapToDouble(playerDto -> playerDto.getExitpercent()).sum()
                / players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).count();
        return totalRanking;
    }
    //Agafa tots els usuaris que hagin jugat alguna partida i retorna el que tingui el pitjor percentatge
    @Override
    public PlayerDto worstPlayer() {
        List<PlayerDto> players = getAllPlayers();
        return players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).min(Comparator.comparingDouble(PlayerDto::getExitpercent)).get();
    }
    //Agafa tots els usuaris que hagin jugat alguna partida i retorna el que tingui el millor percentatge
    @Override
    public PlayerDto bestPlayer() {
        List<PlayerDto> players = getAllPlayers();
        return players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).max(Comparator.comparingDouble(PlayerDto::getExitpercent)).get();
    }
    //endregion RANKING

    //region AUXILIAR
    //Aquest mètode emula l'aleatorietat d'una tirada de daus
    @Override
    public int diceRoll() {
        int[] dice = {1, 2, 3, 4, 5, 6};
        Random random_method = new Random();
        int result = dice[random_method.nextInt(dice.length)];
        return result;
    }

    //Calcula el percentatge si el jugador ha jugat alguna partida, sino torna una excepció (-1) que en front end
    // pot ser reconvertida (p.ex: indicant que el jugador no ha jugat cap partida)
    @Override
    public float setPlayerExitPercent(String id) {
        try {
            int totalGames = (int) diceGameRepo.findById(id).get().getGames().stream().count();
            int wonGames = (int) diceGameRepo.findById(id).get().getGames().stream()
                    .filter(game -> game.getGameresult().equals("WON")).count();
            float exitPercent = (wonGames * 100) / totalGames;
            return exitPercent;
        } catch (Exception e){
            return -1;
        }

    }
    //endregion AUXILIAR

}
