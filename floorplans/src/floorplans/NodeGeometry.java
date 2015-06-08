package floorplans;
import java.util.*;
import java.awt.geom.Line2D;

import processing.core.PApplet;
import static java.lang.Math.pow;

public class NodeGeometry{
  PApplet parent;
  public ArrayList<Integer> X;
  public ArrayList<Integer> Y;
  public ArrayList<Line2D.Float> L;
  public ArrayList<Integer> D;
  // indice della connessione, tra le connesioni globali, a cui appartiene la porta i-esima (D).
  public ArrayList<Integer> C;
  //TODO COSA E RESOLUTION? LA HO INVENTATA
  // TODO VA CAMBIATA
  public int resolution = Globals.resolution;
  private int x1,y1;
  
  NodeGeometry(PApplet _parent){
  this.parent = _parent;
  X = new ArrayList<Integer>();
  Y = new ArrayList<Integer>();
  L = new ArrayList<Line2D.Float>();
  // Porte
  D = new ArrayList<Integer>();
  C = new ArrayList<Integer>();
  }
  
  void addPoint(int x, int y) {
    //TODO NON SO COSA SONO
      X.add(x);
      Y.add(y);
      if (X.size() != 1) {
        L.add(new Line2D.Float(x,y,x1,y1));
      }
      x1 = x;
      y1 = y;
  }
  
  void addDoor(int x, int y) {
      D.add(this.pointIndex(x, y));
  }
  
  void addConnection(int c){
	  C.add(c);
  }
  
  int lineSize() {
    return L.size();
  }
  
  // restituisce l'indice di un punto se è parte dell'offset. -1 al contrario.
  int pointIndex(int x, int y) {
  int tmpX,tmpY;
  for (int i=0; i < X.size(); i++) {
    tmpX = X.get(i);
    tmpY = Y.get(i);
    if  (pow(tmpX-x,2)+pow(tmpY-y,2) < Globals.p_distance*Globals.p_distance) {
      return i;
    }
  }
  return -1;
  }
  
  void movePoint(int directions){
	  int index =this.X.size() -1;
	  // 1 su 2 giu 3 dx 4 sx 
	  switch (directions) {
	  case 1: 
		  Y.set(index,Y.get(index)-Globals.step);
		  L.get(L.size()-1).y1 -= (float)Globals.step;
		  break;
	  case 2: 
		  Y.set(index,Y.get(index)+Globals.step);
		  L.get(L.size()-1).y1 += (float)Globals.step;
		  break;
	  case 3: 
		  X.set(index,X.get(index)+Globals.step);
		  L.get(L.size()-1).x1 += (float)Globals.step;
		  break;
	  case 4: 
		  X.set(index,X.get(index)-Globals.step);
		  L.get(L.size()-1).x1 -= (float)Globals.step;
		  break;
	  }
	  //TODO MUOVO SU SIA IL PUNTO CHE QUELLI CHE CI SONO PRIMA
  }
  
  void setLastX(int nx) {
	  // TODO FARE sia linea che punto
 	  X.set(X.size()-1, nx);
	  L.get(L.size()-1).x1=(float)nx;
	  x1 = nx;
	  }
 
  void setLastY(int ny) {
	  // TODO Fare sia linea che punto
	  Y.set(Y.size()-1, ny);
	  L.get(L.size()-1).y1=(float)ny;
	  y1 = ny;
	  }
  
  void display(){
	  parent.stroke(10);
	  // stampo le linee
	  if (X.size() <= 1)
		  return;
	  for (int i=1; i < X.size(); i++){
		    //TODO
		    parent.line(Math.round(L.get(i-1).getX1()),Math.round(L.get(i-1).getY1()),Math.round(L.get(i-1).getX2()),Math.round(L.get(i-1).getY2()));
		    //line(X.get(i-1),Y.get(i-1),X.get(i),Y.get(i));
		    parent.ellipse(X.get(i-1),Y.get(i-1),5,5);
		    parent.ellipse(X.get(i),Y.get(i),5,5);
		  }
  }
  
  void displayDoors(){
	    //parent.fill(239,192,167);
	   	parent.fill(255);
	    for (int j=0; j < D.size();j++){
	    	int i = D.get(j);
	    	parent.rect(X.get(i)-5,Y.get(i)-5,10,10);
	    	}
  }
 
  boolean closedRoom(){
	  if (this.X.size()<3)
		  return false;
	  int tmpX1 = X.get(0);
	  int tmpY1 = Y.get(0);
	  int tmpX2 = X.get(X.size()-1);
	  int tmpY2 = Y.get(Y.size()-1);
	  if  (pow(tmpX1-tmpX2,2)+pow(tmpY1-tmpY2,2) < Globals.p_distance*Globals.p_distance) {
		  // TODO la linea che va dal primo all'ultimo c'è già?
	      return true;
	      }
	  return false;
  }
 
  void removeLastPoint(){
	  x1 = X.get(X.size()-2);
	  y1 = Y.get(Y.size()-2);
	  X.remove(X.size()-1);
	  Y.remove(Y.size()-1);
	  L.remove(L.size()-1);
  }
  /*int changePoint(int index, int x, int y) {
  X.get(i) = x;
  Y.get(i) = y;
  if (i != 0) {
    // se i è il primo cambio solo quello. Se i è l'ultimo idem.
    //Se invece è uno in mezzo devo cambiare due segmenti. E poi fare backtracking.
  }
  } 
  */
}