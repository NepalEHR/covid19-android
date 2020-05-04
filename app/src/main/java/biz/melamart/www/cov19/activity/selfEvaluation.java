package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.selfEvaluationCalculator;
import biz.melamart.www.cov19.utils.resultDilouge;
import butterknife.BindView;
import butterknife.ButterKnife;

public class selfEvaluation extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn11)
    Button btn11;
    @BindView(R.id.btn12)
    Button btn12;

    @BindView(R.id.btn13)
    Button btn13;

    @BindView(R.id.btn14)
    Button btn14;

    @BindView(R.id.btn15)
    Button btn15;

    @BindView(R.id.btn16)
    Button btn16;

    @BindView(R.id.btn17)
    Button btn17;

    @BindView(R.id.btn18)
    Button btn18;

    @BindView(R.id.btn19)
    Button btn19;

    @BindView(R.id.btn20)
    Button btn20;

    @BindView(R.id.btn21)
    Button btn21;

    @BindView(R.id.btn22)
    Button btn22;

    @BindView(R.id.btn23)
    Button btn23;

    @BindView(R.id.btn24)
    Button btn24;

    @BindView(R.id.btn25)
    Button btn25;

    @BindView(R.id.btn26)
    Button btn26;

    @BindView(R.id.btn27)
    Button btn27;

    @BindView(R.id.btn28)
    Button btn28;

    @BindView(R.id.btn29)
    Button btn29;

    @BindView(R.id.btn30)
    Button btn30;

    @BindView(R.id.btn31)
    Button btn31;

    @BindView(R.id.btn32)
    Button btn32;

    @BindView(R.id.btn33)
    Button btn33;


    @BindView(R.id.btn34)
    Button btn34;

    @BindView(R.id.btn35)
    Button btn35;

    @BindView(R.id.btn36)
    Button btn36;

    @BindView(R.id.btn37)
    Button btn37;

    @BindView(R.id.btn38)
    Button btn38;

    @BindView(R.id.btn39)
    Button btn39;

    @BindView(R.id.btn40)
    Button btn40;

    @BindView(R.id.btn41)
    Button btn41;

    @BindView(R.id.btn42)
    Button btn42;

    float ageLevel = 0f;
    float genderLevel = 0f;
    float bodyTempLevel = 0f;
    float travelLevel = 0f;

    float breathProblem= 0f;
    float dryCough= 0f;
    float smellTaste= 0f;
    float soreThroat= 0f;
    float weakness= 0f;
    float appetite= 0f;
    float chestPain= 0f;

    float patientHistory = 0f;
    int countHistory = 0;

    Boolean d1=false,d2=false,d3=false,d4=false,d5=false,d6=false,d7=false,d8 =false,d9=false;

    selfEvaluationCalculator selfEvaluationCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_evaluation);
        setTitle("COVID 19 Self Diagnostic");
        ButterKnife.bind(this);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.diagonostic_menu, menu);

        MenuItem diagonoseItem = menu.findItem(R.id.action_diagnose);
        diagonoseItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
        selfEvaluationCalculator = new selfEvaluationCalculator();
        selfEvaluationCalculator.setAgeLevel(ageLevel);
        selfEvaluationCalculator.setGenderLevel(genderLevel);
        selfEvaluationCalculator.setBodyTempLevel(bodyTempLevel);
        selfEvaluationCalculator.setTravelLevel(travelLevel);
        selfEvaluationCalculator.setBreathProblem(breathProblem);
        selfEvaluationCalculator.setDryCough(dryCough);
        selfEvaluationCalculator.setSmellTaste(smellTaste);
        selfEvaluationCalculator.setSoreThroat(soreThroat);
        selfEvaluationCalculator.setWeakness(weakness);
        selfEvaluationCalculator.setAppetite(appetite);
        selfEvaluationCalculator.setChestPain(chestPain);
        selfEvaluationCalculator.setCountHistory(countHistory);
        float result =((selfEvaluationCalculator.calculateWeight()/77)*100);
