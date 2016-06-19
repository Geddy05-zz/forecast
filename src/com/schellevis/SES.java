package com.schellevis;

import java.util.ArrayList;

/**
 * Created by geddy on 14/06/16.
 */
public class SES {
    double smoothingLevel;
    ArrayList<Double> bestPredication = new ArrayList<>();
    double bestError = 0;
    ArrayList<Integer> values;
    ArrayList<Double> predications;
    ArrayList<Double> errors ;
    double lastSmoothing = 0.0;

    public SES(ArrayList<Integer> values){
        this.values = values;
    }

    public void setSmoothingLevel(double smoothingLevel) {
        this.smoothingLevel = smoothingLevel;
    }

    public void doPrediction(int k){
        int target = 48;
        predications = new ArrayList<>();
        errors = new ArrayList<>();

        double LevelEstimate = 0.0;
        for(int i = 0; i < target; i++) {
            int last = (i < values.size()) ? i : values.size()-1;
            if( i == 0 ){
                LevelEstimate = calcuclateStartPoint(k);
            }else{
                if (i < values.size()+1) {
                    LevelEstimate = LevelEstimate + (smoothingLevel * errors.get(i -1));
                    // If we pass the know values keep the last smoothingValue
                    lastSmoothing = LevelEstimate;
                }else{
                    LevelEstimate = lastSmoothing;
                }
            }
            int first = values.get(last);

            double error = first - LevelEstimate;

            predications.add(LevelEstimate);
            errors.add(error);
        }

        double sumOfSquaredErrors = squaredError();
        if(bestError == 0 || sumOfSquaredErrors < bestError){
            bestPredication = predications;
            bestError = sumOfSquaredErrors;

            System.out.println(predications);
            System.out.println(errors);

            System.out.println("Sum Squared errors : "+sumOfSquaredErrors);
            System.out.println("Alpha : "+smoothingLevel);
        }
    }

    public double calcuclateStartPoint(int k){

        int sum = 0;
        for (int i =0; i < k; i++ ){
            sum += values.get(i);
        }
        return (sum/k);
    }

    public double squaredError(){
        double sumOfSquaredErrors = 0.0;
        int count = 0;
        for (Double error: errors) {
            if(count < values.size()) {
                sumOfSquaredErrors += Math.pow(error, 2);
            }
            count++;
        }
        return sumOfSquaredErrors;
    }
}
