package model;

import scenegraph.ContainerNode;

public class Photo {
	protected String photoPath;
	protected ContainerNode sceneGraph;
	
	
	public Photo(String photoPath, ContainerNode sceneGraph) {
		super();
		this.photoPath = photoPath;
		this.sceneGraph = sceneGraph;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public ContainerNode getSceneGraph() {
		return sceneGraph;
	}
	public void setSceneGraph(ContainerNode sceneGraph) {
		this.sceneGraph = sceneGraph;
	}
	
	

}
