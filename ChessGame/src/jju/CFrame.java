package jju;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//��������洰����
public class CFrame extends JFrame implements MouseListener {
	// ������Ϸ�м�������
	final int N = 10; // ���̵�ά��
	final int D = 50; // ����ÿ�����ӵĴ�С
	final int x0 = 100, y0 = 100; // ���̵����Ͻ�����
	// ����������ÿ�������λ��״̬�Ķ�λ����
	int[][] pos = new int[N][N];
	int chessColor = 1; // ����һ����̬��������д��ÿһ����������Ϣ(�кţ��кţ���ɫ)
	// (�����Ԫ�ؿ����ǲ�ͬ������)
	//
	// ArrayList a =new ArrayList();
	// ������ÿһ���Ķ�̬����
	ArrayList<ChessStep> steps = new ArrayList<ChessStep>();

	// �������췽��
	public CFrame() {
		setTitle("�ҵ���������Ϸ�������棩v0.1");
		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���öԴ�����������¼�
		addMouseListener(this);
		// setResizable(false);
	}

	public void paint(Graphics g) {
		// ͨ��graphic��������������Ļ
		// 1.��������
		for (int i = 1; i <= N; i++) {
			// ��i������
			g.setColor(Color.red);
			g.drawLine(x0, y0 + (i - 1) * D, x0 + (N - 1) * D, y0 + (i - 1) * D);
			// ��i������
			g.drawLine(x0 + (i - 1) * D, y0, x0 + (i - 1) * D, y0 + (N - 1) * D);

		}
		// 2.�������ӣ� --���������ϵ�ÿ������
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {

				int status = pos[i][j];
				if (status == 0)
					continue;
				if (status == 1)
					g.setColor(Color.green);
				else
					g.setColor(Color.blue);
				g.fillOval(x0 + j * D - 10, y0 + i * D - 10, 20, 20);

			}

	}

