package br.edu.utfpr.futfantracker.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.edu.utfpr.futfantracker.R;

public final class UtilsAlert {

    private UtilsAlert(){};

    // Quando não quero ter tratamentos de botão
    public static void mostrarAviso(Context context, int idMensagem){
        mostrarAviso(context, context.getString(idMensagem), null);
    }

    // Quanto tenho um ID de String já pronto/salvo
    public static void mostrarAviso(Context context, int idMensagem, DialogInterface.OnClickListener listener){
        mostrarAviso(context, context.getString(idMensagem), listener);
    }

    public static void mostrarAviso(Context context, String mensagem, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(mensagem);
        builder.setNeutralButton(R.string.ok, listener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmarAcao(Context context, int idMensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao){
        confirmarAcao(context, context.getString(idMensagem), listenerSim, listenerNao);
    }

    public static void confirmarAcao(Context context, String mensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);
        builder.setNegativeButton("No", listenerNao);
        builder.setPositiveButton("Yes", listenerSim);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
