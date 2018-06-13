package io.github.tlaabs.toast_sy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void SoultionCaseTest(){
        BucketItem item = new BucketItem();
        item.setEndTime("2018-05-30 17:49");
        String endTime = item.getEndTime();
        String now = "2018-05-30 18:49";
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
            assertEquals(23, H);
        }else
            assertEquals("23:00", HS+":"+MS);
    }

    @Test
    public void integrateDBWithRemainingTime(){

    }

    @Test
    public void EndTimeNomalTest(){
        BucketItem item = new BucketItem();
//        item.setEndTime("2018-05-30 21:49");
        item.setEndTime("2018-05-30 17.49");
        String endTime = item.getEndTime(); //setEndTime
        String now = "2018-05-30 18:49"; //now Time
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
        //This case, H < 12.
        assertEquals("23" +
                ":00",HS+":"+MS);
    }

    @Test
    public void CheckTimeOutTest(){
        BucketItem item = new BucketItem();
        item.setEndTime("2018-05-30 17:49");
        String endTime = item.getEndTime();
        String now = "2018-05-30 18:49";
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
        assertEquals(23, H);
    }
}