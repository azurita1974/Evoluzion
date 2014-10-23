
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





import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Mundo implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected Evoluzion ev;//clase prinsipal
MenuInicio mi;         // menu inicio
Texto tx;
Array<Senergia> ase;   //lista de energia verde
Array<Qenergia> aqe;   //lista de energia roja (biomasa)
Array<Organismo> aorg;  //lista de organismos
Array<Organismo> aEspesies, aEspesiesTotales; //listas de organismos colectados segun especies

float ancho, alto, ratio;   //dimenciones de la pantalla
                //
float deltaX=0;             //delta de x entre dos individuos
float deltaY=0;             //delta de y entre dos individuos
float medSpeed;             //se usa para el calcula de velosidad media
float temperatura= 25;
float medTem;
float eficiencia=1;
             //suma de toda la masa libre

int dias, horas, minutos, minutos2 ,minutos3, segundos, segundos2,segundos3,segundos4,segundos5; //anota el paso del tiempo
long edad, delta, delta2,delta3, tiempo;                         // mide diferencia de tiempo entre una accion y la siguiente 
int tiempoMaximo=0;
int Masatotal;
int tiempoMuestreo;
int tiempoCatastrofe;
int tiempoATB;
int cantidadMuestras;
int contadorMuestreo;
int Senergia;
int Qbiomasa;
int numOrg;
int numSen;
int numQen;
int EnRe;
int BioRe;
float medSize;
int medMut;
int medLon;
int pausaGame=1;// 1= play -1=stop
int zoom=2;
int index=0;
int maximo= 1000; //numero maximo de organismos
int ingles = -1; //-1=false 1=true

float minStar1,minStar2;
float TempFinal1, TempFinal2,tempXSecond1,tempXSecond2;
float deltaTime1,deltaTime2;

int antibiotico= -1; //1= true -1= false

boolean colectarancho=false;
boolean colectaralto=false;
boolean colectSpeed=false;
boolean colectColor=false;
boolean colectSentir=false;
boolean colectPredador=false;
boolean colectCazar=false;
boolean colectEscapar=false;
boolean colectRadioconsiente=false;
boolean colectarTasaMut=false;
boolean colectarLongevidad=false;
boolean colectarTemp=false;
boolean colectarResistencia=false;

boolean mutarColor=true;
boolean mutarSize= true;
boolean mutarSpeed= true;
boolean mutarSentir=true;
boolean mutarPredador=true;
boolean mutarCazar=true;
boolean mutarEscapar=true;
boolean mutarRadioconsiente=true;
boolean mutarTasaMut=true;
boolean mutarLongevidad=true;
boolean mutarTemp=true;
boolean mutarResistencia=true;

boolean cargarPoblacion= false;
boolean moverMasa;

String ruta,poblacion;
StringBuffer linea, orgNombre;             //se usa para archivar los resultado
String nombre;
TextureAtlas textuRA_ENER, textura_ORG,textura_organismos;   // contine las imagenes

Archivar f_datos, f_genes, f_arbol, f_proteoma, f_poblacion, f_mutantes;  //para archivar
NumberFormat format = new DecimalFormat("0.000");





public Mundo(Evoluzion ev,String ruta,String nombre,String poblacion, int numOrg, int numSen, int numQen, int Senergia, int Qbiomasa, boolean cargarPoblacion, boolean moverMasa, String genesPedidos, int ingles){
	
	this.ev=ev;
	
	
	this.numOrg=numOrg;
	this.numSen=numSen;
	this.numQen=numQen;
	this.Senergia=Senergia;
	this.Qbiomasa=Qbiomasa;
	this.cargarPoblacion=cargarPoblacion;
	this.moverMasa= moverMasa;
	this.nombre=nombre;
	this.ruta=ruta;
	this.poblacion=poblacion;
	this.ingles = ingles;
	
	tx = new Texto();
	if(ingles==1){ tx.setIngles();}
	if(ingles== -1){tx.setEspanol();}
	
	orgNombre= new StringBuffer("a");
	
	
	
	// tamaño de la pantalla
			ancho = Gdx.graphics.getWidth();
			alto = Gdx.graphics.getHeight();
			
	//listas
			ase = new Array<Senergia>();
			aqe = new Array<Qenergia>();
			aorg = new Array<Organismo>();
			aEspesies= new Array<Organismo>();
			aEspesiesTotales = new Array<Organismo>();
			
	// set time to 0	
			setDelta();
			setDelta2();
			setDelta3();
			setTiempo();
			
	linea = new StringBuffer();// used to write text in files
		
			textuRA_ENER = new TextureAtlas("data/energia.pack");
			textura_ORG = new TextureAtlas("data/objetos.pack");
			textura_organismos= new TextureAtlas("data/organ.pack");
			
				
			
	//agregar cuantos de energia solar
			
		float x=0;
		float y=alto;	
	 for (int i =0; i < this.numSen; i++){
		 		
		 Vector2 pos = new Vector2((float)Math.random()*ancho, (float)Math.random()*alto);
				 
		 x=x+20;
		 if(x>= ancho){x=0; y=y-20;}
				 
		 ase.add(new Senergia (pos, this));}	
			
    //agregar materia
	 
	 for (int i =0; i < numQen; i++){
		 
		 Vector2 pos = new Vector2((float)(Math.random()*(ancho-5+1))+5, (float)Math.random()*alto); 
	
		 aqe.add(new Qenergia (pos,moverMasa, this));	}
	 
 //agregar materia invisible para usarla en el balanse de masa
	 
	 for (int i =0; i < numQen/3; i++){
		 
		 Vector2 pos = new Vector2((float)(Math.random()*(ancho-5+1))+5, (float)Math.random()*alto); 
		
		 Qenergia qe = new Qenergia (pos,moverMasa, this);
		 qe.visible=false;
		 aqe.add(qe);	}
	 
	 
   //colecta la primera especie en la lista


for(Organismo or: aorg){
	
	boolean igual=false;
	
	
	String  id = or.identificador;
	for(Organismo or2: aEspesies){
		
		if(id.equals(or2.nombre)){ igual=true;}
		
	}if(igual==false){aEspesies.add(or);}
	
	}

 for(Organismo or: aorg){
	
	boolean igual=false;
	String id = or.identificador;
	
	for(Organismo or2: aEspesiesTotales){
		
	if(id.equals(or2.nombre)){ igual=true;}
		
	}if(igual==false){aEspesiesTotales.add(or);}
	
	}
 

 //manejamos los archivos
    f_datos = new Archivar();
	f_genes = new Archivar();
	f_proteoma = new Archivar();
    f_arbol = new Archivar();
    f_poblacion = new Archivar();
    f_mutantes = new Archivar();
   
	
}

