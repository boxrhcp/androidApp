package com.example.leopardo.taskorganizer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import net.sqlcipher.database.*;
import android.widget.Toast;
import android.database.Cursor;
import java.io.IOException;
import java.security.MessageDigest;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import java.security.NoSuchAlgorithmException;
import android.util.Log;
import javax.crypto.SecretKey;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String username, passwd;
    private String SHAHash;
    private SecretKey key;
    private SharedPreferences shaPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        TaskDataBaseHelper mDbHelper = new TaskDataBaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        shaPref = getSharedPreferences("loginInfo", MODE_PRIVATE);
        mPasswordView = (EditText) findViewById(R.id.password);
        if(mPasswordView == null){
            System.out.println("an error occured here mPasswordView is null");
            return;
        }

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        final byte[] salt = Crypto.generateSalt();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String epassword="";
                String dpassword="";
                username = mEmailView.getText().toString();
                passwd = mPasswordView.getText().toString();
                System.out.println(" INIT CHECK: " +  username);
                epassword= shaPref.getString("password", "");
                if(epassword ==""){
                    SHAHash = getRandom(passwd);
                    epassword= encrypt(SHAHash, passwd);
                    SharedPreferences.Editor mEditor = shaPref.edit();
                    mEditor.putString("password", epassword);
                    mEditor.commit();
                    //TODO: encrypt db
                    System.out.println("YOU CREATED THE PASS FOR DB: " + epassword);

                    loginDatabase(epassword);
                }else{
                    dpassword = decrypt(epassword,passwd);
                    System.out.println("YOU ARE ACCESSING TO THE DB: " + dpassword);
                    loginDatabase(dpassword);
                }





                passwd = salt + mPasswordView.getText().toString(); // concatenamos el salt + la contraseña
                if( (username != null && !username.equals("") ) && (passwd !=null && !passwd.equals("")) )
                {
                    //TODO: Decrypt the Database here!!!! if it works log in
                    //  verificamos si el username o passwd son no null
                     /*if(decryption is okay and key is)
                    {
                        login (Functionality for changing activities can be found in calc hash function in LoginActicity_old)
                    }
                    else
                    {
                        error message
                    }*/
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Write your credentials..", Toast.LENGTH_LONG).show();
                }


                /*if( (username != null && !username.equals("") ) && (passwd !=null && !passwd.equals("")) )
                {
                    // llamamos al metodo para calcular el SHA hash
                    //calcSHAHash(passwd);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Write your credentials..", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

    public String getRandom(String input){
        String result = null;
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-256");
            mdSha1.update(input.toString().getBytes()); // obtenemos la secuencias de bytes
            byte[] data = mdSha1.digest(); // pasamos los digitos a un arreglo de bytes
            result = convertToHex(data);
        } catch (NoSuchAlgorithmException e1) {
            Log.e("miApp", "Error initializing SHA1 message digest");
        }catch ( IOException e) {
            Log.e("miApp", "Error initializing SHA1 message digest");
        }

        return result;
    }

    public void loginDatabase(String password)
    {
        System.out.println("YOU ARE IN LOGIN DATABASE");

     /*   CharSequence contraGuardada = "normal";

        String pswEnResFolder = "";
        pswEnResFolder = contraGuardada.toString();
        Context context = getApplicationContext();
        TaskDataBaseHelper mDbHelper = new TaskDataBaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        System.out.println(" CHECK: " +  username);

        String selectQuery = "SELECT * FROM " + TaskDataBase.UserEntry.TABLE_NAME +
                " WHERE " + TaskDataBase.UserEntry.COLUMN_NAME_USERNAME + " = '" + username + "'";
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String storedPasswd = "";

        if (cursor.moveToFirst()) {
            do {
                storedPasswd = cursor.getString(3);
            } while (cursor.moveToNext());
        }

        cursor.close();
        //fetch this hash from database
        //pswEnResFolder = "317b32c143692b9939c197f6a5df54f9698df9a4882fe8bf19608968662be4fa";
        pswEnResFolder = storedPasswd;
        System.out.println(" vamos a ver que pasa aqui: " +  pswEnResFolder);
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-256");
            mdSha1.update(password.toString().getBytes()); // obtenemos la secuencias de bytes
        } catch (NoSuchAlgorithmException e1) {
            Log.e("miApp", "Error initializing SHA1 message digest");
        }
        byte[] data = mdSha1.digest(); // pasamos los digitos a un arreglo de bytes
        try {
            SHAHash = convertToHex(data);
            System.out.println(" vamos a ver que pasa y aqui tambien: " +  SHAHash);
            if(SHAHash.equals(pswEnResFolder))
            {
                mEmailView.setText("");
                mPasswordView.setText("");
                Toast.makeText(getApplicationContext(), "Correct credentials...", Toast.LENGTH_LONG).show();
                Intent i=new Intent(
                        LoginActivity.this,
                        Home.class);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(), "Incorrect credentials...", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }  /*** Fin del metodo public void calcSHAHash(String password) ***/


    private static String convertToHex(byte[] data) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        for(byte recogerDatos : data)
        {
            sb.append(Integer.toString((recogerDatos & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    } /*** Fin del metodo private static String convertToHex(byte[] data) ***/


    public SecretKey deriveKey(String password, byte[] salt) {
        return Crypto.deriveKeyPbkdf2(salt, password);
    }
    public String encrypt(String plaintext, String password) {
        byte[] salt = Crypto.generateSalt();
        key = deriveKey(password, salt);
        //Log.d(TAG, "Generated key: " + getRawKey());
        return Crypto.encrypt(plaintext, key, salt);
    }
    public String decrypt(String ciphertext, String password) {
        return Crypto.decryptPbkdf2(ciphertext, password);
    }

    //Generates a salt or retrieves it from the Shared Preferences
    public byte[] getSalt(){
        //TODO: salt in SP?
        return Crypto.generateSalt();
    }


}

