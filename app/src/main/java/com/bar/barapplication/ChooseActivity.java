package com.bar.barapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

public class ChooseActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        context = this;
        String appRole = PreferencesManager.getStringFromPrefs(this, Constants.APP_ROLE);
        if (appRole != null) {
            switch (appRole) {
                case Constants.APP_ROLE_BAR: {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case Constants.APP_ROLE_KITCHEN: {
                    Intent intent = new Intent(this, KitchenActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case Constants.APP_ROLE_DISPLAY: {
                    Intent intent = new Intent(this, DisplayActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }

        Button bar = findViewById(R.id.bar);
        Button kitchen = findViewById(R.id.kitchen);
        Button display = findViewById(R.id.display);

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.saveStringToPrefs(context, Constants.APP_ROLE, Constants.APP_ROLE_BAR);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.saveStringToPrefs(context, Constants.APP_ROLE, Constants.APP_ROLE_KITCHEN);
                Intent intent = new Intent(context, KitchenActivity.class);
                startActivity(intent);
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.saveStringToPrefs(context, Constants.APP_ROLE, Constants.APP_ROLE_DISPLAY);
                Intent intent = new Intent(context,  DisplayActivity.class);
                startActivity(intent);
            }
        });

    }

}