// end of cronstructor metod


// metods

public void agregarPrimerosOrg( int num){
	
	for (int i =0; i < num; i++){
		 
		 Vector2 pos = new Vector2( (float) Math.random()*ancho , (float)Math.random()*alto); 
		 Vector2 dir = new Vector2( (float) Math.random()*20 , (float)Math.random()*20);
		 if(dir.x<10){dir.x=dir.x*(-1);}
		 if(dir.x>10){dir.x=dir.x-10;}
		 if(dir.y<10){dir.y=dir.y*(-1);}
		 if(dir.y>10){dir.y=dir.y-10;}
	
		 aorg.add(new Organismo(new Genoma(), pos, orgNombre , this));
		 aorg.get(i).direccion= dir;
	
}	
	int o = BiomasaTotal()/Qbiomasa;
	//System.out.println(BiomasaTotal());
	for (int i =0; i < o; i++){
		 
		 Qenergia qe = aqe.get(i);
			qe.visible=false; 
			
			}
	
	Masatotal=MateriaLibre()+BiomasaTotal();		
		
}



//methods for quantifying values ​​or do Statistics

public float temOptimaMedia(){
	medTem=0;
	int size = aorg.size;
	if(size>0){
		for(int i = size-1;i>=0;i--){
		medTem= medTem+ aorg.get(i).tempOptima;}}
	if(size==0){size=1;}	
	
	return medTem/size;}

public int cantidadPredadores(){
	int carnivoros=0;
	int size=aorg.size;
for(int i = size-1; i>=0;i--){
	if(aorg.get(i).carnivoro==true){ carnivoros= carnivoros+1;}}
	return carnivoros;}

public int cantidadResistentes(){
	int resistente=0;
	int size=aorg.size;
for(int i = size-1; i>=0;i--){
	if(aorg.get(i).resistenciaATB==true){ resistente= resistente+1;}}
	return resistente;}

public float velocidadMedia(){
	medSpeed=0;
	int size = aorg.size;
	if(size>0){
		for(int i = size-1;i>=0;i--){
		medSpeed= medSpeed+ aorg.get(i).speed;}}
	if(size==0){size=1;}	
	
	return medSpeed/size;}

public int tasaMutMedio(){
	medMut=0;
	int size = aorg.size;
	if(size>0){
		for(int i = size-1;i>=0;i--){
		medMut= medMut+ aorg.get(i).tasaMut;}}
	if(size==0){size=1;}
	
	return (medMut/size)/2;}

public float longevidadMedio(){
	medLon=0;
	int size = aorg.size;
	if(size>0){
		for(int i = size-1;i>=0;i--){
		medLon= medLon+ aorg.get(i).longevidad;}}
	if(size==0){size=1;}
	
	return (float)(medLon)/1000/size;}

public float tamanoMedio(){
	medSize=0;
	int size = aorg.size;
	if(size>0){
		for(int i = size-1;i>=0;i--){
		medSize= medSize+ aorg.get(i).capacidad;}}
	if(size==0){size=1;}
	
	return medSize/size;}

public int MateriaLibre(){
	int materia =0;
	int size= aqe.size;
	for(int i=size-1; i>=0; i--){
		if (aqe.get(i).visible==true){materia =  (int) (materia+ aqe.get(i).masa);}}
	return materia;}

public int BiomasaTotal(){
    int biomasaTotal=0;
	int size= aorg.size;
	for(int i = size-1; i>=0; i--){
		biomasaTotal= (int) (biomasaTotal+ aorg.get(i).biomasa);}
	return biomasaTotal;}



