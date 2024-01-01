package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大戰的繪圖區
 */

// 為了監聽 鍵盤事件，實現KeyListener
// ⚠️ 為了讓Panel不停的重繪子彈，需要將MyPanel實現Runnable，當作一個線程來使用
public class MyPanel extends JPanel implements KeyListener, Runnable {

    //定義我的坦克
    Hero hero = null;

    // 定義敵人的坦克，放入到線程安全的Vector集合中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 6;

    // 定義一個Vector，用於存放炸彈(一顆炸彈是由三張圖片構成的)
    // 當子彈擊中坦克時，加入一個Bomb對象到bombs
    Vector<Bomb> bombs = new Vector<>();
    // 定義三張圖片，用於顯示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    // 定義存放Node對象的Vector，用於恢復敵人坦克的座標和方向
    Vector<Node> nodes = new Vector<>();

    // 增加形參 key，let user choose to start new game or continue last game record
    public MyPanel(String key) { // constructor
        // 先判斷紀錄文件是否存在
        // 如果存在，正常執行；不存在，提示只能開啟新遊戲，並set key = "1"
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("文件不存在，現在開啟新遊戲：）");
            key = "1";
        }
        // 將MyPanel對象的 enemyTanks 設置給 Recorder 的 enemyTanks，否則Recorder報空指針
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(500, 600);// 初始化自己的坦克，位置是100，100
        hero.setSpeed(6); // 改變移動速度

        // 初始化敵人坦克 分兩種情況
        switch (key) {
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 10);
                    // ⚠️ 為防衝撞設計，將enemyTanks 設置給 enemyTank 對象！！！
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    // 啟動enemyTank線程
                    enemyTank.setSpeed(3); // 需要設置速度，不然只會轉向不會移動
                    new Thread(enemyTank).start();
                    //-------- 啟動enemyTank線程 run方法中即new shot並啟動了shot線程 故下面暫時注釋  ------------
                    // 給enemyTank 加入一顆子彈（後續可以加入多顆子彈）
                    //Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+70,enemyTank.getDirect());
                    // ⚠️ 將新建的子彈，加入到enemyTank的Vector成員中去
                    //enemyTank.shots.add(shot);
                    //立即啟動 shot 對象
                    // new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": // 繼續上局遊戲
                // nodes = Recorder.getNodesAndEnemyTankRec();

