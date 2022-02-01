package org.app.rpsgame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    ImageView player_set,com_set,player_ani,com_ani;
    ImageView heart_1,heart_2,heart_3;
    AnimationDrawable ani_p,ani_c;
    ImageButton bt_ss,bt_rock,bt_paper,bt_Start;
    TextView result_t,tx_ct;
    int ramdom=(int)((Math.random()*3+1));
    String result="";
    int count = 3;
   public static int count_score =0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //인터넷 연결상태 체크
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if (currentNetwork==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("인터넷 연결이 없습니다.");
            builder.setCancelable(false);
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                    finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.show();
        }



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        tx_ct=findViewById(R.id.tx_ct);
        player_set = findViewById(R.id.player_set);
        com_set = findViewById(R.id.com_set);
        player_ani=findViewById(R.id.player_ani);
        com_ani=findViewById(R.id.com_ani);
        result_t=findViewById(R.id.result_t);
        ani_p=(AnimationDrawable) player_ani.getDrawable();
        ani_c=(AnimationDrawable) com_ani.getDrawable();

    //목숨 정의
        heart_1=findViewById(R.id.heart_1);
        heart_2=findViewById(R.id.heart_2);
        heart_3=findViewById(R.id.heart_3);


    //애니메이션 시작버튼 누르기 전에 숨기기
        player_ani.setVisibility(View.GONE);
        player_set.setVisibility(View.VISIBLE);
        com_ani.setVisibility(View.GONE);
        com_set.setVisibility(View.VISIBLE);


    //버튼 정의
        bt_ss=findViewById(R.id.bt_s);
        bt_rock=findViewById(R.id.bt_r);
        bt_paper=findViewById(R.id.bt_p);
        bt_Start=findViewById(R.id.bt_Start);

        bt_ss.setEnabled(false);
        bt_rock.setEnabled(false);
        bt_paper.setEnabled(false);


        //시작버튼을 눌렀을때
        bt_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_ss.setEnabled(true);
                bt_rock.setEnabled(true);
                bt_paper.setEnabled(true);
                ramdom=(int) (Math.random()*3)+1;
                player_ani.setVisibility(View.VISIBLE);
                player_set.setVisibility(View.GONE);
                com_ani.setVisibility(View.VISIBLE);
                com_set.setVisibility(View.GONE);
                ani_c.start();
                ani_p.start();
                if(ramdom==1){
                    com_set.setImageResource(R.drawable.ss);
                }
                else if(ramdom==2){
                    com_set.setImageResource(R.drawable.rock);
                }
                else if(ramdom==3){
                    com_set.setImageResource(R.drawable.paper);
                }
                bt_Start.setEnabled(false);


            }
        });
        bt_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Start.setEnabled(true);
                ani_p.stop();
                ani_c.stop();
                player_set.setImageResource(R.drawable.ss);
                player_ani.setVisibility(View.GONE);
                player_set.setVisibility(View.VISIBLE);
                com_ani.setVisibility(View.GONE);
                com_set.setVisibility(View.VISIBLE);
                if(ramdom==1){
                    result="DRAW";
                    count_score++;
                }
                else if(ramdom==2){
                    result="LOSE...";
                    count--;
                    heart_minus();
                }
                else if(ramdom==3){
                    result="WIN!!";
                    count_score +=2;
                }
                result_t.setText(result);
                bt_ss.setEnabled(false);
                bt_rock.setEnabled(false);
                bt_paper.setEnabled(false);
                tx_ct.setText(String.valueOf(count_score));
            }
        });

        bt_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Start.setEnabled(true);
                ani_p.stop();
                ani_c.stop();
                player_set.setImageResource(R.drawable.paper);
                player_ani.setVisibility(View.GONE);
                player_set.setVisibility(View.VISIBLE);
                com_ani.setVisibility(View.GONE);
                com_set.setVisibility(View.VISIBLE);
                if(ramdom==1){
                    result="LOSE...";
                    count--;
                    heart_minus();
                }
                else if(ramdom==2){
                    result="WIN!!";
                    count_score +=2;
                }
                else if(ramdom==3){
                    result="DRAW";
                    count_score++;
                }
                result_t.setText(result);
                bt_ss.setEnabled(false);
                bt_rock.setEnabled(false);
                bt_paper.setEnabled(false);
                tx_ct.setText(String.valueOf(count_score));
            }
        });

        bt_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Start.setEnabled(true);
                ani_p.stop();
                ani_c.stop();
                player_set.setImageResource(R.drawable.rock);
                player_ani.setVisibility(View.GONE);
                player_set.setVisibility(View.VISIBLE);
                com_ani.setVisibility(View.GONE);
                com_set.setVisibility(View.VISIBLE);
                if(ramdom==1){
                    result="WIN!!";
                    count_score +=2;
                }
                else if(ramdom==2){
                    result="DRAW";
                    count_score++;
                }
                else if(ramdom==3){
                    result="LOSE...";
                    count--;
                    heart_minus();
                }
                result_t.setText(result);
                bt_ss.setEnabled(false);
                bt_rock.setEnabled(false);
                bt_paper.setEnabled(false);
                tx_ct.setText(String.valueOf(count_score));
            }
        });





    }
    public void heart_minus(){
        //목숨 줄어듬 표시
        if(count==2){
            heart_1.setVisibility(View.INVISIBLE);
        }
        else if(count==1){
            heart_2.setVisibility(View.INVISIBLE);
        }
        else if(count==0){
            heart_3.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class); //화면 전환
                    startActivity(intent);
                    finish();
                }
            }, 1000); //딜레이 타임 조절

        }
    }




}