package mtvisualizer;

import org.mt4j.components.visibleComponents.AbstractVisibleComponent;

import processing.core.PApplet;
import processing.core.PGraphics;
import wblut.core.processing.WB_Render;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.creators.HEC_Cone;


public class SimpleComponent extends AbstractVisibleComponent{

	HE_Mesh cone;
	WB_Render render;
	
	public SimpleComponent(PApplet pApplet) {
		super(pApplet);
		// TODO Auto-generated constructor stub
		HEC_Cone conCreator = new HEC_Cone();
		conCreator.setRadius(100).setHeight(40).setFacets(10).setSteps(10);
		cone = new HE_Mesh(conCreator);
		render = new WB_Render(pApplet);
	}

	@Override
	public void drawComponent(PGraphics g) {
		// TODO Auto-generated method stub
		g.fill(255, 255, 255);
		g.translate(400, 400);
		g.stroke(0);
		
		render.drawEdges(cone);
		render.drawFaces(cone);
	}

}