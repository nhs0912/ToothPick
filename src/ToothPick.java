import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


class EventFireGui extends JFrame implements ActionListener {

    private static final long serialVersionUID=1L;

    public EventFireGui(){
        super("event Fire");
        setBounds(0,0,800,500); //창의 시작 위
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //창이 가운데 나오도록 설정
        Container ContentPane = this.getContentPane();
        ContentPane.setLayout(new FlowLayout());


        /**
         * 단축키 설정
         */
        KeyStroke exitShortKey = KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK);
        System.out.println(exitShortKey);

        /**
         * 버튼 구성
         */

        JPanel buttonPane = new JPanel();
        JPanel ComponentPane = new JPanel();

        buttonPane.setLayout(new FlowLayout());
        //시작버튼
        JButton buttonStart = new JButton("Start");
        //종료버튼
        JButton buttonEnd = new JButton("End");
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

        startTimeLabel.setBounds(100,100,100,100);
        JTextField startTimeField = new JTextField(12);
        JTextField endTimeField = new JTextField(12);
        JTextField executeTimeField = new JTextField(10);

        executeTimeField.setText("90");

        //Start Time and end Time can't edit
        startTimeField.setEditable(false);
        endTimeField.setEditable(false);

        ComponentPane.add(startTimeLabel);
        ComponentPane.add(startTimeField);

        ComponentPane.add(endTimeLabel);
        ComponentPane.add(endTimeField);

        ComponentPane.add(executeTimeLablel);
        ComponentPane.add(executeTimeField);

        ContentPane.add(ComponentPane);


        ContentPane.add(buttonPane);

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
                //KeyListener keyListener = new KeyListener()  <--원래 소스
                KeyListener keyListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                };

                ComponentPane.addKeyListener(keyListener);
                ComponentPane.setFocusable(true);

                int executeTime = Integer.parseInt(executeTimeStr);

                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE,executeTime);

                String startTimeStr = dateFormat.format(startTime);
                String endTimeStr = dateFormat.format(cal.getTime());

                startTimeField.setText(startTimeStr);
                endTimeField.setText(endTimeStr);

                Timer timer = new Timer(executeTimeField,startTimeField,endTimeField);
                timer.run();

                try{
                    Robot robot = new Robot();
                    //robot.delay(10000); 10초 딜레이
                    for(int i=200;i<800;i++){
                        if(keyListener.toString().equals("S")){
                            break;
                        }
                        robot.mouseMove(i,i);
                    }



                }catch(AWTException e1){
                    e1.printStackTrace();
                }






            }
        });

        buttonEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimeField.setText("");
                endTimeField.setText("");
            }
        });




    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }

}


class KeyListener extends KeyAdapter implements  Runnable{
    Timer timer;
    KeyListener(){

    }
    KeyListener(Timer timer){
        this.timer = timer;
    }

    public void KeyPressed(KeyEvent e){
        System.out.println(e.getKeyChar() + "키 입력");
        if(e.getKeyChar() == 'q' || e.getKeyChar() =='Q'){
            System.exit(0);
        }else if(e.getKeyChar()=='s' || e.getKeyChar()=='S'){

        }
    }


    @Override
    public void run() {

    }
}

class Timer implements Runnable{
    JTextField executeTimeField;
    JTextField startTimeField;
    JTextField endTimeField;

    Timer(JTextField executeTimeField,JTextField startTimeField,JTextField endTimeField){
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

public class ToothPick{
    public static void main(String[] args){
        new EventFireGui();
    }
}