public int BioenergiaTotal(){
	int energiaTotal=0;
	int size= aorg.size;
for(int i=size-1;i>=0; i--){
		energiaTotal = (int) (energiaTotal+ aorg.get(i).energia);}
	return energiaTotal;
	}

//to correct rounding errors in the total mass of the system, add or remove mass

public void chequerBalance(){
	
	int a = Masatotal-(MateriaLibre()+BiomasaTotal());
	if (a> Qbiomasa){
		int size=aqe.size;
	    for(int i= size-1; i>=0; i--){
		if(aqe.get(i).visible==false){aqe.get(i).visible=true; i=0;} }}
	
	if (a> Qbiomasa){
		int size=aqe.size;
	    for(int i= size-1; i>=0; i--){
		if(aqe.get(i).visible==false){aqe.get(i).visible=true; i=0;} }}
	
		
	
	if (a< Qbiomasa*(-1)){
		int size=aqe.size;
	    for(int i= size-1; i>=0; i--){
		if(aqe.get(i).visible==true){aqe.get(i).visible=false; i=0;} }}	
	
	if (a< Qbiomasa*(-1)){
		int size=aqe.size;
	    for(int i= size-1; i>=0; i--){
		if(aqe.get(i).visible==true){aqe.get(i).visible=false; i=0;} }}
		}
		

//kill 95% of all organism

public void catastrofe(){
	
	int size= aorg.size;
	for(int i=size-1;i>=0; i--){
	
		int e = (int) (Math.random()*10000);
		
		if (e>500){aorg.get(i).morir(); }
		
	}
	
		
}

//metodos para colectar los datos

public void guardarPoblacion(){
	
	try {
		JFileChooser fc = new JFileChooser( );
		fc.setCurrentDirectory(new File (ruta));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("población Total"+"     (pob)",  "pob");
		
		fc.setFileFilter(filter);
		
		 
		  int returnVal = fc.showSaveDialog(fc);
		  
		 if(fc.getFileFilter() == filter){
				
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
//			            File file = fc.getSelectedFile();
					
						f_poblacion.creararchivo(fc.getSelectedFile() + ".pob");
						
						StringBuffer linea = new StringBuffer();
						
						int size= aorg.size;
						
						for(int i = size-1; i>=0; i--){
							Organismo or = aorg.get(i);
							linea.replace(0,linea.length(),"<h>"+or.nombre.toString()+"dX"+or.posicion.x+"dY"+or.posicion.y+"<color>"+or.adn.color+"<ancho>"+or.adn.ancho+"<alto>"+or.adn.alto+"<speed>"+or.adn.speed+"<temp>"+or.adn.toleranciaTemp+"<sentir>"+or.adn.sentir+"<alcance>"+or.adn.radioConsiente+"<cazar>"+or.adn.cazar+"<escapar>"+or.adn.escapar+"<predador>"+or.adn.predador+"<longevidad>"+or.adn.longevidad+"<tasamut>"+or.adn.tasaMutacion+"<atb>"+or.adn.resistenciaATB+"\n" );	
							f_poblacion.escribirArchivo(linea.toString());	
						}
						f_poblacion.cerrarArchivo();
										
					} }
	} catch (HeadlessException e) {
		 JOptionPane.showMessageDialog(null,"error al escribir el archivo");
		e.printStackTrace();
	}}


public void guardarPoblacionMarcada(){
	
	try {
		JFileChooser fc = new JFileChooser( );
		fc.setCurrentDirectory(new File (ruta));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("población Marcada"+"     (pob)",  "pob");
		
		fc.setFileFilter(filter);
		
		 
		  int returnVal = fc.showSaveDialog(fc);
		  
		 if(fc.getFileFilter() == filter){
				
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
//			            File file = fc.getSelectedFile();
					
						f_poblacion.creararchivo(fc.getSelectedFile() + ".pob");
						
						StringBuffer linea = new StringBuffer();
						
						int size= aorg.size;
						
						for(int i = size-1; i>=0; i--){
							Organismo or = aorg.get(i);
						if (or.marcado==-1){	// -1 == true
						linea.replace(0,linea.length(),"<h>"+or.nombre.toString()+"dX"+or.posicion.x+"dY"+or.posicion.y+"<color>"+or.adn.color+"<ancho>"+or.adn.ancho+"<alto>"+or.adn.alto+"<speed>"+or.adn.speed+"<temp>"+or.adn.toleranciaTemp+"<sentir>"+or.adn.sentir+"<alcance>"+or.adn.radioConsiente+"<cazar>"+or.adn.cazar+"<escapar>"+or.adn.escapar+"<predador>"+or.adn.predador+"<longevidad>"+or.adn.longevidad+"<tasamut>"+or.adn.tasaMutacion+"<atb>"+or.adn.resistenciaATB+"\n" );	
						f_poblacion.escribirArchivo(linea.toString());	}
						}
						f_poblacion.cerrarArchivo();
										
					} }
	} catch (HeadlessException e) {
		 JOptionPane.showMessageDialog(null,"error al escribir el archivo");
		e.printStackTrace();
	}}

