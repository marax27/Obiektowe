package SubtitlesAdjuster;

import java.util.*;
import java.util.regex.*;

// Reprezentacja pojedynczej linii napisow.
class SubtitleLine{
	public SubtitleLine(int t1, int t2, String text){
		this.t1 = t1;
		this.t2 = t2;
		this.text = text;
	}

	public SubtitleLine(String line) throws LineException {
		//Format: {t1}{t2}text
		Matcher m = subtitle_pattern.matcher(line);
		if(m.matches()){
			int n = 1;
			try{
				this.t1 = Integer.parseInt(m.group(1));
				++n;
				this.t2 = Integer.parseInt(m.group(2));
				this.text = m.group(3);
				// this.text = line.substring(m.group(1).length() + m.group(2).length() + 4);
			}catch(NumberFormatException exc){
				throw new LineException(
					String.format("Failed to read a %d. number from '%s'\n", n, line
				));
			}
		}
		else
			throw new LineException(String.format("Invalid format: line '%s'\n", line));
	}

	public String toString(){
		return String.format("{%d}{%d}%s", t1, t2, text);
	}

	// "Skladniki" linii napisow.
	private int t1,
	            t2;
	private String text;

	// Get
	public int getStartTime(){ return t1; }
	public int getEndTime(){ return t2; }
	public String getText(){ return text; }

	// Set
	public void setStartTime(int t){ t1 = t; }
	public void setEndTime(int t){ t2 = t; }
	public void setText(String t){ text = t; }

	// Regex Pattern
	private static final Pattern subtitle_pattern;
	static{
		subtitle_pattern = Pattern.compile("\\{(\\d+)\\}\\{(\\d+)\\}(.*)");
	}

	public static class LineException extends RuntimeException {
		public LineException(String error_message){
			super(error_message);
		}
	}
}