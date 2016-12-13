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
	private static final long serialVersionUID = 1L;// 序列化时保持版本的兼容性
	private Thread m_TickedSale=null;//定义一个多线程
	private Graphics m_Graphics;//一个画笔    
	private Image m_Images[];//照片组
	private int m_nCurrImage=0;//整型变量指示照片    
    private boolean m_fAllLoaded=false;//布尔变量指示是否成功加载照片
    private final int NUM_IMAGES=3;//表示照片数     
    Label label1,label2;//标签变量    
    Clock2 pp;//时间类对象
    private boolean m_fStandAlone=false;//是否加载外部文件
    TextField tRemain,tSold,tTotal;//文本框     
    int remain=200;     
    int sold=0; 
    int total=0;     
    Button mybutton;     
    @SuppressWarnings("deprecation")
    public void init(){
    	resize(1024,600);//调整浏览器大小      
    	setBackground(Color.pink);//将背景显示为粉色
    	setFont(new Font("TimesRoman",Font.BOLD,10));//设置字体为"TimesRoman"，font bold 字体加粗，大小为10号
    	setLayout(null);//默认为流式布局      
    	addSeat();//调用函数绘制座位 
    	label1=new Label();//定义标签       
    	label2=new Label(); 
    	add(label1); //把标签加入布局管理器 
    	add(label2);        
    	
    	label1.reshape(600,10,150,50);//在指定位置绘制标签     
    	label2.reshape(600,40,150,50);
    	pp=new clock2(label1,label2);//两个标签用于显示时间和日期 
    	tRemain=new TextField(Integer.toString(remain)+"张",10);//显示剩余票数，字宽为10 
    	tRemain.setEditable(false);//表示此控件不可编辑    
    	add(tRemain);//加入布局管理器     
    	tRemain.reshape(400, 160, 80, 30);//在指定位置绘制
    	tSold=new TextField(Integer.toString(sold)+"张",10);//显示售出票数，字宽为10    
    	tSold.setEditable(false);//表示此控件不可编辑
    	add(tSold);//加入布局管理器      
    	tSold.reshape(100,160,80,30);//在指定位置绘制      
    	tTotal=new TextField(Integer.toString(total)+"元",10);//显示总金额，字宽为10
    	tTotal.setEditable(false);//表示此控件不可编辑   
    	add(tTotal);//加入布局管理器
    	tTotal.reshape(640,160,80,30);//在指定位置绘制    
    	m_Graphics=getGraphics();//获得一个当前对象的画笔    
    	Label myLabel1=new Label("今日电影：");//在mylabel1上添加字符串"今日电影："
    	add(myLabel1);//加入布局管理器      
    	myLabel1.reshape(300,10,150,50);//在指定位置绘制
    	Label myLabel2=new Label("速度与激情7");     
    	add(myLabel2);
    	myLabel2.reshape(300,60,150,50); 
    	
    	Label myLabel3=new Label("售出票：");    
    	add(myLabel3);       
    	myLabel3.reshape(10,160,80,50);     
    	Label myLabel4=new Label("剩余票：");     
    	add(myLabel4);     
    	myLabel4.reshape(300,160,80,50);   
    	Label myLabel5=new Label("总金额：");   
    	add(myLabel5);       
    	myLabel5.reshape(600,160,80,50);      
    	Label myLabel6=new Label("票价:1-3排 10元  4-6排 5元 7-10排 2元");    
    	add(myLabel6);       
    	myLabel6.reshape(5,200,500,50); 
    }
}
