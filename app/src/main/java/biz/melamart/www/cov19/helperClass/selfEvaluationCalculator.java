package biz.melamart.www.cov19.helperClass;

public class selfEvaluationCalculator {
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

    int countHistory = 0;


    //calculating factors
    float totalWeightage = 77f;
    float lowWeightage=45f;
    float midWeightage=60f;
    float highWeightage =77f;

    public float calculateWeight()
    {
        float calculateWeight = 0f;
        calculateWeight = calculateWeight + getAgeLevel()+getGenderLevel()+getBodyTempLevel()+getTravelLevel();
        calculateWeight =  calculateWeight + getBreathProblem()+getDryCough()+getSmellTaste()+getSoreThroat()+getWeakness()+getAppetite()+getChestPain();

        return  calculateWeight;
    }


    public int result()
    {
        int result=0;
        if (calculateWeight() < 15f)
        {
            result = 0;
        }
        if(calculateWeight() < 15f && getCountHistory() >=2)
        {
            result =1;
        }
          if((calculateWeight() > 15f && calculateWeight() <25f) && getCountHistory() <2)
        {
            result =1;
        }

          if((calculateWeight() > 15f && calculateWeight() <25f) && getCountHistory() >=2)
        {
            result =2;
        }

         if(calculateWeight() > 25f )
        {
            result =2;
        }

        return  result;
    }
    public float getAgeLevel() {
        return ageLevel;
    }

    public void setAgeLevel(float ageLevel) {
        this.ageLevel = ageLevel;
    }

    public float getGenderLevel() {
        return genderLevel;
    }

    public void setGenderLevel(float genderLevel) {
        this.genderLevel = genderLevel;
    }

    public float getBodyTempLevel() {
        return bodyTempLevel*10f;
    }

    public void setBodyTempLevel(float bodyTempLevel) {
        this.bodyTempLevel = bodyTempLevel;
    }

    public float getTravelLevel() {
        return travelLevel*25f;
    }

    public void setTravelLevel(float travelLevel) {
        this.travelLevel = travelLevel;
    }

    public float getBreathProblem() {
        return breathProblem*8f;
    }

    public void setBreathProblem(float breathProblem) {
        this.breathProblem = breathProblem;
    }

    public float getDryCough() {
        return dryCough*8f;
    }

    public void setDryCough(float dryCough) {
        this.dryCough = dryCough;
    }

    public float getSmellTaste() {
        return smellTaste*8f;
    }

    public void setSmellTaste(float smellTaste) {
        this.smellTaste = smellTaste;
    }

    public float getSoreThroat() {
        return soreThroat*8f;
    }

    public void setSoreThroat(float soreThroat) {
        this.soreThroat = soreThroat;
    }

    public float getWeakness() {
        return weakness*8f;
    }

    public void setWeakness(float weakness) {
        this.weakness = weakness;
    }

    public float getAppetite() {
        return appetite*8f;
    }

    public void setAppetite(float appetite) {
        this.appetite = appetite;
    }

    public float getChestPain() {
        return chestPain*8f;
    }

    public void setChestPain(float chestPain) {
        this.chestPain = chestPain;
    }



    public int getCountHistory() {
        return countHistory*5;
    }

    public void setCountHistory(int countHistory) {
        this.countHistory = countHistory;
    }

    public float getTotalWeightage() {
        return totalWeightage;
    }

    public float getLowWeightage() {
        return lowWeightage;
    }

    public float getMidWeightage() {
        return midWeightage;
    }

    public float getHighWeightage() {
        return highWeightage;
    }
}
