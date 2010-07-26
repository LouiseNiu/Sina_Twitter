/**
 *
 */
package twitter4j.http;

import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
//import java.util.Iterator;

//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.MemoryCacheImageInputStream;

//import com.sun.imageio.plugins.bmp.BMPImageReader;
//import com.sun.imageio.plugins.gif.GIFImageReader;
//import com.sun.imageio.plugins.jpeg.JPEGImageReader;
//import com.sun.imageio.plugins.png.PNGImageReader;

/**
 * 临时存储上传图片的内容，格式，文件信息等
 * 
 * @author zhulei
 * 
 */
public class ImageItem {
	private byte[] content;
	private String name;
	private String contentType;
	
	/*
	
	public ImageItem(String name,byte[] content) throws Exception{
		String imgtype=getContentType(content);
		
	    if(imgtype!=null&&(imgtype.equalsIgnoreCase("image/gif")||imgtype.equalsIgnoreCase("image/png")
	            ||imgtype.equalsIgnoreCase("image/jpeg"))){
	    	this.content=content;
	    	this.name=name;
	    	this.content=content;
	    }else{
	    	throw new IllegalStateException(
            "Unsupported image type, Only Suport JPG ,GIF,PNG!");
	    }
	    
	
	}
    */
public ImageItem(String filePath) throws Exception{
	
	BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(filePath));
    int len=bufferedInputStream.available();
    byte[] bytes=new byte[len];
    int r=bufferedInputStream.read(bytes);
    if(len!=r)
    {
      bytes=null;
      throw new IOException("testtest");
    }
    bufferedInputStream.close();
    
    this.content=bytes;
	this.name="pic";
	this.contentType= getContentType(filePath);    
	
	}
	
	public byte[] getContent() {
		return content;
	}
	public String getName() {
		return name;
	}
	public String getContentType() {
		return contentType;
	}
	
public String getContentType(String filePath) throws Exception{
	String type = "";
	String mimeType = "";
	
	int pos = filePath.lastIndexOf(".");
	 System.out.println("position of . is " + pos );
	   if(pos==-1)
	      type = ""; 
	   else
		   type = filePath.substring(pos, filePath.length()); 
	   System.out.println("type is  " + type );
	   
     if(type.equalsIgnoreCase(".jpg"))
    	 mimeType = "image/jpg";
     else if(type.equalsIgnoreCase(".jpeg"))
    	 mimeType = "image/lpeg";
     else if(type.equalsIgnoreCase(".png"))
    	 mimeType = "image/png";
     else if (type.equalsIgnoreCase(".gif"))
    	 mimeType ="image/gif";
     else{  	 
    	 throw new IllegalStateException(
         "Unsupported image type, Only Suport JPG ,GIF,PNG!");
         }

     return mimeType;     
	 }
    

/*
	public static String getContentType(byte[] mapObj) throws IOException {

		String type = "";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(mapObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader) {
					type = "image/gif";
				} else if (reader instanceof JPEGImageReader) {
					type = "image/jpeg";
				} else if (reader instanceof PNGImageReader) {
					type = "image/png";
				} else if (reader instanceof BMPImageReader) {
					type = "application/x-bmp";
				}
			}
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {

				}
			}
			if (mcis != null) {
				try {
					mcis.close();
				} catch (IOException ioe) {

				}
			}
		}
		return type;
	}
	*/
}
