import java.net.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;


public class BabyNameHelper{
	
	private static final String USER_AGENT = "Mozilla/5.0";
	/*
	Host: www.ssa.gov
	User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:64.0) Gecko/20100101 Firefox/64.0
	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*//*;q=0.8
	Accept-Language: en-US,en;q=0.5
	Accept-Encoding: gzip, deflate, br
	Content-Length: 28
	Connection: keep-alive
	Pragma: no-cache
	Cache-Control: no-cache
*/
	public static ArrayList<BabyName> badlyParse(String htmlTable, int year){
		Document doc =Jsoup.parse(htmlTable);
		Elements data = doc.getElementsByTag("tr");
		int keepTrack = 0;
		ArrayList<BabyName> names = new ArrayList<BabyName>();
		for (Element d : data){
			if(keepTrack < 4 || keepTrack > data.size() - 4){
				keepTrack++;
				continue;
			}
			BabyName name1, name2;
			String[] rankMaleFemale = d.text().split("\\s+");
			int rank = Integer.parseInt(rankMaleFemale[0]);
			name1 = new BabyName(rank, 'M', rankMaleFemale[1], year);
			name2 = new BabyName(rank, 'F', rankMaleFemale[3], year);
			names.add(name1);
			names.add(name2);				
			keepTrack++;
		}
		return names;
	}



	public static ArrayList<BabyName> makeRequest(int year, int num) throws IOException{
		URL requestUrl = new URL("https://www.ssa.gov/cgi-bin/popularnames.cgi");
		String POST_PARAMS = String.format("year=%d&top=%d&number=p", year, num);
		HttpsURLConnection con = (HttpsURLConnection) requestUrl.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		if (con.getResponseCode() == 200){
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine())!=null){
				response.append(inputLine);
			}
			in.close();
			return badlyParse(response.toString(), year);
		} else{
			return new ArrayList<BabyName>();
		}
	}
}
/*class BabyName implements Serializable, Comparable<BabyName>{
    private int rank;
    private char gender;
    private String name;
    private int year;

    public BabyName(int rank, char gender, String name, int year){
        this.rank = rank;
        this.gender = gender;
        this.name = name;
        this.year = year;
    }

    @Override
    public int compareTo(BabyName name2) {
        return this.getName().compareTo(name2.getName());
    }

    public String getName(){
    	return this.name;
    }	
    public int getRank(){
        return rank;
    }

    public char getChar(){
        return gender;
    }

    public String toString(){
        return String.format("%s is ranked #%d in year %d", this.name, this.rank, this.year);
    }
}
*/
