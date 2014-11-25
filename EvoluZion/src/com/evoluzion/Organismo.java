/*Copyright 2014 Adolfo R. Zurita*/

/*This file is part of EvoluZion.

    EvoluZion is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    EvoluZion is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with EvoluZion.  If not, see <http://www.gnu.org/licenses/>.*/

package com.evoluzion;


import java.text.DecimalFormat;
import java.text.NumberFormat;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.org.apache.xml.internal.serializer.ToStream;

public class Organismo implements Comparable<Organismo>{
	
	private Mundo m;
	
	Sprite imagen,imagen2,imagen3;
	Sprite auraATB;
	
	float speed;
	float radioConsiente; //radio de los sentidos
	float toleranciaTemp;
	float tempOptima;
	private NumberFormat format = new DecimalFormat("0.00");
	float cuadradoTemp;
	
	float ancho;
	float alto;
	int tasaMut = 1000;  //si mutar es true, indica la frecuencia de mutacion
	int capacidad;    //cantidad maxima de masa y energia que puede portar el organismo
	float energia= 484;      //energia del organismo
	int biomasa; //biomasa del organismo
	int color;         //variable color
	int longevidad;    //tiempo en que muere de viejo milisegundos
	int tiempoMitosis;    //tiempo en que se divide 
	int segundos;
	int azar;// se usa para las operaciones random
	int marcado= 1; // 1= false -1= true
	int cantidad =1;
	Vector2 posicion;
	Vector2 direccion;
	
	Rectangle borde;
	
	char[] letras= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	
	String fechaNacimiento;
	
	boolean mutar= true; //puede mutar o no
	boolean carnivoro=false; //capacidad de tomar alimentarse de otros organismos
	boolean resistenciaATB; //capacidad de resistir Antibioticos
	boolean sentir;     //capacidad de sensar el entornno por comida
	boolean cazar;       //capacidad de buscar su comida
	boolean escapar;
	boolean transferred= false;

	long edad, delta,delta2;   //se usa para determinar el paso del tiempo
	Genoma adn;         //genoma
	
	String identificador;// indica la especie del organismo
	int carniI;
	int sentI;
	int cazarI;
	int escapI;
	String cromosoma;        //todos los genes juntos
	StringBuffer nombre;
	
	
	Array<Vector2> aObjetos;    //memoria donde se guarda la posicion de las fuentes de alimento
	Array<Vector2> aPredadores; //memoria donde se guarda la posicion de las fuentes de peligro
	
	char base;//nucleotido

	private int resistenciaI;

	private Sprite transferido;

	
	
	
	

	public Organismo(Genoma adn, Vector2 posicion,StringBuffer nombre,  Mundo m) {
				
		this.m=m;
		
		fechaNacimiento= ""+m.minutos2+":"+m.segundos;
		
		aObjetos= new Array<Vector2>();
		aPredadores= new Array<Vector2>();
		
		this.adn=adn;
		this.posicion=posicion;
		this.nombre = nombre;
		
		direccion= new Vector2();
		borde = new Rectangle();
		
		
		translate();

		segundos=0;		
		setTime();
		setEdad();
		setDelta();
     
		
		 identificador = new String(Identificar(m));
		
		
		if(capacidad<=0){capacidad=1; morir();}
		
		//System.out.println("identificador"+identificador);
		
	}
	
