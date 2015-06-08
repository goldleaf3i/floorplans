package floorplans;

import processing.core.*;
import processing.data.XML;

import java.awt.geom.Line2D;
import java.io.File;
import java.util.*;

public class FloorPlanSketch extends PApplet {


	// TODO fare che se premo n e m aggiungo o tolgo un po' al parametro di scala.
	
	PImage img;
	// grandezza x e y dell'immagine + grandezza pannello dx
	String filename = "/Users/matteoluperto/Documents/eclipseworkspace/floorplans/poli.png";
	boolean show_image = true;
	int x_dim=1100, y_dim=700;
	int x2_dim = 300;
	// width e height original dell'immagine
	int x;
	int y;
	// diametro degli ellissi generati
	// MESSO IN GLOBALS
	//int shape = 15;
	// nodi disegnati
	ArrayList<Node>  n = new ArrayList<Node>();
	ArrayList<Connection> c = new ArrayList<Connection>();
	// penultimo e ultimo nodo selezionato
	int last = -1;
	int lastbu = -1;
	// label / colore selezionato
	int current_label = -1;
	// MESSO IN GLOBALS
	//int opacity1 = 130, opacity2 = 255;

	// last_inser vale 0 se non ho fatto nulla. se vale 1 ho inserito un nodo; se vale 2 ho inserito un arco;
	int last_insert = 0;

	// tolerance value for point-to-point comparison, in pixel;
	int resolution = 15;

	String message = " ";

	XML xml;
	// dove inizia pannello dx
	int offset = x_dim-x2_dim;
	// dati che servono a disporre i vari pallini
	int X_label_offset = 40;
	int X_label_shift = 100;
	int Y_label_limit = 400;
	int Y_label_offset = 85;
	Label[] labels;
	String buildingtype; 
	
	// SE STO INSERENDO UNA STANZA.
	int insert_room=-1;
	
	// PUNTI E LINEE GLOBALI
	// Coordinate X e Y dei punti
	ArrayList<Integer> X = new ArrayList<Integer>();
	ArrayList<Integer> Y = new ArrayList<Integer>();
	// Punti come linee
	ArrayList<Line2D.Float> L = new ArrayList<Line2D.Float>();
	// indice delle linee / punti che sono da considerare come porte.
	ArrayList<Integer> D = new ArrayList<Integer>();
	// indice delle delle stanze a cui appartengono le porte.
	ArrayList<Integer> D2 = new ArrayList<Integer>();
	int set_door = 0;
	// punti temporanei inseriti 
	int xp=-1,yp=-1, xp1=-1, yp1=-1;
	
	public void setup() {
	  size(x_dim, y_dim);
	  textSize(11);
	  // The background image must be the same size as the parameters
	  // into the size() method. In this program, the size of the image
	  // is 640 x 360 pixels.
	  img = loadImage(filename);
	  x = img.width;
	  y = img.height;
	  image(img, 0, 0,x_dim-x2_dim, (x_dim-x2_dim)/x*y);
	  //img.resize(x/10,y/10);
	  //image(img,800,100);
	  fill(100,255,0);
	  //ellipse(offset, 200, 30, 30);
	  //fill(122,11,222);
	  //ellipse(offset,400,30,30);
	  loadData();
	  noLoop();
	  redraw();
	  
	}

