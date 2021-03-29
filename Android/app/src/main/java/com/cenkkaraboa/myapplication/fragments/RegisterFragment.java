package com.cenkkaraboa.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.activities.LoginActivity;
import com.cenkkaraboa.myapplication.activities.MainActivity;
import com.cenkkaraboa.myapplication.models.UsersModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;


public class RegisterFragment extends Fragment {


    View view;
    Button login,register;
    EditText name,surname,username,mail,password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);
        name=view.findViewById(R.id.name);
        surname=view.findViewById(R.id.surname);
        username=view.findViewById(R.id.username);
        mail=view.findViewById(R.id.mail);
        password=view.findViewById(R.id.password);

        login=view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new LoginFragment();
                ((LoginActivity) requireActivity()).moveToFragment(fragment);
            }
        });
        register=view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0 || surname.getText().length()==0 || password.getText().length()==0 || username.getText().length()==0 || mail.getText().length()==0){
                    Toast.makeText(getContext(),"Gerekli yerleri doldurunuz",Toast.LENGTH_SHORT).show();
                }else {
                    addUser(name.getText().toString(),surname.getText().toString(),password.getText().toString(),username.getText().toString(),mail.getText().toString());
                }
            }
        });
        return  view;
    }

    public void addUser(String name, String surname, final String password, final String username, String mail){
        Callback<UsersModel> listCallBack = new Callback<UsersModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatusCode().equals("C4")){
                        Toast.makeText(getContext(),"Kullandığınız mail önceden kayıtlı",Toast.LENGTH_SHORT).show();
                    }else if(response.body().getStatusCode().equals("C5")){
                        Toast.makeText(getContext(),"Kayıt başarılı",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mail", username);
                        editor.putString("password", password);
                        editor.apply();
                        startActivity(intent);
                        requireActivity().finish();
                    }else if(response.body().getStatusCode().equals("C6")){
                        Toast.makeText(getContext(),"Kullandığınız kullanıcı adı önceden kayıtlı",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
            }
        };
        apiService.addUser(name,surname,password,username,mail).enqueue(listCallBack);
    }
}