public void guardarPoblacionNoMaracada(){
	
	try {
		JFileChooser fc = new JFileChooser( );
		fc.setCurrentDirectory(new File (ruta));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("población No Marcada"+"     (pob)",  "pob");
		
		fc.setFileFilter(filter);
		
		 
		  int returnVal = fc.showSaveDialog(fc);
		  
		 if(fc.getFileFilter() == filter){
				
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
//			            File file = fc.getSelectedFile();
					
						f_poblacion.creararchivo(fc.getSelectedFile()+".pob");
						
						StringBuffer linea = new StringBuffer();
						
						int size= aorg.size;
						
						for(int i = size-1; i>=0; i--){
							Organismo or = aorg.get(i);
						if (or.marcado==1){	// 1 = false
							linea.replace(0,linea.length(),"<h>"+or.nombre.toString()+"dX"+or.posicion.x+"dY"+or.posicion.y+"<color>"+or.adn.color+"<ancho>"+or.adn.ancho+"<alto>"+or.adn.alto+"<speed>"+or.adn.speed+"<temp>"+or.adn.toleranciaTemp+"<sentir>"+or.adn.sentir+"<alcance>"+or.adn.radioConsiente+"<cazar>"+or.adn.cazar+"<escapar>"+or.adn.escapar+"<predador>"+or.adn.predador+"<longevidad>"+or.adn.longevidad+"<tasamut>"+or.adn.tasaMutacion+"<atb>"+or.adn.resistenciaATB+"\n" );	
						f_poblacion.escribirArchivo(linea.toString());	
						}}
						f_poblacion.cerrarArchivo();
										
					} }
	} catch (HeadlessException e) {
		 JOptionPane.showMessageDialog(null,"error al escribir el archivo");
		e.printStackTrace();
	}}


public void leerArchivoPoblacion(){
	
		for(Organismo or: aorg){ aorg.removeValue(or,true); }; 
		for(Organismo or: aEspesiesTotales){aEspesiesTotales.removeValue(or, true);}
			 
		 try{

			 FileReader fr = new FileReader(poblacion);
				BufferedReader br = new BufferedReader(fr);
				String linea = null;
				while((linea=br.readLine()) != null){
					
					Genoma gen = new Genoma();
					
					gen.color = new StringBuffer(linea.substring(linea.indexOf("<color>")+7, linea.indexOf("<ancho>")));					
					gen.ancho  = new StringBuffer(linea.substring(linea.indexOf("<ancho>")+7,  linea.indexOf("<alto>")));
					gen.alto  = new StringBuffer(linea.substring(linea.indexOf("<alto>")+6,  linea.indexOf("<speed>")));
					gen.speed  = new StringBuffer(linea.substring(linea.indexOf("<speed>")+7, linea.indexOf("<temp>")));
					gen.toleranciaTemp= new StringBuffer(linea.substring(linea.indexOf("<temp>")+6, linea.indexOf("<sentir>")));
					gen.sentir = new StringBuffer(linea.substring(linea.indexOf("<sentir>")+8,linea.indexOf("<alcance>")));
					gen.radioConsiente = new StringBuffer(linea.substring(linea.indexOf("<alcance>")+9,linea.indexOf("<cazar>")));
					gen.cazar  = new StringBuffer(linea.substring(linea.indexOf("<cazar>")+7, linea.indexOf("<escapar>")));
					gen.escapar  = new StringBuffer(linea.substring(linea.indexOf("<escapar>")+9, linea.indexOf("<predador>")));
					gen.predador = new StringBuffer(linea.substring(linea.indexOf("<predador>")+10,  linea.indexOf("<longevidad>")));
					gen.longevidad = new StringBuffer(linea.substring(linea.indexOf("<longevidad>")+12,  linea.indexOf("<tasamut>")));
					gen.tasaMutacion = new StringBuffer(linea.substring(linea.indexOf("<tasamut>")+9,  linea.indexOf("<atb>")));
					gen.resistenciaATB= new StringBuffer(linea.substring(linea.indexOf("<atb>")+5,  linea.length()));
					Vector2 pos = new Vector2(Float.parseFloat(linea.substring(linea.indexOf("dX")+2, linea.indexOf("dY"))), Float.parseFloat(linea.substring(linea.indexOf("dY")+2, linea.indexOf("<color>"))));
					StringBuffer nombre =  new StringBuffer(linea.substring(linea.indexOf("<h>")+3, linea.indexOf("dX")));
				   
					Organismo or = new Organismo(gen, pos,nombre, this);
				   or.direccion.x= (float) (Math.random()*10);
				   or.direccion.y= (float) (Math.random()*10);
					aorg.add(or);
				}
							
				br.close();
				fr.close();
			 
			
		 }catch(IOException ex){
			 
			 JOptionPane.showMessageDialog(null,"no se puede leer este archivo");
			 
                  for (int i =0; i < numOrg; i++){
				 
				 Vector2 pos = new Vector2( (float) Math.random()*ancho , (float)Math.random()*alto); 
				 
				 Vector2 dir = new Vector2( (float) Math.random()*20 , (float)Math.random()*20);
				 if(dir.x<10){dir.x=dir.x*(-1);}
				 if(dir.x>10){dir.x=dir.x-10;}
				 if(dir.y<10){dir.y=dir.y*(-1);}
				 if(dir.y>10){dir.y=dir.y-10;}
				// System.out.println("x "+ dir.x +" y "+dir.y);
				 aorg.add(new Organismo(new Genoma(), pos,orgNombre, this));	}	 
		
			
			 
			 ex.printStackTrace();}}

	
	
	
	
	
	