	public void draw() {
		
	FARE UN SELETTORE CHE FUNZIONI COSI:  
		NORMALMENTE DISEGNO.
		SE SCHIACCIO UN PULSANTE PASSO ALLA FASE B: CREO I GRUPPI (CAMBIO LA DRAW CHE DIVENTA UNA FUNZIONE E CAMBIO IL KEYPRESSED CHE DIVENTA UNA FUNZIONE SEMPRE CON LO SWITCH)
		IL KEYPRESSED DI QUESTO MOMENTO MI FA METTERE IL NOME DELL'ETICHETTA.
		IL MOUSEKEY MI FA SELEZIONARE I NODI DA INSERIRE.
	  
		
	  fill(235);
	  rect(0,0,offset,y_dim);
	  if (show_image)
		  {
		  if (x > y) {
		    image(img, 0, 0,x_dim-x2_dim, y*(x_dim-x2_dim)/x);
		  }
		  else {
		    image(img, 0, 0,x*y_dim/y, y_dim);
		    //image(img, 0, 0,650 , 400);
		  }
		  }
	 // message = Integer.toString(x*y_dim/y);
	  //image(img, 0, 0, 600,650);
	  fill(235);
	  stroke(135);
	  rect(offset,0,x2_dim-2,y_dim);
	  fill(50);
	  textSize(8);
	  text(filename,x_dim-x2_dim/2,70);
	  textSize(11);
	  strokeWeight(3);
	  for (Label l : labels) {
	    l.display();
	    l.rollover(mouseX, mouseY);
	  }
	  strokeWeight(1);
	  // rettangolo di debug
	  fill(220);
	  rect(offset,0,x2_dim -2 ,20);
	  
	  //bottone save;
	  fill(190);
	  rect(offset+30, Y_label_limit+100, x2_dim-60, 30, 5);
	  fill(0);
	  textSize(15);
	  text("save", offset+20+(x2_dim-60)/2, Y_label_limit+120 );
	  
	  fill(190);
	  rect(offset+30, Y_label_limit+150, x2_dim-60, 30, 5);
	  fill(0);
	  text("load", offset+20+(x2_dim-60)/2, Y_label_limit+170 );
	  
	  // TODO SALVARE LA RESOLUTION
	  textSize(15);
	  text("1 m (n/m)(+/-)", offset+20+(x2_dim-60)/2, Y_label_limit+200 );
	  textSize(8);
	  text("1m",offset+20+(x2_dim-60)/2, Y_label_limit+208);
	  line(offset+20+(x2_dim-60)/2-resolution/2,Y_label_limit+210,offset+20+(x2_dim-60)/2+resolution/2,Y_label_limit+210);
	  line(offset+20+(x2_dim-60)/2-resolution/2,Y_label_limit+212,offset+20+(x2_dim-60)/2-resolution/2,Y_label_limit+208);
	  line(offset+20+(x2_dim-60)/2+resolution/2,Y_label_limit+212,offset+20+(x2_dim-60)/2+resolution/2,Y_label_limit+208);
	  textSize(15);
	  
	  fill(190);
	  rect(offset+30, Y_label_limit+230, x2_dim-60, 30, 5);
	  fill(0);
	  text("add groups", offset+20+(x2_dim-60)/2, Y_label_limit+250 );
	  
	  textSize(11);
	  
	  stroke(10);
	  for (int i=0; i < c.size(); i++)
	    ((Connection) c.get(i)).display();
	  stroke(220);
	  for (int i=0; i < n.size(); i++)
	    ((Node) n.get(i)).display();
	  
	  deb(message);
	  //deb(Integer.toString(mouseX));
	  
	}

	public void mousePressed() 
	{ 
	   
	  boolean found = false;
	  if ((mouseX < offset) && (current_label != -1))  
	  {
	    fill(255);
	    // SE NON STO AGGIUNGENDO UNA STANZA
	    if (insert_room == -1) {
		    // Cerco se ho selezionato già un nodo.
		    for (int i=0; i < n.size(); i++)
		        if  (n.get(i).occupied(mouseX,mouseY)) {
		          found = true;
		          if (last != -1 ) {
		            n.get(last).setOpacity(Globals.opacity2);
		          }
		          last = lastbu;
		          lastbu = i;
		          n.get(i).setOpacity(Globals.opacity1);
		         break;
		        }
		    // Se non lo ho selezionato, inserisco un nodo.
		    if (!found) {
		      ellipse(mouseX,mouseY,10,10);
		      Label l = labels[current_label];
		      n.add(new Node(this, n.size(), mouseX, mouseY, l, current_label, false, l.r, l.g, l.b));
		      last_insert = 1;
		      insert_room = n.size()-1;
		      n.get(n.size() - 1).setStroke(Globals.rn_active, Globals.gn_active, Globals.bn_active);
		      if (last != -1 ) {
		            n.get(last).setOpacity(Globals.opacity2);
		          }
		      if (lastbu != -1 ) {
		            n.get(lastbu).setOpacity(Globals.opacity2);
		          }
		      last = -1;
		      lastbu = -1;
		    }
		    else {
		    // TODO RIMUOVERE LE CONNESSIONI FATTE IN QUESTO MODO
		    // altrimenti se ho selezionato il nodo, sto facendo la connessione.
		      if ((last!=-1) && (lastbu!=-1)) {
		        last_insert = 2;
		        c.add(new Connection(this, last, lastbu, n.get(last).x, n.get(last).y, n.get(lastbu).x, n.get(lastbu).y));
		        n.get(last).setOpacity(Globals.opacity2);
		        n.get(lastbu).setOpacity(Globals.opacity2);
		        last = -1;
		        lastbu = -1;
		      }
		    }
	    }
	    else {
	    	// STO AGGIUNGENDO UNA NUOVA STANZA
	    	addPoint(mouseX, mouseY);
	    	last_insert = 3;
	    }
	  }
	  else{
	   // sono nella parte dove si salva.
	   last_insert = 0;
	   if ((mouseX >= offset+30) && (mouseX <= offset+x2_dim-30) && (mouseY >= Y_label_limit+100) && (mouseY <= Y_label_limit+130)){
	         saveGraph();
	         message = "saved";
	   }
	   if ((mouseX >= offset+30) && (mouseX <= offset+x2_dim-30) && (mouseY >= Y_label_limit+150) && (mouseY <= Y_label_limit+180)){
	         //loadGraph();  
	         selectInput("Select a file to process:", "fileSelected");
	         message = "loaded";
	   }
	    
	    
	    
	   found = false;
	   int new_selected=-1;
	   // seleziono il colore da mettere giù
	    for (int i=0; i< labels.length; i++ ) {
	        if (labels[i].occupied(mouseX,mouseY)) {
	          found = true;
	          new_selected = i;
	          break;
	        }
	    }
	    if (found) {
	      if(current_label != -1) {
	        labels[current_label].switch_val();
	      }
	       current_label = new_selected;
	       labels[current_label].switch_val();
	    }
	  }
	  redraw();
	}

