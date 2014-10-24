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

public class Texto {
	
	MenuInicio mi;
	//texto del Menu inicio
	
	String errorEscribir= "error al escribir el archivo evo_star.conf";
	String errorLectura= "error en la lectura del archivo evo_star.conf \n se usará la configuración por defecto";
	String nombre = "Nombre";
	String simuladorDigital="Simulador digital de evolución";
	String parametrosEnergiaMasa= "Paramentros de Energía y Masa";
	String soloNenteros = "(Solo números enteros)";
	String otrosParamentros = "Otros parametros";
	String directorioDeTrabajo = "Directorio de Trabajo: ";
	String valorEnergia = "Valor energía";
	String valorBiomasa = "Valor biomasa";
	String cantidadEnergia = "Cantidad energía";
	String cantidadBiomasa = "Cantidad biomasa";
	String tiempoEntreMuestras = "Tiempo entre muestras (seg)";
	String tiempoEntreCatastrofes = "Tiempo entre catástrofes (seg, 0 = inactivado)";
	String tiempoATB = "Tiempo de ATB                 (seg, 0 = inactivado)";
	String tiempoPartida = "Tiempo de la partida         (seg, 0 = ilimitado)";
	String tiempo= "Tiempo";
	String numeroInOrganismos = "Número Inicial de Organismos";
	String numeroMaximoOrg = "Número Maximo de Organismos";
	String temperaturaInicial = "Temperatura inicial (grados centigrados)";
	String grados = "grados centigrados";
	String modificacionMambiente = "Modificación del medio ambiente";
	String comenzar2 = "Comenzar*       Temp. final           Delta tiempo";
	String UnominGradosMin = "1)                min                     grados                   min  ";
	String DosminGradosMin = "2)                min                     grados                   min  ";
	String funcionInactivada = "* 0 = función incativada";
	String marcarGenesMutaran = "Marcar los genes que se mutarán";
	String marcarMutAnalizar ="Marcar las mutaciones a analizar";
	String multiploADNpol = "Múltiplo que afecta la eficiencia de la ADN-pol";
	String comenzar = "COMENZAR";
	String sobreEvolizion = "Sobre EvoluZion";
	String directorioTrabajo2 = "Directorio de trabajo";
	String cargaPoblacion = "Cargar Población";
	String salir = "SALIR";
	String moverLaMasa= "Mover la Masa";
	String cargarArchivo = "Cargar Archivo: ";
	String color ="Color";
	String tamano="Tamaño";
	String alto ="alto";
	String ancho = "ancho";
	String cantidad= "Cantidad";
	String velocidad ="Velocidad";
	String sentidos = "Sentidos";
	String buscarComida= "Buscar Comida";
	String escapar="Escapar";
	String alcanceVisual = "Alcance Visual";
	String genPredador= "Gen Predador";
	String fidelidadADNpol = "Fidelidad ADNpol";
	String longevidad ="Longevidad";
	String temOptima = "Temp. Óptima";
	String ResATB = "Resist. ATB";
	String mensajeSobre = "Evoluzion: simulador digital de evolución \n\n" +
            "Autor: Adolfo R, Zurita \n\n" +
            "Investigador Asistente de Conicet \n" +
            "Profesor adjunto de Genética e introducción a la biología molecular\n" +
             "Universidad Nacional de San Luis, San Luis, Argentina \n\n"+
             "azurita1974@gmail.com     \n\n" +
             "Copyright 2014 Adolfo R. Zurita";
	String agregarDespues = "Agregar /. despues del directorio seleccionado";
	String poblacion = "población";
	String directorio="Directorio";
	String si= "Si";
	String no= "No";
	String deseaSalir= "¿Desea salir del programa?";
	String cuandoEstenPresentes = "Los genes (cuando están presentes) aparecen en el siguiente orden: \n ";
	String genomasTXT = "_Genomas.txt";
	String datosXls = "_datos_.xls";
	String datosOrdenados = "Tiempo,Organismos,Mutantes,Predadores,Res.ATB,Vel Media, Tam. Medio,Long Media, Fidelidad ADNpol Media, Temperatura, Temp. Optima Media   \n";
	String proteomaTXT = "_Proteoma.txt";
	String cuandoEstenPresntesProt = "Las proteínas (cuando están presentes) aparecen en el siguiente orden: \n";
	String arbolTXT = "_Arbol.txt";
	String fenomaTXT= "_Fenotipo.txt";
	String mutantesXLS= "_Mutantes.xls";
	String idioma ="Set to english";
	String losCambiosReiniciar= "Algunos cambios se completarán luego de reiniciar";
	
