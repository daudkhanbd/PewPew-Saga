package empireit.ro.myfirstgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by George Netu on 06.03.2015.
 */
public class MainThread extends Thread {

    private int fps = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        long frameCount = 0;
        long targetTime = 1000 / fps;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            timeMillis = System.nanoTime() - startTime / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime = System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == fps) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b) {
        running = b;
    }
}
