import java.applet.*;
import java.awt.*; 
import java.util.*; 
public class Clock2 implements Runnable {
	Date m_date;//定义一个日期类对象   
	Thread m_runner=null;//定义一个多线程   
	Label gr;//定义一个标签表示日期   
	Label gt;//定义一个标签表示时间 
	public Clock2(Label r,Label t){ 
		gr=r;gt=t;    
		if(m_runner==null){    
			m_runner=new Thread(this); 
			m_runner.start();
		}
	}
	@SuppressWarnings("deprecation")
	public void stop(){     
		if(m_runner!=null){
			m_runner.stop();  
		    m_runner=null;    
		    }     
	}
	public void run(){
		while(true){
			m_date=new Date(); 
            @SuppressWarnings("deprecation")
			String temp1=new String ("日 期:"+String.valueOf(1+m_date.getMonth())+
            		"/"+String.valueOf(m_date.getDate())+
            		"/"+String.valueOf(1900+m_date.getYear()));//定义并初始化字符串表示日期
            String temp2=new String ("时 间:"+String.valueOf(m_date.getHours())+
            		":"+String.valueOf(m_date.getMinutes())+
            		"/"+String.valueOf(m_date.getSeconds()));//定义并初始化字符串表示时间 
            gr.setText(temp1);//显示日期       
            gt.setText(temp2);//显示时间
            try{       
            	Thread.sleep(1000);//暂停1s    
            	}catch(InterruptedException e){}
            }
		}
	}