//guara las especies que estan en un momento determinado


//colectar mutantes o especies del momento

public void colectorEspesies(){
	
	for (Organismo or: aEspesies){or.cantidad=1;aEspesies.removeValue(or,true);}
	
	for(Organismo or: aorg){
		
		boolean igual=false;
				
		String id = or.nombre.toString();
		for(Organismo or2: aEspesies){
			
			if(id.equals(or2.nombre.toString())){ igual=true; or2.cantidad++;}
			
		}if(igual==false){aEspesies.add(or);}
		
		}
	
	   aEspesies.sort();
	
}
//colectar todas las que aparesen

public void colectorEspesiesTotales(){
	
		
  for(Organismo or: aorg){
		
		boolean igual=false;
		String id = or.nombre.toString();
		
		for(Organismo or2: aEspesiesTotales){
			
		if(id.equals(or2.nombre.toString())){ igual=true;}
			
		}if(igual==false){aEspesiesTotales.add(or);}
		
		}
		
	
	}



public void archivarGenoma(){
if(aorg.size>0){
	
		
	f_genes.escribirArchivo(""+minutos2+":"+segundos+"\n");
	int size= aEspesies.size;
	for(int i = size-1; i>=0; i--){
		   Organismo  or= aEspesies.get(i);
		linea.replace(0, linea.length(), "");   
		linea.append(">"+or.nombre.toString() +"\n"+ or.adn.colectarGenoma(this)+"\n\n");
			
	f_genes.escribirArchivo(linea.toString());}
	f_genes.escribirArchivo("\n\n");
	}}

public void archivarProteoma(){
if(aorg.size>0){
	
	
	f_proteoma.escribirArchivo(""+minutos2+":"+segundos+"\n");
	int size= aEspesies.size;
	for(int i = size-1; i>=0; i--){
		   Organismo  or= aEspesies.get(i);
		linea.replace(0, linea.length(), "");     
		linea.append(">"+or.nombre.toString()+"\n"+ or.adn.colectarProteoma(this)+"\n\n");
			
	f_proteoma.escribirArchivo(linea.toString());}
	f_proteoma.escribirArchivo("\n\n");
	}}

public void archivarFenotipo(){
if(aorg.size>0){
		
	f_mutantes.escribirArchivo(""+minutos2+":"+segundos);
	int size= aEspesies.size;
	for(int i = size-1; i>=0; i--){
		   Organismo  or= aEspesies.get(i);
		   
		linea.replace(0, linea.length(), "");     
		linea.append(" "+","+or.nombre.toString()+","+or.cantidad+"\n");
					
//					tx.alto+": "+or.alto+"//"+
//					tx.ancho+": "+ or.ancho+"//"+
//					tx.velocidad+": "+or.speed+"\n"+
//					tx.color+ ": "+or.color+"//"+
//					tx.fidelidadADNpol+ ": "+ or.tasaMut+"//"+
//					tx.temOptima+": "+ or.tempOptima+" ("+tx.grados+")\n"+
//					tx.longevidad+": "+ or.longevidad/1000+" (seg)//"+
//					tx.alcanceVisual+": "+or.radioConsiente+"//"+
//					tx.genPredador +": "+ or.carnivoro+ "\n"+
//					tx.sentidos+": "+ or.sentir+"//"+
//					tx.buscarComida+ ": "+or.cazar+"//"+
//					tx.escapar+ ": "+ or.escapar+"//"+
//					tx.resistensiaATB+": "+or.resistenciaATB +"\n\n");
			
	f_mutantes.escribirArchivo(linea.toString());}
	//f_fenoma.escribirArchivo();
	}}

//colecta datos para graficar 
public void guardarDatos(){
	
	linea.replace(0, linea.length(), ""+minutos2+":"+segundos+","+aorg.size+","+aEspesies.size+","+ cantidadPredadores()+","+cantidadResistentes()+"," +velocidadMedia()+","+ tamanoMedio()+","+longevidadMedio()+","+tasaMutMedio()+","+temperatura+","+temOptimaMedia()+"\n");
	f_datos.escribirArchivo(linea.toString());
	}

public void tomarMuestras(){
	
	if(segundos2 >= tiempoMuestreo){
	
	colectorEspesies();// colecta las especies o mutante de ese momento	
		
	guardarDatos();  
	archivarGenoma();
	archivarProteoma();
	archivarFenotipo();
		
	segundos2=0;
	}}

