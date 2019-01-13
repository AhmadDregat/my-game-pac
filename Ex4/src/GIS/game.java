package GIS;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Algorithm.ShortestPathAlgo;
import Coords.map;
import Geom.Box;
import Geom.Point3D;
import Robot.Play;

/**
 * This class represents the function that manages the games (pacmans, fruits ..)
 * It has many features.
 *
 */

public class game {

	public  ArrayList<Packman> Packmanarr = new ArrayList<>();
	public  ArrayList<Fruit> fruits_arr = new ArrayList<>();
	public  ArrayList<Box> Box_arr = new ArrayList<>();
	public  ArrayList<Ghost> ghostarr = new ArrayList<>();
	public Player player;
	public 	String directory_file = "";
	public map Map = new map();
	public Play play;



	/**
	 * Constractor
	 * @param Packmans Receiv Packmans
	 * @param Fruits Receiv Fruits
	 * @param Boxs Receiv Box
	 * @param Ghosts Receiv Ghosts
	 * @throws IOException 
	 */

	public game(ArrayList<Packman> p,ArrayList<Fruit> f,ArrayList<Box> B,ArrayList<Ghost> G) {

		this.Packmanarr = p;
		this.fruits_arr = f;
		this.Box_arr = B;
		this.ghostarr = G;

	}
	public game(Play play) throws IOException {
		this.play = play;
		makeGame(play.getBoard());
	}


	/**
	 * Getter Method
	 * @return  a Directory of file
	 */
	public String getDiractroy() {
		return this.directory_file;
	}
	/**
	 * Setter Method
	 * @param file_directory  Receiv a new file directory
	 */
	public void setfile_directory(String file_directory) {
		this.directory_file = file_directory;
	}


	public  void savetoCsv() throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(new File(getDiractroy()+".csv"));
		StringBuilder sb = new StringBuilder();
		String[] heders = {"Type","ID"	,"Lat"	,"Lon"	,"Alt"	,"Speed/Weight"	,"Radius"};

		for (int i = 0; i < heders.length; i++) {
			sb.append(heders[i]);
			sb.append(",");	
		}
		sb.append(this.Packmanarr.size());
		sb.append(',');
		sb.append(this.fruits_arr.size());
		sb.append(',');
		sb.append(this.Box_arr.size());
		sb.append('\n');

		for (int i = 0; i < Packmanarr.size(); i++) {
			Packmanarr.get(i).packLocation = Map.Pixel2coord(Packmanarr.get(i).getPack().x(), Packmanarr.get(i).getPack().y());
		}
		for (int i = 0; i < fruits_arr.size(); i++) {
			fruits_arr.get(i).Furit = Map.Pixel2coord(fruits_arr.get(i).getFruit().x(), fruits_arr.get(i).getFruit().y());

		}

		for (int i = 0; i < this.Packmanarr.size(); i++) {
			sb.append("P");
			sb.append(',');
			sb.append(i);
			sb.append(',');
			sb.append(Packmanarr.get(i).packLocation.x());
			sb.append(',');
			sb.append(Packmanarr.get(i).packLocation.y());
			sb.append(',');
			sb.append(Packmanarr.get(i).packLocation.z());
			sb.append(',');
			sb.append(this.Packmanarr.get(i).getSpeed());
			sb.append(',');
			sb.append(this.Packmanarr.get(i).getrad());
			sb.append('\n');
		}
		for (int i = 0; i < fruits_arr.size(); i++) {
			sb.append("F");
			sb.append(',');
			sb.append(i);
			sb.append(',');
			sb.append(fruits_arr.get(i).Furit.x());
			sb.append(',');
			sb.append(fruits_arr.get(i).Furit.y());
			sb.append(',');
			sb.append(fruits_arr.get(i).Furit.z());
			sb.append(',');	
			sb.append(this.fruits_arr.get(i).getWeight());
			sb.append('\n');

		}
		sb.append('\n');

