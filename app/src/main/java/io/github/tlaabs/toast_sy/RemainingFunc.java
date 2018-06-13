package io.github.tlaabs.toast_sy;

/**
 * Created by devsimMe on 2018-06-09.
 */

public class RemainingFunc {
    public RemainingFunc(){}

    public String init(String _endTime, String _now){
        BucketItem item = new BucketItem();
        item.setEndTime(_endTime);
        String endTime = item.getEndTime();
        String now = new String(_now);
        //EndTime - NowTime
        String endTimeP = endTime.substring(11,endTime.length());
        String nowP = now.substring(11,now.length());
        //Processing
        int M = Integer.parseInt(endTimeP.substring(3,5)) - Integer.parseInt(nowP.substring(3,5));
        int dayFlag = 0;
        if(M < 0) {
            M = Integer.parseInt(endTimeP.substring(3,5)) + 60 - Integer.parseInt(nowP.substring(3,5));
            dayFlag = 1;
        }
        int H = Integer.parseInt(endTimeP.substring(0,2))-dayFlag - Integer.parseInt(nowP.substring(0,2));
        if(H < 0) H = Integer.parseInt(endTimeP.substring(0,2)) + 24 - Integer.parseInt(nowP.substring(0,2));
        String HS = H + "";
        if(HS.length() == 1) HS = "0" + H;
        String MS = M + "";
        if(MS.length() == 1) MS = "0" + M;

        if(H > 12){
            return new String("만료");
        }else
            return new String(HS+":"+MS);
    }
}
