import java.io.*;
import java.util.*;
import java.io.Serializable;


public class BabyNameRankings{
	static String aName;
	static String fileName;
	static char gender;
	static int startYear;
	static int endYear;
	public static void main(String[] args){
		if(args.length == 0){
			Scanner kb = new Scanner(System.in);
			System.out.print("Enter The Year: ");
			fileName = kb.nextLine().trim();
			ArrayList<BabyName> names = readIn(fileName);
			System.out.print("Enter the Gender: ");
			gender = kb.nextLine().trim().toUpperCase().charAt(0);
			System.out.print("Enter The name: ");
			aName = kb.nextLine();
			String searchFor = aName.substring(0, 1).toUpperCase() + aName.substring(1);
			System.out.println(binarySearch(searchFor, gender, names)); 
		}
		else{
            parseArgs(args);
            for(int i = startYear; i <= endYear; i++){
				lazyFunctionName(i);
			}
		}
	}

	public static void lazyFunctionName(int year){
		ArrayList<BabyName> names = readIn(Integer.toString(year));
		System.out.println(binarySearch(aName, gender, names));
	}

	public static void parseArgs(String[] args){
		System.out.println("FUCK");
		if(args[0].matches("\\b+\\w+\\b")){
			System.out.println("FUCK");
			try{
				try{
					aName = args[0];
					if(args[1].matches("[MF]")){
							gender = args[1].trim().toUpperCase().charAt(0);
					} 
					else {
							System.out.println("Program expects [name] [MF]");
							System.exit(127);
					}
				} catch(IndexOutOfBoundsException e){
						System.out.println("You forgot the gender and as we all know\ngender is the most important thing in the world\ntry again and this time specify a single charecter gender marker after the name");
						System.exit(127);
				}
				try{
					if(args[2].equals("-y")){
						System.out.println("FUCK");
						String[] fuckThisAll = args[3].split("-");
						startYear = Integer.parseInt(fuckThisAll[0]);
						endYear = Integer.parseInt(fuckThisAll[1]);
					}
					else{
						fuck();
					}
				} catch(IndexOutOfBoundsException e){
					startYear = 1897;
					endYear = 2017;
				}
			} catch(Exception e){
				fuck();
			}
		}
/* Turns out you cant use regex in a switch statement, which, imho is complete shit
		switch (args[0]){
			case "\\+\b\w\b":
				static String Name = args[0];
				try{
					switch(args[1]){
						case [MF]:
							static char gender = args[1];
							break;
						default:
							System.out.println("Program expects [name] [MF]");
							System.exit(127);
					} catch(IndexOutOfBoundsException e){
						System.out.println("You forgot the gender and as we all know\ngender is the most important thing in the world\ntry again and this time specify a single charecter gender marker after the name");
						System.exit(127)
					}
					try{
						if(args[2].equals("-y")){
							String[] fuckThisAll = args[2].split("-");
							startYear = fuckThisAll[0];
							endYear = fuckThisAll[1];
						}
						else{
							fuck();
						}
					} catch(IndexOutOfBoundsException e){
						startYear = 1897;
						endYear = 2017;
					}
				} catch(Exception e){
					fuck();
				}
				break;
			case "-f":
				fileName = args[1];
					if(args[2].equals("-n")){
						aName = args[3];
					}
				break;
			case "-y":
				if(args[1].indexOf('-') >= 0){
					String[] fuckThisAll = args[1].split("-");
					startYear = fuckThisAll[0];
					endYear = fuckThisAll[1];
				} else {
					fuck();
				}
		}
		*/
	}

	public static void fuck(){
		System.out.println("Look Man, it's 3 in the morning, I'm not putting that much more effort into this");
		System.exit(127);
	}

	public static String binarySearch(String name, char gender, ArrayList<BabyName> aList){
		int lookieHere = (gender == 'M') ? binarySearch(aList, aList.size()/2, aList.size()-1, name) : binarySearch(aList, 0, aList.size()/2, name);
		return (lookieHere != -1) ? aList.get(lookieHere).toString() : String.format("The name %s is not ranked", name);
	}

	public static int binarySearch(ArrayList<BabyName> a, int l, int r, String name){
		if (r >= l) { 
            int mid = l + (r - l) / 2;

            if(a.get(mid).getName().compareTo(name) == 0){
            	return mid;
            }

            if(a.get(mid).getName().compareTo(name) < 0){
            	return binarySearch(a, mid + 1, r, name);
            }
            return binarySearch(a, l, mid - 1, name);

        }
        return -1;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<BabyName> readIn(String filename){
		ArrayList<BabyName> names;
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			names = (ArrayList<BabyName>) ois.readObject();
			ois.close();
			fis.close();
			return names;
		} catch (IOException ioe) {
			try {
				names = BabyNameHelper.makeRequest(Integer.parseInt(filename), 1000);
				makeObjects(filename, names);
				return names;
			}
			catch (IOException ie){
				fuck();
			}
		} catch (ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		return null;
	}

	public static ArrayList<BabyName> readIn(String filename, int year){
		ArrayList<BabyName> names = new ArrayList<BabyName>();
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null){
				BabyName name1, name2;
				String[] rankMaleFemale = line.split("\\s+");
				int rank = Integer.parseInt(rankMaleFemale[0]);
				name1 = new BabyName(rank, 'M', rankMaleFemale[1], year);
				name2 = new BabyName(rank, 'F', rankMaleFemale[2], year);
				names.add(name1);
				names.add(name2);				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e){
			try {
				names = BabyNameHelper.makeRequest(Integer.parseInt(filename), 1000);
				makeObjects(filename, names);
				return names;
			}
			catch (IOException ie){
				fuck();
			}
		} 
		return names;
	}

	public static void makeObjects(String filename, ArrayList<BabyName> fuck){
		nameSort(fuck);
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(fuck);
			oos.close();
			fos.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void nameSort(ArrayList<BabyName> names){
		for(int i = 0; i <= names.size()/2; i++){
			names.add(names.get(i));
			names.remove(i);
		}
		List<BabyName> males = names.subList(names.size()/2, names.size());
		List<BabyName> females = names.subList(0, names.size()/2);
		Collections.sort(males);
		Collections.sort(females);
		for(int i = 0; i < names.size(); i++){
			try{
				names.set(i, females.get(i));
			}
			catch (IndexOutOfBoundsException e){
				names.set(i, males.get(i-females.size()));
			}
		}

	}

}

class BabyName implements Serializable, Comparable<BabyName>{
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


// https://www.ssa.gov/cgi-bin/popularnames.cgi
