package tankgame;

/**
 * 子彈射擊 的行為是一個線程
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direct = 0;
    int speed = 3;
    boolean isLive = true; // 子彈是否銷毀

    public Shot(int x, int y, int direct) { // 構造器 初始化子彈的x y座標 & 方向
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() { // 子彈射擊

        while (true) {

            try {   // 讓子彈休眠，否則看不到移動軌跡
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 根據子彈方向，改變xy座標
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            // System.out.println("x = "+x+" y = "+y);

            // 子彈碰到邊界，就銷毀,退出射擊線程
            // 當子彈碰到敵人坦克，也應該結束線程 isLive
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                // System.out.println("子彈線程退出");
                isLive = false;
                break;
            }
        }
    }
}
