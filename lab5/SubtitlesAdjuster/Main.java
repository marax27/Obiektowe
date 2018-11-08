package SubtitlesAdjuster;

import java.io.*;
import java.lang.*;
import java.util.*;

public class Main{
	public static void main(String[] args){
		if(args.length != 4){
			System.out.println("Usage: ./app InputFile OutputFile milliseconds framerate");
			return;
		}

		// Parse numbers.
		int milliseconds, framerate;
		try{
			milliseconds = Integer.parseInt(args[2]);
			framerate = Integer.parseInt(args[3]);
		}catch(NumberFormatException exc){
			System.out.println("Error: Invalid values.");
			return;
		}

		try{
			Subtitles.addOffset(args[0], args[1], milliseconds, framerate);
			System.out.println("Operation successful.");
		}catch(IOException exc){
			System.out.format("I/O error: %s\n", exc.getMessage());
		}
		catch(Subtitles.SubtitlesException exc){
			System.out.format("Subtitles processing error: %s\n", exc.getMessage());
		}
	}
}