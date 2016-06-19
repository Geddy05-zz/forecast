package com.schellevis;

import java.util.ArrayList;

/**
 * Created by geddy on 14/06/16.
 */
public class DES {

    double smoothingLevel;
    double trendLevel;
    ArrayList<Integer> values;
    ArrayList<Double> predications = new ArrayList<>();
    ArrayList<Double> errors = new ArrayList<>();
    double lastSmoothing = 0.0;

//    public DES(double smoothingLevel,double trendLevel, ArrayList<Integer> values){
    public DES(ArrayList<Integer> values){
        this.smoothingLevel = 0.66;
        this.trendLevel = 0.05;
        this.values = values;
    }

    public void doPrediction(int k){
        int target = 48;

        double LevelEstimate = values.get(1);
        double intercept = LevelEstimate - values.get(0);
        double forecast = LevelEstimate + intercept;
        double error = values.get(2) - forecast;
        System.out.println(forecast);

        // =trend+gamma*alphe*error
        for(int i = 3; i < target; i++) {
            LevelEstimate = LevelEstimate + intercept + (smoothingLevel*error);
            intercept = intercept+smoothingLevel*trendLevel*error;
            forecast = LevelEstimate + intercept;
            if (i > values.size()-1){
                error = 0;
                System.out.println(i);

            }else{
                error = values.get(i) - forecast;
            }

            predications.add(forecast);
            errors.add(error);

            System.out.println(forecast);
        }

        System.out.println(predications);
        System.out.println(errors);
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
