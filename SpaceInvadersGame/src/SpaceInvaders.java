
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceInvaders extends JFrame implements KeyListener {

    JPanel panel;

    int[] invadersX = new int[34];
    int[] invadersY = new int[34];

    int currentVertical = 50;

    int userX = 300;

    int invadersMovement;

    int[] userBulletX = new int[10000];
    int[] userBulletY = new int[10000];

    int currentBullet = 0;

    int[] invaderBulletX = new int[10000];
    int[] invaderBulletY = new int[10000];

    int count = 0;
    int currentInvaderBullet = 0;

    int[] alive = new int[34];

    JLabel score;
    int currentScore = 0;
    
    boolean gameover=false;
    

    public SpaceInvaders() {
        super("Space Invaders");
        init();
        setVisible(true);
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        for (int x = 0; x < alive.length; x++) {
            alive[x] = 1;
        }

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
            	BufferedImage img;
                
            	try {
            	if(gameover) {
            		img = ImageIO.read(new File("C:\\Users\\EMON\\Desktop\\SpaceInvader\\gamePieces\\gameover.jpg"));
                    g.drawImage(img, 0, 0, 700, 600, null);
            	}
            	else {
                

                    img = ImageIO.read(new File("C:\\Users\\EMON\\Desktop\\SpaceInvader\\gamePieces\\background.jpg"));
                    g.drawImage(img, 0, 0, 700, 600, null);
                    currentVertical = 50;
                    for (int i = 0; i < 33; i++) {
                        invadersX[i + 1] = invadersX[i] + 40;
                        invadersY[i] = currentVertical;

                        if (i == 10 || i == 21) {
                            currentVertical += 40;
                            invadersX[i + 1] = invadersX[0];
                        }

                        BufferedImage invader = ImageIO.read(new File("C:\\Users\\EMON\\Desktop\\SpaceInvader\\gamePieces\\spaceInvaders.png"));
                        if (invadersX[0] <= 10) {
                            invadersMovement = 10;
                        } else if (invadersX[0] >= 150) {
                            invadersMovement = -10;
                        }

                        invadersX[i] = invadersX[i] + invadersMovement;
                        if (alive[i] == 1) {
                            g.drawImage(invader, invadersX[i], invadersY[i], 26, 26, null);
                        }
                    }

                    BufferedImage user = ImageIO.read(new File("C:\\Users\\EMON\\Desktop\\SpaceInvader\\gamePieces\\user.png"));
                    if (userX <= 20) {
                        userX = 0;

                    } else if (userX >= 570) {
                        userX = 570;
                    }
                    g.drawImage(user, userX, 400, 80, 80, null);

                    for (int j = 0; j < currentBullet; j++) {
                        g.setColor(Color.red);
                        g.fillRect(userBulletX[j], userBulletY[j], 5, 10);
                        userBulletY[j] = userBulletY[j] - 25;
                    }
                    if (count == 10) {
                        Random r = new Random();
                        int randomInvader = r.nextInt(33);
                        if (alive[randomInvader] == 1) {
                            invaderBulletX[currentInvaderBullet] = invadersX[randomInvader];
                            invaderBulletY[currentInvaderBullet] = invadersY[randomInvader];
                        }

                        currentInvaderBullet++;
                        count = 0;
                    }
                    for (int k = 0; k < currentInvaderBullet; k++) {
                        g.setColor(Color.orange);
                        g.fillRect(invaderBulletX[k], invaderBulletY[k], 5, 10);
                        invaderBulletY[k] = invaderBulletY[k] + 25;
                    }

                    for (int y = 0; y < 33; y++) {
                        for (int z = 0; z < currentBullet; z++) {
                            if (alive[y] == 1 && userBulletX[z] >= invadersX[y] && userBulletX[z] <= invadersX[y] + 20 && userBulletY[z] >= invadersY[y] && userBulletY[z] <= invadersY[y]) {
                                alive[y] = 0;
                                userBulletY[z] = -20;
                                currentScore += 2;
                                updateScore();
                            }
                        }
                    }

                    for(int w=0; w<currentInvaderBullet; w++) {
                    	if(invaderBulletX[w]>userX && invaderBulletX[w]<= userX+60 && invaderBulletY[w]>=420 && invaderBulletY[w]<=440) {
                    		
                    		gameover=true;
                    		invaderBulletY[w]= -20;
                    		
                    	}
                    }
                    
                    count++;
                    repaint();
            	}
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        score = new JLabel("SCORE : " + currentScore);
        score.setFont(score.getFont().deriveFont(14.0f));
        score.setForeground(Color.red);
        panel.add(score);
        add(panel);
        setSize(700, 600);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new SpaceInvaders();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == 37) {
            userX -= 10;
        } else if (e.getKeyCode() == 39) {
            userX += 10;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == 32) {
            userBulletX[currentBullet] = userX + 36;

            userBulletY[currentBullet] = 400;
            currentBullet++;
            repaint();
        }

    }

    public void updateScore() {
        score.setText("SCORE : " + currentScore);
    }

}
