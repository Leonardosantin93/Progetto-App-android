package com.example.lettoremusicalesantin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    static ArrayList<MusicFiles> musicFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();

    }

    private void permission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        else{

            musicFiles = getAllAudio(this);
            inizViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                musicFiles = getAllAudio(this);
                inizViewPager();
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

    }

    private void inizViewPager(){
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragments(new CanzoniFragment(), "Canzoni");
        viewPageAdapter.addFragments(new AlbumFragment(), "Album");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public class ViewPageAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titoli;

        public ViewPageAdapter(@NonNull FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titoli = new ArrayList<>();
        }


        void addFragments(Fragment fragment, String titolo){
             fragments.add(fragment);
             titoli.add(titolo);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size() ;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titoli.get(position);
        }
    }


    public static ArrayList<MusicFiles> getAllAudio(Context context){
        ArrayList<MusicFiles> tempoAudioList = new ArrayList<>();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ARTIST,
                };

                Cursor cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                if(cursor != null){
                    while(cursor.moveToNext())
                    {
                        String album = cursor.getString(0);
                        String title = cursor.getString(1);
                        String duration = cursor.getString(2);
                        String path = cursor.getString(3);
                        String artist = cursor.getString(4);

                        MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration);
                        Log.e("Path : "+path, "Album : "+album);
                        tempoAudioList.add(musicFiles);
                    }

                    cursor.close();
                }
                return tempoAudioList;
    }

}