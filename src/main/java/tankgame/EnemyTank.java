package tankgame;

import java.util.Vector;

@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    // 在敵人坦克類，使用Vector 保存多個Shot 為方便使用，這類屬性都不封裝（一般需要封裝）
    Vector<Shot> shots = new Vector<>();

    // 防止碰撞，增加成員，需要得到敵人坦克的集合
    // and setEnemyTanks 方法
    Vector<EnemyTank> enemyTanks = new Vector<>();

    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    // 提供一個方法，可以將MyPanel 的成員 Vector<EnemyTank> enemyTanks = new Vector<>();
    // 設置到 EnemyTank 的成員 enemyTanks
    // 這就意味著我們敵人坦克每一個成員，都可以得到Vector的成員的 --> 就可以依次跟其他坦克比較是否碰撞到
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    // 編寫方法，判斷當前的這個敵人坦克，是否和 enemyTanks 中的其他塔克發生了碰撞
    public boolean isTouchEnemyTank() {
        // 判斷當前敵人坦克(this)方向
        switch (this.getDirect()) {
            case 0:
                // 讓當前this敵人坦克，和其他所有敵人坦克循環比較
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比較，否則一定會碰撞
                    if (enemyTank != this) {
                        // 如果敵人坦克是上下方向
                        /*
                        如果敵人坦克是上下方向 x rang == [enemyTank.getX(),enemyTank.getX()+40]
                        如果敵人坦克是上下方向 y rang == [enemyTank.getY(),enemyTank.getY()+60]
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 當前坦克 左上角的座標 == [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右上角的座標 == [this.getX()+40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                        }
                        // 如果敵人坦克是右左方向
                        /*
                        如果敵人坦克是右左方向 x rang == [enemyTank.getX(),enemyTank.getX()+60]
                        如果敵人坦克是右左方向 y rang == [enemyTank.getY(),enemyTank.getY()+40]
                         */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 當前坦克 左上角的座標 == [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右上角的座標 == [this.getX()+40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                        }
                    }
                }
                break;
            case 1:
                // 讓當前this敵人坦克，和其他所有敵人坦克循環比較
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比較，否則一定會碰撞
                    if (enemyTank != this) {
                        // 如果敵人坦克是上下方向
                        /*
                        如果敵人坦克是上下方向 x rang == [enemyTank.getX(),enemyTank.getX()+40]
                        如果敵人坦克是上下方向 y rang == [enemyTank.getY(),enemyTank.getY()+60]
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 當前坦克 右上角的座標 == [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右下角的座標 == [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                        }
                        // 如果敵人坦克是右左方向
                        /*
                        如果敵人坦克是右左方向 x rang == [enemyTank.getX(),enemyTank.getX()+60]
                        如果敵人坦克是右左方向 y rang == [enemyTank.getY(),enemyTank.getY()+40]
                         */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 當前坦克 右上角的座標 == [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右下角的座標 == [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                        }
                    }
                }
                break;
            case 2:
                // 讓當前this敵人坦克，和其他所有敵人坦克循環比較
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比較，否則一定會碰撞
                    if (enemyTank != this) {
                        // 如果敵人坦克是上下方向
                        /*
                        如果敵人坦克是上下方向 x rang == [enemyTank.getX(),enemyTank.getX()+40]
                        如果敵人坦克是上下方向 y rang == [enemyTank.getY(),enemyTank.getY()+60]
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 當前坦克 左下角的座標 == [this.getX(),this.getY()+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右下角的座標 == [this.getX()+40,this.getY()+60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                        }
                        // 如果敵人坦克是右左方向
                        /*
                        如果敵人坦克是右左方向 x rang == [enemyTank.getX(),enemyTank.getX()+60]
                        如果敵人坦克是右左方向 y rang == [enemyTank.getY(),enemyTank.getY()+40]
                         */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 當前坦克 左下角的座標 == [this.getX(),this.getY()+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 右下角的座標 == [this.getX()+40,this.getY()+60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                        }
                    }
                }
                break;
            case 3:
                // 讓當前this敵人坦克，和其他所有敵人坦克循環比較
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比較，否則一定會碰撞
                    if (enemyTank != this) {
                        // 如果敵人坦克是上下方向
                        /*
                        如果敵人坦克是上下方向 x rang == [enemyTank.getX(),enemyTank.getX()+40]
                        如果敵人坦克是上下方向 y rang == [enemyTank.getY(),enemyTank.getY()+60]
                         */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 當前坦克 左上角的座標 == [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 左下角的座標 == [this.getX(),this.getY()+40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true; // 說明發生碰撞
                            }
                        }
                        // 如果敵人坦克是右左方向
                        /*
                        如果敵人坦克是右左方向 x rang == [enemyTank.getX(),enemyTank.getX()+60]
                        如果敵人坦克是右左方向 y rang == [enemyTank.getY(),enemyTank.getY()+40]
                         */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 當前坦克 左上角的座標 == [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                            // 當前坦克 左下角的座標 == [this.getX(),this.getY()+40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true; // 說明發生碰撞
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {

        while (true) {
            // 如果tank is live and 子彈為0，創建一顆加入enemyTank子彈陣列，再啟動子彈線程
            if (isLive && shots.size() == 0) {

                Shot s = null;
                // 判斷坦克方向，創建對應方向的子彈
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY() + 10, 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 70, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 70, 2);
                        break;
                    case 3:
                        s = new Shot(getX() + 10, getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();

//                Shot shot = new Shot(getX()+20,getY()+70,getDirect()); 子彈方向判斷不對
//                shots.add(shot);
//                new Thread(shot).start();
            }

            // 根據坦克的方向繼續移動
            switch (getDirect()) {
                case 0:  // 讓坦克保持一個方向，走30像素
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {  // 不能超出y 0 座標
                            moveUp();
                        }
                        try { // 休眠一下 才看的到坦克蹤跡
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) { // 以坦克左上角為原座標，需要加上坦克的長度60
                            moveRight();
                        }
                        try { // 休眠一下 才看的到坦克蹤跡
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 90 < 750 && !isTouchEnemyTank()) {  // 實際不是 + 60
                            moveDown();
                        }
                        try { // 休眠一下 才看的到坦克蹤跡
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try { // 休眠一下 才看的到坦克蹤跡
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            // 再隨機改變移動方向
            setDirect((int) (Math.random() * 4));

            // 寫併發程式，一定要考慮清楚，該線程什麼時候結束(多線程的debug是很恐怖的...)
            if (!isLive) {
                break;
            }
        }
    }
}

