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
    	setBackground(Color.yellow);//将背景显示为粉色
    	setFont(new Font("TimesRoman",Font.BOLD,12));//设置字体为"TimesRoman"，font bold 字体加粗，大小为10号
    	setLayout(null);//默认为流式布局      
    	addseat();//调用函数绘制座位 
    	label1=new Label();//定义标签       
    	label2=new Label(); 
    	add(label1); //把标签加入布局管理器 
    	add(label2);        
    	
    	label1.reshape(600,10,150,50);//在指定位置绘制标签     
    	label2.reshape(600,40,150,50);
    	pp=new Clock2(label1,label2);//两个标签用于显示时间和日期 
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

public void addseat(){
	int xx,yy;
	for(int i=1;i<=10;i++){
		yy=230+35*(i-1);
		for(int j=1;j<=20;j++){
			xx=50*(j-1);
			Button mybutton=new Button(Integer.toString(i)+"排"+Integer.toString(j)+"号");//构建按钮
			add(mybutton);
			mybutton.show();//显示按钮
			mybutton.move(xx,yy);//将按钮移动到指定位置
			mybutton.resize(50,30);//设置按钮大小
		}
	}
}//增加10排20列的座位表
public void destroy(){
	pp.stop();
}//浏览器关闭的时候调用该方法
private void displayImage(Graphics g){
	if(!m_fAllLoaded)return;
	g.drawImage(m_Images[m_nCurrImage], 1, 1, 200, 150,null);//绘制图片，播放动画
}
public void paint(Graphics g){
	if(m_fAllLoaded)
	{
		displayImage(g);
	}
	else 
		g.drawString("loading images...", 10, 20);
}//若成功加载图片则调用displayImage显示，若没有则显示loading images..
public boolean action(Event evt,Object arg)
{
	int price=0;
	if(evt.target instanceof Button){
		Button btn=(Button)evt.target;//根据捕捉到的目标确定按钮信息，并存储在一个按钮对象中
		String label3=btn.getLabel();//将按钮的文本信息储存在字符串中
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
		}//根据排数来确定票价
		btn.setLabel("已售出");//将按钮文本替换为“已售出”
		btn.disable();//使得该按钮无法再被编辑
		remain--;//现存的票数-1
		sold++;//售出的票数+1
		total+=price;//总价相应增加；
		tRemain.setText(Integer.toString(remain)+"张");//将剩余票数重新输出
		tSold.setText(Integer.toString(sold)+"张");//将售出票数重新输出
		tTotal.setText(Integer.toString(total)+"元");//将总价重新输出
	}
	return true;
}//根据用户点击的按钮来判断票价，座位号，改变相关的参数值
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
		m_Images=new Image[NUM_IMAGES];//新建Image数组，有3个图片
		MediaTracker tracker=new MediaTracker(this);//调用媒体跟踪器MediaTracker 来加载图像
		String strImage;//字符串表示图片路径
		for(int i=1;i<=NUM_IMAGES;i++){
			strImage="D:\\images"+((i<4)?"0":" ")+i+".jpg";//初始化图片路径
			System.out.println(strImage);
			if(m_fStandAlone)
				m_Images[i-1]=Toolkit.getDefaultToolkit().getImage(strImage);// 若成功加载图片，取得指定路径的图片，Toolkit.getDefaultToolkit().getImage(...) 方法可接受String 或者是 URL 参数，用以指定图像文件的路径
			else 
			    m_Images[i-1]=getImage(getDocumentBase(),strImage);//若 未成功加载则使用getDocumentBase()返回图片路径，再调用getImage(	显示图片
			tracker.addImage(m_Images[i-1], 0);//将图片在布局管理器中显示
		}
		try{
			tracker.waitForAll();//调用waitForAll方法初始化加载过程，并等待所有被跟踪的图像加载完毕后返回
			m_fAllLoaded=!tracker.isErrorAny();//调用了isErrorAny方法，用来检查并确定任何被追踪的图像是否产生了错误。如果方法返回false，则说明没有错误发生；如果图像产生了错误，isErrorAny方法返回真
			 

		}catch(InterruptedException e){
			stop();
		}//try,catch来捕获图片加载异常
		if(!m_fAllLoaded)
		{
			stop();
			m_Graphics.drawString(" Error loding images", 10, 10);
			return;
		}//若有错误，！m_fAllLoaded为真，结束线程，输出" Error loading images!"
	}
	repaint();//产生重绘事件
	while(true){
		try{
			displayImage(m_Graphics);
			m_nCurrImage++;
			if(m_nCurrImage!=NUM_IMAGES)m_nCurrImage=0;
			Thread.sleep(3000);
		}//播放图片
		catch(InterruptedException e){
			stop();
		}//检查图片加载是否异常
	}
}
}