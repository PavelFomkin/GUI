package calculator;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.regex.Pattern;

public class Calculator {

    private static final String TITLE_OF_APPLICATION = "Calculator";
    private static final int WIDTH_OF_CALCULATOR = 250;
    private static final int HEIGHT_OF_CALCULATOR = 300;
    private static final int WIDTH_OF_PANEL = WIDTH_OF_CALCULATOR;
    private static final int HEIGHT_OF_PANEL = 80;
    private static final int WIDTH_OF_BUTTON = 45;
    private static final int HEIGHT_OF_BUTTON = 40;
    private static final int WIDTH_OF_LABEL = 60;
    private static final int HEIGHT_OF_LABEL = 20;
    private static final int GAPS_BETWEEN_BUTTONS = 5;
    private static final int GAPS_BETWEEN_DATA = 10;

    private static JFrame jFrame;

    private static JPanel jPanelData;
    private static JPanel jPanelFunctions;
    private static JPanel jPanelResult;

    private static JTextField jTextFieldValue1;
    private static JTextField jTextFieldValue2;
    private static JTextField jTextFieldResult;

    private static JLabel jLabel1;
    private static JLabel jLabel2;
    private static JLabel jLabelResult;

    private static JButton jButtonPlus;
    private static JButton jButtonMinus;
    private static JButton jButtonMultiply;
    private static JButton jButtonDivide;
    private static JButton jButtonUseResult;
    private static JButton jButtonClear;

    private Calculator(){ }

    static void start(){
        initial();
        setRelations();
        setListeners();
    }

    private static void initial() {
        jLabel1 = createJLabel("first value", WIDTH_OF_LABEL, HEIGHT_OF_LABEL);
        jLabel2 = createJLabel("second value", WIDTH_OF_LABEL, HEIGHT_OF_LABEL);
        jLabelResult = createJLabel("result", WIDTH_OF_LABEL, HEIGHT_OF_LABEL);

        jTextFieldValue1 = JTextField( 10, true);
        jTextFieldValue2 = JTextField( 10, true);
        jTextFieldResult = JTextField( 15, false);

        jButtonPlus = createJButton("+", WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
        jButtonMinus = createJButton("-", WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
        jButtonMultiply = createJButton("*", WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
        jButtonDivide = createJButton("/", WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
        jButtonUseResult = createJButton("Use result", WIDTH_OF_BUTTON+50, HEIGHT_OF_BUTTON);
        jButtonClear = createJButton("Clear", WIDTH_OF_BUTTON+50, HEIGHT_OF_BUTTON);

        jPanelData = createJPanel(WIDTH_OF_PANEL, HEIGHT_OF_PANEL, new FlowLayout(FlowLayout.RIGHT, GAPS_BETWEEN_DATA,GAPS_BETWEEN_DATA));
        jPanelFunctions = createJPanel(WIDTH_OF_PANEL, HEIGHT_OF_PANEL, new FlowLayout(FlowLayout.CENTER, GAPS_BETWEEN_BUTTONS, GAPS_BETWEEN_BUTTONS));
        jPanelResult = createJPanel(WIDTH_OF_PANEL, HEIGHT_OF_PANEL, new FlowLayout(FlowLayout.LEADING, GAPS_BETWEEN_DATA, GAPS_BETWEEN_DATA));

        jFrame = createJFrame(TITLE_OF_APPLICATION, WIDTH_OF_CALCULATOR, HEIGHT_OF_CALCULATOR);
    }

    private static void setRelations() {
        jFrame.add(jPanelData, BorderLayout.NORTH);
        jFrame.add(jPanelFunctions, BorderLayout.CENTER);
        jFrame.add(jPanelResult, BorderLayout.SOUTH);

        jPanelData.add(jLabel1);
        jPanelData.add(jTextFieldValue1);
        jPanelData.add(jLabel2);
        jPanelData.add(jTextFieldValue2);

        jPanelFunctions.add(jButtonPlus);
        jPanelFunctions.add(jButtonMinus);
        jPanelFunctions.add(jButtonUseResult);
        jPanelFunctions.add(jButtonMultiply);
        jPanelFunctions.add(jButtonDivide);
        jPanelFunctions.add(jButtonClear);

        jPanelResult.add(jLabelResult);
        jPanelResult.add(jTextFieldResult);

        jFrame.setVisible(true);
    }

    private static void setListeners() {
        jButtonPlus.addActionListener((event)-> {
            setResult(getValue(jTextFieldValue1)+getValue(jTextFieldValue2));
        });
        jButtonMinus.addActionListener((event)-> {
            setResult(getValue(jTextFieldValue1)-getValue(jTextFieldValue2));
        });
        jButtonMultiply.addActionListener((event)-> {
            setResult(getValue(jTextFieldValue1)*getValue(jTextFieldValue2));
        });
        jButtonDivide.addActionListener((event)-> {
            setResult(getValue(jTextFieldValue1)/getValue(jTextFieldValue2));
        });
        jButtonClear.addActionListener((event)-> {
            jTextFieldValue1.setText("");
            jTextFieldValue2.setText("");
            jTextFieldResult.setText("");
        });
        jButtonUseResult.addActionListener((event)-> {
            jTextFieldValue1.setText(jTextFieldResult.getText());
            jTextFieldValue2.setText("");
            jTextFieldResult.setText("");
        });
    }

    private static double getValue(JTextField value){
        Pattern pattern = Pattern.compile("^-?[0-9]+.?[0-9]*");
        if(pattern.matcher(value.getText()).matches()){
            return Double.valueOf(value.getText());
        } else {
            value.setText("");
            return Double.NaN;
        }
    }

    private static void setResult(Double result){
        if(Double.isNaN(result)){
            jTextFieldResult.setText("");
            JOptionPane.showMessageDialog(null,"The data was pointed wrong.");
        } else {
            jTextFieldResult.setText(String.valueOf(result));
        }
    }

    private static JLabel createJLabel(String name, int width, int height){
        JLabel label = new JLabel(name);
        label.setSize(width, height);
        label.setFocusable(false);

        return label;
    }

    private static JTextField JTextField(Integer length, Boolean isEditable){
        JTextField textField = new JTextField(length);
        textField.setEditable(isEditable);
        textField.setFocusable(isEditable);

        return textField;
    }

    private static JButton createJButton(String name, int width, int height){
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(width,height));

        return button;
    }

    private static JPanel createJPanel(int width, int height, LayoutManager layoutManager){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(layoutManager);

        return panel;
    }

    private static JFrame createJFrame(String title, int width, int height){
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("vmf.png").getImage());
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        return frame;
    }
}
