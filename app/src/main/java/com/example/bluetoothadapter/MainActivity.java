package com.example.bluetoothadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private MenuItem menuItem;
    private BluetoothAdapter bluetoothAdapter;
    private final int  ENABLE_REQUEST = 10;



    private static MyView imgview;
    private static final int REQ_ENABLE_BT = 10;
    private SharedPreferences pref;
    private BtConnection btConnection;
    private float mScale = 1f;
    int count = 0;
    float x1;
    float x2;
    float x3;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgview.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new GestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.
                SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                if (mScale > 10f)
                    mScale = 10f;

                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale,
                        1f / mScale, 1f / prevScale, 1f / mScale,
                        detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                imgview.startAnimation(scaleAnimation);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.id_bt_button){
            if(!bluetoothAdapter.isEnabled()){
                enableBt();
            } else {
                bluetoothAdapter.disable();
                menuItem.setIcon(R.drawable.bluetooth_enable);
            }

        } else  if(item.getItemId() == R.id.id_menu){
            if(bluetoothAdapter.isEnabled()){
                Intent i = new Intent(MainActivity.this, BtListActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Включите блютуз для перехода на этот экран!",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(item.getItemId() == R.id.clear){
            imgview.clearZone();
        }

        if(item.getItemId() == R.id.half_zone){
            imgview.halfZone();
        }

        if(item.getItemId() == R.id.full_zone){
            imgview.fullZone();
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableBt() {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, ENABLE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_REQUEST){

            if(resultCode == RESULT_OK){

                setBtIcon();

            }

        }

    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);
        imgview = findViewById(R.id.imageView);
    }

    private void setBtIcon(){

        if(bluetoothAdapter.isEnabled()){

            menuItem.setIcon(R.drawable.bluetooth_disable);

        } else {

            menuItem.setIcon(R.drawable.bluetooth_enable);

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        mScaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(count == 0){
                    x1 = motionEvent.getX();
                    count++;
                }
                if(count == 1){
                    x2 = motionEvent.getX();
                   if( x1-x2 >= view.getWidth()/4 || x1-x2 <= -view.getWidth()/4 ) {
                       if (x1 - x2 > 60) {
                           x3 = 45;
                           startLeftRotation();
                           count = 3;
                       }

                       if (x1 - x2 < 60) {
                           System.out.println(x1 - x2);
                           x3 = 45;
                           startRightRotation();
                           count = 3;
                       }
                   }
                }
                if(count == 3){
                    count = 0;
                }
                break;
        }
        return true;
    }

    private void startRightRotation() {
        imgview.animate().rotation(imgview.getRotation() - x3);
        imgview = findViewById(R.id.imageView);
    }

    private void startLeftRotation() {
        imgview.animate().rotation(imgview.getRotation() + x3);
        imgview = findViewById(R.id.imageView);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}