	public void keyPressed(){
	  switch (key) {
	  // TODO commentare cosa fanno da qualche parte
	  case BACKSPACE:
		  // last_inser vale 0 se non ho fatto nulla. se vale 1 ho inserito un nodo; se vale 2 ho inserito un arco;
		  switch (last_insert) {
          case 1:  last_insert = 0;
                   n.remove(n.size() - 1);
                   break;
          case 2:  last_insert = 0;
                   c.remove(c.size() - 1);
                   break;
          case 3: // CASO IN CUI HO INSERITO UN PUNTO TODO
        	  	 n.get(insert_room).removeLastPoint();
        		  xp1 = X.get(X.size()-2);
        		  yp1 = Y.get(Y.size()-2);
        		  X.remove(X.size()-1);
        		  Y.remove(Y.size()-1);
        		  L.remove(L.size()-1);
          		 break;
          default: break;
		  }
		  break;
	  case 'c':
	  case 'C': 
		  if (set_door == 0 )
			  set_door = 1;
		  else 
			  set_door = 0;
		  break;
		  // TODO SPOSTO I PUNTI ANCHE NEL GLOBALE CHE NON LO HO FATTO
	  case 'w': 
	  case 'W':
		  // TODO ex case UP
		  if (insert_room == -1)
			  return;
		  n.get(insert_room).movePoint(1);
		  break;
	  case 's': 
	  case 'S':
		  //TODO ex caso down
		  if (insert_room == -1)
			  return;
		  n.get(insert_room).movePoint(2);
		  break;
	  case 'a': 
	  case 'A':
		  // TODO ex caso left
		  if (insert_room == -1)
			  return;
		  n.get(insert_room).movePoint(4);
		  break;
	  case 'd': 
	  case 'D':
		  // TODO ex caso RIGHT
		  if (insert_room == -1)
			  return;
		  n.get(insert_room).movePoint(3);
		  break;
	  case 'm': // INGRANDISCO LA SCALA
	  case 'M':
		  resolution+=1;
		  break;
	  case 'n': // RIDUCO LA SCALA
	  case 'N':
		  resolution -=1;
		  break;
	  case 'i':
	  case 'I':
		  if (show_image)
			  show_image = false;
		  else
			  show_image = true;
		  break;
	
	  }	
	
    message = String.valueOf(key);
    //message = "asdallalla";ù
    
  
    redraw();
	}

	
	public void addPoint(int mX, int mY) {
	// LA FUNZIONE CHE GESTISCE L'AGGIUNTA DI UN PUNTO ALLA MAPPA.
	
	// se fa una retta quasi orizzontale o quasi verticale, 
	// la rendo orizzontale o verticale
	    xp1 = xp; 
	    yp1 = yp;
	    xp = mX;
	    yp = mY;
	    
	    if (abs(xp-xp1)<Globals.shape)
	      xp = xp1;
	    if (abs(yp-yp1)<Globals.shape)
	      yp = yp1;
	  
	    float tmpX1 = 0;
	    float tmpY1 = 0;
	    float tmpX2 = 0;
	    float tmpY2 = 0;
	    float tmpX = 0;
	    float tmpY = 0;
	    boolean foundSegment = false;
	    // TODO se è collineare o quasi ad un altro segmento, li matcho.
	    // TODO CASO 1: sono orizzontali
		for (int i=0; i < L.size(); i++){
		  tmpX1 = (float)L.get(i).getX1();
		  tmpY1 = (float)L.get(i).getY1();
		  tmpX2 = (float)L.get(i).getX2();
		  tmpY2 = (float)L.get(i).getY2();
		  // CASO 1: linea orizzontale.
		  if (!(foundSegment) && (tmpX1 == tmpX2) && abs(xp-tmpX1)<Globals.p_distance && (((yp>=tmpY1)&&(yp<=tmpY2)) || ((yp>=tmpY2)&&(yp<=tmpY1)))) {
		    // CASO 1.1: se anche l'altra linea è orizzontale la allineo (TODO: backtracking)
		    if (xp1 == xp) {
		        xp1 =Math.round(tmpX1);
		        //TODO FARE BACKTRACKING DEL PUNTO.  
		    }
		    xp = Math.round(tmpX1);
		    foundSegment = true;  
		
		  }
		  // CASO 2: sono verticali
		   if (!(foundSegment) && (tmpY1 == tmpY2) && abs(yp-tmpY1)<Globals.p_distance && (((xp>=tmpX1)&&(xp<=tmpX2)) || ((xp>=tmpX2)&&(xp<=tmpX1)))) {
		    // CASO 1.1: se anche l'altra linea è orizzontale la allineo (TODO: backtracking)
		    if (yp1 == yp) {
		        yp1 =Math.round(tmpY1);
		        //TODO FARE BACKTRACKING DEL PUNTO.  
		    }
		    yp = Math.round(tmpY1);
		    foundSegment = true;  
		  }
		  // TODO CASO 3: sono generici
		  if (!(foundSegment) && (L.get(i).ptLineDistSq(xp,yp)< Globals.p_distance) && (((xp>=tmpX1)&&(xp<=tmpX2)) || ((xp>=tmpX2)&&(xp<=tmpX1))) && (((yp>=tmpY1)&&(yp<=tmpY2)) || ((yp>=tmpY2)&&(yp<=tmpY1))))
			  {
			  foundSegment= true;
			  float m= (tmpY2-tmpY1)/(float)(tmpX2-tmpX1);
			  float mp = -1/m;
			  float q = (float)(tmpY2)-m*(float)(tmpX2);
			  float qp = (float)(yp) - mp*(float)(xp);
			  float intersectsX = (qp-q)/(m-mp);
			  float intersectsY = m*intersectsX + q;
			  stroke(90,155,21);
			  ellipse(Math.round(intersectsX),Math.round(intersectsY), 10,10);
			  xp = Math.round(intersectsX);
			  yp = Math.round(intersectsY);
			  //(y2-y1)/(x2-x1) = M; m' = -1/; -> da qii calcolarsi le puttanate.  
			  }
		  }
		//  se è vicino ad un punto li matcho.
		int door_index = -1;
		for (int i=1; i < X.size(); i++){
		  tmpX = X.get(i);
		  tmpY = Y.get(i);
		  if (pow(tmpX-xp,2)+pow(tmpY-yp,2) < Globals.p_distance*Globals.p_distance ){
		    // TODO caso particolare: se è verticale o orizzontale, devo rendere la retta verticale o orizzontale.
			if (D.contains(i)){
				door_index = i;
			}
		    if (xp == xp1) {
		        xp1 = Math.round(tmpX);
		        // TODO BACKTRACK
		        // TODO ANCHE NELLA CAMERA
		        // TODO ANCHE LA LINEA
		        
		        X.set(X.size()-1, xp1);
		        L.get(L.size()-1).x1=(float)xp1;
		        if (!n.get(insert_room).firstPoint())
		        	n.get(insert_room).setLastX(xp1);
		        
		    }
		    if (yp == yp1) {
		       yp1 = Math.round(tmpY);
		       Y.set(Y.size()-1, yp1);
		       L.get(L.size()-1).y1=(float)yp1;
		       if (!n.get(insert_room).firstPoint())
		    	   n.get(insert_room).setLastY(yp1);
		       // TODO fare si che i punti siano matchati giusti in qualche modo.
		       // TODO ANCHE NELLA CAMERA
		       //TODO ANCHE LA LINEA
		       //TODO BACKTRACK
		         }
		        xp = Math.round(tmpX);
		        yp = Math.round(tmpY);
		      }
		    }
	    X.add(xp);
	    Y.add(yp);
	    if (xp1 != -1)
	    	L.add(new Line2D.Float(xp,yp,xp1,yp1));
	    
	    if (insert_room != -1) {
		    // TODO lo aggiungo anche alla stanza
		    n.get(insert_room).addPoint(xp, yp);

		    if (set_door == 1){
		    	set_door = 0;
		    	D.add(X.size()-1);
		    	D2.add(insert_room);
		    	//non aggiunge le porte
		    	// TODO NON VA ADD  DOOOR
		    	if (insert_room != -1){
		    		n.get(insert_room).addDoor(xp, yp);
		    		if (door_index != -1) {
		    			// Ho trovato una porta, aggiungo una connessione.
		    			addDoor(door_index, X.size()-1);
		    		}
		    	}
		    	// TODO STAMPARLO COME PORTAs
		    	// AGGIUNGERLO ALLA PORTA
		    }
	        if (n.get(insert_room).closedRoom()) {
	        	// se il mio punto è già nella stanza chiudo la stanza.
	        	insert_room = - 1;
	        	n.get(n.size() - 1).setStroke(Globals.rn_unactive, Globals.gn_unactive, Globals.bn_unactive);
	        	xp = -1;
	        	yp = -1;
	        	last_insert = 0;
	        }
	    }
	  }

