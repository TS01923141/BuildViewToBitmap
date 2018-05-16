package com.example.biji.buildviewtobitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    View view;
    ConstraintLayout constraintLayout_module;
    ImageView imageView_module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup parent = (ViewGroup) findViewById(R.id.constrain_main);

        imageView = findViewById(R.id.imageView_main);
        button = findViewById(R.id.button_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBitmap();
            }
        });
        //可以在xml用include替換, 一樣意思
        view = LayoutInflater.from(this).inflate(R.layout.module_image, parent, true);
        constraintLayout_module = view.findViewById(R.id.constraintLayout_module);
        constraintLayout_module.setVisibility(View.INVISIBLE);
        imageView_module = view.findViewById(R.id.imageView_module);

        constraintLayout_module.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                constraintLayout_module.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                createBitmap();
//                createSharePhotoBitmap(photoUris.get(0));
            }
        });
    }

    private void createBitmap(){
        Glide.with(MainActivity.this)
                .asBitmap()
                .load(R.drawable.ic_launcher_background)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView_module.setImageBitmap(resource);
                        Glide.with(MainActivity.this)
                                .load(getBitmapFromView(constraintLayout_module))
                                .into(imageView);
                    }
                });
    }

    private static Bitmap getBitmapFromView(View view) {
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

}
