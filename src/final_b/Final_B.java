package final_b;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Final_B extends JFrame {
    PongGame game = new PongGame();
    public Final_B(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem score = new JMenuItem("Score");
        JMenuItem end = new JMenuItem("Exit");
        
        newGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                game.start();
            }
        });
        score.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                JOptionPane.showMessageDialog(null, game.getScore(), "Score", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        end.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                System.exit(0);
            }
        });
        
        file.add(newGame);
        file.add(score);
        file.add(end);
        
        menu.add(file);
        
        
        
        setJMenuBar(menu);
        add(game);
    }
    
    public static void main(String[] args) {
        Final_B box = new Final_B();
        
        box.pack();
        box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        box.setLocationRelativeTo(null);
        box.setVisible(true);
    }

}