//                Toast.makeText(selfEvaluation.this, "your result is " + selfEvaluationCalculator.calculateWeight(), Toast.LENGTH_SHORT).show();
                resultDilouge resultDilouge = new resultDilouge(selfEvaluation.this,selfEvaluationCalculator.result(),result);
                resultDilouge.show();
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }
    public void setAgeGroup(View v) {
        String age = v.getTag().toString().trim();
        if (age.trim().equals("Low")) {
//        btn1.setBackgroundColor(getResources().getColor(R.color.stat_screen2));
            btn1.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn2.getBackground().setColorFilter(null);
            btn3.getBackground().setColorFilter(null);
            ageLevel = 0.15f;
        } else if (age.trim().equals("Med")) {
            btn2.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn1.getBackground().setColorFilter(null);
            btn3.getBackground().setColorFilter(null);
            ageLevel = 0.5f;

        } else {
            btn3.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn1.getBackground().setColorFilter(null);
            btn2.getBackground().setColorFilter(null);
            ageLevel = 1.0f;
        }


    }


    public void setGenderGroup(View v) {
        String gender = v.getTag().toString().trim();

        if (gender.trim().equals("Low")) {
//        btn1.setBackgroundColor(getResources().getColor(R.color.stat_screen2));
            btn4.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn5.getBackground().setColorFilter(null);
            btn6.getBackground().setColorFilter(null);
            genderLevel = 0.05f;
        } else if (gender.trim().equals("Med")) {
            btn5.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn4.getBackground().setColorFilter(null);
            btn6.getBackground().setColorFilter(null);
            genderLevel = 0.5f;

        } else {

            btn6.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn4.getBackground().setColorFilter(null);
            btn5.getBackground().setColorFilter(null);
            genderLevel = 1.0f;
        }


    }


    public void setBodyTemp(View v) {
        String body = v.getTag().toString().trim();

        if (body.trim().equals("Low")) {
//        btn1.setBackgroundColor(getResources().getColor(R.color.stat_screen2));
            btn7.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn8.getBackground().setColorFilter(null);
            btn9.getBackground().setColorFilter(null);
            bodyTempLevel = 0.15f;
        } else if (body.trim().equals("Med")) {
            btn8.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn7.getBackground().setColorFilter(null);
            btn9.getBackground().setColorFilter(null);
            bodyTempLevel = 0.75f;

        } else {

            btn9.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn7.getBackground().setColorFilter(null);
            btn8.getBackground().setColorFilter(null);
            bodyTempLevel = 1.0f;
        }


    }


    public void setTravelLevel(View v) {
        String travel = v.getTag().toString().trim();

        if (travel.trim().equals("Low")) {
//        btn1.setBackgroundColor(getResources().getColor(R.color.stat_screen2));
            btn10.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn11.getBackground().setColorFilter(null);
            btn12.getBackground().setColorFilter(null);
            travelLevel = 0.15f;
        } else if (travel.trim().equals("Med")) {
            btn11.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn10.getBackground().setColorFilter(null);
            btn12.getBackground().setColorFilter(null);
            travelLevel = 1.0f;

        } else {

            btn12.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btn10.getBackground().setColorFilter(null);
            btn11.getBackground().setColorFilter(null);
            travelLevel = 1.0f;
        }


    }

    public float toggleSelectButton(Button btnA, Button btnB, Button btnC, String level)
    {
        float wt = 0.0f;
        if (level.trim().toLowerCase().equals("low")) {
//        btn1.setBackgroundColor(getResources().getColor(R.color.stat_screen2));
            btnA.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btnB.getBackground().setColorFilter(null);
            btnC.getBackground().setColorFilter(null);
            wt = 0.15f;
        } else if (level.trim().toLowerCase().equals("med")) {
            btnB.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btnA.getBackground().setColorFilter(null);
            btnC.getBackground().setColorFilter(null);
            wt = 0.5f;

        } else {

            btnC.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
            btnA.getBackground().setColorFilter(null);
            btnB.getBackground().setColorFilter(null);
            wt=1.0f;
        }
return wt;

    }

    public void toggleButton(Button btnA, Boolean toggle, Boolean noneb)
    {
        if(!toggle)
        {
            btnA.getBackground().setColorFilter(0xDD20d2bb, PorterDuff.Mode.MULTIPLY);
        }
        else
        {  btnA.getBackground().setColorFilter(null);

        }

        if(noneb)
        {
            btn34.getBackground().setColorFilter(null);
            btn35.getBackground().setColorFilter(null);
            btn36.getBackground().setColorFilter(null);
            btn37.getBackground().setColorFilter(null);
            btn38.getBackground().setColorFilter(null);
            btn39.getBackground().setColorFilter(null);
            btn40.getBackground().setColorFilter(null);
            btn41.getBackground().setColorFilter(null);
            d1=false;
            d2=false;
            d3=false;
            d4=false;
            d5=false;
            d6=false;
            d7=false;
            d8=false;
            d9 =true;
        }
        else
        {
            d9 =false;
            btn42.getBackground().setColorFilter(null);
        }
    }

