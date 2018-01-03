package jju;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.security.auth.login.CredentialException;


//�ļ�IO��ʹ�ð���
public class IOTest {
	public static void main(String[] args) 
	{
		//1.�ڴ��̵�ĳ��Ŀ¼�£�����һ���ļ�
			//1.1����һ��Path����
		Path f1=Paths.get("E:\\1.txt");
		
		//1.2�����ļ�
		//OutputStream os=Files.newOutputStream(f1,StandardOpenOption.CREATE);
		try {
			//OutputStream os=Files.newOutputStream(f1, StandardOpenOption.CREATE);
			OutputStream os=Files.newOutputStream(f1, StandardOpenOption.APPEND);
			os.write('A');
			os.write('D');
			os.write('C');
		
			
			//�����ַ������������ݵ�д��
			//--PrintWrite
			PrintWriter pw=new PrintWriter(os);
							pw.print("ɵ��");	
							pw.println("������");
			
							pw.flush();//ǿ������д��;
							pw.close();
							os.close();
			//�����ַ���,��ȡ�ļ��е�����
			BufferedReader br=
					Files.newBufferedReader(
							f1, Charset.defaultCharset());
			while(true)
			{
			String s=br.readLine();
				if(s==null)
					break;
			System.out.println(s);
			}
			//InputStream in=Files.newOutputStream(f1,StandardOpenOption.CREATE);
			byte byt[]=new byte[100];
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		File file=new File("E:\\2.txt");
		
		if(file.exists())
		{
			String name=file.getName();
			long length=file.length();
			System.out.println("�ļ�����Ϊ:"+name);
			System.out.println("�ļ�������:"+length);
		}
		else
		{
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("���ļ��Ѵ���!");
		}
	}

}
