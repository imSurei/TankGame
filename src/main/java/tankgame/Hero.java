package tankgame;

import java.util.Vector;

public class Hero extends Tank {

    Shot shot = null; // 定義一個射擊行為（一個線程）
    // 用集合保存多顆子彈
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemy() {
        // 控制面板上我方只能一次打出6顆子彈
        if(shots.size() == 6) return;

        // 根據當前hero對象的座標，來創建shot子彈座標
        switch(getDirect()){
            case 0: // up and 以下按順時針
                shot = new Shot(getX()+20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2:
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        // 將新建的shot加入集合
        shots.add(shot);

        // 確定好子彈位置後，啟動射擊線程
        new Thread(shot).start();
    }
}
