package cn.com.hiss.www.sharephoto.util;

import java.io.IOException;
import java.io.Serializable;

import android.graphics.Bitmap;


public class ImageItem implements Serializable {
	private String imageId;
	private String thumbnailPath;
	private String imagePath;
	private Bitmap bitmap;
	private String ossUrl;
	private boolean isSelected = false;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getOssUrl() {
		return ossUrl;
	}

	public void setOssUrl(String ossUrl) {
		this.ossUrl = ossUrl;
	}

	public Bitmap getBitmap() {
		if(bitmap == null){
			try {
				bitmap = Bimp.revisionImageSize(imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("imageId = " + imageId + " ; ");
		sb.append("thumbnailPath = " + thumbnailPath + " ; ");
		sb.append("imagePath = " + imagePath + "");
		return sb.toString();
	}
}
