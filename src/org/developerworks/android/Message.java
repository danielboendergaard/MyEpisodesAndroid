package org.developerworks.android;

import java.net.MalformedURLException;
import java.net.URL;

public class Message implements Comparable<Message>{
//	static SimpleDateFormat FORMATTER = 
//		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	private String showName;
	private String episodeTitle;
	private String episodeNumber;
	private String episodeDate;
	private URL link;
	private String description;
//	private Date date;

//	public String getTitle() {
//		return this.showName;
//	}

	public void setTitle(String title) {
		//The MyEpisodes RSS has all show data in the title so we'll have to do a bit of parsing here
		String showData = title.trim();
		
		//Input is [ ShowName ][ episodeNumber ][ episodeTitle ][ episodeDate ]
		
		//Delete 2 characters from start and end of string
		showData = showData.substring(2, showData.length()-2);
		
		//Split string
		String[] showDataSplit = showData.split(" \\]\\[ ");
		
		this.showName = showDataSplit[0];
		this.episodeNumber = showDataSplit[1];
		this.episodeTitle = showDataSplit[2];
		this.episodeDate = showDataSplit[3];
	}
	
	//New getters
	
	public String getShowName() {
		return this.showName;
	}
	
	public String getEpisodeTitle() {
		return episodeTitle;
	}
	
	public String getEpisodeNumber() {
		return this.episodeNumber;
	}
	
	public String getepisodeDate() {
		return this.episodeDate;
	}
	
	// getters and setters omitted for brevity 
	public URL getLink() {
		return link;
	}
	
	public void setLink(String link) {
		try {
			this.link = new URL(link);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

//	public String getDate() {
//		return FORMATTER.format(this.date);
//	}

//	public void setDate(String date) {
//		// pad the date if necessary
//		while (!date.endsWith("00")){
//			date += "0";
//		}
//		try {
//			this.date = FORMATTER.parse(date.trim());
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	public Message copy(){
		Message copy = new Message();
		copy.showName = showName;
		copy.episodeNumber = episodeNumber;
		copy.episodeTitle = episodeTitle;
		copy.episodeDate = episodeDate;
		copy.link = link;
		copy.description = description;
//		copy.date = date;
		return copy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(showName);
		sb.append('\n');
		sb.append("Date: ");
//		sb.append(this.getDate());
		sb.append('\n');
		sb.append("Link: ");
		sb.append(link);
		sb.append('\n');
		sb.append("Description: ");
		sb.append(description);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((showName == null) ? 0 : showName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
//		if (date == null) {
//			if (other.date != null)
//				return false;
//		} else if (!date.equals(other.date))
//			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (showName == null) {
			if (other.showName != null)
				return false;
		} else if (!showName.equals(other.showName))
			return false;
		return true;
	}

	public int compareTo(Message another) {
		if (another == null) return 1;
		// sort descending, most recent first
		return 0;
		//return another.date.compareTo(date);
	}
}