	  public void addDoor(int index1, int index2) {
		  // una stanza è sicuramente id1.
		  int id1 = insert_room;
		  int id2 = D2.get(D.indexOf(index1));
		  c.add(new Connection(this, id1, id2, n.get(id1).x, n.get(id1).y, n.get(id2).x, n.get(id2).y));
		  c.get(c.size()-1).setDoor(X.get(index1), Y.get(index1), index1, index2);
		  n.get(id1).addConnection(c.size()-1);
		  n.get(id2).addConnection(c.size()-1);
	  }
	
	public void loadData() {

	  int counter = 1;
	  
	  // Load XML file
	  xml = loadXML("/Users/matteoluperto/Documents/eclipseworkspace/floorplans/labels.xml");
	  XML buildingElement = xml.getChild("buildingtype");
	  XML building_name = xml.getChild("type_name");
	  buildingtype = building_name.getContent();
	  message = buildingtype;
	  
	  XML Xlabels = xml.getChild("labels");
	  XML[] children = Xlabels.getChildren("label");

	  // The size of the array of Bubble objects is determined by the total XML elements named "bubble"
	  labels = new Label[children.length]; 
	  //deb( Integer.toString(labels.length));
	  int xo = X_label_offset  + offset ;
	  int yo = 0;
	  for (int i = 0; i < labels.length; i++) {
	    
	    XML typeElement = children[i].getChild("type");
	    String type = typeElement.getContent();
	    
	    XML nameElement = children[i].getChild("name");
	    String name = nameElement.getContent();
	    
	    // The position element has two attributes: x and y
	    XML colorElement = children[i].getChild("color");
	    // Note how with attributes we can get an integer or float via getInt() and getFloat()
	    int r = colorElement.getInt("r");
	    int g = colorElement.getInt("g");
	    int b = colorElement.getInt("b");

	    if (yo > Y_label_limit) {
	      xo += X_label_shift;
	      counter = 1;
	    }
	    yo = Y_label_offset * counter ;
	    counter ++;
	    // Make a Bubble object out of the data read
	    labels[i] = new Label(this, name, type, r, g, b, Globals.shape*2, xo, yo);
	  }  

	}

	public void fileSelected(File selection) {
	  if (selection == null) {
	    message = "Window was closed or the user hit cancel.";
	  } else {
	    filename  =  selection.getAbsolutePath();
	    message = "loaded";
	    img = loadImage(filename);
	    x = img.width;
	    y = img.height;
	    //image(img, 0, 0,x_dim-x2_dim, (x_dim-x2_dim)/x*y);
	    //image(img, 0, 0, width/10, height/10);
	    redraw();
	  }
	}
	public void saveGraph() {
	  String fileNext = filename.substring(0, filename.lastIndexOf('.')) + ".xml";
	  XML  savedata = new XML("prova");
	  XML nodes = savedata.addChild("nodes");
	  XML connections = savedata.addChild("connections");
	  for (int i=0; i < n.size(); i++) 
	  { 
	    n.get(i).toXML(nodes);      
	  }
	  for (int i=0; i < c.size(); i++) 
	  { 
	    c.get(i).toXML(connections);      
	  }
	  saveXML(savedata, fileNext);
	  message = "lallalalla";

	}
	public void deb(String s) {
	   fill(0);
	   text(s,offset+10+(x2_dim-60)/2,15);
	}
	
}