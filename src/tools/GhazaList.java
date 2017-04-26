package tools;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GhazaList {
	
	private ArrayList<Ghaza> ghazas = new ArrayList<Ghaza>();
	
	private ArrayList<Ghaza> sobhane = new ArrayList<Ghaza>();
	private ArrayList<Ghaza> nahar = new ArrayList<Ghaza>();
	private ArrayList<Ghaza> sham = new ArrayList<Ghaza>();


	public void addGhaza(Ghaza ghaza) {
		ghazas.add(ghaza);
		
		if(ghaza.nameId.startsWith("txts") || ghaza.nameId.contains("S")) {
			sham.add(ghaza);
		}
		else if(ghaza.nameId.startsWith("txtn") || ghaza.nameId.contains("N")) {
			nahar.add(ghaza);
		}
		else if(ghaza.nameId.startsWith("txtc") || ghaza.nameId.contains("C")) {
			sobhane.add(ghaza);
		}
	}
	
	public ArrayList<Ghaza> getAllGhazas(){

		return ghazas;
	}
	
	public ArrayList<Ghaza> getSobhane() {
		ArrayList<Ghaza> res = new ArrayList<Ghaza>();
		res.addAll(sobhane);
		return res;
	}
	
	public ArrayList<Ghaza> getNahar() {
		ArrayList<Ghaza> res = new ArrayList<Ghaza>();
		res.addAll(nahar);
		return res;
	}
	
	public ArrayList<Ghaza> getSham() {
		ArrayList<Ghaza> res = new ArrayList<Ghaza>();
		res.addAll(sham);
		return res;
	}

	
}