	public void translate(){
		
		speed= (adn.traducirMagnitud(this,adn.speed,".[RKH][LMI][LMI].....Q[AG]........")/250)/m.zoom;
		ancho= (int) (adn.traducirMagnitud(this, adn.ancho, "..[RKH][R]......S[KRH]..")/224)/m.zoom;
		alto= (int) (adn.traducirMagnitud(this, adn.alto, "..[RKH][R]......S[KRH]..")/224)/m.zoom;
		
		toleranciaTemp= (float) (adn.traducirMagnitud(this,adn.toleranciaTemp,"...[VYS]..........[HPF].......")/126.2552f);
		
		
		
		color = adn.traducirColor(this, "YT","ILI")/2;
		
		radioConsiente= adn.traducirMagnitud(this, adn.radioConsiente, "..[APTS]..[MILV]F")/30;
		longevidad = (int) (adn.traducirMagnitud(this, adn.longevidad, ".....[DN][LMIV]......[QE]....[FY]")*8.090f);
		tasaMut= 	(int) ((adn.traducirMagnitud(this, adn.tasaMutacion, ".M.[LMIV][LMI]...[VLI]..")/1.9)*m.eficiencia);
		
		
		carnivoro= adn.traducirBoolean(adn.predador,".....[M].{4,7}[MIV][KRH]..");
		sentir= adn.traducirBoolean(adn.sentir, "[M].{4,13}[IV][EV]");
		cazar= adn.traducirBoolean(adn.cazar, "..[PAG][A][L].{4,10}[HKR]...");
		escapar = adn.traducirBoolean(adn.escapar, ".....[QE].{2,15}[RKH][C]......");
		resistenciaATB= adn.traducirBoolean(adn.resistenciaATB, "......[GAP]G..{2,12}[IMLV][IMLV]....");
		
				
		
		carniI = (int) adn.traducirMagnitud(this, adn.predador, "M");
		sentI = (int) adn.traducirMagnitud(this,adn.sentir, "M");
		cazarI = (int) adn.traducirMagnitud(this,adn.cazar, "M");
		escapI = (int) adn.traducirMagnitud(this,adn.escapar, "M" );
		resistenciaI=(int)adn.traducirMagnitud(this, adn.resistenciaATB, "M");
		
			
		tiempoMitosis= longevidad/3;
		tempOptima = toleranciaTemp-3f;

       
       
        //limita el tamaño menos
        
        if(alto>1 && alto <=2){ alto=2;}
    	if(ancho>1 && ancho <=2){ ancho=2;}
		
   
		capacidad = (int) (ancho*alto);
		biomasa=capacidad;
				
		
		if(energia>capacidad){energia=capacidad;}
		if(biomasa>capacidad){biomasa=capacidad;}
		
	
		int indexcolor=0;
		
		if(color==0){indexcolor=11;}
		if(color>0 && color>= 76){indexcolor=0;}
		if(color>76 && color<= 152){indexcolor=1;}
		if(color>152 && color<= 228){indexcolor=2;}
		if(color>228 && color<= 304){indexcolor=3;}
		if(color>304 && color<= 380){indexcolor=4;}
		if(color>380 && color<= 456){indexcolor=5;}
		if(color>456 && color<= 532){indexcolor=6;}
		if(color>532 && color<= 608){indexcolor=7;}
		if(color>608 && color<= 684){indexcolor=8;}
		if(color>684 && color<= 760){indexcolor=9;}
		if(color>760){indexcolor=10;}
		
		
		
		
		imagen = new Sprite(m.textura_organismos.getRegions().get(indexcolor));
		imagen.setPosition(this.posicion.x,this.posicion.y);
		imagen.setSize(ancho, alto);
		
		imagen2 = new Sprite(m.textura_organismos.getRegions().get(12));
		imagen2.setPosition(this.posicion.x,this.posicion.y);
		imagen2.setSize(ancho, alto);
		
		imagen3 = new Sprite(m.textura_organismos.getRegions().get(13));
		imagen3.setPosition(this.posicion.x,this.posicion.y);
		imagen3.setSize(ancho, alto);
		
		auraATB = new Sprite(m.auraATB);
		auraATB.setPosition(this.posicion.x,this.posicion.y);
		auraATB.setSize(ancho+3, alto+3);
		
		transferido = new Sprite(m.transferido);
		transferido.setPosition(this.posicion.x,this.posicion.y);
		transferido.setSize(ancho+5, alto+5);
		
		
		borde.height= alto;
		borde.width=  ancho;
		
		
	}
	
	
	// permite al programa saber se un organismo es diferente uno de otro
	public String Identificar(Mundo mu){
		
		StringBuffer sb= new StringBuffer();
		
		if(mu.colectColor==true){sb.append(this.color);}
		if(mu.colectarancho==true){sb.append((int)this.ancho*100);}
		if(mu.colectaralto==true){sb.append((int)this.alto*100);}
		if(mu.colectSpeed==true){sb.append((int)this.speed*100);}
		if(mu.colectRadioconsiente==true){sb.append((int)this.radioConsiente*100);}
		if(mu.colectarLongevidad==true){sb.append(this.longevidad);}
		if(mu.colectarTasaMut==true){sb.append(this.tasaMut);}
		if(mu.colectSentir==true){sb.append(this.sentI);}
		if(mu.colectPredador==true){sb.append(this.carniI);}
		if(mu.colectEscapar==true){sb.append(this.escapI);}
		if(mu.colectCazar==true){sb.append(this.cazarI);}
		if(mu.colectarTemp==true){sb.append((int)this.toleranciaTemp*100);}
		if(mu.colectarResistencia==true){sb.append(this.resistenciaI);}
		
		
		return sb.toString();
		
		
	}
	
