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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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


public class LoginFragment extends Fragment implements View.OnClickListener {

    Button login;
    TextView register;
    EditText username,password;
    View view;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);
        login=view.findViewById(R.id.login);
        register=view.findViewById(R.id.register);
        linearLayout=view.findViewById(R.id.linear);
        progressBar=view.findViewById(R.id.progressBar);
        username=view.findViewById(R.id.username);
        password=view.findViewById(R.id.password);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String mail=preferences.getString("mail","null");
        String password=preferences.getString("password","null");
        if(!mail.equals("null")  && !password.equals("null")){
            login(mail,password);
        }
        else {
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        return view;
    }


    public void login(final String username, final String password){
        Callback<UsersModel> listCallBack = new Callback<UsersModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatusCode().equals("C1")){
                        Toast.makeText(getContext(),"Kullanıcı adı veya mail adresiniz kayıtlı değil",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }else if(response.body().getStatusCode().equals("C2")){
                        Toast.makeText(getContext(),"Şifre Yanlış",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }else if(response.body().getStatusCode().equals("C3")){
                        Toast.makeText(getContext(),"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mail", username);
                        editor.putString("password", password);
                        editor.apply();
                        startActivity(intent);
                        requireActivity().finish();



                    }
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
            }
        };
        apiService.loginUser(username,password).enqueue(listCallBack);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.login:
                System.out.println("asd");
                login(username.getText().toString(),password.getText().toString());
                break;

            case R.id.register:
                Fragment fragment=new RegisterFragment();
                ((LoginActivity) requireActivity()).moveToFragment(fragment);
                break;
        }
    }
}