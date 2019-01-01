/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.AdminLogin;
import application.AppMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import models.Admin;
import models.Projection;

/**
 *
 * @author dslim
 */
public class MainController {
    private AppMain appMain = null;
    private AdminLogin adminLogin = null;
    private AdminController adminController= null;
    
    public MainController() {
        appMain = new AppMain(Projection.getAll());
        init();
        
    }
    
    public void init() {
        appMain.administratioBTN.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                appMain.administratioBTN.setEnabled(false);
                adminLogin = new AdminLogin();
                adminLogin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                adminLogin.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        appMain.administratioBTN.setEnabled(true);
                    }
                });
                initLogin();
            }
        });
    }
    
    public void initLogin() {
        adminLogin.login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!(adminLogin.email.getText().equals("") || adminLogin.password.getText().equals(""))){
                        if(Admin.verifyExistense(adminLogin.email.getText(), adminLogin.password.getText())) {
                            adminLogin.login.setEnabled(false);
                            adminLogin.setVisible(false);
                            appMain.setVisible(false);
                            adminController = new AdminController();
                        }
                    }else {
                        adminLogin.error.setText("You must insert your email and password");
                    }
                } catch(Exception ex) {
                    adminLogin.error.setText(ex.getMessage());
                }
            }
        });
    }
    
}
