package com.example.quizzapp.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizzapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    ImageView category_image;
    TextView timer;
    TextView score;
    TextView question_number;
    TextView question_text;
    RadioGroup radioGroup;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    Button submit;
    int total=1;
    int iterator=0;
    int correct,wrong=0;
    ArrayList<String> arrayList;
    DatabaseReference reference;
    String correctAns;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_and_literature);

        category_image=findViewById(R.id.Imagecategory);
        final Intent intent= getIntent();
        category_image.setImageResource(intent.getIntExtra("Category Image",R.drawable.artandliterature));

        timer= findViewById(R.id.textView2);
        score=findViewById(R.id.score);
        question_number=findViewById(R.id.quesNo);
        question_text=findViewById(R.id.quesText);
        radioGroup=findViewById(R.id.rg);
        r1=findViewById(R.id.option1);
        r2=findViewById(R.id.option2);
        r3=findViewById(R.id.option3);
        r4=findViewById(R.id.option4);
        submit=findViewById(R.id.submit);
        start=findViewById(R.id.startQuiz);


        arrayList= new ArrayList<>();
        arrayList.add("Ques1");
        arrayList.add("Ques2");
        arrayList.add("Ques3");
        arrayList.add("Ques4");
        arrayList.add("Ques5");
        reference= FirebaseDatabase.getInstance().getReference().child("CATEGORIES");

