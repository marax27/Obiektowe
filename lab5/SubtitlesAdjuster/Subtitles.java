package SubtitlesAdjuster;

import java.io.*;
import java.util.*;

public class Subtitles{
	// addOffset - funkcja wczytujaca
	public static void addOffset(
		String input_filename, String output_filename,
		int delay_milliseconds, int fps)
			throws IOException, SubtitlesException{
		
		int offset = delay_milliseconds * fps / 1000;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try{
			reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(input_filename), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(
    			new FileOutputStream(output_filename), "UTF-8"));

			// Wczytanie wszystkich linii do kontenera.
			String line = reader.readLine();
			SubtitleLine sl;
			int line_number = 1;
			while(line != null){
				try{
					sl = new SubtitleLine(line);
				}catch(SubtitleLine.LineException exc){
					throw new SubtitlesException(
						String.format("Error while reading '%s' at line %d: %s\n",
							input_filename, line_number, exc.getMessage()
					));
				}

				// Wykonanie przesuniecia.
				sl.setStartTime( sl.getStartTime() + offset );
				sl.setEndTime( sl.getEndTime() + offset );

				// Zapis zmodyfikowanej linii.
				writer.write(sl.toString());
				writer.newLine();

				line = reader.readLine();
				++line_number;
			}
		}
		catch(IOException exc){
			throw exc;
		}
		finally{
			try{
				if(reader != null)
					reader.close();
				if(writer != null)
					writer.close();
			}catch(IOException exc){
				exc.printStackTrace();
			}
		}
	}

	public static class SubtitlesException extends Exception {
		public SubtitlesException(String error_message){
			super(error_message);
		}
	}
}