public  void  setDisease(View v)
{
    String disease= v.getTag().toString().trim().toLowerCase();
    switch ( disease)
    {
        case "1":
            toggleButton(btn34,d1,false);
            if(d1)
            {
                d1= false;
                countHistory--;
            }
            else
            {
                d1=true;
                countHistory++;
            }
            break;
        case "2":
            toggleButton(btn35,d2,false);
            if(d2)
            {
                d2= false;
                countHistory--;
            }
            else
            {
                d2=true;
                countHistory++;
            }
            break;
        case "3":
            toggleButton(btn36,d3,false);
            if(d3)
            {
                d3= false;
                countHistory--;
            }
            else
            {
                d3=true;
                countHistory++;
            }
            break;
        case "4":
            toggleButton(btn37,d4,false);
            if(d4)
            {
                d4= false;
                countHistory--;
            }
            else
            {
                d4=true;
                countHistory++;
            }
            break;
        case "5":
            toggleButton(btn38,d5,false);
            if(d5)
            {
                d5= false;
                countHistory--;
            }
            else
            {
                d5=true;
                countHistory++;
            }
            break;
        case "6":
            toggleButton(btn39,d6,false);
            if(d6)
            {
                d6= false;
                countHistory--;
            }
            else
            {
                d6=true;
                countHistory++;
            }
            break;
        case "7":
            toggleButton(btn40,d7,false);
            if(d7)
            {
                d7= false;
                countHistory--;
            }
            else
            {
                d7=true;
                countHistory++;
            }
            break;
        case "8":
            toggleButton(btn41,d8,false);
            if(d8)
            {
                d8= false;
                countHistory--;
            }
            else
            {
                d8=true;
                countHistory++;
            }
            break;
        case "9":
            toggleButton(btn42,d9,true);
            if(d9)
            {
                d9= false;
                countHistory=0;
            }
            else
            {
                d9=true;
                countHistory=0;
            }
            break;
    }


}
    public void setSymptoms(View v) {
        String travel = v.getTag().toString().trim();
        String[] data = travel.split(",");

        switch (data[0].trim())
        {
            case "1":
               breathProblem = toggleSelectButton(btn13,btn14,btn15, data[1].trim());

                break;
            case "2":
              dryCough=  toggleSelectButton(btn16,btn17,btn18, data[1].trim());
                break;
            case "3":
               smellTaste= toggleSelectButton(btn19,btn20,btn21, data[1].trim());
                break;
            case "4":
                soreThroat = toggleSelectButton(btn22,btn23,btn24, data[1].trim());
                break;
            case "5":
                weakness = toggleSelectButton(btn25,btn26,btn27, data[1].trim());
                break;
            case "6":
                appetite = toggleSelectButton(btn28,btn29,btn30, data[1].trim());
                break;
            case "7":
               chestPain= toggleSelectButton(btn31,btn32,btn15, data[1].trim());
                break;
        }

    }

}
