/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ammcp
 */
public class frm_Inicio extends javax.swing.JFrame {

    /**
     * Creates new form frm_Inicio
     */
    public frm_Inicio(frm_login frmLogin) {
        initComponents();
        this.setLocationRelativeTo(null);
        iniciarBarra(frmLogin);
    }

   public void iniciarBarra(frm_login frmLogin){
       Thread hilo;
       hilo = new Thread(){
         public void run(){
             for(int i=1; i<=100; i++){
                 try {
                     Thread.sleep(50);//milisegundos
                     lblTitulo.setText("Cargando...");
                     barra.setValue(i);
                     if(i ==50){
                         barra.setForeground(Color.yellow);
                     }
                     if(i ==90){
                         barra.setForeground(Color.green);
                         lblTitulo.setText("Listo");
                     }
                 } catch (InterruptedException ex) {
                     Logger.getLogger(frm_Inicio.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             dispose();
             frmLogin.setVisible(true);
         }  
       };
       hilo.start();
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        barra = new javax.swing.JProgressBar();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoAbarrotes (Custom).png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(50, 0, 170, 180);

        barra.setBackground(new java.awt.Color(0, 0, 0));
        barra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        barra.setForeground(new java.awt.Color(255, 204, 102));
        barra.setStringPainted(true);
        jPanel1.add(barra);
        barra.setBounds(20, 200, 210, 32);

        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lblTitulo);
        lblTitulo.setBounds(100, 170, 100, 30);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
