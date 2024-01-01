package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame extends JFrame {

    MyPanel mp = null; // 定義面板
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }

    public TankGame() {  // 參考DrawCircle.java
        System.out.println("請輸入數字選擇  1: 新遊戲  2: 繼續上局 ");
        String key = scanner.next();
        mp = new MyPanel(key);

        new Thread(mp).start(); // 啟動MyPanel線程，通過run方法的不停重繪面板，做出子彈飛行的痕跡
        this.add(mp); // 把面板（遊戲的繪圖區）放入畫框中
        this.setSize(1300, 950);
        this.addKeyListener(mp); // 讓JFrame監聽mp的鍵盤事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // 在JFrame 中增加響應關閉窗口的處理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // System.out.println("監聽到關閉窗口");
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