	public void verOrganismo(SpriteBatch sb){
		
		sb.begin();
		imagen.draw(sb);
		if (transferred == true){transferido.draw(sb);}
		if(carnivoro==true){imagen2.draw(sb);}
		if(sentir==true){imagen3.draw(sb);}
		if(resistenciaATB==true){auraATB.draw(sb);}
	
		sb.end();
		
	}
	
	public void verMarcado(ShapeRenderer sr, SpriteBatch sb, BitmapFont f){
	if (marcado==-1){	
        sr.begin(ShapeType.Rectangle);
		
		sr.setColor(Color.GREEN);
		sr.rect( borde.x-2,borde.y-2, borde.width+4,borde.height+4);
		sr.end();	
		
		sb.begin();
		f.setColor(Color.GREEN);
		f.draw(sb, nombre, posicion.x+ancho+5,posicion.y+(alto)+5);
		f.draw(sb, ""+format.format(energia), posicion.x+ancho+5,posicion.y);
		sb.end();
				
	}}
	

    
    public void verBorde(ShapeRenderer sr){
		
		sr.begin(ShapeType.Rectangle);
		
		sr.setColor(Color.CYAN);
		sr.rect( borde.x,borde.y, borde.width,borde.height);
		sr.end();	
		
		
	}
	

	
	public void update() {
		
	
		
	if(alto<=1){ morir();}
	if(ancho<=1){morir();}
	
	if(toleranciaTemp< m.temperatura){morir();}
	if(m.temperatura< toleranciaTemp-6){morir();}
	
	if(m.antibiotico==1){ if (resistenciaATB==false) {morir();}}
	
	if(m.pausaGame==1){	
	metabolismo();
	contadorTiempo();
	dividirse();
	apoptosis();
	if (sentir==true){escannearObjetos();}
	if (cazar==true){cazarObjetos();}
	if (escapar==true){escapar();}}
		
		
	posicion.add(Gdx.graphics.getDeltaTime()*(direccion.x)*speed, Gdx.graphics.getDeltaTime()*(direccion.y)*speed);
	
	
	imagen.setPosition(posicion.x,posicion.y);
	imagen2.setPosition(posicion.x,posicion.y);
	imagen3.setPosition(posicion.x,posicion.y);
	auraATB.setPosition(posicion.x-1.5f,posicion.y-1.5f);
	transferido.setPosition(posicion.x-2.5f,posicion.y-2.5f);
	
	borde.x=posicion.x;
	borde.y=posicion.y;
	
	//direccion.x= (float) Math.sqrt((this.SPEED*this.SPEED) - (direccion.y*direccion.y));
		
	if(posicion.x<=0)            {posicion.x = 1;              direccion.x=direccion.x*(-1);}
	if(posicion.x>=m.ancho-ancho) {posicion.x = m.ancho-ancho; direccion.x=direccion.x*(-1);}
	
	if(posicion.y<=0)            {posicion.y = 1;              direccion.y=direccion.y*(-1);}
	if(posicion.y>=m.alto-50) {posicion.y = m.alto-51;   direccion.y=direccion.y*(-1);}
	//System.out.println("Speed " + SPEED);
	
	}
	
