package com.franklintapia.camara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.btnCamara);
        boton.setOnClickListener(l -> abrirCamara());

    }

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private void abrirCamara() {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if(intentCamara.resolveActivity(getPackageManager())!=null){
        startActivityForResult(intentCamara, REQUEST_IMAGE_CAPTURE);

        File imagenArchivo = null;
        if (imagenArchivo != null) {
            Uri fotoUri = FileProvider.getUriForFile(this, "com.cdp.camara.filprovider", imagenArchivo);
            intentCamara.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);

        }

        // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle info = data.getExtras();
            Bitmap imagen = (Bitmap) info.get("data");
            //Bitmap imagen = BitmapFactory.decodeFile(rutaImagen);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(imagen);

        }
    }

    private File crearImagen() throws IOException {
        String imgname = "foto";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(imgname, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }
}
