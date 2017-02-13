package com.example.eneasseven.acenco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.example.eneasseven.acenco.cftv.AtualizaImagem;
import com.example.eneasseven.acenco.cftv.CapturaImagem;
import com.example.eneasseven.acenco.cftv.StatusCapturaImagem;
import com.example.eneasseven.acenco.dto.DtoDadosRecebidos;

public class actCamera extends AppCompatActivity implements AtualizaImagem {

    private CapturaImagem capturaImagem;
    private ImageView imageViewCamera;
    private String ip; // = "acenco1.ddns.net";
    private String camera;// = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);

        Intent intent = getIntent();
        this.ip = intent.getStringExtra("ip");
        DtoDadosRecebidos dtoDadosRecebidos = (DtoDadosRecebidos) intent.getSerializableExtra("dados");
        //10,600,Escada1,99,1,0,0
        this.camera = dtoDadosRecebidos.getParametros().split(",")[4];

        capturaImagem = new CapturaImagem(this,this);
        capturaImagem.execute(this.ip, this.camera, StatusCapturaImagem.INICIO.name());

    }

    @Override
    public void atualizar(Bitmap bitmap) {
        imageViewCamera.setImageBitmap(bitmap);
    }

    public void gravaGaleria(Bitmap bitmap, String nome){
        try {

            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nome, null);
        }
        catch (Exception e) {
           Log.e("Servico", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        capturaImagem.fecharConexao();
        super.onDestroy();
    }
}
