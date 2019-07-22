package lab6;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/*
 * 猴子过河工作台
 * 运行之后，在框内输入需要运行的参数
 * 点击run之后等待一会
 * 在下方的文本框内会打印出各个猴子当前的状态
 */
public class GUI {

	static JFrame frame = new JFrame("猴子过河命令台");
	
	
	class Run extends JPanel {
		public Run() {
			setLayout(null);
			JTextArea ta = new JTextArea();
			ta.setEditable(false);
			StringBuffer sb = new StringBuffer();
			ta.setText(sb.toString());

			List<String> labels = Arrays.asList("梯子总数n", "梯子长度h", "时间周期t", "产生总数N", "每次产生k", "最大速度MV");
			JLabel[] label = new JLabel[6];
			JTextField[] text = new JTextField[6];
			for (int i = 0; i < 6; i++) {
				text[i] = new JTextField();
				text[i].setBounds(80 + 120 * i, 50, 100, 50);
				add(text[i]);
				label[i] = new JLabel();
				label[i].setBounds(90 + 120 * i, 0, 100, 50);
				label[i].setText(labels.get(i));
				add(label[i]);
			}
			Button run = new Button("run");
			run.setBounds(350, 120, 100, 50);
			add(run);
			
			//输出界面
			JTextArea output=new JTextArea();
			
			output.setFont(new Font(null,Font.BOLD,20));
			output.setEditable(true);//设置可编辑
			JTextAreaOutputStream out = new JTextAreaOutputStream (output);
			System.setOut (new PrintStream (out));//设置输出重定向 
			System.setErr(new PrintStream(out));//将错误输出也重定向,用于e.pritnStackTrace
			JScrollPane jsp=new JScrollPane();//设置滚动条
			jsp.setBounds(50, 200, 700, 250);
			add(jsp);
			output.setBounds(50, 200, 700, 250);
			jsp.setViewportView(output);

			run.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					int[] message = new int[6];
					String input;
					boolean tag = true;
					for (int i = 0; i < 6; i++) {
						input = text[i].getText();
						try {
							message[i] = Integer.valueOf(input);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "请重新输入");
							tag = false;
							break;
						}
					}
					if (tag) {
						River river = new River(message[0],message[1]);
						MonkeyGenerator generator = new MonkeyGenerator(message[2],message[4],message[5],message[3]);
						try {
							generator.born(river);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			setVisible(true);
		}
	}

	//启动工作台
	public static void main(String[] args) {
		GUI gui = new GUI();
		frame.setBounds(100, 100, 900, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Run run = gui.new Run();
		frame.add(run);
		frame.setVisible(true);
	}
}


class JTextAreaOutputStream extends OutputStream
{
    private final JTextArea destination;
 
 
    public JTextAreaOutputStream (JTextArea destination)
    {
        if (destination == null)
            throw new IllegalArgumentException ("Destination is null");
 
 
        this.destination = destination;
    }
    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable ()
            {
                @Override
                public void run() 
                {
                    destination.append (text);
                }
            });
    }
    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }
}
 


