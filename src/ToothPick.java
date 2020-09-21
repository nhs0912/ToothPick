import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
/**
 * version history
 * [v0.12]
 * 2020-09-21
 * 1) start button & end Button disabled
 * [v0.11]
 * 2020-09-11
 * 1) 진행시간 두자릿수 표현
 *
 * [v0.1]
 * 2020-09-10
 * 1) 이쑤시개 초기 배포
 */

class EventFireGui extends JFrame implements ActionListener {

    private static final long serialVersionUID=1L;
    static String versionInfo = "ToothPick_이쑤시개 v0.12";
    JTextField startTimeField = new JTextField(12);
    JTextField endTimeField = new JTextField(12);
    JTextField executeTimeField = new JTextField(10);

    //남은 시간
    JTextField remainTimeField = new JTextField(10);
    //진행시간
    JTextField progressTimeField = new JTextField(12);
    //현재 상태
    JTextField currentState = new JTextField("초기화상태");
    //시작버튼
    JButton buttonStart = new JButton("Start");
    //종료버튼
    JButton buttonEnd = new JButton("End");


    Thread timerThread;
    Thread startBtnThread;

    myTimer timer;
    StartBtn startBtn;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EventFireGui(){
        super(versionInfo);
        setBounds(0,0,800,500); //창의 시작 위
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //창이 가운데 나오도록 설정
        Container ContentPane = this.getContentPane();
        ContentPane.setLayout(new FlowLayout());
        buttonEnd.setEnabled(false);

        /**
         * 단축키 설정
         */
        KeyStroke exitShortKey = KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK);
        System.out.println(exitShortKey);

        /**
         * 버튼 구성
         */

        JPanel buttonPane = new JPanel();
        //시간 입력 Panel
        JPanel ComponentPane = new JPanel();
        //남은 시간, 진행시간 표시 Panel
        JPanel secondComponenetPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());

        //시작버튼 단축키
        buttonStart.setMnemonic('s');
        //종료버튼 단축키
        buttonEnd.setMnemonic('e');

        buttonStart.setBounds(100,100,100,100);

        buttonPane.add(buttonStart);
        buttonPane.add(buttonEnd, BorderLayout.SOUTH);

        JLabel startTimeLabel = new JLabel("시작시간");
        JLabel endTimeLabel = new JLabel("종료시간");
        JLabel executeTimeLablel = new JLabel("실행시간(분)");

        JLabel remainTimeLabel = new JLabel("남은시간(분)");
        JLabel progressTimeLablel = new JLabel("진행시간");


        startTimeLabel.setBounds(100,100,100,100);


        executeTimeField.setText("90");

        //Start Time and end Time can't edit
        startTimeField.setEditable(false);
        endTimeField.setEditable(false);
        remainTimeField.setEditable(false);
        progressTimeField.setEditable(false);

        //시작시간
        ComponentPane.add(startTimeLabel);
        ComponentPane.add(startTimeField);
        //종료시간
        ComponentPane.add(endTimeLabel);
        ComponentPane.add(endTimeField);
        //실행시간 입력
        ComponentPane.add(executeTimeLablel);
        ComponentPane.add(executeTimeField);

        //남은시간
        secondComponenetPane.add(remainTimeLabel);
        secondComponenetPane.add(remainTimeField);
        //진행시간
        secondComponenetPane.add(progressTimeLablel);
        secondComponenetPane.add(progressTimeField);

        ContentPane.add(ComponentPane);
        ContentPane.add(buttonPane);
        ContentPane.add(secondComponenetPane);

        setVisible(true);


        //Start Button 실행
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date startTime = new Date();
                String executeTimeStr = executeTimeField.getText();
                if(executeTimeStr== null || executeTimeStr==""){

                }

                int executeTime = Integer.parseInt(executeTimeStr);

                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE,executeTime);

                String startTimeStr = dateFormat.format(startTime);
                String endTimeStr = dateFormat.format(cal.getTime());

                startTimeField.setText(startTimeStr);
                endTimeField.setText(endTimeStr);

                timer = new myTimer(executeTimeField,startTimeField,endTimeField);
                startBtn = new StartBtn(executeTimeField,startTimeField,endTimeField,remainTimeField,progressTimeField);
                timerThread = new Thread(timer);
                timerThread.setPriority(Thread.MAX_PRIORITY);
                timerThread.start();

                startBtnThread = new Thread(startBtn);
                startBtnThread.setPriority(Thread.NORM_PRIORITY);
                startBtnThread.start();

                buttonStart.setEnabled(false);
                buttonEnd.setEnabled(true);





            }
        });

        //End 버튼 실행
        buttonEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimeField.setText("");
                endTimeField.setText("");
                startBtn.SetIsEnd(true);
                if(startBtnThread.getState().equals("TERMINATED")){
                    currentState.setText("종료!!");
                }
                buttonStart.setEnabled(true);
                buttonEnd.setEnabled(false);
            }
        });





    }

    public static String getVersionInfo() {
        return versionInfo;
    }

    public static void setVersionInfo(String versionInfo) {
        EventFireGui.versionInfo = versionInfo;
    }

    public JTextField getStartTimeField() {
        return startTimeField;
    }

    public void setStartTimeField(JTextField startTimeField) {
        this.startTimeField = startTimeField;
    }

    public JTextField getEndTimeField() {
        return endTimeField;
    }

    public void setEndTimeField(JTextField endTimeField) {
        this.endTimeField = endTimeField;
    }

    public JTextField getExecuteTimeField() {
        return executeTimeField;
    }

    public void setExecuteTimeField(JTextField executeTimeField) {
        this.executeTimeField = executeTimeField;
    }

    public JTextField getRemainTimeField() {
        return remainTimeField;
    }

    public void setRemainTimeField(JTextField remainTimeField) {
        this.remainTimeField = remainTimeField;
    }

    public JTextField getProgressTimeField() {
        return progressTimeField;
    }

    public void setProgressTimeField(JTextField progressTimeField) {
        this.progressTimeField = progressTimeField;
    }

    public JTextField getCurrentState() {
        return currentState;
    }

    public void setCurrentState(JTextField currentState) {
        this.currentState = currentState;
    }

    public JButton getButtonStart() {
        return buttonStart;
    }

    public void setButtonStart(JButton buttonStart) {
        this.buttonStart = buttonStart;
    }

    public JButton getButtonEnd() {
        return buttonEnd;
    }

    public void setButtonEnd(JButton buttonEnd) {
        this.buttonEnd = buttonEnd;
    }

    public Thread getTimerThread() {
        return timerThread;
    }

    public void setTimerThread(Thread timerThread) {
        this.timerThread = timerThread;
    }

    public Thread getStartBtnThread() {
        return startBtnThread;
    }

    public void setStartBtnThread(Thread startBtnThread) {
        this.startBtnThread = startBtnThread;
    }

    public myTimer getTimer() {
        return timer;
    }

    public void setTimer(myTimer timer) {
        this.timer = timer;
    }

    public StartBtn getStartBtn() {
        return startBtn;
    }

    public void setStartBtn(StartBtn startBtn) {
        this.startBtn = startBtn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}



