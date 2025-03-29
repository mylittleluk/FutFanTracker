package br.edu.utfpr.futfantracker.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.futfantracker.modelo.Partida;

@Dao
public interface PartidaDao {

    @Insert
    long insert(Partida partida);

    @Delete
    int delete(Partida partida);

    @Update
    int update(Partida partida);

    @Query("SELECT * FROM partida WHERE id=:id")
    Partida queryForId(long id);

    @Query("SELECT * FROM partida ORDER BY local DESC")
    List<Partida> queryAllAscending();

    @Query("SELECT * FROM partida ORDER BY local ASC")
    List<Partida> queryAllDescending();
}
