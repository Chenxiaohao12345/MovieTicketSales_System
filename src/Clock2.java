import java.applet.*;
import java.awt.*; 
import java.util.*; 
public class Clock2 implements Runnable {
	Date m_date;//����һ�����������   
	Thread m_runner=null;//����һ�����߳�   
	Label gr;//����һ����ǩ��ʾ����   
	Label gt;//����һ����ǩ��ʾʱ�� 
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
			String temp1=new String ("�� ��:"+String.valueOf(1+m_date.getMonth())+
            		"/"+String.valueOf(m_date.getDate())+
            		"/"+String.valueOf(1900+m_date.getYear()));//���岢��ʼ���ַ�����ʾ����
            String temp2=new String ("ʱ ��:"+String.valueOf(m_date.getHours())+
            		":"+String.valueOf(m_date.getMinutes())+
            		"/"+String.valueOf(m_date.getSeconds()));//���岢��ʼ���ַ�����ʾʱ�� 
            gr.setText(temp1);//��ʾ����       
            gt.setText(temp2);//��ʾʱ��
            try{       
            	Thread.sleep(1000);//��ͣ1s    
            	}catch(InterruptedException e){}
            }
		}
	}

