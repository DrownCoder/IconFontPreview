package main;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class TTFChooser implements ActionListener {
    JFrame frame = new JFrame("选择资源文件");
    JTabbedPane tabPane = new JTabbedPane();//选项卡布局
    Container con = new Container();//布局1
    Container con1 = new Container();//布局2
    JLabel label1 = new JLabel("ttf文件");
    JLabel label2 = new JLabel("string.xml");
    JTextField text1 = new JTextField();
    JTextField text2 = new JTextField();
    JButton button1 = new JButton("选择");
    JButton button2 = new JButton("选择");
    JButton ok = new JButton("确认");
    JButton cancel = new JButton("取消");
    JFileChooser jfc = new JFileChooser();//文件选择器

    public interface onPathSelected {
        void onPathComplete(String ttfPath, String xmlPath);
    }

    private onPathSelected onPathSelected;

    public void setOnPathSelected(TTFChooser.onPathSelected onPathSelected) {
        this.onPathSelected = onPathSelected;
    }

    TTFChooser(String initPath) {
        jfc.setCurrentDirectory(new File(initPath));//文件选择器的初始目录
        //下面两行是取得屏幕的高度和宽度  
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));//设定窗口出现位置
        frame.setSize(300, 160);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局  
        //下面设定标签等的出现位置和高宽
        label1.setBounds(10, 10, 70, 20);
        label2.setBounds(10, 40, 100, 20);
        text1.setBounds(80, 10, 120, 20);
        text2.setBounds(80, 40, 120, 20);
        button1.setBounds(210, 10, 60, 20);
        button2.setBounds(210, 40, 60, 20);
        ok.setBounds(210, 70, 60, 20);
        cancel.setBounds(150, 70, 60, 20);

        button1.addActionListener(this);//添加事件处理  
        button2.addActionListener(this);//添加事件处理
        cancel.addActionListener(this);
        ok.addActionListener(this);
        con.add(label1);
        con.add(label2);
        con.add(text1);
        con.add(text2);
        con.add(button1);
        con.add(button2);
        con.add(jfc);
        con.add(ok);
        con.add(cancel);
        tabPane.add("目录/文件选择", con);//添加布局1
        //tabPane.add("暂无内容",con1);//添加布局2
        frame.setVisible(true);//窗口可见  
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//使能关闭窗口
    }

    public void actionPerformed(ActionEvent e) {//事件处理
        if (e.getSource().equals(button1)) {//判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;//撤销则返回  
            } else {
                File f = jfc.getSelectedFile();//f为选择到的目录
                text1.setText(f.getAbsolutePath());
            }
        }
        if (e.getSource().equals(button2)) {
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//设定只能选择到文件
            int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;//撤销则返回  
            } else {
                File f = jfc.getSelectedFile();//f为选择到的文件
                text2.setText(f.getAbsolutePath());
            }
        }
        if (e.getSource().equals(ok)) {
            //确认
            String ttfPath = text1.getText();
            String xmlPath = text2.getText();
            boolean isTTF = ttfPath.endsWith(".ttf");
            boolean isXML = xmlPath.endsWith(".xml");
            if (!isTTF) {
                JOptionPane.showMessageDialog(null, "ttf文件路径不正确！", "路径错误",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!isXML) {
                JOptionPane.showMessageDialog(null, "xml资源文件路径不正确！", "路径错误",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            PersistentPath.getInstance().setTtfPath(ttfPath);
            PersistentPath.getInstance().setXmlPath(xmlPath);
            if (onPathSelected != null) {
                onPathSelected.onPathComplete(ttfPath, xmlPath);
            }
            frame.dispose();
        }
        if (e.getSource().equals(cancel)) {
            //取消
            frame.dispose();
        }
    }

    public static TTFChooser show(String initPath) {
        return new TTFChooser(initPath);
    }

}  