                for (int i = 0; i < nodes.size(); i++) {
                    Node node = (Node) nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    // ⚠️ 為防衝撞設計，將enemyTanks 設置給 enemyTank 對象！！！
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());
                    // 啟動enemyTank線程
                    enemyTank.setSpeed(3); // 需要設置速度，不然只會轉向不會移動
                    new Thread(enemyTank).start();
                    //-------- 啟動enemyTank線程 run方法中即new shot並啟動了shot線程 故下面暫時注釋  ------------
                    // 給enemyTank 加入一顆子彈（後續可以加入多顆子彈）
                    //Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+70,enemyTank.getDirect());
                    // ⚠️ 將新建的子彈，加入到enemyTank的Vector成員中去
                    //enemyTank.shots.add(shot);
                    //立即啟動 shot 對象
                    // new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("您的輸入有誤...");
        }

        // 初始化圖片對象  jdk11 要寫成MyPanel.class   不是Panel.class(jdk8)
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.jpg"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.jpg"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.jpg"));

        // 初始化面板結束後，播放音樂
        new AePlayWave("src/tankgame.wav").start();
    }

    // 編寫方法，顯示我方擊毀敵方坦克的數量
    public void showInfo(Graphics g) {
        // 畫出玩家的總成績
        g.setColor(Color.black);
        Font font = new Font("宋體", Font.BOLD, 24);
        g.setFont(font);

        g.drawString("您累積擊毀敵方坦克:", 1020, 30);
        drawTank(1020, 60, g, 0, 0); // 畫了個敵方坦克
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); //填充矩形，默認是黑色
        showInfo(g);
        // 畫出自己的坦克-封裝成方法
        // 此處方向需要換成變量，才能活用-->在tank類中加一個direct屬性-->hero.getDirect()
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        // 畫出hero射出的子彈(單顆)
//        if(hero.shot != null && hero.shot.isLive){
//            //g.fill3DRect(hero.shot.x,hero.shot.y,3,3,false);
//            g.draw3DRect(hero.shot.x,hero.shot.y,1,1,false);
//        }

        // 將hero的子彈集合，遍歷取出and繪製
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else { // 如果該shot已無效，就從集合中拿掉
                hero.shots.remove(shot);
            }
        }

        // 如果bombs集合中有炸彈對象，就畫出
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸彈
            try {
                Thread.sleep(60);  // ⚠️ 第一顆子彈沒效果，取出子彈前先休眠一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bomb bomb = bombs.get(i);
            // 根據當前bomb對象的life值 畫出對應圖片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeMinus();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        // 畫出敵人的坦克，遍歷Vector
        for (int i = 0; i < enemyTanks.size(); i++) { //⚠️不能用enemyTankSize這個定值，因為可能會銷毀，這樣打不完
            EnemyTank enemyTank = enemyTanks.get(i);
            // 判斷當前坦克是否還存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                // 畫出敵人坦克的子彈 遍歷 因為不只有一顆子彈
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    // 取出子彈
                    Shot shot = enemyTank.shots.get(j);
                    // 繪製
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        // 從Vector移除子彈
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    // 編寫方法，封裝坦克

    /**
     * @param x      tank左上角x橫坐標
     * @param y      tank左上角y縱座標
     * @param g      畫筆
     * @param direct 坦克方向（上下左右）
     * @param type   坦克類型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // set color according with its type
        switch (type) {
            case 0:// enemy tank
                g.setColor(Color.cyan);
                break;
            case 1:// my tank
                g.setColor(Color.orange);
                break;
        }
        // 根據坦克不同方向，繪製對應形狀的坦克
        // direct 表示方向 0 向上 1 向右 2 向下 3 向左 （順時針寫：）
        switch (direct) {
            case 0: // means up
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // middle body
                g.fillOval(x + 10, y + 20, 20, 20); // round top
                g.drawLine(x + 20, y + 30, x + 20, y); // line == start point + end point
                break;
            case 1: // means right
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // middle body
                g.fillOval(x + 20, y + 10, 20, 20); // round top
                g.drawLine(x + 30, y + 20, x + 60, y + 20); // line
                break;
            case 2: // means down
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // middle body
                g.fillOval(x + 10, y + 20, 20, 20); // round top
                g.drawLine(x + 20, y + 30, x + 20, y + 60); // line
                break;
            case 3: // means left
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // middle body
                g.fillOval(x + 20, y + 10, 20, 20); // round top
                g.drawLine(x + 30, y + 20, x, y + 20); // line
                break;
            default:
                System.out.println("no process...");
        }
    }

    // 如我方坦克可發送多顆子彈，在判斷我方子彈是否擊中敵人坦克時
    // 就需要把我們子彈集合中所有子彈取出，和敵人的所有坦克進行判斷
    public void hitEnemyTank() {

        //遍歷所有子彈
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            // 判斷是否擊中敵人的坦克
            if (shot != null && shot.isLive) {

                //遍歷敵人所有的坦克-->並不知道子彈擊中哪個坦克，所以只能遍歷
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    // 編寫方法，判斷敵人坦克是否擊中hero
    public void hitHero() {
        // 遍歷所有敵人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            // 遍歷enemyTank所有子彈
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                // 判斷shot是否擊中hero
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }

    }

    // 編寫方法，判斷我方的子彈是否擊中敵人坦克 --> 進階到是否擊中坦克，hero從此危險了...
    // 什麼時候判斷 我方的子彈是否擊中敵人的坦克？--> 放在run方法中去判斷，因為其每隔100秒重繪一次
    public void hitTank(Shot shot, Tank tank) {
        // 判斷shot是否擊中坦克
        switch (tank.getDirect()) {
            case 0:
            case 2:  // 上下方向，子彈在坦克的區域中
                if (shot.x > tank.getX() && shot.x < tank.getX() + 40
                        && shot.y > tank.getY() && shot.y < tank.getY() + 60) {
                    shot.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    // 當我方擊毀一個敵方坦克，就對數據allEnemyTankNum++
                    // 但需要先判斷是hero被擊毀，還是敵方坦克被擊毀
                    if (tank instanceof EnemyTank)
                        Recorder.addAllEnemyTankNum();
                    // 創建一個bomb對象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3: // 左右方向，子彈在坦克的區域中
                if (shot.x > tank.getX() && shot.x < tank.getX() + 60
                        && shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                    shot.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    // 當我方擊毀一個敵方坦克，就對數據allEnemyTankNum++
                    // 但需要先判斷是hero被擊毀，還是敵方坦克被擊毀
                    if (tank instanceof EnemyTank)
                        Recorder.addAllEnemyTankNum();
                    // 創建一個bomb對象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // 處理上下左右鍵 按下的情況
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);  // 增加tank direct屬性 + 監聽鍵盤 --> 改變坦克方向
            // 再更改坦克的移動位置，可用hero.setY(hero.getY()-1),但效率差，將移動位置的方法封裝到tank類中再調用即可
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 90 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        // 如果用戶按下J，就發射子彈
        if (e.getKeyCode() == KeyEvent.VK_J) {
            // 判斷當前hero的子彈是否銷毀 （發射一顆子彈）
//            if(hero.shot == null || !hero.shot.isLive){ // 子彈沒銷毀之前 無法射擊
//                hero.shotEnemy();
//            }
            // 發射多顆子彈
            hero.shotEnemy();
        }

        this.repaint(); // 一定要讓面板重繪
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 判斷hero是否擊中敵人坦克
            hitEnemyTank();
            // 判斷敵人坦克是否擊中hero
            hitHero();
            this.repaint();
        }
    }
}
