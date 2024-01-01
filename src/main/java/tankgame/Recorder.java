package tankgame;

import java.io.*;
import java.util.Vector;

/**
 * 該類用於紀錄相關信息
 * 和文件交互
 */
public class Recorder {
    // 定義變量，紀錄我方擊毀敵人坦克數量
    private static int allEnemyTankNum = 0;
    // 定義IO對象,用於寫數據到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src/myRecord.txt";

    // 定義Vector，指向MyPanel對象的 敵人坦克Vector --> 接下面的OOP老套路註解
    private static Vector<EnemyTank> enemyTanks = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    // 拿到紀錄文件，在MyPanel中判斷是否為空
    public static String getRecordFile() {
        return recordFile;
    }

    // 定義一個Node的Vector，用於保存敵人坦克的信息
    private static Vector<Node> nodes = new Vector<>();

    // 編寫方法，用於讀取 recordFile，恢復相關信息
    // 該方法在重啟，想要繼續上局遊戲的時候調用
    public static Vector<Node> getNodesAndEnemyTankRec() {

        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            // 循環讀取文件，生成nodes 集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node); // 進一步封裝到nodes的Vector
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    // 當遊戲退出，將 allEnemyTankNum 的值保存到 recordFile --> 存盤退出時，調用次方法即可
    // 對Recorder 進行升級，保存敵人坦克的座標，以及方向
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            // 遍歷敵人坦克的Vector 保存活著的敵人坦克的座標方向
            // OOP老套路--> 在此類中定義一個屬性，然後通過setXxx得到 敵人坦克的Vector ✨
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) { // 一般來說，擊毀坦克都會移除，但這邊為了保險判斷一下，代碼優化後可以不用
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    // 保存座標或方向後，寫入到文件
                    bw.write(record + "\r\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    // 當我方坦克擊毀一輛敵人坦克，就應該對 allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