	//Texto de la pantalla
	
	String Organismos = "Organismos : ";
	String mutantes ="Mutantes      : ";
	String masaTotal = "Masa Total  : ";
	String masa = "Masa           : ";
	String biomasa = "Biomasa      : ";
	String velocidadMedia = "Vel Media    : ";
	String tamanoMedi= "Tam Medio    : ";
	String tasaMutacionMedia= "T. Mut. Media: 1/";
	String vidaMdia= "Vida Media  : ";
	String resistensiaATB ="Resist. a ATB: ";
	String temperatura = "Temperatura  : ";
	String temOptimaMedia = "T.Óptima Med.: ";
	String antibioticoON= "Antibiótico ON";
	String antibioticoOFF ="Antibiótico OFF";
	String guardarYcerrar ="Guardar y Cerrar";
	String playPausa= "Play/Pausa";
	String verOcultar ="Ver/ocultar";
	String menuGuardar= "Menú Guardar";
	String tomarMuestra= "Tomar Muestra";
	String guardarTodos="Guardar Todos";
	String guardarMarcados= "Guardar Marcados";
	String guardarNoMarcados ="Guardar No Marcados";
	String marcarDesmarcar = "Marcar/Desmarcar";
	String antibiotico = "Antibiótico!!!";
	String catastrofe = "Catástrofe!!!";
	String verEnergia = "Ver Energía";
	String verMasa ="Ver Masa";
	String verOrgansimo= "Ver Org.";
	String terminarGuardarMensaje = "Terminar simulación, se guardarán los datos colectados";
	
	
public Texto(){
		
		
		
		
		
		
	}
public void setEspanol(){
	
	errorEscribir= "error al escribir el archivo evo_star.conf";
	errorLectura= "error en la lectura del archivo evo_star.conf \n se usará la configuración por defecto";
	nombre = "Nombre";
	simuladorDigital="Simulador digital de evolución";
	parametrosEnergiaMasa= "Paramentros de Energía y Masa";
	soloNenteros = "(Solo números enteros)";
	otrosParamentros = "Otros parametros";
	directorioDeTrabajo = "Directorio de Trabajo: ";
	valorEnergia = "Valor energía";
	valorBiomasa = "Valor biomasa";
	cantidadEnergia = "Cantidad energía";
	cantidadBiomasa = "Cantidad biomasa";
	tiempoEntreMuestras = "Tiempo entre muestras (seg)";
	tiempoEntreCatastrofes = "Tiempo entre catástrofes (seg, 0 = inactivado)";
	tiempoATB = "Tiempo de ATB                 (seg, 0 = inactivado)";
	tiempoPartida = "Tiempo de la partida         (seg, 0 = ilimitado)";
	tiempo= "Tiempo";
	numeroInOrganismos = "Número Inicial de Organismos";
	numeroMaximoOrg = "Número Maximo de Organismos";
	temperaturaInicial = "Temperatura inicial (grados centigrados)";
	grados = "grados centigrados";
	 modificacionMambiente = "Modificación del medio ambiente";
	 comenzar2 = "Comenzar*       Temp. final           Delta tiempo";
	 UnominGradosMin = "1)                min                     grados                   min  ";
	 DosminGradosMin = "2)                min                     grados                   min  ";
	 funcionInactivada = "* 0 = función incativada";
	 marcarGenesMutaran = "Marcar los genes que se mutarán";
	 marcarMutAnalizar ="Marcar las mutaciones a analizar";
	 multiploADNpol = "Múltiplo que afecta la eficiencia de la ADN-pol";
	 comenzar = "COMENZAR";
	sobreEvolizion = "Sobre EvoluZion";
	directorioTrabajo2 = "Directorio de trabajo";
	cargaPoblacion = "Cargar Población";
	salir = "SALIR";
	 moverLaMasa= "Mover la Masa";
	cargarArchivo = "Cargar Archivo: ";
	 color ="Color";
	 tamano="Tamaño";
	 alto ="alto";
	 ancho = "ancho";
	 cantidad= "Cantidad";
	 velocidad ="Velocidad";
	 sentidos = "Sentidos";
	 buscarComida= "Buscar Comida";
	 escapar="Escapar";
	alcanceVisual = "Alcance Visual";
	 genPredador= "Gen Predador";
	 fidelidadADNpol = "Fidelidad ADNpol";
	longevidad ="Longevidad";
	temOptima = "Temp. Óptima";
	ResATB = "Resist. ATB";
	mensajeSobre = "Evoluzion: simulador digital de evolución \n\n" +
            "Autor: Adolfo R, Zurita \n\n" +
            "Investigador Asistente de Conicet \n" +
            "Profesor adjunto de Genética e introducción a la biología molecular\n" +
             "Universidad Nacional de San Luis, San Luis, Argentina \n\n"+
             "azurita1974@gmail.com     \n\n" +
             "Copyright 2014 Adolfo R. Zurita";
	agregarDespues = "Agregar /. despues del directorio seleccionado";
	poblacion = "población";
	directorio="Directorio";
	si= "Si";
	no= "No";
	deseaSalir= "¿Desea salir del programa?";
	cuandoEstenPresentes = "Los genes (cuando están presentes) aparecen en el siguiente orden: \n ";
	genomasTXT = "_Genomas.txt";
	datosXls = "_datos_.xls";
	datosOrdenados = "Tiempo,Organismos,Mutantes,Predadores,Res.ATB,Vel Media, Tam. Medio,Long Media, Fidelidad ADNpol Media, Temperatura, Temp. Óptima Media   \n";
	proteomaTXT = "_Proteoma.txt";
	cuandoEstenPresntesProt = "Las proteínas (cuando están presentes) aparecen en el siguiente orden: \n";
	arbolTXT = "_Arbol.txt";
	fenomaTXT= "_Fenotipo.txt";
	mutantesXLS= "_Mutantes.xls";
	idioma ="Set to english";
	losCambiosReiniciar= "Algunos cambios se completarán luego de reiniciar";
	//de la pantalla
	
	 Organismos = "Organismos : ";
	 mutantes ="Mutantes      : ";
	 masaTotal = "Masa Total  : ";
	 masa = "Masa           : ";
	 biomasa = "Biomasa      : ";
	 velocidadMedia = "Vel Media    : ";
	 tamanoMedi= "Tam Medio    : ";
	 tasaMutacionMedia= "T. Mut. Media: 1/";
	 vidaMdia= "Vida Media  : ";
	 resistensiaATB ="Resist. a ATB: ";
	 temperatura = "Temperatura  : ";
	 temOptimaMedia = "T.Óptima Med.: ";
	 antibioticoON= "Antibiótico ON";
	 antibioticoOFF ="Antibiótico OFF";
	 guardarYcerrar ="Guardar y Cerrar";
	 playPausa= "Play/Pausa";
	 verOcultar ="Ver/ocultar";
	 menuGuardar= "Menú Guardar";
	 tomarMuestra= "Tomar Muestra";
	 guardarTodos="Guardar Todos";
	 guardarMarcados= "Guardar Marcados";
	 guardarNoMarcados ="Guardar No Marcados";
	 marcarDesmarcar = "Marcar/Desmarcar";
	 antibiotico = "Antibiotico!!!";
	 catastrofe = "Catástrofe!!!";
	 verEnergia = "Ver Energía";
	 verMasa ="Ver Masa";
	 verOrgansimo= "Ver Org.";
	 terminarGuardarMensaje = "Terminar simulación, se guardarán los datos colectados";
	
	
	
	
}

public void setIngles(){
	
	errorEscribir= "Error writing file evo_star.conf";
	errorLectura= "error reading the file evo_star.conf \n the original configuration will be used";
	nombre = "Name";
	simuladorDigital="Digital simulator of evolution";
	parametrosEnergiaMasa= "Parameters of Energy and Mass";
	soloNenteros = "(Only integer numbers)";
	otrosParamentros = "Additional parameters";
	directorioDeTrabajo = "Working folder: ";
	valorEnergia = "Energy value";
	valorBiomasa = "Mass value";
	cantidadEnergia = "Amount of energy";
	cantidadBiomasa = "Amount of mass";
	tiempoEntreMuestras = "Time between samples(seg)";
	tiempoEntreCatastrofes = "Time btw catastrophes     (seg, 0 = inactivated)";
	tiempoATB = "Timeo of ATB                     (seg, 0 = inactivated)";
	tiempoPartida = "Running time                     (seg, 0 = unlimited)";
	tiempo= "Time";
	numeroInOrganismos = "Starting Number of Organisms";
	numeroMaximoOrg = "Maximum Number of Organisms";
	temperaturaInicial = "Initial temperature (Celsius degrees )";
	grados = "Celsius degrees";
	 modificacionMambiente = "Environmental modification";
	 comenzar2 = "Start*                Final Temp.           Delta time";
	 UnominGradosMin = "1)                min                     degrees                   min  ";
	 DosminGradosMin = "2)                min                     degrees                   min  ";
	 funcionInactivada = "* 0 = inactivated";
	 marcarGenesMutaran = "Mark genes allowed to mutate";
	 marcarMutAnalizar ="Mark mutations you want to analyse";
	 multiploADNpol = "Multiple that affect DNApol efficiency";
	 comenzar = "START";
	sobreEvolizion = "About EvoluZion";
	directorioTrabajo2 = "Working folder";
	cargaPoblacion = "Load Population";
	salir = "EXIT";
	 moverLaMasa= "Moving the Mass";
	cargarArchivo = "Load File: ";
	 color ="Color";
	 tamano="Size";
	 alto ="high";
	 ancho = "width";
	 cantidad= "Number";
	 velocidad ="Speed";
	 sentidos = "Senses";
	 buscarComida= "Search for Food";
	 escapar="Escape";
	alcanceVisual = "Visual Range";
	 genPredador= "Predator gen";
	 fidelidadADNpol = "DNApol fielity";
	longevidad ="Lifespan";
	temOptima = "Optimal temp.";
	ResATB = "ATB resistance";
	mensajeSobre = "Evoluzion: digital simulator of evolution \n\n" +
            "Autor: Adolfo R, Zurita \n\n" +
            "Assistant Researcher of CONICET\n" +
            "Associate Professor of Genetics and introduction to molecular biology\n" +
            "National University of San Luis, San Luis, Argentina \n\n"+
             "azurita1974@gmail.com     \n\n" +
             "Copyright 2014 Adolfo R. Zurita";
	agregarDespues = "Add /. after the selcted folder";
	poblacion = "population";
	directorio="Folder";
	si= "Yes";
	no= "No";
	deseaSalir= "Do you want to exit the program?";
	cuandoEstenPresentes = "The genes (when present) appear in the following order: \n ";
	genomasTXT = "_Genome.txt";
	datosXls = "_data.xls";
	datosOrdenados = "Time,Organism,Mutant,Predators,ATB Res.,Avg. Speed,Avg. Size,Avg. Lifespan,Avg. ADNpol fidelity, Temperature,Avg. Optimal temp. \n";
	proteomaTXT = "_Proteome.txt";
	cuandoEstenPresntesProt = "The proteins (when present) appear in the following order: \n";
	arbolTXT = "_Tree.txt"; 
	fenomaTXT= "_Phenotype.txt";
	mutantesXLS= "_Mutants.xls";
	idioma ="cambiar a español";
	losCambiosReiniciar= "Some changes will be completed after restart";
	//de la pantalla
	
		 Organismos = "Organism   : ";
		 mutantes ="Mutants       : ";
		 masaTotal = "Total Mass  : ";
		 masa = "Mass           : ";
		 biomasa = "Bio-mass     : ";
		 velocidadMedia = "Avg. Speed    : ";
		 tamanoMedi= "Avg. Size    : ";
		 tasaMutacionMedia= "Avg. Mut. rate.: 1/";
		 vidaMdia= "Avg. Lifespan: ";
		 resistensiaATB ="ATB Resistense: ";
		 temperatura = "Temperature  : ";
		 temOptimaMedia = "Avg. Opt. Temp.: ";
		 antibioticoON= "Antibiotic  ON";
		 antibioticoOFF ="Antibiotic  OFF";
		 guardarYcerrar ="Save and Close";
		 playPausa= "Play/Pause";
		 verOcultar ="View / hide";
		 menuGuardar= "Save menu";
		 tomarMuestra= "Take Sample";
		 guardarTodos="Save all";
		 guardarMarcados= "Save Marked";
		 guardarNoMarcados ="Save Unmarked";
		 marcarDesmarcar = "Mark/Unmark";
		 antibiotico = "Antibiotic!!!";
		 catastrofe = "catastrophe!!!";
		 verEnergia = "See Energy";
		 verMasa ="See Mass";
		 verOrgansimo= "See Org.";
		 terminarGuardarMensaje = "End simulation, collected data will be saved";
	
		
}


}