public void activarCatastrofe(){
	
	if(segundos3 >= tiempoCatastrofe && tiempoCatastrofe != 0){
		
		catastrofe();
		
		segundos3=0;
	}
	
	
}


public void activarATB(){
	
if(segundos5 == tiempoATB && antibiotico==-1 && tiempoATB != 0){
		
		antibiotico=1;
		
		
	}
	
	
}


public void archivaTodaslasEspecies(){
	
	StringBuffer linea= new StringBuffer();
			
	for(Organismo or: aEspesiesTotales){
	linea.replace(0, linea.length(), "");
	linea.append(">"+or.fechaNacimiento+" "+ or.nombre.toString()+"\n" +or.adn.colectarGenoma(this)+"\n");
	f_arbol.escribirArchivo(linea.toString());}
	
	f_arbol.cerrarArchivo();}

// metodo que actualiza el sitema

public void update(){
	
	if(tiempoMaximo==0){
	if(aorg.size>0 ){
		
	for(Senergia es: ase){es.update();}//mueve los cuantos de luz
	for(Qenergia eq: aqe){eq.update();}//mueve los cuantos de luz
	for(Organismo or: aorg){or.update();}//mueve los organismos	
	
	detectarColiciones();
	chequerBalance();
	activarCatastrofe();
	activarATB();
	colectorEspesiesTotales();
	tomarMuestras();
	termociclar();
	contadorTiempo();
	
	
	}}	
	
	if(tiempoMaximo > 0){
		if(aorg.size>0 && segundos4<tiempoMaximo ){
			
			for(Senergia es: ase){es.update();}//mueve los cuantos de luz
			for(Qenergia eq: aqe){eq.update();}//mueve los cuantos de luz
			for(Organismo or: aorg){or.update();}//mueve los organismos	
			
			detectarColiciones();
			chequerBalance();
			activarCatastrofe();
			activarATB();
			colectorEspesiesTotales();
			tomarMuestras();
			termociclar();
			contadorTiempo();
		
		}}	
}


//method that controls the temperature

public float termociclar(){
	
	//primer control de temperatura
	
	if(deltaTime1<=0 && minStar1!=0 && minutos2 >= minStar1){ temperatura = TempFinal1;}
	
	if(deltaTime1 >0 && minStar1!=0 && minutos2 >= minStar1 && minutos2 < minStar1+deltaTime1){
			
	      if(delta3Time() >msecondTime(1000)){
		
		     temperatura = temperatura+tempXSecond1;
			
			setDelta3();
						
	}}
	
	//segundo control de temperatura
	
    if(deltaTime2<=0 && minStar2!=0 && minutos2 >= minStar2){ temperatura = TempFinal2;}
	
	if(deltaTime2 >0 && minStar2!=0 && minutos2 >= minStar2 && minutos2 < minStar2+deltaTime2){
			
	      if(delta3Time() >msecondTime(1000)){
		
		     temperatura = temperatura+tempXSecond2;
			
			setDelta3();
						
	}}
			
	
	return temperatura;
		
	
}



// metodos que manejan el tiempo de corrida

public void contadorTiempo(){
	
	if(deltaTime()>msecondTime(1000)){
		
		segundos=segundos+1;
		segundos2=segundos2+1;
		segundos3=segundos3+1;
		segundos4=segundos4+1;
		segundos5 = segundos5+1;
				
		if(segundos==60){segundos=0; minutos= minutos+1;minutos2= minutos2+1; minutos3=minutos3+1;}
		
		if(minutos==60){minutos=0; horas= horas+1;}
		
		if(horas==24){horas=0; dias=dias+1;}
		
		setDelta();}
}

//mide el tiempo transcurrido desde el unltimo set

		public long deltaTime(){return TimeUtils.nanoTime() -delta;}
		public long delta2Time(){return TimeUtils.nanoTime() -delta2;}
		public long delta3Time(){return TimeUtils.nanoTime() -delta3;}
		public long tiemoTime(){return TimeUtils.nanoTime() -tiempo;}
	
//anota el tiempo de juego transcurrido en el momento que se invoca un evento		
	
	
 public void setDelta(){ delta = TimeUtils.nanoTime();}
 public void setDelta2(){ delta2 = TimeUtils.nanoTime();}
 public void setDelta3(){ delta3 = TimeUtils.nanoTime();}
 public void setTiempo(){ tiempo = TimeUtils.nanoTime();}
	
	// convierte de ms a nanosegundos para mas comodidad	
	public long msecondTime(long ms){
		
		return ms*1000000;}
	

