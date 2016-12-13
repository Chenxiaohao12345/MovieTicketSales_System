import java.applet.Applet; 
import java.applet.AudioClip; 
import java.awt.Button; 
import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics; 
import java.awt.Image; 
import java.awt.Label; 
import java.awt.MediaTracker;
import java.awt.TextField; 
import java.awt.Toolkit;
import java.applet.*; 
import java.awt.*; 
import java.util.*; 
import javax.print.attribute.standard.Media;

public class MyTicketSale extends Applet implements Runnable{
	private static final long serialVersionUID = 1L;// ���л�ʱ���ְ汾�ļ�����
	private Thread m_TickedSale=null;//����һ�����߳�
	private Graphics m_Graphics;//һ������    
	private Image m_Images[];//��Ƭ��
	private int m_nCurrImage=0;//���ͱ���ָʾ��Ƭ    
    private boolean m_fAllLoaded=false;//��������ָʾ�Ƿ�ɹ�������Ƭ
    private final int NUM_IMAGES=3;//��ʾ��Ƭ��     
    Label label1,label2;//��ǩ����    
    Clock2 pp;//ʱ�������
    private boolean m_fStandAlone=false;//�Ƿ�����ⲿ�ļ�
    TextField tRemain,tSold,tTotal;//�ı���     
    int remain=200;     
    int sold=0; 
    int total=0;     
    Button mybutton;     
    @SuppressWarnings("deprecation")
    public void init(){
    	resize(1024,600);//�����������С      
    	setBackground(Color.pink);//��������ʾΪ��ɫ
    	setFont(new Font("TimesRoman",Font.BOLD,10));//��������Ϊ"TimesRoman"��font bold ����Ӵ֣���СΪ10��
    	setLayout(null);//Ĭ��Ϊ��ʽ����      
    	addSeat();//���ú���������λ 
    	label1=new Label();//�����ǩ       
    	label2=new Label(); 
    	add(label1); //�ѱ�ǩ���벼�ֹ����� 
    	add(label2);        
    	
    	label1.reshape(600,10,150,50);//��ָ��λ�û��Ʊ�ǩ     
    	label2.reshape(600,40,150,50);
    	pp=new clock2(label1,label2);//������ǩ������ʾʱ������� 
    	tRemain=new TextField(Integer.toString(remain)+"��",10);//��ʾʣ��Ʊ�����ֿ�Ϊ10 
    	tRemain.setEditable(false);//��ʾ�˿ؼ����ɱ༭    
    	add(tRemain);//���벼�ֹ�����     
    	tRemain.reshape(400, 160, 80, 30);//��ָ��λ�û���
    	tSold=new TextField(Integer.toString(sold)+"��",10);//��ʾ�۳�Ʊ�����ֿ�Ϊ10    
    	tSold.setEditable(false);//��ʾ�˿ؼ����ɱ༭
    	add(tSold);//���벼�ֹ�����      
    	tSold.reshape(100,160,80,30);//��ָ��λ�û���      
    	tTotal=new TextField(Integer.toString(total)+"Ԫ",10);//��ʾ�ܽ��ֿ�Ϊ10
    	tTotal.setEditable(false);//��ʾ�˿ؼ����ɱ༭   
    	add(tTotal);//���벼�ֹ�����
    	tTotal.reshape(640,160,80,30);//��ָ��λ�û���    
    	m_Graphics=getGraphics();//���һ����ǰ����Ļ���    
    	Label myLabel1=new Label("���յ�Ӱ��");//��mylabel1������ַ���"���յ�Ӱ��"
    	add(myLabel1);//���벼�ֹ�����      
    	myLabel1.reshape(300,10,150,50);//��ָ��λ�û���
    	Label myLabel2=new Label("�ٶ��뼤��7");     
    	add(myLabel2);
    	myLabel2.reshape(300,60,150,50); 
    	
    	Label myLabel3=new Label("�۳�Ʊ��");    
    	add(myLabel3);       
    	myLabel3.reshape(10,160,80,50);     
    	Label myLabel4=new Label("ʣ��Ʊ��");     
    	add(myLabel4);     
    	myLabel4.reshape(300,160,80,50);   
    	Label myLabel5=new Label("�ܽ�");   
    	add(myLabel5);       
    	myLabel5.reshape(600,160,80,50);      
    	Label myLabel6=new Label("Ʊ��:1-3�� 10Ԫ  4-6�� 5Ԫ 7-10�� 2Ԫ");    
    	add(myLabel6);       
    	myLabel6.reshape(5,200,500,50); 
    }
}
