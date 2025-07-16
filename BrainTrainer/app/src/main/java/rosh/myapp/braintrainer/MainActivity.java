package rosh.myapp.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAns;
    int score = 0;
    int numberOfQuestions;
    TextView resultTextView;
    TextView scoreTextView;
    Button button, button1, button2, button3;
    TextView questionTextView;
    TextView timerTextView;
    Button playAgainButton;
    Button goButton;
    ConstraintLayout gameLayout;

    //start game
    public void startGame(View view){
        gameLayout.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.INVISIBLE);
    }

    //method to select answers
    public void chooseAnswer(View view){

        resultTextView.setVisibility(View.VISIBLE);

        //check if user has selected correct answer
        if (Integer.toString(locationOfCorrectAns).equals(view.getTag().toString())){
            //if correct
            resultTextView.setText("Correct :)");
            score++;
        }else {
            //if wrong
            resultTextView.setText("Wrong :(");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
    }

    //update new question
    public void newQuestion(){
        //get random variable to gerate random numbers in bound to 21
        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        //generate random sum question
        questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        //select the random location for correct ans
        locationOfCorrectAns = random.nextInt(4);

        answers.clear();

        //generate wrong answer and add to arraylist on random index including correct ans
        for (int i=0; i<4; i++){
            if (i == locationOfCorrectAns){
                answers.add(a+b);
            }else {
                int wrongAns = random.nextInt(41);

                while (wrongAns == a+b){
                    wrongAns = random.nextInt(41);
                }
                answers.add(random.nextInt(41));
            }
        }

        //set random answers stored in arraylist
        button.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    //reset the game
    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);

        //set timer for 30  min
        new CountDownTimer(30100,1000){

            @Override
            public void onFinish() {
                resultTextView.setText("Yay!");
                playAgainButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView)findViewById(R.id.gameStatusTextView);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        goButton = (Button)findViewById(R.id.goButton);
        gameLayout = (ConstraintLayout)findViewById(R.id.gameLayout);

        playAgain(findViewById(R.id.scoreTextView));

    }
}