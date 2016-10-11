package model;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollection {
	
	protected List<Photo> collection ;
	
	private static PhotoCollection instance = null;
	
	private PhotoCollection(){
		collection = new ArrayList<>();
	};
	
	public static PhotoCollection getInstance() {
		if(instance==null) {
			instance = new PhotoCollection();
		}
		
		return instance;
	}
	
	public int addPhoto(Photo newPhoto) {
		collection.add(newPhoto);
		return collection.size()-1;
	}
	
	public Photo getPhoto(int index) {
		if(collection.size()-1 < index) {
			System.err.println("Stop doing shit : the index is to large ");
			return null;
		}
		else {
			return collection.get(index);
		}
	}

}