	public void metabolismo(){
		
		if(deltaTime()>msecondTime(100)){
			
			float movX= direccion.x*speed;
			float movY= direccion.y*speed;
			
			float movh= (float) Math.sqrt((movX*movX)+(movY*movY));
		
		cuadradoTemp= (tempOptima-m.temperatura)*(tempOptima-m.temperatura);	
		
		//System.out.println(cuadradoTemp);
			
		if(cuadradoTemp<=1){	
			energia =  energia - (float)(ancho*alto)/500f - (0.5f*(float)(ancho*alto)*(movh*movh)/400000f)*m.zoom;
			
		
		}
			
		if(cuadradoTemp>1){	
		
			energia =  (float) energia - (float)((ancho*alto)/500f)*cuadradoTemp - (0.5f*(float)(ancho*alto)*(movh*movh)/400000f)*m.zoom*cuadradoTemp;
			
		//System.out.println((float)(ancho*alto)/10000f*cuadradoTemp);
			
		}
			setTime();
		}
		
		if (energia <= 0){
					
			morir();
	
		        }}
	
	 
	 
	public void escannearObjetos(){
		
	if(sentir==true){	
		for(int i=0 ;i< aObjetos.size;i++){ aObjetos.removeIndex(i);}//limpia la lista par un nuevo escaneo
		for(int i=0 ;i< aPredadores.size;i++){ aPredadores.removeIndex(i);}//limpia la lista par un nuevo escaneo
		
		if(carnivoro==false){			
				
			for(Qenergia qe : m.aqe){
				
				if (qe.visible==true){
										
					float qX= qe.posicion.x;
					float qY= qe.posicion.y;
					
					float Dx= posicion.x-qX;
					float Dy= posicion.y -qY;
					
					float h= (float) Math.sqrt((Dy*Dy)+(Dx*Dx));
					
					if(h<radioConsiente){aObjetos.add(new Vector2(qX,qY));}
					}}}
				
		if(carnivoro==true){			
			
			for(int i=0; i< m.aorg.size;i++){
				Organismo or = m.aorg.get(i);
				
									 
				if(this != or && !identificador.equals(or.identificador) && capacidad > or.capacidad){
					
				//if (!or.getIdentificador().equals(or2.getIdentificador())){
										
					float qX= or.posicion.x;
					float qY= or.posicion.y;
					
					float Dx= posicion.x-qX;
					float Dy= posicion.y -qY;
					
					float h= (float) Math.sqrt((Dy*Dy)+(Dx*Dx));
					
					if(h<radioConsiente){aObjetos.add(new Vector2(qX,qY));}
					
			//	System.out.println("presa detectada");	
				
				}}}}
		
		//detectar peligro
		for(int i=0; i< m.aorg.size;i++){
			Organismo or = m.aorg.get(i);
				 
			if (this != or && or.carnivoro==true && capacidad < or.capacidad){			
												
				float qX= or.posicion.x;
				float qY= or.posicion.y;
				
				float Dx= posicion.x-qX;
				float Dy= posicion.y -qY;
				
				float h= (float) Math.sqrt((Dy*Dy)+(Dx*Dx));
				// System.out.println(adn.getSize());
				if(h<radioConsiente){aPredadores.add(new Vector2(qX,qY));}
			//	System.out.println("predadores detectados: ");
				}}}
		
		
	//}
		
	
	
