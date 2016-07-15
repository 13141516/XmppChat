package com.xmpp.client.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Base64;

public class FileUtil {
	/**
	 * @param filePath is a file path 
	 * @return 
	 */
	public static String getBase64StringFromFile(String filePath)
	{
		InputStream in = null;
		byte[] data = null;
		try
		{
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		/* ���ֽ�����Base64����,����Base64��������ֽ������ַ� */
		return Base64.encodeToString(data, Base64.DEFAULT);
	}
	
	public static boolean saveFileByBytes(byte[] data,String filePath) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
        	 file = new File(filePath);
             if (!file.exists()) {
 				File file2 = new File(filePath.substring(0, filePath.lastIndexOf("/")+1));
 				file2.mkdirs();
 			}
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return true;
    }
	
	public static boolean saveFileByBase64(String fileString, String filePath) {
		if (fileString == null)
			return false;
		byte[] data = Base64.decode(fileString, Base64.DEFAULT);
        saveFileByBytes(data, filePath);
        return true;
    }
	
	public static String getFileName(String url) {
		String fileName = "";
		if (url != null) {
			fileName = url.substring(url.lastIndexOf("/") + 1);
		}
		return fileName;
	}
	
	  public static boolean deleteFile(String filePath) {
		    File file = new File(filePath);
	        if (file.isFile() && file.exists()) {
	        	return file.delete();
	        }
	        	return false;
	  }
	  
	  public static boolean deleteDirectory(String filePath) {
		    boolean flag = false;
		        //���filePath�����ļ��ָ�����β���Զ�����ļ��ָ���
		        if (!filePath.endsWith(File.separator)) {
		            filePath = filePath + File.separator;
		        }
		        File dirFile = new File(filePath);
		        if (!dirFile.exists() || !dirFile.isDirectory()) {
		            return false;
		        }
		        flag = true;
		        File[] files = dirFile.listFiles();
		        //����ɾ���ļ����µ������ļ�(������Ŀ¼)
		        for (int i = 0; i < files.length; i++) {
		            if (files[i].isFile()) {
		            //ɾ�����ļ�
		                flag = deleteFile(files[i].getAbsolutePath());
		                if (!flag) break;
		            } else {
		            //ɾ����Ŀ¼
		                flag = deleteDirectory(files[i].getAbsolutePath());
		                if (!flag) break;
		            }
		        }
		        if (!flag) return false;
		        //ɾ����ǰ��Ŀ¼
		        return dirFile.delete();
		    }
}
