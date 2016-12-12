package com.example.lenovo.encriptionmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    TextView hasil;
    EditText masukan,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasil = (TextView) findViewById(R.id.hasil);
        masukan = (EditText) findViewById(R.id.masukan);
        key = (EditText) findViewById(R.id.key);
    }
    public void encrypt(View v){
        try{
            byte[] text = masukan.getText().toString().getBytes();
            byte[] textEncrypted1 = DES.encrypt(text,key.getText().toString());
            masukan.setText(masukan.getText()+ " --- Heksa = " + bytesToHeksa(masukan.getText().toString().getBytes()));
            key.setText(key.getText()+ " --- Heksa = " + bytesToHeksa(key.getText().toString().getBytes()));
            hasil.setText(new String(textEncrypted1) + " --- Heksa = " + bytesToHeksa(textEncrypted1));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String bytesToHeksa(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