	public void escapar(){
		
		if(aPredadores.size>0){
			
            for(Vector2 v2 : aPredadores){
														
					float qX= v2.x;
					float qY= v2.y;
					
					float Dx= posicion.x-qX;
					float Dy= posicion.y -qY;
					
					float h= (float) Math.sqrt((Dy*Dy)+(Dx*Dx));
					
					 
				float cosX=Dx/h;
				float senY=Dy/h;
					
				float fh= 100/(h);
					
//					float fx= cosX*fh;
//					float fy= senY*fh;
//					
//					float ax=fx/1000;
//					float ay=fy/1000;
					
				direccion.x= direccion.x + (cosX*fh/1);
				direccion.y= direccion.y + (senY*fh/1);
					
				
					
//			System.out.println("Objetos vistos: "+ aObjetos.size);
			
					}}
			if(direccion.x>10){direccion.x=10;}
			if(direccion.x<-10){direccion.x=-10;}
			if(direccion.y>10){direccion.y=10;}
			if(direccion.y<-10){direccion.y=-10;}
		//	System.out.println("dirX: "+direccion.x + " dirY: "+ direccion.y);
	}
		
		
	
		
	public void cazarObjetos(){
		
	if((100*biomasa)/capacidad<80){
		
		if(aObjetos.size>0){
			
             for(Vector2 v2 : aObjetos){
														
					float qX= v2.x;
					float qY= v2.y;
					
					float Dx= posicion.x-qX;
					float Dy= posicion.y -qY;
					
					float h= (float) Math.sqrt((Dy*Dy)+(Dx*Dx));
					
					 
				float cosX=Dx/h;
				float senY=Dy/h;
					
				float fh= 100/(h);
					
//					float fx= cosX*fh;
//					float fy= senY*fh;
//					
//					float ax=fx/1000;
//					float ay=fy/1000;
					
				direccion.x= direccion.x - (cosX*fh/1);
				direccion.y= direccion.y - (senY*fh/1);
					
				
					
//			System.out.println("Objetos vistos: "+ aObjetos.size);
			
					}}
			if(direccion.x>10){direccion.x=10;}
			if(direccion.x<-10){direccion.x=-10;}
			if(direccion.y>10){direccion.y=10;}
			if(direccion.y<-10){direccion.y=-10;}
		//	System.out.println("dirX: "+direccion.x + " dirY: "+ direccion.y);
	}}
	public void morir(){
		
	direccion.x=0;
	direccion.y=0;
		
		for(int i=0;i<m.aqe.size;i++){
				
			Qenergia qe = m.aqe.get(i);
			float posx=0;
			float posy=0;
			if(qe.visible==false ){
							
			if (biomasa >m.Qbiomasa){
				
			    posx=(float) (Math.random()*(posicion.x-(posicion.x-50))+(posicion.x-50));	
			    posy= (float) (Math.random()*(posicion.y-(posicion.y-50))+(posicion.y-50));		
				qe.visible=true;		
			//	qe.setMasa(m.Qbiomasa);
				qe.posicion=new Vector2(posx, posy);
				qe.update();
				
				
				biomasa = biomasa- qe.masa;
			
			
			}
			
			if ( biomasa <= m.Qbiomasa){	
				
				 posx=(float) (Math.random()*(posicion.x-(posicion.x-50))+(posicion.x-50));	
				 posy= (float) (Math.random()*(posicion.y-(posicion.y-50))+(posicion.y-50));	
				
				qe.visible=true;  
			//	qe.setMasa(biomasa);
				qe.posicion=new Vector2(posx, posy);
				qe.update();
				
				biomasa = 0;			}
			
			if(biomasa<=0){i=m.aqe.size;}
			
			
			
			
			}} m.aorg.removeValue(this, true);
			
			
			// System.out.println("mori"+ m.aorg.size);
		
	}
			
		
	
	public void apoptosis(){
	
		if(segundos>=longevidad/1000){
			
			morir();
			
		}
				
		
	}
	
