package com.example.leopardo.taskorganizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity_old extends Activity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String username, pswdTotal, passwd;
    private String SHAHash;
    private EditText usuario, passUsuario;
    private SecretKey key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        TaskDataBaseHelper mDbHelper = new TaskDataBaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        /*System.out.println(" CHECK: " +  username);

        String selectQuery = "SELECT * FROM " + TaskDataBase.UserEntry.TABLE_NAME +
                " WHERE " + TaskDataBase.UserEntry.COLUMN_NAME_USERNAME + " = 'bla@bla.com'";
        Cursor cursor      = db.rawQuery(selectQuery, null);


        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(TaskDataBase.UserEntry.COLUMN_NAME_ENTRY_ID, "1");
            values.put(TaskDataBase.UserEntry.COLUMN_NAME_USERNAME, "bla@bla.com");
            values.put(TaskDataBase.UserEntry.COLUMN_NAME_PASSWORD, "317b32c143692b9939c197f6a5df54f9698df9a4882fe8bf19608968662be4fa");


            long newRowId;
            newRowId = db.insert(
                    TaskDataBase.UserEntry.TABLE_NAME,
                    null,
                    values);
        }
        cursor.close();*/

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);



        mPasswordView = (EditText) findViewById(R.id.password);
       /* mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //TODO add salt here
                CharSequence saltGuardada = "";
                username = mEmailView.getText().toString();
                System.out.println(" INIT CHECK: " +  username);

                passwd = saltGuardada + mPasswordView.getText().toString(); // concatenamos el salt + la contraseña
                //  verificamos si el username o passwd son no null
                if( (username != null && !username.equals("") ) && (passwd !=null && !passwd.equals("")) )
                {
                    // llamamos al metodo para calcular el SHA hash
                    calcSHAHash(passwd);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Write your credentials..", Toast.LENGTH_LONG).show();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private static String convertToHex(byte[] data) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        for(byte recogerDatos : data)
        {
            sb.append(Integer.toString((recogerDatos & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    } /*** Fin del metodo private static String convertToHex(byte[] data) ***/


    public void calcSHAHash(String password)
    {

        CharSequence contraGuardada = "normal";

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
                        LoginActivity_old.this,
                        Home.class);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(), "Incorrect credentials...", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  /*** Fin del metodo public void calcSHAHash(String password) ***/



    String getRawKey() {
        if (key == null) {
            return null;
        }
        return Crypto.toHex(key.getEncoded());
    }
    byte[] salt = getSalt();

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

