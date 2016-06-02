package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	// Prevent instances of static class
	private FileUtils(){	
	}
	
	public static String loadAsString(String file){
		StringBuilder result = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = reader.readLine()) != null){
				result.append(buffer + '\n');
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static int [] [] loadMap(String path){
		
		//split by line
		String [] lines =  FileUtils.loadAsString(path).split("\n");
		
		
		//split by space
		String [] [] data = new String [lines.length][lines[0].split(" ").length];
		for (int i = 0;i< lines.length;i++){
			data[i] = lines[i].split(" ");
		}
		
		//parse into integer tile ID's
		int [] [] parsedData = new int [data.length][data[0].length];
		for (int y = 0; y < parsedData.length; y++){
			for (int x = 0; x < parsedData[0].length;x++){
				parsedData[y][x] = Integer.parseInt(data[y][x]);
			}
		}
		
		return parsedData;
		
	}
}