	public void dividirse(){
		
if(segundos>= tiempoMitosis/1000 && m.aorg.size<m.maximo){
	
	if((100*biomasa)/capacidad > 50){
	    int biom = biomasa;
		int bio=  biom/2;
		int bio2= biom-bio;
		float ener= energia/2;
		float ener2= energia-ener;
		
	 Genoma gen2 = new Genoma();//nuevo gen para las celulas hijas
	 Genoma gen = new Genoma();//nuevo gen para las celulas hijas
	//el nuevo gen sale de copiar el gen de la celula madre
	//  System.out.println(adn.getSize());
	 if(m.mutarColor==true){ gen2.color = new StringBuffer(replicar((this.adn.color)));}
	 if(m.mutarSize==true){gen2.ancho =new StringBuffer(replicar((this.adn.ancho)));}
	 if(m.mutarSize==true){gen2.alto =new StringBuffer(replicar((this.adn.alto)));}
	if(m.mutarSpeed==true){ gen2.speed= new StringBuffer(replicar((this.adn.speed)));}
	if(m.mutarTemp==true){ gen2.toleranciaTemp= new StringBuffer(replicar((this.adn.toleranciaTemp)));}
	 if(m.mutarPredador==true) {gen2.predador =new StringBuffer(replicar((this.adn.predador)));}
	if(m.mutarSentir==true){ gen2.sentir = new StringBuffer(replicar((this.adn.sentir)));}
	if(m.mutarCazar==true){ gen2.cazar = new StringBuffer(replicar((this.adn.cazar)));}
	if(m.mutarEscapar==true){ gen2.escapar = new StringBuffer(replicar((this.adn.escapar)));}
	if(m.mutarRadioconsiente==true){gen2.radioConsiente= new StringBuffer(replicar(this.adn.radioConsiente));}
	if(m.mutarTasaMut==true){gen2.tasaMutacion= new StringBuffer(replicar(this.adn.tasaMutacion));}
	if(m.mutarLongevidad==true){gen2.longevidad= new StringBuffer(replicar(this.adn.longevidad));}
	if(m.mutarResistencia==true){gen2.resistenciaATB= new StringBuffer(replicar(this.adn.resistenciaATB));}
	
	 if(m.mutarColor==false){ gen2.color = new StringBuffer(adn.color.toString());}
	 if(m.mutarSize==false){gen2.ancho = new StringBuffer(adn.ancho.toString());}
	 if(m.mutarSize==false){gen2.alto = new StringBuffer(adn.alto.toString());}
	 if(m.mutarTemp==false){ gen2.toleranciaTemp= new StringBuffer(adn.toleranciaTemp.toString());}
	if(m.mutarSpeed==false){ gen2.speed= new StringBuffer(adn.speed.toString());}
	 if(m.mutarPredador==false) {gen2.predador= new StringBuffer(adn.predador.toString());}
	if(m.mutarSentir==false){ gen2.sentir = new StringBuffer(adn.sentir.toString());}
	if(m.mutarCazar==false){ gen2.cazar = new StringBuffer(adn.cazar.toString());}
	if(m.mutarEscapar==false){ gen2.escapar = new StringBuffer(adn.escapar.toString());}
	if(m.mutarRadioconsiente==false){gen2.radioConsiente= new StringBuffer(adn.radioConsiente.toString());}
	if(m.mutarTasaMut==false){gen2.tasaMutacion= new StringBuffer(adn.tasaMutacion.toString());}
	if(m.mutarLongevidad==false){gen2.longevidad= new StringBuffer(adn.longevidad.toString());}
	if(m.mutarResistencia==false){gen2.resistenciaATB= new StringBuffer(adn.resistenciaATB.toString());}
	
	 if(m.mutarColor==true){ gen.color = new StringBuffer(replicar((this.adn.color)));}
	 if(m.mutarSize==true){gen.ancho =new StringBuffer(replicar((this.adn.ancho)));}
	 if(m.mutarSize==true){gen.alto =new StringBuffer(replicar((this.adn.alto)));}
	if(m.mutarSpeed==true){ gen.speed= new StringBuffer(replicar((this.adn.speed)));}
	if(m.mutarTemp==true){ gen.toleranciaTemp= new StringBuffer(replicar((this.adn.toleranciaTemp)));}
	 if(m.mutarPredador==true) {gen.predador= new StringBuffer(replicar((this.adn.predador)));}
	if(m.mutarSentir==true){ gen.sentir = new StringBuffer(replicar((this.adn.sentir)));}
	if(m.mutarCazar==true){ gen.cazar = new StringBuffer(replicar((this.adn.cazar)));}
	if(m.mutarEscapar==true){ gen.escapar = new StringBuffer(replicar((this.adn.escapar)));}
	if(m.mutarRadioconsiente==true){gen.radioConsiente= new StringBuffer(replicar(this.adn.radioConsiente));}
	if(m.mutarTasaMut==true){gen.tasaMutacion= new StringBuffer(replicar(this.adn.tasaMutacion));}
	if(m.mutarLongevidad==true){gen.longevidad= new StringBuffer(replicar(this.adn.longevidad));}
	if(m.mutarResistencia==true){gen.resistenciaATB= new StringBuffer(replicar(this.adn.resistenciaATB));}
	
	 if(m.mutarColor==false){ gen.color = new StringBuffer(adn.color.toString());}
	 if(m.mutarSize==false){gen.ancho = new StringBuffer(adn.ancho.toString());}
	 if(m.mutarSize==false){gen.alto = new StringBuffer(adn.alto.toString());}
	if(m.mutarSpeed==false){ gen.speed= new StringBuffer(adn.speed.toString());}
	 if(m.mutarTemp==false){ gen2.toleranciaTemp= new StringBuffer(adn.toleranciaTemp.toString());}
	 if(m.mutarPredador==false) {gen.predador= new StringBuffer(adn.predador.toString());}
	if(m.mutarSentir==false){ gen.sentir = new StringBuffer(adn.sentir.toString());}
	if(m.mutarCazar==false){ gen.cazar = new StringBuffer(adn.cazar.toString());}
	if(m.mutarEscapar==false){ gen.escapar = new StringBuffer(adn.escapar.toString());}
	if(m.mutarRadioconsiente==false){gen.radioConsiente= new StringBuffer(adn.radioConsiente.toString());}
	if(m.mutarTasaMut==false){gen.tasaMutacion= new StringBuffer(adn.tasaMutacion.toString());}
	if(m.mutarLongevidad==false){gen.longevidad= new StringBuffer(adn.longevidad.toString());}
	if(m.mutarResistencia==false){gen.resistenciaATB= new StringBuffer(adn.resistenciaATB.toString());}
	
	
	// System.out.println(gen2.getSize());
	 
	 Vector2 pos = new Vector2( (float) posicion.x+((ancho)+3)/2 , (float) posicion.y );
	 Vector2 dir = new Vector2( (float) Math.random()*20 , (float)Math.random()*20);
	 if(dir.x<10){dir.x=dir.x*(-1);}
	 if(dir.x>10){dir.x=dir.x-10;}
	 if(dir.y<10){dir.y=dir.y*(-1);}
	 if(dir.y>10){dir.y=dir.y-10;}
	 
	 StringBuffer sb = new StringBuffer(nombre.toString());
	 StringBuffer sb2= new StringBuffer(nombre.toString());
	
	 Organismo or = new Organismo(gen, pos, sb, m );
	 or.direccion= dir;
	 or.energia= ener;
	 or.biomasa=bio;
	 or.marcado = 1*marcado;
	 or.transferred = transferred;
	 m.aorg.add( or);
	
	Vector2 pos2 = new Vector2( (float) posicion.x-((ancho)+3)/2 , (float) posicion.y );
	 Vector2 dir2 = new Vector2( (float) Math.random()*20 , (float)Math.random()*20);
	 if(dir2.x<10){dir2.x=dir2.x*(-1);}
	 if(dir2.x>10){dir2.x=dir2.x-10;}
	 if(dir2.y<10){dir2.y=dir2.y*(-1);}
	 if(dir2.y>10){dir2.y=dir2.y-10;}
	 Organismo or2 = new Organismo(gen2, pos2,sb2, m);
	 or2.direccion= dir2;
	 or2.energia= ener2;
	 or2.biomasa=bio2;
	 or2.marcado = 1*marcado;
	 or2.transferred = transferred;
	 
	m.aorg.add( or2 );
	
	if (!this.identificador.equals(or.identificador)){
		
	//	System.out.println("nombre antes: "+ or.nombre);
//		
		or.nombre.append(letras[m.index]);
			
		m.index++;
		//System.out.println("nombre despues: " + or.nombre+ "index"+ index);
		
		if (m.index>25){m.index=0;}
//		
	}
	
	if (!this.identificador.equals(or2.identificador)){
		
				
			or2.nombre.append(letras[m.index]);
			
			m.index++;
					
			if (m.index>25){m.index=0;}
//			
		}
	
	m.aorg.removeValue(this, true);
	
		}}
			
	}
	
