package mtvisualizer;

import mtvisualizer.components.EffectBoxUIComponent;
import mtvisualizer.components.VisualizationComponent;
import netP5.NetAddress;

import org.mt4j.AbstractMTApplication;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.transition.SlideTransition;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import floader.visuals.IVisual;
import floader.visuals.VisualConstants;
import oscP5.OscMessage;
import oscP5.OscP5;

public class VisualizationScene extends AbstractScene {

	AbstractMTApplication app;
	VisualizationComponent vizComp;
	//NanoUIComponent uiComp;
	EffectBoxUIComponent uiComp;
	OscP5 oscP5;
	NetAddress remoteAddress;
	SlideTransition slideLeftTransition;
	
	public VisualizationScene(AbstractMTApplication app, OscP5 oscP5, NetAddress remoteAddress, String visualName) {
		super(app, visualName);
		this.app = app;
		this.oscP5 = oscP5;
		this.remoteAddress = remoteAddress;
		this.registerGlobalInputProcessor(new CursorTracer(app, this));
		this.setTransition(new SlideTransition(app, 800, true));
		
		vizComp = new VisualizationComponent(app, visualName);
		vizComp.translate(new Vector3D(app.width/2, app.height/2, 0, 1));
		uiComp = new EffectBoxUIComponent(app, oscP5, remoteAddress, vizComp.getIVisual());
		//this.setClearColor(new MTColor(200f,0f,0f,20f));
		
	}

	public void oscEvent(OscMessage msg) {
		if(msg.checkAddrPattern(VisualConstants.OSC_CTRL_PATH))
		{
			vizComp.getIVisual().ctrlEvent(msg.get(0).intValue(), msg.get(1).intValue(), msg.get(2).intValue());
		}else if (msg.checkAddrPattern(VisualConstants.OSC_NOTE_PATH)) {
			if (msg.get(2).intValue() == VisualConstants.OBJECT_EVENT_CHANNEL)
				vizComp.getIVisual().noteObjEvent(msg.get(0).intValue(), msg.get(1).intValue());
			else if (msg.get(2).intValue() == VisualConstants.CAM_EVENT_CHANNEL)
				vizComp.getIVisual().noteCamEvent(msg.get(0).intValue(), msg.get(1).intValue());
		}
		
		
	}
	
	public void setVizDraw(boolean draw)
	{
		vizComp.draw = draw;
	}

	@Override
	public void onEnter() {
		this.getCanvas().addChild(vizComp);
		vizComp.draw = true;
		this.getCanvas().addChild(uiComp);
		
	}

	@Override
	public void onLeave() {
		vizComp.draw = false;
		vizComp.destroy();
		uiComp.destroy();
	}
	
	public IVisual getIVisual()
	{
		return vizComp.getIVisual();
	}
}
