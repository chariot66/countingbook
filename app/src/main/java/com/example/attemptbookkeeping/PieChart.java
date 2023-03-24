package com.example.attemptbookkeeping;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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


            //饼图的核心其实就是画好扇形就成功了一大半了
            //饼图的核心就是一个圆形，学会切割圆形，就知道怎么画饼图
            //先绘制一个整体的背景
            canvas.drawColor(Color.GRAY);
            paint.setStyle(Paint.Style.FILL);
            //这里要注意的是标准的饼图是一个圆形，那既然是圆形，这里right和left的差值要和 top和bottom的差值相等
            //因为只有对应的矩形长宽相等，里面才是个圆形 最终画出来的才是完整的圆形否则就是不规则的椭圆很难看
            //-110的这个参数 是起始角度 100这个参数是划过的角度 ，这里的划 代表是顺时针， x轴方向是0度，这个地方要自己体会一下
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

            Paint circlePaint = new Paint();
            circlePaint.setColor(Color.BLUE);
            canvas.drawCircle(100, 500, 10, circlePaint);
            canvas.drawText(getString(R.string.food)+food/sum, 130, 510, textPaint);
            circlePaint.setColor(Color.RED);
            canvas.drawCircle(400, 500, 10, circlePaint);
            canvas.drawText(getString(R.string.Transport), 430, 510, textPaint);
            circlePaint.setColor(Color.YELLOW);
            canvas.drawCircle(700, 500, 10, circlePaint);
            canvas.drawText(getString(R.string.Health), 730, 510, textPaint);
            circlePaint.setColor(Color.GREEN);
            canvas.drawCircle(100, 600, 10, circlePaint);
            canvas.drawText(getString(R.string.SocialLife), 130, 610, textPaint);
            circlePaint.setColor(Color.MAGENTA);
            canvas.drawCircle(400, 600, 10, circlePaint);
            canvas.drawText(getString(R.string.Entertainment), 430, 610, textPaint);
            circlePaint.setColor(Color.CYAN);
            canvas.drawCircle(700, 600, 10, circlePaint);
            canvas.drawText(getString(R.string.Living), 730, 610, textPaint);

            paint.setColor(Color.BLUE);
            canvas.drawArc(200, 700, 800, 1300, 0, 360*food/sum, true, paint); // 绘制扇形

            paint.setColor(Color.RED);
            //保证饼图的重合性，可以看出来 这个-10的值 就是 -110+100得到的，上一个扇形的尾巴等于这个一个扇形的头
            canvas.drawArc(200, 700, 800, 1300, 360*food/sum, 360*Transport/sum, true, paint); // 绘制扇形

            paint.setColor(Color.YELLOW);
            //最后画一个大的，完成这个圆即可，注意这里 三个划过的角度是100+50+210=360 刚好是一个圆形的角度 且起始角度40的值就是上一个扇形的-10+50 计算而来，这样就完美组成了一个完整的圆形饼图
            canvas.drawArc(200, 700, 800, 1300, 360*(food+Transport)/sum, 360*Health/sum, true, paint); // 绘制扇形
            paint.setColor(Color.GREEN);
            canvas.drawArc(200, 700, 800, 1300, 360*(food+Transport+Health)/sum, 360*SocialLife/sum, true, paint); // 绘制扇形
            paint.setColor(Color.MAGENTA);
            canvas.drawArc(200, 700, 800, 1300, 360*(food+Transport+Health+SocialLife)/sum, 360*Entertainment/sum, true, paint); // 绘制扇形
            paint.setColor(Color.CYAN);
            canvas.drawArc(200, 700, 800, 1300, 360*(food+Transport+Health+Entertainment+SocialLife)/sum, 360*Living/sum, true, paint); // 绘制扇形
        }
    }




}