//        radioGroup.setEnabled(false);
        r1.setEnabled(false);
        r2.setEnabled(false);
        r3.setEnabled(false);
        r4.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timer(30,timer);
                iterator=0;
                updateQuestion();
                start.setVisibility(View.GONE);
            }
        });
    }

    private void updateQuestion() {
        if (total >5) {
// open result dialog


        } else {
            DatabaseReference ref;
            ref=reference.child("History").child(arrayList.get(iterator));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    question_text.setText(dataSnapshot.child("question").getValue().toString());
                    r1.setText(dataSnapshot.child("option1").getValue().toString());
                    r2.setText(dataSnapshot.child("option2").getValue().toString());
                    r3.setText(dataSnapshot.child("option3").getValue().toString());
                    r4.setText(dataSnapshot.child("option4").getValue().toString());
                    correctAns = dataSnapshot.child("correct").getValue().toString();
//                    score.setText(correct);
                    question_number.setText("Question: " + total+"/5");
//                    score.setText("Score: "+ correct);

                    r1.setEnabled(true);
                    r2.setEnabled(true);
                    r3.setEnabled(true);
                    r4.setEnabled(true);


                    r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r1.setEnabled(false);
                            r2.setEnabled(false);
                            r3.setEnabled(false);
                            r4.setEnabled(false);
                            if (r1.getText().toString().equals(correctAns)) {
                                r1.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                score.setText("Score: "+ correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r1.setBackgroundColor(Color.TRANSPARENT);
                                        iterator = iterator + 1;
                                        total = total + 1;
                                        unselectSelectedOption();
                                        updateQuestion();
//                                            question_number.setText(total);
                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                r1.setBackgroundColor(Color.RED);
                                if (r2.getText().toString().equals(correctAns)) {
                                    r2.setBackgroundColor(Color.GREEN);
                                } else if (r3.getText().toString().equals(correctAns)) {
                                    r3.setBackgroundColor(Color.GREEN);
                                } else if (r4.getText().toString().equals(correctAns)) {
                                    r4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r1.setBackgroundColor(Color.TRANSPARENT);
                                        r2.setBackgroundColor(Color.TRANSPARENT);
                                        r3.setBackgroundColor(Color.TRANSPARENT);
                                        r4.setBackgroundColor(Color.TRANSPARENT);
                                        iterator++;
                                        total++;
                                        unselectSelectedOption();
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });

                    r2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r1.setEnabled(false);
                            r2.setEnabled(false);
                            r3.setEnabled(false);
                            r4.setEnabled(false);
                            if (r2.getText().toString().equals(correctAns)) {
                                r2.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                score.setText("Score: "+ correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r2.setBackgroundColor(Color.TRANSPARENT);
                                        iterator = iterator + 1;
                                        total = total + 1;
                                        updateQuestion();
                                        unselectSelectedOption();
//                                            question_number.setText(total);
                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                r2.setBackgroundColor(Color.RED);
                                if (r1.getText().toString().equals(correctAns)) {
                                    r1.setBackgroundColor(Color.GREEN);
                                } else if (r3.getText().toString().equals(correctAns)) {
                                    r3.setBackgroundColor(Color.GREEN);
                                } else if (r4.getText().toString().equals(correctAns)) {
                                    r4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r1.setBackgroundColor(Color.TRANSPARENT);
                                        r2.setBackgroundColor(Color.TRANSPARENT);
                                        r3.setBackgroundColor(Color.TRANSPARENT);
                                        r4.setBackgroundColor(Color.TRANSPARENT);
                                        iterator++;
                                        total++;
                                        unselectSelectedOption();
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });

                    r3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r1.setEnabled(false);
                            r2.setEnabled(false);
                            r3.setEnabled(false);
                            r4.setEnabled(false);
                            if (r3.getText().toString().equals(correctAns)) {
                                r3.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                score.setText("Score: "+ correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r3.setBackgroundColor(Color.TRANSPARENT);
                                        iterator = iterator + 1;
                                        total = total + 1;
                                        updateQuestion();
                                        unselectSelectedOption();
//                                            question_number.setText(total);
                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                r3.setBackgroundColor(Color.RED);
                                if (r1.getText().toString().equals(correctAns)) {
                                    r1.setBackgroundColor(Color.GREEN);
                                } else if (r2.getText().toString().equals(correctAns)) {
                                    r2.setBackgroundColor(Color.GREEN);
                                } else if (r4.getText().toString().equals(correctAns)) {
                                    r4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r1.setBackgroundColor(Color.TRANSPARENT);
                                        r2.setBackgroundColor(Color.TRANSPARENT);
                                        r3.setBackgroundColor(Color.TRANSPARENT);
                                        r4.setBackgroundColor(Color.TRANSPARENT);
                                        iterator++;
                                        total++;
                                        unselectSelectedOption();
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });


                    r4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r1.setEnabled(false);
                            r2.setEnabled(false);
                            r3.setEnabled(false);
                            r4.setEnabled(false);
                            if (r4.getText().toString().equals(correctAns)) {
                                r4.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                score.setText("Score: "+ correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r4.setBackgroundColor(Color.TRANSPARENT);
                                        iterator = iterator + 1;
                                        total = total + 1;
                                        updateQuestion();
                                        unselectSelectedOption();
//                                                question_number.setText(total);
                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                r4.setBackgroundColor(Color.RED);
                                if (r1.getText().toString().equals(correctAns)) {
                                    r1.setBackgroundColor(Color.GREEN);
                                } else if (r2.getText().toString().equals(correctAns)) {
                                    r2.setBackgroundColor(Color.GREEN);
                                } else if (r3.getText().toString().equals(correctAns)) {
                                    r3.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        r1.setBackgroundColor(Color.TRANSPARENT);
                                        r2.setBackgroundColor(Color.TRANSPARENT);
                                        r3.setBackgroundColor(Color.TRANSPARENT);
                                        r4.setBackgroundColor(Color.TRANSPARENT);
                                        iterator++;
                                        total++;
                                        unselectSelectedOption();
                                        updateQuestion();
                                    }
                                }, 1500);
                            }

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Quiz Questions","Something Went Wrong!!");
                }
            });
        }
    }

    private void unselectSelectedOption(){
        if (r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked()){
            r1.setChecked(false);
            r2.setChecked(false);
            r3.setChecked(false);
            r4.setChecked(false);
            r1.setEnabled(false);
            r2.setEnabled(false);
            r3.setEnabled(false);
            r4.setEnabled(false);
        }
    }

    public void Timer(int seconds, final TextView timer){
        new CountDownTimer(seconds*1000 + 1000, 1000){

            @Override
            public void onTick(long milisUntilFinished) {

                int seconds= (int) (milisUntilFinished/1000);
                int minutes= seconds/60;
                timer.setText(minutes+ ":" + seconds);

            }

            @Override
            public void onFinish() {
                timer.setText("Time Over!!");
                unselectSelectedOption();
                start.setText("Restart");
                start.setVisibility(View.VISIBLE);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        restartQuiz();
                        updateQuestion();
                    }
                });
            }
        }.start();
    }

    public void restartQuiz(){
        iterator=0;
        total=1;
        correct=wrong=0;
        score.setText("Score: 0");
        unselectSelectedOption();
        Timer(20,timer);
    }

}