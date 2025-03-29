package br.edu.utfpr.futfantracker.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.futfantracker.modelo.Partida;

@Database(entities = {Partida.class}, version = 1, exportSchema = false)
public abstract class PartidasDatabase extends RoomDatabase {

    public abstract PartidaDao getPartidaDao();

    private static PartidasDatabase INSTANCE;

    public static PartidasDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (PartidasDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context,
                                                    PartidasDatabase.class,
                                                    "partidas.db")
                               .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
