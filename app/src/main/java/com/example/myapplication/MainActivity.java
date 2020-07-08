package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fl1;
    private FrameLayout fl2;
    private FrameLayout fl3;
    private FrameLayout fl4;
    private FrameLayout fl5;
    private FrameLayout fl6;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;

    private FrameLayout currentFra = null;
    private TextView tvv = null;

    private boolean canReset = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        t1 = findViewById(R.id.tv_1);
        t2 = findViewById(R.id.tv_2);
        t3 = findViewById(R.id.tv_3);
        t4 = findViewById(R.id.tv_4);
        t5 = findViewById(R.id.tv_5);
        t6 = findViewById(R.id.tv_6);

        fl1 = findViewById(R.id.fl1);
        fl2 = findViewById(R.id.fl2);
        fl3 = findViewById(R.id.fl3);
        fl4 = findViewById(R.id.fl4);
        fl5 = findViewById(R.id.fl5);
        fl6 = findViewById(R.id.fl6);


        setStartDrag(fl1, t1);
        setStartDrag(fl2, t2);
        setStartDrag(fl3, t3);
        setStartDrag(fl4, t4);
        setStartDrag(fl5, t5);
        setStartDrag(fl6, t6);

        addListener(fl1, t1);
        addListener(fl2, t2);
        addListener(fl3, t3);
        addListener(fl4, t4);
        addListener(fl5, t5);
        addListener(fl6, t6);


    }


    private void setStartDrag(final FrameLayout frameLayout, final TextView view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                currentFra = frameLayout;
                ClipData.Item item = new ClipData.Item(view.getText().toString());  //传过去的数据
                ClipData data = new ClipData(null, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

                tvv = view;
                View.DragShadowBuilder builder = new View.DragShadowBuilder(frameLayout);
//                builder.getView().setVisibility(View.INVISIBLE);
                view.startDrag(data, builder, null, 0);
                canReset = true;
                return true;
            }
        });
    }

    private void addListener(final FrameLayout tv, final TextView tvvv) {
        tvvv.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();

                Log.e("swttt", action + "===========");
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED: // 拖拽开始
                        tvv.setVisibility(View.INVISIBLE);
                        return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    case DragEvent.ACTION_DRAG_ENTERED: // 被拖拽View进入目标区域
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION: // 被拖拽View在目标区域移动
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED: // 被拖拽View离开目标区域
                        return true;
                    case DragEvent.ACTION_DROP: // 放开被拖拽View

//                        String content = event.getClipData().getItemAt(0).getText().toString(); //接收数据
////                        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
//                        mImageView.setVisibility(View.INVISIBLE);
//                        mImageView2.setBackgroundResource(R.mipmap.d2222);+


                        int a = Integer.parseInt(tvv.getText().toString());
                        int b = Integer.parseInt(tvvv.getText().toString());

                        if (a == b) {
                            tvvv.setText(a + b + "");
                            canReset = false;
                        } else {
                            canReset = true;
                            Toast.makeText(MainActivity.this, "不同类别不能合并", 1).show();
                        }

                        return true;
                    case DragEvent.ACTION_DRAG_ENDED: // 拖拽完成

                        if (canReset) {
                            tvv.setVisibility(View.VISIBLE);
                        } else {
                            tvv.setVisibility(View.INVISIBLE);
                        }
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });
    }
}
