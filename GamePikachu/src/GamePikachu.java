
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SnowGroup
 */
public class GamePikachu extends javax.swing.JFrame {

    /**
     * Creates new form GamePikachu
     */
    public GamePikachu() {
        initComponents();
//        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test2/1.png"));
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test/1.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlogAbout = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        lblPanda = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        btnRank = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 693, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        dlogAbout.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pikachu Game");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/kung-fu-panda-png-po-kung-fu-panda-3-png-400.png"))); // NOI18N
        getContentPane().add(lblPanda, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 380, 610));

        btnStart.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnStart.setMnemonic('s');
        btnStart.setText("Start");
        btnStart.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 204), new java.awt.Color(0, 255, 0)));
        btnStart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        getContentPane().add(btnStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 230, 60));

        btnAbout.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnAbout.setText("About");
        btnAbout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153)));
        btnAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        getContentPane().add(btnAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 230, 60));

        btnQuit.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnQuit.setText("Quit");
        btnQuit.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(255, 255, 0)));
        btnQuit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 530, 220, 50));

        btnRank.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnRank.setMnemonic('r');
        btnRank.setText("Rank");
        btnRank.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 255, 255), new java.awt.Color(51, 255, 255)));
        btnRank.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRankActionPerformed(evt);
            }
        });
        getContentPane().add(btnRank, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 230, 60));

        jLabel1.setBackground(new java.awt.Color(204, 255, 204));
        jLabel1.setFont(new java.awt.Font("Imprint MT Shadow", 1, 80)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pikachu");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 510, 100));

        jLabel2.setFont(new java.awt.Font("Imprint MT Shadow", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 255));
        jLabel2.setText("Version: Cartoon");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 270, 40));

        btnHelp.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnHelp.setText("Help");
        btnHelp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 102, 0), new java.awt.Color(0, 255, 153)));
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        getContentPane().add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 440, 230, 60));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Background 3.jpg"))); // NOI18N
        getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * button Start Game
     *
     * @param evt
     */
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        this.setVisible(false);
        StartGame s = new StartGame();
        s.setVisible(true);
    }//GEN-LAST:event_btnStartActionPerformed

    /**
     * Button exit game
     *
     * @param evt
     */
    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnQuitActionPerformed

    /**
     * button show information about game
     *
     * @param evt
     */
    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        this.setVisible(false);
        About ab = new About();
        ab.setVisible(true);
    }//GEN-LAST:event_btnAboutActionPerformed

    /**
     * button show HighScores of player
     *
     * @param evt
     */
    private void btnRankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRankActionPerformed
        this.setVisible(false);
        HighScore r;
        try {
            r = new HighScore();
            r.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(GamePikachu.class.getName()).log(Level.SEVERE, null, ex);
        }
//        JOptionPane.showMessageDialog(this, "This function have not completed! " + 
//                "\nPlease come back late!", "Note", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnRankActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        this.setVisible(false);
        Help h = new Help();
        h.setVisible(true);
    }//GEN-LAST:event_btnHelpActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePikachu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePikachu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePikachu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePikachu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GamePikachu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnRank;
    private javax.swing.JButton btnStart;
    private javax.swing.JDialog dlogAbout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblPanda;
    // End of variables declaration//GEN-END:variables
}
