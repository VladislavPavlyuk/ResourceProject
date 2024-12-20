package com.example.resourceproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("t", "onCreate: ");
		//
		SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
		String localeTag = preferences.getString("locale", Locale.ENGLISH.toLanguageTag());
		//setLocale(Locale.forLanguageTag(localeTag));
		//
		//Встановлення локалізованого тексту із ресурсів у заголовок актівіті
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(R.string.app_name);
		}
		//Theme
		setTheme(R.style.Light_Theme_ResourceProject);

		super.onCreate(savedInstanceState);
		setTheme(getSavedThemePreference());
		setContentView(R.layout.activity_main);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

	/*private void saveThemePreference(int themeId) {
		SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("theme_id", themeId);
		editor.apply();
	}*/
	private int getSavedThemePreference() {
		SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
		return preferences.getInt("theme_id", R.style.Light_Theme_ResourceProject);
	}


	@Override
	protected void attachBaseContext(Context contextBase) {
		//Приклад отримання налаштувань з SharedPreferences
		SharedPreferences preferences = contextBase.getSharedPreferences("settings", MODE_PRIVATE);
		String localeTag = preferences.getString("locale", Locale.ENGLISH.toLanguageTag());
		//Новий спосіб встановлення локалізації
		Locale locale = Locale.forLanguageTag(localeTag);
		Locale.setDefault(locale);
		Configuration configuration = new Configuration();
		configuration.setLocale(locale);
		Context newContext = contextBase.createConfigurationContext(configuration);

		//
		super.attachBaseContext(newContext);
	}
	
	//Створення Option Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("t", "onCreateOptionsMenu: ");
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	//Обробка натискання на пункти меню
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int itemId = item.getItemId();
		//Shared Pref
		SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		//Orientations
		if (itemId == R.id.portrait) {
			Toast.makeText(this, R.string.portrait, Toast.LENGTH_SHORT).show();
			//1
			if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
		else if (itemId == R.id.landscape) {	//landscapeMenu
			Toast toast = Toast.makeText(this, R.string.landscape, Toast.LENGTH_LONG);
			toast.addCallback(new Toast.Callback() {
				@Override
				public void onToastShown() {
					super.onToastShown();
					Log.d("t", "onToastShown: ");
				}
				
				@Override
				public void onToastHidden() {
					super.onToastHidden();
					Log.d("t", "onToastHidden: ");
				}
			});
			toast.show();
			//
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		else if (itemId == R.id.sensor) {
		}
		//Locale
		else if (itemId == R.id.enLocaleMenu) {
			editor.putString("locale", Locale.ENGLISH.toLanguageTag());
			editor.apply();
			//setLocale(Locale.ENGLISH); //"en", ""
			recreate();
		}
		else if (itemId == R.id.enGBLocaleMenu) {
			editor.putString("locale", Locale.UK.toLanguageTag());
			editor.apply();
			//setLocale(Locale.UK); //GB
			recreate();
		}
		else if (itemId == R.id.ukLocaleMenu) {
			editor.putString("locale", Locale.forLanguageTag("uk").toLanguageTag());
			editor.apply();
			//setLocale(new Locale("uk")); //UA
			recreate();
		}
		//Themes...
		else if (itemId == R.id.lightThemeMenu) {
			setTheme(R.style.Light_ResourceProject);
			saveThemePreference(R.style.Light_ResourceProject);
			recreate();
			}
		else if (itemId == R.id.darkThemeMenu) {
			setTheme(R.style.Dark_ResourceProject);
			saveThemePreference(R.style.Dark_ResourceProject);
			recreate();
		}
		else if (itemId == R.id.coloredThemeMenu) {
			setTheme(R.style.Color_Theme_ResourceProject);
			saveThemePreference(R.style.Color_Theme_ResourceProject);
			recreate();
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveThemePreference(int themeId) {
		SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("theme_id", themeId);
		editor.apply();
	}
	
	/*private void setLocale(Locale locale){
		Locale.setDefault(locale);
		Resources resources = getResources();
		Configuration configuration = resources.getConfiguration();
		configuration.setLocale(locale);
		resources.updateConfiguration(configuration, resources.getDisplayMetrics());
		/*getBaseContext().getResources().updateConfiguration(
				configuration, resources.getDisplayMetrics()
		);
		recreate();
		//2
		/*Intent 	intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}*/

		/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			int itemId = item.getItemId();
			//
			if (itemId == R.id.unspecified) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
			}
			else if (itemId == R.id.sensor) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
			else if (itemId == R.id.sensor_portrait) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			else if (itemId == R.id.sensor_landscape) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			else if (itemId == R.id.full_sensor) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
			}
			else if (itemId == R.id.full_user) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
			}
			else if (itemId == R.id.portrait) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			else if (itemId == R.id.landscape) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			else if (itemId == R.id.locked) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
			}
			return super.onOptionsItemSelected(item);
		}*/

	}