	// mide los segundos transcurrido desde su creación
	
		public void contadorTiempo(){
			
			if(delta2Time()>msecondTime(1000)){
				
				segundos=segundos+1;
			//System.out.println(segundos + " tiempo mitosis "+ tiempoMitosis);	
				
				setDelta();}
		}
			
	// mide el tiempo transcurrido desde el unltimo set
	
		public long deltaTime(){return TimeUtils.nanoTime() -delta;}	
		public long edadTime(){return TimeUtils.nanoTime() -edad;}
	    public long delta2Time(){return TimeUtils.nanoTime() -delta2;}
// anota el tiempo de juego transcurrido en el momento que se invoca un evento		
	
	
    public void setTime(){ delta = TimeUtils.nanoTime();}
    public void setEdad(){edad = TimeUtils.nanoTime();}
	public void setDelta(){ delta2 = TimeUtils.nanoTime();}
	
	// convierte de ms a nanosegundos para mas comodidad	
	public long msecondTime(long ms){
		
		return ms*1000000;}
	
	
	public String replicar(StringBuffer str){
	
	//System.out.println(str);
	
	
	
	for(int ei=0; ei<str.length(); ei++){//se recorre toada la longitud edl gen letra por letra
		
		//obtenemos la letra de una posicion determinada	
		base = str.charAt(ei);

		//tiramos los dados	
		int a=0;
		if(mutar==true){ a= (int) (Math.random()*tasaMut);}
		if(mutar==false){a=100;}
	   
		if(a <= 1){//se produce una mutacion
		
		     int b = (int) (Math.random()*1000);
	
		if(b>10){   
			//sustitucion
		  int c = (int) (Math.random()*40);
		  if(c<=10){c=0;}
		  if(c>10 && c<=20){c=1;}
		  if(c>20 && c<=30){c=2;}
		  if(c>30 && c<=40){c=3;}
		  
		  switch (c) {
		 	case 0: str.setCharAt(ei,'a');
		 	break;
		 	case 1: str.setCharAt(ei,'c');
		 	break;
		 	case 2: str.setCharAt(ei,'g');
		 	break;
			case 3: str.setCharAt(ei,'t');
			break;
				 
		 	}}
		if (b<=10){ //                        delecion o insercion
			int e = (int) (Math.random()*81);
			  if(e>0 &&  e<=10){e=0;}
			  if(e>10 && e<=20){e=1;}
			  if(e>20 && e<=30){e=2;}
			  if(e>30 && e<=40){e=3;}
			  if(e>40 && e<=50){e=4;}
			  if(e>50 && e<=60){e=5;}
			  if(e>60 && e<=70){e=6;}
			  if(e>70 && e<=80){e=7;}
			  if (e==0){e=8;}
			  if (e==81){e=9;}
			
			switch (e) {
			case 0: str.insert(ei,"a");
			break;
			case 1: str.insert(ei,"c");
			break;
			case 2: str.insert(ei,"g");
			break;
			case 3: str.insert(ei,"t");
			break;
			case 4: str.deleteCharAt(ei);
			break;
			case 5: str.deleteCharAt(ei);
			break;
			case 6: str.deleteCharAt(ei);
			break;
			case 7: str.deleteCharAt(ei);
			break;
			case 8 : str.replace(0, str.length(), " "); ei=str.length();
			break;
			case 9 : str.append("aaaaaa"+str);
			break;
		} }
		
		}
	
	}
	
	
	//System.out.println(str);
	return str.toString();
	}
	
	@Override
	public String toString() {
	    return nombre.toString();
	}

	@Override
	public int compareTo(Organismo arg0) {
		{
		    // The order of two Organism's is determined by the name.
		    String p2Name = arg0.nombre.toString();
		    int result = nombre.toString().compareTo(p2Name); 
		    if (result < 0)
		      return 1;         // or any negative int
		    else if (result > 0)
		      return -1;          // or any positive int
		    else
		      return 0;
		  }
		
	}

	

	
	
	
}
