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
    	setBackground(Color.yellow);//��������ʾΪ��ɫ
    	setFont(new Font("TimesRoman",Font.BOLD,12));//��������Ϊ"TimesRoman"��font bold ����Ӵ֣���СΪ10��
    	setLayout(null);//Ĭ��Ϊ��ʽ����      
    	addseat();//���ú���������λ 
    	label1=new Label();//�����ǩ       
    	label2=new Label(); 
    	add(label1); //�ѱ�ǩ���벼�ֹ����� 
    	add(label2);        
    	
    	label1.reshape(600,10,150,50);//��ָ��λ�û��Ʊ�ǩ     
    	label2.reshape(600,40,150,50);
    	pp=new Clock2(label1,label2);//������ǩ������ʾʱ������� 
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

public void addseat(){
	int xx,yy;
	for(int i=1;i<=10;i++){
		yy=230+35*(i-1);
		for(int j=1;j<=20;j++){
			xx=50*(j-1);
			Button mybutton=new Button(Integer.toString(i)+"��"+Integer.toString(j)+"��");//������ť
			add(mybutton);
			mybutton.show();//��ʾ��ť
			mybutton.move(xx,yy);//����ť�ƶ���ָ��λ��
			mybutton.resize(50,30);//���ð�ť��С
		}
	}
}//����10��20�е���λ��
public void destroy(){
	pp.stop();
}//������رյ�ʱ����ø÷���
private void displayImage(Graphics g){
	if(!m_fAllLoaded)return;
	g.drawImage(m_Images[m_nCurrImage], 1, 1, 200, 150,null);//����ͼƬ�����Ŷ���
}
public void paint(Graphics g){
	if(m_fAllLoaded)
	{
		displayImage(g);
	}
	else 
		g.drawString("loading images...", 10, 20);
}//���ɹ�����ͼƬ�����displayImage��ʾ����û������ʾloading images..
public boolean action(Event evt,Object arg)
{
	int price=0;
	if(evt.target instanceof Button){
		Button btn=(Button)evt.target;//���ݲ�׽����Ŀ��ȷ����ť��Ϣ�����洢��һ����ť������
		String label3=btn.getLabel();//����ť���ı���Ϣ�������ַ�����
		switch(label3.charAt(0)){
		case'1':{
			if(label3.charAt(1)=='0')
				price=2;
			else
				price=10;
		}break;
		case'2':price=10;break;
		case'3':price=10;break;
		case'4':price=5;break;
		case'5':price=5;break;
		case'6':price=5;break;
		case'7':price=2;break;
		case'8':price=2;break;
		case'9':price=2;break;
		default:return false;
		}//����������ȷ��Ʊ��
		btn.setLabel("���۳�");//����ť�ı��滻Ϊ�����۳���
		btn.disable();//ʹ�øð�ť�޷��ٱ��༭
		remain--;//�ִ��Ʊ��-1
		sold++;//�۳���Ʊ��+1
		total+=price;//�ܼ���Ӧ���ӣ�
		tRemain.setText(Integer.toString(remain)+"��");//��ʣ��Ʊ���������
		tSold.setText(Integer.toString(sold)+"��");//���۳�Ʊ���������
		tTotal.setText(Integer.toString(total)+"Ԫ");//���ܼ��������
	}
	return true;
}//�����û�����İ�ť���ж�Ʊ�ۣ���λ�ţ��ı���صĲ���ֵ
public void start(){
	if(m_TickedSale==null){
		m_TickedSale=new Thread(this);
		m_TickedSale.start();
	}
}
public void stop(){
	if(m_TickedSale!=null)
	{
		m_TickedSale.stop();
		m_TickedSale=null;
	}
}
public void run(){
	int m_nCurrImage=0;
	if(!m_fAllLoaded)
	{
		repaint();
		m_Images=new Image[NUM_IMAGES];//�½�Image���飬��3��ͼƬ
		MediaTracker tracker=new MediaTracker(this);//����ý�������MediaTracker ������ͼ��
		String strImage;//�ַ�����ʾͼƬ·��
		for(int i=1;i<=NUM_IMAGES;i++){
			strImage="D:\\images"+((i<4)?"0":" ")+i+".jpg";//��ʼ��ͼƬ·��
			System.out.println(strImage);
			if(m_fStandAlone)
				m_Images[i-1]=Toolkit.getDefaultToolkit().getImage(strImage);// ���ɹ�����ͼƬ��ȡ��ָ��·����ͼƬ��Toolkit.getDefaultToolkit().getImage(...) �����ɽ���String ������ URL ����������ָ��ͼ���ļ���·��
			else 
			    m_Images[i-1]=getImage(getDocumentBase(),strImage);//�� δ�ɹ�������ʹ��getDocumentBase()����ͼƬ·�����ٵ���getImage(	��ʾͼƬ
			tracker.addImage(m_Images[i-1], 0);//��ͼƬ�ڲ��ֹ���������ʾ
		}
		try{
			tracker.waitForAll();//����waitForAll������ʼ�����ع��̣����ȴ����б����ٵ�ͼ�������Ϻ󷵻�
			m_fAllLoaded=!tracker.isErrorAny();//������isErrorAny������������鲢ȷ���κα�׷�ٵ�ͼ���Ƿ�����˴��������������false����˵��û�д����������ͼ������˴���isErrorAny����������
			 

		}catch(InterruptedException e){
			stop();
		}//try,catch������ͼƬ�����쳣
		if(!m_fAllLoaded)
		{
			stop();
			m_Graphics.drawString(" Error loding images", 10, 10);
			return;
		}//���д��󣬣�m_fAllLoadedΪ�棬�����̣߳����" Error loading images!"
	}
	repaint();//�����ػ��¼�
	while(true){
		try{
			displayImage(m_Graphics);
			m_nCurrImage++;
			if(m_nCurrImage!=NUM_IMAGES)m_nCurrImage=0;
			Thread.sleep(3000);
		}//����ͼƬ
		catch(InterruptedException e){
			stop();
		}//���ͼƬ�����Ƿ��쳣
	}
}
}