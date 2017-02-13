package com.example.eneasseven.acenco.cftv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by Eneas.Seven on 30/01/2017.
 */

public class CapturaImagem extends AsyncTask<String, Bitmap, Bitmap> {

    private ClienteRecebeImagem clienteRecebeImagem;
    private Context context;
    private AtualizaImagem atualizaImagem;
    private boolean processo = false;

    public CapturaImagem(Context context, AtualizaImagem atualizaImagem) {
        this.context = context;
        this.atualizaImagem = atualizaImagem;
    }

    @Override
    protected void onPreExecute() {
         this.processo = true;
         super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        if (params[2].equals(StatusCapturaImagem.INICIO.name())) {
            clienteRecebeImagem = new ClienteRecebeImagem(params[0], Integer.valueOf(params[1]));
        }
        while (processo) {
            byte[] imagem = clienteRecebeImagem.getImagem();
            if (imagem != null) {
                bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                publishProgress(bitmap);
            } else {
                processo = false;
            }
        }

        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        atualizaImagem.atualizar(values[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        atualizaImagem.atualizar(bitmap);
    }

    public void fecharConexao(){
        processo = false;
        if (clienteRecebeImagem != null) {
            clienteRecebeImagem.FecharConexao();
        }
    }

}