public void detectarColiciones(){
	
	//organismo toca la energia verde
	
			for(int i=0; i< aorg.size;i++){
				Organismo or = aorg.get(i);
				Rectangle er = or.borde;
				if(or.carnivoro==false){		
				for(int a=0; a< ase.size;a++){
					Senergia se = ase.get(a);
					Rectangle tr = se.borde;
					if(se.visible==true){
					if (er.overlaps(tr)){
												
					or.energia= or.energia + se.energia;
					//se.setEnergia(0);
					se.energia = se.energia-(or.capacidad-or.energia);
					if(se.energia<=0){se.visible=false;}
				
					
					if(or.energia>or.capacidad){or.energia = or.capacidad;};
//					
//					
//					
//					
						
					}}}}}
			//organismo toca la biomasa
			
			for(int i=0; i< aorg.size;i++){
				Organismo or = aorg.get(i);
				Rectangle er = or.borde;
				
			if(or.carnivoro==false){	
				for(int a=0; a< aqe.size;a++){
					Qenergia qe = aqe.get(a);
					Rectangle tr = qe.borde;
					
				if(qe.visible==true){
					
					if (er.overlaps(tr)){
						
					if( or.biomasa< or.capacidad){	
						
	                 or.biomasa= or.biomasa + qe.masa;
					
					qe.posicion = new Vector2(-100,-100);
					qe.visible =false;
				//	qe.setMasa(0);
					
					}
											
					}}}}}
			
			
			//organismo toca los obstaculos
			
//			for(int i=0; i< aorg.size;i++){
//				Organismo or = aorg.get(i);
//				Rectangle er = or.borde;
//				
//				for(int a=0; a< aCaja.size;a++){
//					Caja ca = aCaja.get(a);
//					Rectangle tr = ca.borde;
//					
//					if (er.overlaps(tr)){
//				
//					
//					if ( or.posicion.y + or.alto/2 < ca.posicion.y + ca.alto){ or.direccion.x= or.direccion.x*(-1);}
//					if ( or.posicion.x + or.ancho/2 > ca.posicion.x){ or.direccion.y= or.direccion.y*(-1);}
//				}}}
				
	//organismo toca otro organismo
			
			for(int i=0; i< aorg.size;i++){
				Organismo or = aorg.get(i);
				Rectangle er = or.borde;
				
				for(int a=0; a< aorg.size;a++){
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;
					
											
					if( er.overlaps(er2)){
					if (a != i ){
						
		horizontalTransfer(or, or2);//horizontal transfer of genes			
						
						
		if(or.carnivoro==true && !or.identificador.equals(or2.identificador) && or.capacidad>=or2.capacidad){
			
			EnRe= (int) (or.capacidad-or.energia);
			BioRe = (int) (or.capacidad-or.biomasa);
			
			if( EnRe >= or2.energia && EnRe>0){or.energia=or.energia+ or2.energia;or2.energia=0;}
			if( EnRe <  or2.energia && EnRe>0){or.energia= or.energia+ EnRe; or2.energia= or2.energia-EnRe;}
			
			if( BioRe >= or2.biomasa&& BioRe>0 ){or.biomasa=or.biomasa+ or2.biomasa;or2.biomasa=0;}
			if( BioRe <or2.biomasa  && BioRe>0 ){or.biomasa= or.biomasa+ BioRe; or2.biomasa= or2.biomasa-BioRe;}
								
				}
						
			deltaX=0;
			deltaY=0;
			
			if(or.posicion.x<or2.posicion.x){deltaX=or.posicion.x+or.ancho-or2.posicion.x;}
			if(or2.posicion.x<or.posicion.x){deltaX=or2.posicion.x+or2.ancho-or.posicion.x;}
					
			if( or.posicion.y< or2.posicion.y){ deltaY= or.posicion.y + or.alto-or2.posicion.y;}
			if( or2.posicion.y< or.posicion.y){ deltaY= or2.posicion.y + or2.alto-or.posicion.y;}
			
			if(or.posicion.x==or2.posicion.x){deltaX= 0;}
			if(or2.posicion.y==or.posicion.x){deltaY= 0;}
			
			if(deltaY==0 && deltaX==0){
				
				or.posicion.x= or.posicion.x-(deltaX/2)-1; or2.posicion.x= or2.posicion.x+(deltaX/2)+1;
				or.posicion.y= or.posicion.y-(deltaY/2)-1; or2.posicion.y= or2.posicion.y+(deltaY/2)+1;
			}
			
			
			if(deltaY*deltaY>deltaX*deltaX){
				
			if(or.posicion.x+or.ancho>=or2.posicion.x && or.posicion.x< or2.posicion.x){or.posicion.x= or.posicion.x-(deltaX/2)-1; or2.posicion.x= or2.posicion.x+(deltaX/2)+1;
			
			if(or.direccion.x<=0 && or2.direccion.x<0){ or2.direccion.x=or2.direccion.x*(-1);}
			if(or.direccion.x>=0 && or2.direccion.x<=0){ or.direccion.x= or.direccion.x*(-1);or2.direccion.x=or2.direccion.x*(-1);}
			if(or.direccion.x>0 && or2.direccion.x>=0){ or.direccion.x=or.direccion.x*(-1);}}
			
			if(or2.posicion.x+or2.ancho>=or.posicion.x && or2.posicion.x< or.posicion.x){or2.posicion.x= or2.posicion.x-(deltaX/2)-1; or.posicion.x= or.posicion.x+(deltaX/2)+1;
			
			if(or2.direccion.x<=0 && or.direccion.x<0){ or.direccion.x=or.direccion.x*(-1);}
			if(or2.direccion.x>0 && or.direccion.x<=0){ or2.direccion.x= or2.direccion.x*(-1);or.direccion.x=or.direccion.x*(-1);}
			if(or2.direccion.x>0 && or.direccion.x>=0){ or2.direccion.x=or2.direccion.x*(-1);}}
		//	if(or.direccion.x<0 && or2.direccion.x>0){ or.direccion.x= or.direccion.x*(-1);or2.direccion.x=or2.direccion.x*(-1);}
			}
			if(deltaY*deltaY<deltaX*deltaX){
						
			if(or.posicion.y+or.alto>= or2.posicion.y && or.posicion.y< or2.posicion.y){
				
			or.posicion.y= or.posicion.y-(deltaY/2)-1; or2.posicion.y= or2.posicion.y+(deltaY/2)+1;
			
			if(or.direccion.y<0 && or2.direccion.y<=0){ or2.direccion.y=or2.direccion.y*(-1);}
			if(or.direccion.y>0 && or2.direccion.y<0){ or.direccion.y= or.direccion.y*(-1);or2.direccion.y=or2.direccion.y*(-1);}
			if(or.direccion.y>0 && or2.direccion.y>0){ or.direccion.y=or.direccion.y*(-1);}}
			
			if(or2.posicion.y+or2.alto>= or.posicion.y && or2.posicion.y< or.posicion.y){
				
			or2.posicion.y= or2.posicion.y-(deltaY/2)-1; or.posicion.y= or.posicion.y+(deltaY/2)+1;
			
			if(or2.direccion.y<0 && or.direccion.y<=0){ or.direccion.y=or.direccion.y*(-1);}
			if(or2.direccion.y>0 && or.direccion.y<0){ or2.direccion.y= or2.direccion.y*(-1);or.direccion.y=or.direccion.y*(-1);}
			if(or2.direccion.y>0 && or.direccion.y>0){ or2.direccion.y=or2.direccion.y*(-1);}}
			
			}
			
					}}
						
				}}
	
	
}