	public static void main(String[] args) {
		new CFrame().setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 1.��ȡ��ǰ����λ��
		int x = e.getX();
		int y = e.getY();
		System.out.println("x:" + x + "y:" + y);
		// �ڱ߽��һ�� �ķ�Χ��;�������ķ�Χ��ȡ�������λ�á�
		int row = (int) ((y - y0) * 1.0 / D + 0.5);// ��˧,*1.0����Ϊǰ��ñ�֤С���Ĵ���
		int col = (int) ((x - x0) * 1.0 / D + 0.5);// ǿ��ȡ��;
		// �жϸ�λ���Ƿ�����

		if (pos[row][col] != 0)
			return;
		// pos[row][col]=chessColor;

		// 3.���������ϸ�λ�õ�״̬
		pos[row][col] = chessColor;// ? ?��ǰ���ӵ���ɫ
		// !!!���ò��壬��ŵ���̬����steps��
		ChessStep obj = new ChessStep(row, col, chessColor);
		steps.add(obj);
		// (������һ�����ӵ���ɫ)
		chessColor = chessColor == 1 ? 2 : 1;
		/*
		 * if(chessColor==1) chessColor=2; else chessColor=1;
		 */
		// ����һ���ķ���
		// ˢ����Ļ���������:
		repaint();

		// �������ӵ�ʱ�򣬽����ж�ʤ��
		int result = checkWinner();
		if (result > 0) {
			String winner = result == 1 ? "�ڷ���ʤ" : "�׷���ʤ";
			JOptionPane.showMessageDialog(null, "����Ϸ����" + winner);
			// ������Ϸ�ĸ���
			// ����ReviewThread�߳�
			System.out.println("��Ϸ��ʼ���̣�����");
			new ReviewThread().start();
			// �Զ�����;
			saveData();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* �����Զ���ʤ���ķ��� */
	// ����ֵ----0 û�зֳ�ʤ�� 1---�ڷ�ʤ 2---�׷�ʤ
	int checkWinner() {
		int result = 0;

		// �����ң����ϵ���,���α��������ϵ�ÿ���ǿն���
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (pos[i][j] == 0)
					continue;

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;
				// 1.�� �� ����������� 4��λ��
				int count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (j + k) < N; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i][j + k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 2.�� �� ����ʼ

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;

				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (j - k) >= 0; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i][j - k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 3.�� �� ��ʼ����

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;

				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i - k) >= 0; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i - k][j] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 4.�� �� ��ʼ����

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;

				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i + k) < N; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i + k][j] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 5.�� ���� ��ʼ����

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;
				// 1.�� �� ����������� 4��λ��
				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i - k) >= 0 && (j - k) >= 0; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i - k][j - k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 6.�� ���� ��ʼ����

				// (i,j)λ�������ӣ�����(i,j)Ϊ������8�����������;

				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i + k) < N && (j - k) >= 0; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i + k][j - k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];

				// 7.������ ��ʼ����

				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i + k) < N && (j - k) >= 0; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i + k][j - k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];
				// 8.�� ���� ��ʼ����
				count = 1;// ��ͬ����ɫ������
				for (int k = 1; k <= 4 && (i + k) < N && (j + k) < N; k++) {
					// �ж� �Ƿ���pos[i][j]��������ɫ��ͬ
					if (pos[i + k][j + k] == pos[i][j])
						count++;
					else
						break;
				}
				if (count == 5) // ����5�����飬���ظ÷����ӻ�ʤ
					return pos[i][j];
			}

		return result;
	}

	// ����һ���߳���ReviewThread..���ڻ���;
	//
	class ReviewThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// ������������ϵ�����
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++) {
					pos[i][j] = 0;
				}
			// ��˳���stepsȡ��һ��������Ϣ
			// for (ChessStep obj : steps) {

			for (ChessStep obj : steps) {
				int row = obj.getRow();
				int col = obj.getCol();
				int chessColor = obj.getChessColor();
				// ������Ļ���̸��ӵ�״̬
				pos[row][col] = chessColor;

				repaint();// ���»�����Ļ

				try {
					sleep(1000 * 3);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			// super.run();
		}

	}// end class

	// ����һ�����̵ķ���saveData()
	public void saveData() {
		// �������������Ϣ����steps�е�����
		// д�뵽�ļ���

		String dt = "201709271020";
		String filename = "E:\\" + dt + ".txt";
		Path f1 = Paths.get(filename);
		try {
			OutputStream os = Files.newOutputStream(f1,
					StandardOpenOption.CREATE);
			PrintWriter pw = new PrintWriter(os);
			// ����steps����
			for (ChessStep obj : steps) {
				int row = obj.getRow();
				int col = obj.getCol();
				int color = obj.getChessColor();
				String info = row + "#" + col + "#" + color;
				pw.println(info);
				pw.flush();

			}
			pw.close();
			os.close();
			JOptionPane.showMessageDialog(null, "����ɹ�!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// end saveData

	//
	public void loadData() {
		String filename = "";
		// �����ļ�ѡ��Ի���
		FileDialog dig = new FileDialog(this, "Open", FileDialog.LOAD);
		dig.setVisible(true);
		filename = dig.getDirectory() + "\\" + dig.getFile();
		System.out.println(filename);

		// filename="E:\\201709271020.txt";
		Path f1 = Paths.get(filename);
		try {
			BufferedReader br = Files.newBufferedReader(f1,
					Charset.defaultCharset());
			// ����Ѿ���ŵ�������Ϣ
			steps.clear();
			// ��������ļ�
			while (true) {
				String info = br.readLine();
				if (info == null)
					break;
				// ����ĳһ����Ϣ������#�ָ������λ��

				String[] ss = info.split("#");

				int row = Integer.parseInt(ss[0]);
				int col = Integer.parseInt(ss[1]);
				int color = Integer.parseInt(ss[2]);
				// ����һ�����Ӷ���
				ChessStep obj = new ChessStep();
				// ���뵽������
				steps.add(obj);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