class myTimer implements Runnable{
    JTextField executeTimeField;
    JTextField startTimeField;
    JTextField endTimeField;

    myTimer(JTextField executeTimeField,JTextField startTimeField,JTextField endTimeField){
        this.executeTimeField = executeTimeField;
        this.startTimeField = startTimeField;
        this.endTimeField = endTimeField;
    }
    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startTime = new Date();

        String executeTimeStr = executeTimeField.getText();

        if(executeTimeStr == null || executeTimeStr==""){

        }

        int executeTime = Integer.parseInt(executeTimeStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.MINUTE,executeTime);

        String startTimeStr = dateFormat.format(startTime);
        String endTimeSTr = dateFormat.format(cal.getTime());

        startTimeField.setText(startTimeStr);
        endTimeField.setText(endTimeSTr);



    }


}

class StartBtn implements Runnable {
    JTextField executeTimeField;
    JTextField startTimeField;
    JTextField endTimeField;
    JTextField remainTimeField;
    JTextField progressTimeField;

    private boolean isEnd = false;
    String hourStr;
    String minStr;
    String secondStr;

    StartBtn(JTextField executeTimeField, JTextField startTimeField, JTextField endTImeField,
             JTextField remainTimeField, JTextField progressTimeField){
        this.executeTimeField = executeTimeField;
        this.startTimeField = startTimeField;
        this.endTimeField = endTImeField;
        this.remainTimeField = remainTimeField;
        this.progressTimeField = progressTimeField;
    }

    public void SetIsEnd(boolean isEnd){
        this.isEnd =isEnd;
    }

    @Override
    public void run() {

        String executeTimeStr = executeTimeField.getText();
        int executeTime = Integer.parseInt(executeTimeStr);

        Timer screenSaverDisabler = new Timer();
        screenSaverDisabler.schedule(new TimerTask() {
            int seconds = 0;
            int min = 0;
            int hour = 0;
            int total = 0;
            int remainTime = executeTime;
            Robot r = null;
            {
                try{
                    r = new Robot();
                }catch(AWTException e){
                    screenSaverDisabler.cancel();
                }
            }
            @Override
            public void run() {
                Point loc = MouseInfo.getPointerInfo().getLocation();
                if(isEnd){
                    screenSaverDisabler.cancel();
                }
                r.mouseMove(loc.x+1,loc.y);
                r.mouseMove(loc.x,loc.y);
                seconds++;
                total++;
                if(seconds == 60){
                    min++;
                    remainTime--;
                    seconds=0;
                }

                if(min==60){
                    hour++;
                    min=0;
                    seconds=0;
                }
                remainTimeField.setText(Integer.toString(remainTime));

                if(hour<10){
                    hourStr = "0"+hour;
                }else{
                    hourStr = Integer.toString(hour);
                }

                if(min<10){
                    minStr = "0"+min;
                }else{
                    minStr = Integer.toString(min);
                }

                if(seconds<10){
                    secondStr = "0"+seconds;
                }else{
                    secondStr = Integer.toString(seconds);
                }

                progressTimeField.setText(hourStr+":"+minStr+":"+secondStr);
                System.out.println("경과시간==="+hourStr+":"+minStr+":"+secondStr);
                if(executeTime * 60 == total){
                    screenSaverDisabler.cancel();
                }
            }
        },0,1000);

    }
}



public class ToothPick{
    public static void main(String[] args){
        new EventFireGui();
    }
}