//Horizontal transfer

public void horizontalTransfer(Organismo or1, Organismo or2){
	
	int random = (int) (Math.random()*1000000);
	int pos = (int) (Math.random()*100);
	int gentipe = (int) (Math.random()*12);
	
	if(random==0){
		
	switch (gentipe) {	
	case 0: if (pos<50){or1.adn.alto.insert(0, or2.adn.alto);}
	        if (pos>=50){or1.adn.alto.append(or2.adn.alto);}    
	break;
	case 1: if (pos<50){or1.adn.ancho.insert(0, or2.adn.ancho);}
    if (pos>=50){or1.adn.ancho.append(or2.adn.ancho);}    
    break;
    
	case 2: if (pos<50){or1.adn.color.insert(0, or2.adn.color);}
    if (pos>=50){or1.adn.color.append(or2.adn.color);}    
    break;
    
	case 3: if (pos<50){or1.adn.speed.insert(0, or2.adn.speed);}
    if (pos>=50){or1.adn.speed.append(or2.adn.speed);}    
    break;
	
	case 4: if (pos<50){or1.adn.predador.insert(0, or2.adn.predador);}
    if (pos>=50){or1.adn.predador.append(or2.adn.predador);}    
    break;
    
	case 5: if (pos<50){or1.adn.sentir.insert(0, or2.adn.sentir);}
    if (pos>=50){or1.adn.sentir.append(or2.adn.sentir);}    
    break;
    
	case 6: if (pos<50){or1.adn.cazar.insert(0, or2.adn.cazar);}
    if (pos>=50){or1.adn.cazar.append(or2.adn.cazar);}    
    break;
    
	case 7: if (pos<50){or1.adn.escapar.insert(0, or2.adn.escapar);}
    if (pos>=50){or1.adn.escapar.append(or2.adn.escapar);}    
    break;
    
	case 8: if (pos<50){or1.adn.radioConsiente.insert(0, or2.adn.radioConsiente);}
    if (pos>=50){or1.adn.radioConsiente.append(or2.adn.radioConsiente);}    
    break;
    
	case 9: if (pos<50){or1.adn.tasaMutacion.insert(0, or2.adn.tasaMutacion);}
    if (pos>=50){or1.adn.tasaMutacion.append(or2.adn.tasaMutacion);}    
    break;
    
	case 10: if (pos<50){or1.adn.longevidad.insert(0, or2.adn.longevidad);}
    if (pos>=50){or1.adn.longevidad.append(or2.adn.longevidad);}    
    break;
    
	case 11: if (pos<50){or1.adn.toleranciaTemp.insert(0, or2.adn.toleranciaTemp);}
    if (pos>=50){or1.adn.toleranciaTemp.append(or2.adn.toleranciaTemp);}    
    break;
    
	case 12: if (pos<50){or1.adn.resistenciaATB.insert(0, or2.adn.resistenciaATB);}
    if (pos>=50){or1.adn.resistenciaATB.append(or2.adn.resistenciaATB);}    
    break;
   
	}
	 //System.out.println("Se tranfirio un gen");
	}
	
}

public void dispose(){
	
	textuRA_ENER.dispose();
	textura_ORG.dispose();
	
}

}
