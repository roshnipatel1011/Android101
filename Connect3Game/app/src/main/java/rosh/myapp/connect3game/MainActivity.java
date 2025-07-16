package rosh.myapp.connect3game;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //0: yellow , 1: red , 2: empty

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int activePlayer = 0;
    boolean gameActive = true;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        Log.i("Tag", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(1800).setDuration(300);

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    gameActive = false;

                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "yellow";
                    } else {
                        winner = "red";
                    }

                    Button playAgainButton = (Button) findViewById(R.id.playGameButton);
                    TextView winnerText = (TextView) findViewById(R.id.winnerTextView);

                    winnerText.setText(winner + " has won!!!");

                    winnerText.setVisibility(VISIBLE);
                    playAgainButton.setVisibility(VISIBLE);

                }

            }
        }

    }

    public void playAgain(View view){
        Button playAgainButton = (Button) findViewById(R.id.playGameButton);
        TextView winnerText = (TextView) findViewById(R.id.winnerTextView);

        winnerText.setVisibility(INVISIBLE);
        playAgainButton.setVisibility(INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ImageView counters = (ImageView) gridLayout.getChildAt(i);

            counters.setImageDrawable(null);
        }

        Arrays.fill(gameState, 2);
        activePlayer = 0;
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }
}