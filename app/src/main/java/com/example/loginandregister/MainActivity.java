package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userId,password,name;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId=findViewById(R.id.userId);
        password=findViewById(R.id.password);
        name=findViewById(R.id.name);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create User Entity
                UserEntity userEntity=new UserEntity();
                userEntity.setUserId(userId.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(name.getText().toString());
                if (validateInput(userEntity)){
                   //Do insert operation
                    UserDatabase userDatabase= UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao=userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Register
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();


                }else{
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                


            }
        });



    }

    private Boolean validateInput(UserEntity userEntity){

        if(userEntity.getName().isEmpty() ||
           userEntity.getPassword().isEmpty() ||
           userEntity.getName().isEmpty()
        ){
            return false;
        }

        return true;
    }

}