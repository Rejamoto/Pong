package final_b;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PongGame extends JPanel {
    int x, y, player, com, hold, pS, cS, hit;
    int move = 1;
    int ball = 1;
    boolean left, up, run, goUp;
    int[] coord = new int[4];
    Timer tick = new Timer(10, new Refresh());
    Timer keyTime = new Timer(10, new KeyTime());
    
    public PongGame(){
        setBackground(Color.black);
        setPreferredSize(new Dimension(719, 419));
        addKeyListener(new Keys());
        this.setFocusable(true);
        
        x = getPreferredSize().width / 2 - 8;
        y = getPreferredSize().height / 2 - 8;
        player = getPreferredSize().height / 2 - 30;
        com = getPreferredSize().height / 2 - 30;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        // Game Table
        for(int i = 0; i < 70; i++)
            for(int j = 0; j < 40; j++){
                g.setColor(new Color(0, 150, 50));
                g.fillRect(10 * (i + 1), 10 * (j + 1), 9, 9);
                
                if(x + 8 > (i * 10) && y + 8 > (j * 10) && x - 20 < (i * 10) && y - 20 < (j * 10)){
                    g.setColor(new Color(0, 200, 50));
                    g.fillRect(10 * (i + 1), 10 * (j + 1), 9, 9);
                }
                
                g.setColor(Color.black);
                g.drawRect(10 * (i + 1), 10 * (j + 1), 9, 9);
            }
        g.setColor(Color.white);
        g.fillRect(getPreferredSize().width / 2 - 4, 0, 10, getPreferredSize().height);
        // Player One
        g.setColor(Color.blue);
        g.fillRect(13, player, 10, 60);
        // Player Two (CPU)
        g.setColor(Color.black);
        g.fillRect(getPreferredSize().width - 22, com, 10, 60);
        // The Ball
        g.setColor(Color.orange);
        g.fillOval(x, y, 16, 16);
    }
    
    public void start(){
        cS = 0;
        pS = 0;
        random();
        run = true;
        tick.start();
    }

    public String getScore(){
        return "Player: " + pS + "\nComputer: " + cS;
    }
    
    private void resetBall(){
        x = getPreferredSize().width / 2 - 8;
        y = getPreferredSize().height / 2 - 8;
        random();
        ball = 1;
        hit = 0;
    }
    
    private void checkLocation(){
        if(((y + 28) > getPreferredSize().height) || ((y - 10) < 0))
            up = !up;
        if((x < 24) && (y >= player && y <= player + 60)){
            left = false;
            hit++;
        }
        else if((x > getPreferredSize().width - 38) && (y >= com && y <= com + 60)){
            left = true;
            hit++;
        }
        
        if(hit > 20)
            ball = 2;
        
        if(x < 0){
            cS++;
            resetBall();
        }
        if(x > getPreferredSize().width){
            pS++;
            resetBall();
        }
    }
    
    private void computer(){
        if(y > com + 50)
            com += move;
        if(y < com)
            com -= move;
    }
    
    private void random(){
        if((int)(Math.random() * 2) == 0)
            left = true;
        else
            left = false;
        if((int)(Math.random() * 2) == 0)
            up = true;
        else
            up = false;
    }
    
    class Refresh implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            // CPU
            computer();
            // Ball movement
            checkLocation();
            if(left)
                x-= ball;
            else
                x+= ball;
            if(up)
                y-= ball;
            else
                y+= ball;
            
            repaint();
        }
    }
    
    class KeyTime implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            hold++;
            if(hold > 50)
                keyTime.setDelay(5);
            else if(hold > 100)
                keyTime.setDelay(2);
            
            if(goUp && player > 10)
                    player -= move;
            else if(player + 51 < 400)
                    player += move;
        }
    }
    
    class Keys implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN){player -= move;
                goUp = false;
                keyTime.start();
            }
            else if(e.getKeyCode() == KeyEvent.VK_UP){
                goUp = true;
                keyTime.start();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
                hold = 0;
                move = 1;
                keyTime.setDelay(10);
                keyTime.stop();
            }
        }
        
    }
}
