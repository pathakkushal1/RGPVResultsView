/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.FileDialog;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Kushal
 */
public class Result {

    public static void main(String[] args) {

        String com = "Name , Roll No , SGPA , CGPA , Remarks \r";
        ResultShow sw = new ResultShow();
        String[] s = {"ctl00_ContentPlaceHolder1_lblNameGrading", "ctl00_ContentPlaceHolder1_lblRollNoGrading"};

        String[] pao = {"ctl00_ContentPlaceHolder1_lblResultNewGrading", "ctl00_ContentPlaceHolder1_lblSGPA", "ctl00_ContentPlaceHolder1_lblcgpa"};
        //String pi = "width:25%;font-weight:bold;", ch = "";
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File x = new File(fd.getDirectory());
        String data[] = x.list();
        for (int i = 0; i < data.length; i++) {
            System.err.println(data[i]);
        }
        int so = data.length;
        so--;

        String[] name = new String[2];

        String[] mark = new String[11];

        String[] pa = new String[3];

        while (so >= 0) {  //Read Multiple files.
            File y = new File(x + "\\" + data[so]);
            if (y.isFile()) {

                double o = 0;

                for (int i = 0; i < 11; i++) {
                    if (i < 2) {
                        name[i] = "";
                        pa[i] = "";
                    }
                    mark[i] = "";
                }
                pa[2] = "";
                try {
                    FileInputStream fis = new FileInputStream(x + "\\" + data[so]);
                    int p = fis.read(), d, t = 0, co = 0, poo = 0;
                    String id = "";
                    while (p != -1) { //Single File reading starts from here.
                        if (t < 2) {
                            if ((char) p == 'i') { //Gettting the Name and Roll Number
                                p = fis.read();
                                if ((char) p == 'd') {
                                    p = fis.read();
                                    if ((char) p == '=') {
                                        p = fis.read();
                                        p = fis.read();
                                        while ((char) p != '"') {
                                            id = id + (char) p;
                                            p = fis.read();
                                        }
                                        if (id.equals(s[t])) {
                                            while ((char) p != '>') {
                                                p = fis.read();
                                            }
                                            p = fis.read();
                                            while ((char) p != '<') {
                                                name[t] = name[t] + (char) p;
                                                p = fis.read();
                                            }
                                            t++;

                                        }
                                        id = "";
                                    }
                                }
                            }
                        }

                        if (poo < 3) { //Getting SGPA,CGPA,REMARKS
                            if ((char) p == 'i') {
                                p = fis.read();
                                if ((char) p == 'd') {
                                    p = fis.read();
                                    if ((char) p == '=') {
                                        p = fis.read();
                                        p = fis.read();
                                        while ((char) p != '"') {
                                            id = id + (char) p;
                                            p = fis.read();
                                        }
                                        if (id.equals(pao[poo])) {
                                            while ((char) p != '>') {
                                                p = fis.read();
                                            }
                                            p = fis.read();
                                            while ((char) p != '<') {
                                                pa[poo] = pa[poo] + (char) p;
                                                p = fis.read();
                                            }
                                            poo++;

                                        }
                                        id = "";
                                    }
                                }
                            }
                        }

                        if (t != 2 || poo != 3) {
                            p = fis.read();
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    com += name[0] + "," + name[1] + "," + pa[1] + "," + pa[2] + "," + pa[0] + "\r\t";
                    System.out.println("Line now ->" + com);
                    sw.get(name, pa);
                    sw.setVisible(true);

                }
                so--;
            } else {
                so--;
            }
        }
        DataOutputStream dos = null;
        try {
            //Writing that to ".cvv" file
            dos = new DataOutputStream(new FileOutputStream(System.getProperties().getProperty("user.dir") + System.getProperties().getProperty("file.separator") + "TransactionalData.csv"));
            dos.writeBytes(com);
            dos.close();
            //da.data(name, mark, pa);
        } catch (Exception ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dos.close();
            } catch (IOException ex) {
                Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
