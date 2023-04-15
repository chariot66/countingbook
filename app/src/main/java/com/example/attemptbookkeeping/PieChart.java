package com.example.attemptbookkeeping;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class PieChart extends AppCompatActivity {

    float food=0,Transport=0,Health=0,SocialLife=0,Entertainment=0,Living=0,sum=0,inout=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
        Bundle bundle = getIntent().getExtras();
        inout = bundle.getFloat("inout");
        food = bundle.getFloat("food");
        Transport = bundle.getFloat("Transport");
        Health = bundle.getFloat("Health");
        SocialLife = bundle.getFloat("SocialLife");
        Entertainment = bundle.getFloat("Entertainment");
        Living = bundle.getFloat("Living");
        sum = bundle.getFloat("sum");
    }
    Paint paint = new Paint();
    private class CustomView extends View {
        public CustomView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            // basic logic to draw pieChart by this design
            // is to use multiple sector to build up a circle
            canvas.drawColor(Color.GRAY);
            paint.setStyle(Paint.Style.FILL);
            //initialize the layout component
            //Text and related config
            Paint textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(200);
            textPaint.setTextAlign(Paint.Align.CENTER);
            if(inout==1){
                canvas.drawText(getString(R.string.title_income), 430, 250, textPaint);
            } else if (inout==2) {
                canvas.drawText(getString(R.string.title_spend), 430, 250, textPaint);
            }
            textPaint.setTextSize(100);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.total)+sum, 430, 400, textPaint);
            textPaint.setTextSize(40);
            textPaint.setTextAlign(Paint.Align.LEFT);

            DecimalFormat nf = new DecimalFormat("0.0");
            Paint circlePaint = new Paint();
            circlePaint.setColor(Color.BLUE);
            canvas.drawCircle(100, 500, 10, circlePaint);
            canvas.drawText(getString(R.string.food)+" "+nf.format(food*100/sum)+"%", 130, 510, textPaint);
            circlePaint.setColor(Color.RED);
            canvas.drawCircle(600, 500, 10, circlePaint);
            canvas.drawText(getString(R.string.Transport)+" "+nf.format(Transport*100/sum)+"%", 630, 510, textPaint);
            circlePaint.setColor(Color.YELLOW);
            canvas.drawCircle(100, 600, 10, circlePaint);
            canvas.drawText(getString(R.string.Health)+" "+nf.format(Health*100/sum)+"%", 130, 610, textPaint);
            circlePaint.setColor(Color.GREEN);
            canvas.drawCircle(600, 600, 10, circlePaint);
            canvas.drawText(getString(R.string.SocialLife)+" "+nf.format(SocialLife*100/sum)+"%", 630, 610, textPaint);
            circlePaint.setColor(Color.MAGENTA);
            canvas.drawCircle(100, 700, 10, circlePaint);
            canvas.drawText(getString(R.string.Entertainment)+" "+nf.format(Entertainment*100/sum)+"%", 130, 710, textPaint);
            circlePaint.setColor(Color.CYAN);
            canvas.drawCircle(600, 700, 10, circlePaint);
            canvas.drawText(getString(R.string.Living)+" "+nf.format(Living*100/sum)+"%", 630, 710, textPaint);
            // the parts for different class of payments
            paint.setColor(Color.BLUE);
            canvas.drawArc(200, 800, 800, 1400, 0, 360*food/sum, true, paint);
            paint.setColor(Color.RED);
            canvas.drawArc(200, 800, 800, 1400, 360*food/sum, 360*Transport/sum, true, paint);
            paint.setColor(Color.YELLOW);
            canvas.drawArc(200, 800, 800, 1400, 360*(food+Transport)/sum, 360*Health/sum, true, paint);
            paint.setColor(Color.GREEN);
            canvas.drawArc(200, 800, 800, 1400, 360*(food+Transport+Health)/sum, 360*SocialLife/sum, true, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawArc(200, 800, 800, 1400, 360*(food+Transport+Health+SocialLife)/sum, 360*Entertainment/sum, true, paint);
            paint.setColor(Color.CYAN);
            canvas.drawArc(200, 800, 800, 1400, 360*(food+Transport+Health+Entertainment+SocialLife)/sum, 360*Living/sum, true, paint);
        }
    }
}