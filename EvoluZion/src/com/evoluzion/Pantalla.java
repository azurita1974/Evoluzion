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

import javax.swing.JOptionPane;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Pantalla implements Screen{
	
	protected Evoluzion ev;
	protected Mundo m;
	Texto tx;
	
	private OrthographicCamera camara;
	private SpriteBatch batch;
	private ShapeRenderer caja, borde;
	private BitmapFont fuente;
	private BitmapFont fu_fuente;
	private boolean debugin= false;
	private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
	private Stage stage;           //stage maneja elementos que reciben entradas como botones o eventos
	private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
	private TextButton b_pausa, b_Salir, b_verOcultar,b_colectar; //crea botones con texto similares a los de swing
	private int verPanel=1;
	private int verBotones1=1;
	
	Organismo or;
	Senergia se;
	Qenergia qe;
	int cantidad=0;
	int cantidad2=0;
	int cantidad3=0;
	int cont,cont2,cont3;
	int press= 1; // 1=false -1 = true
	
	private NumberFormat format = new DecimalFormat("0.00");
	private TextButton b_colectarP;
	private boolean todoGuardado =false;
	private TextButton b_catastrofe;
	private TextButton b_guardar;
	private TextButton b_colectarPM;
	private TextButton b_colectarPnM;
	private TextButton b_marcarTodo;
	
	private CheckBox cb_verEnergia;
	private Skin sk_skin2;
	private TextureAtlas ta_atlas2;
	private CheckBox cb_verMasa;
	private CheckBox cb_verOrganismos;
	private TextButton b_antibiotico;
	
	public Pantalla(Evoluzion ev, Mundo m){
		
		this.ev=ev;
		this.m=m;
		tx=m.tx;//usamos la configuracionde taxto del menu inicio
		or= m.aorg.get(0);
		se= m.ase.get(0);
		qe = m.aqe.get(0);
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, m.ancho, m.alto);
		
		batch = new SpriteBatch();
		caja = new ShapeRenderer();
		
		borde = new ShapeRenderer();
		fuente = new BitmapFont();
		
		ta_atlas = new TextureAtlas("data/botones.pack");//carga el atlas de texturas donde estan los botones
		ta_atlas2= new TextureAtlas("data/boxes.pack");
		sk_skin = new Skin();
		sk_skin.addRegions(ta_atlas);
		sk_skin2= new Skin();
		sk_skin2.addRegions(ta_atlas2);
		
		fu_fuente = new BitmapFont();
		
		//colecta los datos del tiempo 0
		
//		m.archivarGenoma();
//		m.archivarProteoma();
//		m.guardarDatos();
		
	}

	@Override
	public void render(float delta) {
	
		
		//Game loop
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
			
				camara.update();
				stage.act(delta);
				
			if(verBotones1==1){
				b_colectar.setVisible(false);
				b_colectarP.setVisible(false);
				b_colectarPM.setVisible(false);
				b_colectarPnM.setVisible(false);
				
				verBotones1= verBotones1*2;
						
			}
			
			if(verBotones1==-1){
				b_colectar.setVisible(true);
				b_colectarP.setVisible(true);
				b_colectarPM.setVisible(true);
				b_colectarPnM.setVisible(true);		
				
				verBotones1= verBotones1*2;
			}
			
				
			if(m.pausaGame==1){	//1=play -1=pausa
				m.update();//actualiza los datos que maneja el Mundo
			//	System.out.println("rendering");
			}	
				
				batch.setProjectionMatrix(camara.combined);		
			//comienzo a graficar	
//						
			//dibuja Senergia	
				
		if(cb_verEnergia.isChecked()==true){		
				cantidad= m.ase.size;
				
				for(cont=cantidad-1; cont>=0; cont--){
					se=m.ase.get(cont);
					se.verObjeto(batch);}}
			//dibujamos Qenergia
		if(cb_verMasa.isChecked()==true){
				cantidad2= m.aqe.size;
				
				for(cont2=cantidad2-1; cont2>=0; cont2--){
					qe=m.aqe.get(cont2);
					qe.verObjeto(batch);}}
		
		//	for(Qenergia qe :m.aqe){qe.verObjeto(batch);}	
			//dibujamos los organismos	
		
		if(cb_verOrganismos.isChecked()==true){
				cantidad3= m.aorg.size;
				
				for(cont3=cantidad3-1; cont3>=0; cont3--){
					or=m.aorg.get(cont3);
					or.verOrganismo(batch);}
			// verl los organismos marcados
                cantidad3= m.aorg.size;
				
				for(cont3=cantidad3-1; cont3>=0; cont3--){
					or=m.aorg.get(cont3);
					or.verMarcado(borde, batch,fuente);}}
				
				
			//dibujar rectangulos en modo debug				
		if(debugin==true){
					
				for(Organismo or: m.aorg){ or.verBorde(borde);}	
				//for(Senergia se: m.ase){ se.verBorde(borde);}
				for(Qenergia qe: m.aqe){ qe.verBorde(borde);}
				}
				
				caja.begin(ShapeType.FilledRectangle);
					
				caja.setColor(Color.BLACK);
				caja.filledRect( 0, m.alto-30, m.ancho, 30);
				caja.end();
				batch.begin();
				fuente.draw(batch,"d: "+ m.dias+ " |h: "+ m.horas+ " |m: "+m.minutos+ " |s: "+ m.segundos, 400, m.alto-30 );
				batch.end();
				
				if(verPanel==1){
				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLUE);
				caja.filledRect( 0, m.alto- 400, 170, 400);
			    caja.end();
				
				batch.begin();
				
				fuente.setColor(Color.WHITE);
				fuente.draw(batch,tx.Organismos+ m.aorg.size, 5, m.alto-30);
				fuente.draw(batch,tx.mutantes+ m.aEspesies.size, 5, m.alto-50);
				int masatotal= (int) (m.BiomasaTotal()+ m.MateriaLibre());
				fuente.draw(batch,tx.masaTotal+masatotal , 5, m.alto-70 );
				
				fuente.draw(batch,tx.masa+ m.MateriaLibre(), 5, m.alto-90 );
				fuente.draw(batch,tx.biomasa+ m.BiomasaTotal(), 5, m.alto-110 );
				fuente.draw(batch,tx.velocidadMedia+format.format(m.velocidadMedia()), 5, m.alto-130 );
				fuente.draw(batch,tx.tamanoMedi+ format.format(m.tamanoMedio()), 5, m.alto-150 );
				fuente.draw(batch,tx.tasaMutacionMedia+ m.tasaMutMedio(), 5, m.alto-170 );
				fuente.draw(batch,tx.vidaMdia+ format.format(m.longevidadMedio())+" (s)", 5, m.alto-190 );
				fuente.draw(batch,tx.resistensiaATB+ m.cantidadResistentes(), 5, m.alto-210 );
				fuente.draw(batch,tx.temperatura+ format.format(m.temperatura), 5, m.alto-230);
				fuente.draw(batch,tx.temOptimaMedia+ format.format(m.temOptimaMedia()), 5, m.alto-250);
				
				if(m.antibiotico==1){fuente.draw(batch,tx.antibioticoON, 5, m.alto-370);}
				else{fuente.draw(batch,tx.antibioticoOFF, 5, m.alto-370);};
				
				batch.end();
				
				cb_verEnergia.setVisible(true);
				cb_verMasa.setVisible(true);
				cb_verOrganismos.setVisible(true);
				
				}
				
				if(verPanel==-1){
					
				cb_verEnergia.setVisible(false);
				cb_verMasa.setVisible(false);
				cb_verOrganismos.setVisible(false);	
				}
				
				//botones
				batch.begin();
				stage.draw();
				batch.end();
				
				
				
