package tankgame;

public class Bomb {
    int x, y;
    int life = 9; //炸彈的生命週期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 減少生命值  為了配合出現圖片的爆炸效果
    public void lifeMinus() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