		pw.write(sb.toString());
		pw.close();

	}




	public void makeGame(ArrayList<String> board) throws IOException{		

		ArrayList<String> s = board;

		Packmanarr=new ArrayList<>();
		fruits_arr=new  ArrayList<>();
		ghostarr=new ArrayList<>();
		this.Box_arr=new ArrayList<>();
		for(int i=0;i<s.size();i++) {
			String line = s.get(i);
			String[] row = line.split(",");

			if(row[0].equals("P")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = Map.coordtoPixel(p);
			
				double speed = Double.parseDouble(row[5]);
				double radius = Double.parseDouble(row[6]);
				Packmanarr.add(new Packman(p, speed, radius));	
			}
			if(row[0].equals("F")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = Map.coordtoPixel(p);
				double Weight = Double.parseDouble(row[5]);
				fruits_arr.add(new Fruit(p, Weight));
			}
			if(row[0].equals("B")) {
				Point3D p1 = new Point3D(row[2],row[3],row[4]);
				Point3D p2 = new Point3D(row[5],row[6],row[7]);
				p1 = Map.coordtoPixel(p1);
				p2 = Map.coordtoPixel(p2);
				Box_arr.add(new Box(p1,p2));

			}
			if(row[0].equals("G")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = Map.coordtoPixel(p);

				ghostarr.add(new Ghost(p, Double.parseDouble(row[5]),  Double.parseDouble(row[6])));	
			}
			if(row[0].equals("M")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = Map.coordtoPixel(p);
				
				player = new Player(p, Double.parseDouble(row[5]), Double.parseDouble(row[6]));

			}

		}


	}




	public void save_to_kml() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(getDiractroy()+".kml"));



		ArrayList<String> content = new ArrayList<String>();
		String kmlstart = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
						"<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>\r\n<name> Points with TimeStamps</name>\r\n <Style id=\"red\">\r\n" + 
						"<IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle>\r\n" + 
						"</Style><Style id=\"Packman\"><IconStyle><Icon><href>http://www.iconhot.com/icon/png/quiet/256/pac-man.png</href></Icon></IconStyle>\r\n" + 
						"</Style><Style id=\"Fruit\"><IconStyle><Icon><href>https://www.clipartmax.com/png/full/10-109149_heart-outline-clipart.png</href></Icon></IconStyle></Style>\r\n" + 
						"\r\n" + 
						"    <Style id=\"check-hide-children\">\r\n" + 
						"      <ListStyle>\r\n" + 
						"        <listItemType>checkHideChildren</listItemType>\r\n" + 
						"      </ListStyle>\r\n" + 
						"    </Style>\r\n" + 
						"    <styleUrl>#check-hide-children</styleUrl>"+
						"\r\n"+"<Folder><name>GAME PACKMAN</name>\n\n";

		content.add(kmlstart);
		String[] nameData = {"Type","id","Lat","Lon","Speed/Weight"	,"Radius"};

		String kmlend = "</Folder>\n" + 
				"</Document>\n</kml>";

		ArrayList<Packman> myPackmens = new ArrayList<>();
		ShortestPathAlgo algo = new ShortestPathAlgo(this);

		myPackmens = algo.Multialgo();

		for (int i = 0; i < myPackmens.size(); i++) {
			path p = algo.algoSinglePackman(myPackmens.get(i));
			myPackmens.get(i).getPath().setPath(p.getCPath());
			myPackmens.get(i).getPath().setTheTotalTime(p.getTheTime());

		}



		for (int i = 0; i < myPackmens.size(); i++) {
			myPackmens.get(i).packLocation = Map.Pixel2coord(myPackmens.get(i).getPack().x(), myPackmens.get(i).getPack().y());

			for (int j = 0; j < myPackmens.get(i).getPath().getCPath().size(); j++) {
				myPackmens.get(i).getPath().getCPath().get(j).Furit =
						Map.Pixel2coord( myPackmens.get(i).getPath().getCPath().get(j).getFruit().x(),  myPackmens.get(i).getPath().getCPath().get(j).getFruit().y());

			}
		}

		LocalDateTime now_start=LocalDateTime.now();
		LocalDateTime temp_start_official=now_start;
		LocalDateTime now_start_end=temp_start_official.plusHours(6);
		LocalDateTime temp_start=now_start;


		int j=(-1);

		for (Packman packman_for : myPackmens)
		{
			j++;

			now_start=	temp_start_official;

			String kmlelement ="<Placemark>\n" +
					"<name><![CDATA[ PACKMAN START "+j+"]]></name>\n" +
					"<description>"+
					"<![CDATA["
					+nameData[0]+": <b> PACKMAN  </b><br/>"
					+nameData[1]+": <b> PACKMAN Start Number"+j+" </b><br/>"
					+nameData[2]+": <b>"+packman_for.packLocation.x()+" </b><br/>" 
					+nameData[3]+": <b>"+packman_for.packLocation.y()+" </b><br/>" 
					+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
					+nameData[5]+": <b>"+packman_for.getrad()+" </b><br/>" // altito to meter


					+"]]></description>\n" +
					"<TimeStamp>\r\n" + 
					"        <when>"+now_start+"</when>\r\n" + 
					"      </TimeStamp>"+
					"<styleUrl>#Packman</styleUrl>"+
					"<Point>\n" +
					"<coordinates>"+packman_for.packLocation.y()+","+packman_for.packLocation.x()+"</coordinates>" +
					"</Point>\n" +
					"</Placemark>\n";


			content.add(kmlelement);


			if(packman_for.getPath().getCPath().size()==0)
			{

				String kmlelement2 ="<Placemark>\n" +
						"<name><![CDATA[ PACKMAN Without PATH "+j +"]]></name>\n" +
						"<description>"+
						"<![CDATA["
						+nameData[0]+": <b> PACKMAN  </b><br/>"
						+nameData[1]+": <b> PACKMAN  Without PATH Number"+j+" </b><br/>"
						+nameData[2]+": <b>"+packman_for.packLocation.x()+" </b><br/>" 
						+nameData[3]+": <b>"+packman_for.packLocation.y()+" </b><br/>" 
						+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
						+nameData[5]+": <b>"+Packmanarr.get(j).getrad()+" </b><br/>" // altito to meter



						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"        <when>"+now_start_end+"</when>\r\n" + 
						"      </TimeStamp>"+
						"<styleUrl>#Packman</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+packman_for.packLocation.y()+","+packman_for.packLocation.x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n";


				content.add(kmlelement2);

			}

			for (int i = 0; i < packman_for.getPath().getCPath().size(); i++) {

				now_start=now_start.plusMinutes(5);
				temp_start=now_start.plusMinutes(10);




				kmlelement ="<Placemark>\n" +
						"<name><![CDATA[ FRUIT "+(i)+",Pac:"+j+"]]></name>\n <description>"+
						"<![CDATA["
						+nameData[0]+": <b> FRUIT  </b><br/>"
						+nameData[1]+": <b> FRUIT Number :"+i+",Pac:"+j+" </b><br/>"
						+nameData[2]+": <b>"+packman_for.getPath().getCPath().get(i).getFruit().x()+" </b><br/>" 
						+nameData[3]+": <b>"+packman_for.getPath().getCPath().get(i).getFruit().y()+" </b><br/>" 
						+nameData[4]+": <b>"+packman_for.getPath().getCPath().get(i).getWeight()+" </b><br/>" 


						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"        <when>"+now_start+"</when>\r\n" + 
						"      </TimeStamp>"+
						"<styleUrl>#Fruit</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+packman_for.getPath().getCPath().get(i).getFruit().y()+","
						+packman_for.getPath().getCPath().get(i).getFruit().x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n";



				content.add(kmlelement);			




				if(i+1==packman_for.getPath().getCPath().size()) temp_start=now_start_end;

				String kmlelement2 ="<Placemark>\n" +
						"<name><![CDATA[ PACKMAN Moving "+j+", "+i+"]]></name>\n" +
						"<description>"+
						"<![CDATA["
						+nameData[0]+": <b> PACKMAN  </b><br/>"
						+nameData[1]+": <b> PACKMAN Moving "+j+", "+i+"</b><br/>"
						+nameData[2]+": <b>"+packman_for.packLocation.x()+" </b><br/>" 
						+nameData[3]+": <b>"+packman_for.packLocation.y()+" </b><br/>" 
						+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
						+nameData[5]+": <b>"+Packmanarr.get(j).getrad()+" </b><br/>" // altito to meter



						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"        <when>"+temp_start+"</when>\r\n" + 
						"      </TimeStamp>"+
						"<styleUrl>#Packman</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+packman_for.getPath().getCPath().get(i).getFruit().y()+","
						+packman_for.getPath().getCPath().get(i).getFruit().x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n";


				content.add(kmlelement2);

			}


		}

		content.add(kmlend);
		pw.write(String.join("\n", content));
		System.out.println("Operation Complete");
		pw.close();
	} 

}