if (Gdx.input.justTouched()) {
							Vector3 touchPos = new Vector3();
							touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
							camara.unproject(touchPos);
					//	System.out.println(touchPos.x + " "+ touchPos.y);	
							
					  cantidad3= m.aorg.size;
								
					for(cont3=cantidad3-1; cont3>=0; cont3--){
						
							or=m.aorg.get(cont3);
	if(touchPos.x > or.posicion.x && touchPos.x < or.posicion.x+or.ancho && touchPos.y > or.posicion.y && touchPos.y < or.posicion.y + or.alto   ){
							
							or.marcado= or.marcado*(-1);					
							
						}
	}			}

if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) ) {
	
	Vector3 touchPos = new Vector3();
	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	camara.unproject(touchPos);
	
cantidad3= m.aorg.size;
		
for(cont3=cantidad3-1; cont3>=0; cont3--){

	or=m.aorg.get(cont3);
	
if(or.marcado== -1 && touchPos.x > or.posicion.x-or.ancho && touchPos.x < or.posicion.x+or.ancho*2 && touchPos.y > or.posicion.y -or.alto&& touchPos.y < or.posicion.y + or.alto*2   ){
		
	or.posicion.x = touchPos.x- or.ancho/2;
	or.posicion.y = touchPos.y - or.alto/2;
	or.update();
	}
}		

}
				
				
		
	}
	
	

	@Override
	public void resize(int width, int height) {
		if (stage == null){	
			//stage maneja elementos que reciben entradas como botones o eventos
			//en este caso se us apara los botones
					
					stage = new Stage(width, height, true);
					stage.clear();
					Gdx.input.setInputProcessor(stage);
					
			
					
			//instansia los elemrntos de un boton
			//la posocion up y down usando imagenes y el texto que tiene cada uno		
					TextButtonStyle estilo = new TextButtonStyle();
					estilo.up = sk_skin.getDrawable("BotonUP");
					estilo.down = sk_skin.getDrawable("BotonDown");
					estilo.font = fu_fuente;
					
					//instancia los botones
					
					b_Salir = new TextButton(tx.guardarYcerrar, estilo);
					b_Salir.setWidth(150);
					b_Salir.setHeight(20);
					b_Salir.setX(m.ancho-142);
					b_Salir.setY(m.alto-20);
					
					
					b_pausa = new TextButton(tx.playPausa, estilo);
					b_pausa.setWidth(150);
					b_pausa.setHeight(20);
					b_pausa.setX(m.ancho-284);
					b_pausa.setY(m.alto-20);
					

					b_verOcultar = new TextButton(tx.verOcultar, estilo);
					b_verOcultar.setWidth(140);
					b_verOcultar.setHeight(20);
					b_verOcultar.setX(0);
					b_verOcultar.setY(m.alto-20);
					
					
					b_guardar = new TextButton(tx.menuGuardar, estilo);
					b_guardar.setWidth(140);
					b_guardar.setHeight(20);
					b_guardar.setX(142);
					b_guardar.setY(m.alto-20);
					
					b_colectar = new TextButton(tx.tomarMuestra, estilo);
					b_colectar.setWidth(140);
					b_colectar.setHeight(20);
					b_colectar.setX(200);
					b_colectar.setY(m.alto-42);
										
					
					b_colectarP = new TextButton(tx.guardarTodos, estilo);
					b_colectarP.setWidth(140);
					b_colectarP.setHeight(20);
					b_colectarP.setX(200);
					b_colectarP.setY(m.alto-65);
					
					b_colectarPM = new TextButton(tx.guardarMarcados, estilo);
					b_colectarPM.setWidth(140);
					b_colectarPM.setHeight(20);
					b_colectarPM.setX(200);
					b_colectarPM.setY(m.alto-88);
					
					b_colectarPnM = new TextButton(tx.guardarNoMarcados, estilo);
					b_colectarPnM.setWidth(140);
					b_colectarPnM.setHeight(20);
					b_colectarPnM.setX(200);
					b_colectarPnM.setY(m.alto-111);
					
					b_marcarTodo = new TextButton(tx.marcarDesmarcar, estilo);
					b_marcarTodo.setWidth(140);
					b_marcarTodo.setHeight(20);
					b_marcarTodo.setX(284);
					b_marcarTodo.setY(m.alto-20);
					
					b_antibiotico = new TextButton(tx.antibiotico, estilo);
					b_antibiotico.setWidth(140);
					b_antibiotico.setHeight(20);
					b_antibiotico.setX(428);
					b_antibiotico.setY(m.alto-20);
					
					b_catastrofe = new TextButton(tx.catastrofe, estilo);
					b_catastrofe.setWidth(140);
					b_catastrofe.setHeight(20);
					b_catastrofe.setX(570);
					b_catastrofe.setY(m.alto-20);
					
					
					
					CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
					checkBoxStyle.checkboxOff =  sk_skin2.getDrawable("boxN0");
				    checkBoxStyle.checkboxOn =   sk_skin2.getDrawable("boxYES");
				    checkBoxStyle.font = fu_fuente;
					
					cb_verEnergia = new CheckBox(tx.verEnergia, checkBoxStyle);
					cb_verEnergia.getCells().get(0).size(20,20);
					cb_verEnergia.setChecked(true);
					cb_verEnergia.setPosition(-10,320);
					
					cb_verMasa = new CheckBox(tx.verMasa, checkBoxStyle);
					cb_verMasa.getCells().get(0).size(20,20);
					cb_verMasa.setChecked(true);
					cb_verMasa.setPosition(-10,380);
					
					cb_verOrganismos = new CheckBox(tx.verOrgansimo, checkBoxStyle);
					cb_verOrganismos.getCells().get(0).size(20,20);
					cb_verOrganismos.setChecked(true);
					cb_verOrganismos.setPosition(-10,350);
					
					
					
					stage.addActor(b_Salir);
					stage.addActor(b_pausa);
					stage.addActor(b_verOcultar);
					stage.addActor(b_guardar);
					stage.addActor(b_colectar);
					stage.addActor(b_colectarP);
					stage.addActor(b_colectarPM);
					stage.addActor(b_colectarPnM);
					stage.addActor(b_marcarTodo);
					stage.addActor(b_catastrofe);
					stage.addActor(cb_verEnergia);
					stage.addActor(cb_verMasa);
					stage.addActor(cb_verOrganismos);
					stage.addActor(b_antibiotico);
					
					
					
					//se agregan los listener para los botones	
					
					
					
					b_Salir.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	m.pausaGame= m.pausaGame*(-1);	
					      
					     	Object[] options = {tx.si,tx.no};
					        int n = JOptionPane.showOptionDialog(null, tx.terminarGuardarMensaje,"",
										    
										    	    JOptionPane.YES_NO_OPTION,
										    	    JOptionPane.INFORMATION_MESSAGE, null,options, options[1]);

							if (n == JOptionPane.YES_OPTION) {   
					             if(todoGuardado==false){ 
					               if(m.aorg.size>0){ m.guardarDatos(); m.archivarGenoma(); m.archivarProteoma();}
					                 m.f_genes.cerrarArchivo();
					                 m.f_proteoma.cerrarArchivo();
					                 m.f_mutantes.cerrarArchivo();
					                 m.f_datos.cerrarArchivo();
					                 m.archivaTodaslasEspecies();}
					     				    
					                 ev.setScreen(new MenuInicio(ev));
					     // m = null;
					                 dispose();}
							if (n == JOptionPane.NO_OPTION) { }
				        
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				        	
				        
				        	
				        }});
					
					b_pausa.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	
				       m.pausaGame= m.pausaGame*(-1);
				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { }});
					
					b_verOcultar.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	
				       verPanel=verPanel*(-1);
				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { }});
					
					b_guardar.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				      
				     verBotones1= verBotones1/2;   	
				     verBotones1= verBotones1*(-1);
				     
				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { }});
					
					
					b_colectar.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				      m.colectorEspesies();  	
				      m.guardarDatos();
				      m.archivarGenoma();
				      m.archivarProteoma();
				      m.archivarFenotipo();
				     
				    
					     
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { 
				        	  verBotones1= verBotones1/2;   	
					  verBotones1= verBotones1*(-1);
				        	
				        }});
					
					b_colectarP.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        
				        m.pausaGame=-1;
				        	m.guardarPoblacion();
				        	//				   				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				        	
					          verBotones1= verBotones1/2;   	
							  verBotones1= verBotones1*(-1);
							//  m.pausaGame= 1;
				        	
				        }});
					
					b_colectarPM.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	m.pausaGame= -1;
				        	m.guardarPoblacionMarcada();
				        	//				   				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				        	
					          verBotones1= verBotones1/2;   	
							  verBotones1= verBotones1*(-1);
							//  m.pausaGame= 1;
				        	
				        }});
					
					b_colectarPnM.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	m.pausaGame= -1;
				        	m.guardarPoblacionNoMaracada();
				        	//				   				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				        	
					          verBotones1= verBotones1/2;   	
							  verBotones1= verBotones1*(-1);
							//  m.pausaGame= 1;
				        	
				        }});
					
					b_marcarTodo.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	int size= m.aorg.size;
				        	for(int i=size-1;i>=0;i--){
				        		
				        		m.aorg.get(i).marcado=m.aorg.get(i).marcado*(-1);
				        	}
				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				        	
					        
				        	
				        }});
					
					b_antibiotico.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	
				        	m.antibiotico=m.antibiotico*(-1);
				   				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { }});
					
					b_catastrofe.addListener(new InputListener() {
				        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				        	
				        	m.catastrofe();
				   				        	
				            return true;
				        }
				        
				        public void touchUp (InputEvent event, float x, float y, int pointer, int button) { }});
		
		
		}			
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		m.dispose();
		sk_skin.dispose();
		fu_fuente.dispose();
		ta_atlas.dispose();
		stage.dispose();
		batch.dispose();
	
		
	}

}
