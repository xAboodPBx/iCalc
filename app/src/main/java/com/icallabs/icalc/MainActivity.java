package com.icallabs.icalc;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.content.Context;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class MainActivity extends Activity {
  @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(Color.rgb(11,16,32));getWindow().setNavigationBarColor(Color.rgb(11,16,32));setContentView(new CalcView(this));}
  static class CalcView extends View {
    Paint p=new Paint(3);String display="0",expression="";final int bg=Color.rgb(11,16,32), text=Color.rgb(245,247,255), muted=Color.rgb(157,168,197);float den;
    String[][] keys={{"C","⌫","%","÷"},{"7","8","9","×"},{"4","5","6","−"},{"1","2","3","+"},{"0",".","±","="}};DecimalFormat fmt=new DecimalFormat("0.##########");
    CalcView(Context c){super(c);den=getResources().getDisplayMetrics().density;setBackgroundColor(bg);}
    float d(float n){return n*den;}
    void txt(Canvas c,String s,float x,float y,float size,int col,Paint.Align a){p.setStyle(Paint.Style.FILL);p.setTextSize(d(size));p.setColor(col);p.setTextAlign(a);p.setTypeface(Typeface.create("sans",Typeface.NORMAL));c.drawText(s,x,y,p);}
    void round(Canvas c,float l,float t,float r,float b,float rad,int col){p.setStyle(Paint.Style.FILL);p.setColor(col);c.drawRoundRect(l,t,r,b,d(rad),d(rad),p);}
    @Override protected void onDraw(Canvas c){super.onDraw(c);float w=Math.min(getWidth(),d(420)),left=(getWidth()-w)/2,h=getHeight();
      // جوال عمودي: مساحة حاسبة واحدة فقط، بلا عناوين أو عناصر سطح مكتب
      p.setColor(Color.argb(42,139,92,246));c.drawCircle(left+w*.08f,d(105),w*.55f,p);p.setColor(Color.argb(24,34,211,238));c.drawCircle(left+w*.98f,d(210),w*.42f,p);
      float pad=d(18), panelL=left+pad,panelR=left+w-pad;
      round(c,panelL,d(30),panelR,d(192),24,Color.rgb(25,34,61));txt(c,expression,panelR-d(18),d(82),16,muted,Paint.Align.RIGHT);txt(c,display,panelR-d(16),d(165),48,text,Paint.Align.RIGHT);
      float gap=d(10),top=d(220),bh=Math.min(d(68),(h-d(320)-gap*4)/5),bw=(w-d(36)-gap*3)/4;
      for(int r=0;r<5;r++)for(int col=0;col<4;col++){float x=left+d(18)+col*(bw+gap),y=top+r*(bh+gap);String k=keys[r][col];boolean op="÷×−+=".contains(k),clear=k.equals("C")||k.equals("⌫")||k.equals("%")||k.equals("±");int color=op?Color.rgb(65,47,99):clear?Color.rgb(35,45,76):Color.rgb(27,36,65);round(c,x,y,x+bw,y+bh,18,color);txt(c,k,x+bw/2,y+bh*.64f,21,op?Color.rgb(221,214,254):text,Paint.Align.CENTER);}
    }
    @Override public boolean onTouchEvent(MotionEvent e){if(e.getAction()==0)return true;if(e.getAction()==1){float w=Math.min(getWidth(),d(420)),left=(getWidth()-w)/2,h=getHeight(),gap=d(10),top=d(220),bh=Math.min(d(68),(h-d(320)-gap*4)/5),bw=(w-d(36)-gap*3)/4;int col=(int)((e.getX()-left-d(18))/(bw+gap)),row=(int)((e.getY()-top)/(bh+gap));float ix=(e.getX()-left-d(18))%(bw+gap),iy=(e.getY()-top)%(bh+gap);if(row>=0&&row<5&&col>=0&&col<4&&ix<bw&&iy<bh)tap(keys[row][col]);return true;}return true;}
    void tap(String k){if(k.equals("C")){display="0";expression="";}else if(k.equals("⌫")){display=display.length()>1?display.substring(0,display.length()-1):"0";}else if(k.equals("±")){if(!display.equals("0"))display=display.startsWith("-")?display.substring(1):"-"+display;}else if(k.equals("=")){try{display=fmt.format(eval(expression+display));expression="";}catch(Exception e){display="خطأ";expression="";}}else if("÷×−+%".contains(k)){expression+=display+k;display="0";}else{if(display.equals("0")&&!k.equals("."))display=k;else if(k.equals(".")&&!display.contains("."))display+=k;else display+=k;}invalidate();}
    double eval(String s){s=s.replace("×","*").replace("÷","/").replace("−","-");if(s.endsWith("%"))return Double.parseDouble(s.substring(0,s.length()-1))/100;StringTokenizer t=new StringTokenizer(s,"+-*/",true);double r=Double.parseDouble(t.nextToken());while(t.hasMoreTokens()){String op=t.nextToken();double n=Double.parseDouble(t.nextToken());if(op.equals("+"))r+=n;else if(op.equals("-"))r-=n;else if(op.equals("*"))r*=n;else r/=n;}return r;}
  }
}
