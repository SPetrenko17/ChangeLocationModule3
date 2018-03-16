package com.example.sergei.changelocationmodule3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sergei on 04.02.18.
 */

public class Methods {
    Double latitude;
    Double longitude;
    String[] time;
    int hours;
    int minutes;
    StringBuilder sb = new StringBuilder();
    String info;
    String lastStr;


    public boolean isCorrect(String s) {
        String[] splitS = s.split(",");
        boolean res = false;


        try {
            latitude = Double.parseDouble(splitS[0]);
            longitude = Double.parseDouble(splitS[1]);
            time = splitS[2].split(":");
            hours = Integer.parseInt(time[0]);
            minutes = Integer.parseInt(time[1]);
            if (hours >= 0 && hours <= 24 && minutes >= 0 && minutes <= 60
                //&& Double.toString(latitude).equals(".")&& Double.toString(longitude).equals(".")
                //проверить уникальность времени
                    ) {

                res = true;
            }
            sb.append("latitude: " + Double.toString(latitude) + " longitude: " + Double.toString(longitude) + " hours: " + hours + " minutes: " + minutes);
            info = sb.toString();


        } catch (Exception e) {

        }
        return res;


    }
    public String getInfo(){
        sb.delete(0,sb.length());
        return  info;

    }
    public ArrayList<String> SortList(ArrayList<String> List){
        lastStr = List.get(0);
        for (int i=0;i<List.size();i++){
            if(getTimeFromString("hh", List.get(i).split(",")[2])<getTimeFromString("hh",lastStr.split(",")[2])){
                lastStr = List.get(i);
                List.set(List.indexOf(lastStr),List.get(i));
                List.set(i,lastStr);
            }
        }

        /*for (String Listtime:List) {
            if(Integer.parseInt(Listtime.split(",")[2].split(":")[0])>getCurrentTime("hh") ){

            }
        }
        */

        return List;
    }
    public ArrayList<String> newSortList(ArrayList<String> List){ //пишу
        //lastStr = List.get(1);

        for(int i=0;i<List.size();i++){
            lastStr = List.get(i+1);
            if(getTimeFromString("hh", List.get(i).split(",")[2])>getTimeFromString("hh",lastStr.split(",")[2])){

            }
            else if(getTimeFromString("hh", List.get(i).split(",")[2])==getTimeFromString("hh",lastStr.split(",")[2])){
                if(getTimeFromString("mm", List.get(i).split(",")[2])<getTimeFromString("mm",lastStr.split(",")[2])){

                }
                else if(getTimeFromString("mm", List.get(i).split(",")[2])>getTimeFromString("mm",lastStr.split(",")[2])){

                }
            }

        }
        return List;
    }
    public int getCurrentTime(String bool){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeHM = simpleDateFormat.format(calendar.getTime());
        hours = Integer.parseInt(timeHM.split(":")[0]);
        minutes = Integer.parseInt(timeHM.split(":")[1]);
        if(bool=="hh"){
            return hours;
        }
        else if(bool=="mm"){
            return minutes;
        }
        else
            return 0;
    }


    public int getTimeFromString(String code,String str){//посылаем код и строку - получаем часы или минуты
        if(code=="hh"){
            return Integer.parseInt(str.split(":")[0]);
        }
        else if(code=="mm"){
            return Integer.parseInt(str.split(":")[1]);
        }
        else
            return 0;
    }
    protected Boolean isSmaller(String timeOne, String timeTwo){ // возвращает true, если первый элемент меньше второго, иначе-false
        if(Integer.parseInt(timeOne.split(":")[0])<Integer.parseInt(timeTwo.split(":")[0])){

            return  true;

        }
        else if (Integer.parseInt(timeOne.split(":")[0])==Integer.parseInt(timeTwo.split(":")[0])){
            if(Integer.parseInt(timeOne.split(":")[1])<Integer.parseInt(timeTwo.split(":")[1])){

                return  true;
            }
            else   return false;
        }
        else return false;

    }
    protected static int returnSeconds(String s) {
        return Integer.parseInt(s.split(",")[2].split(":")[0])*3600+Integer.parseInt(s.split(",")[2].split(":")[1])*60;
    }


}
