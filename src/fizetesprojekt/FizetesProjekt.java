package fizetesprojekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FizetesProjekt {

    private List<String> sorok;
    private SzemelyesAdat[] szemAdat;
    private List<String> budapestiek;

    public static void main(String[] args) throws IOException {
        new FizetesProjekt().feladatok();
    }

    public FizetesProjekt() throws IOException {
        sorok = Files.readAllLines(Path.of("szem_adatok2.txt"));
        szemAdat = new SzemelyesAdat[sorok.size() - 1];
        for (int i = 1; i < sorok.size(); i++) {

            szemAdat[i - 1] = new SzemelyesAdat(sorok.get(i));

        }
    }

    private void feladatok() throws IOException {
        feladata();
        Linebreak();
        feladat1();
        Linebreak();
        feladat2();
        Linebreak();
        feladat3();
        Linebreak();
        feladat4();
        Linebreak();
        feladat5();
        Linebreak();
        feladat6();
        Linebreak();
        feladat7();
        Linebreak();

    }

    private void feladata() throws IOException {

        int i = sorok.size();
        System.out.printf("a fájl fejléccel eggyütt %d sort tartalmaz \n", i);
    }

    private void feladat1() {
        System.out.println("1.feladat");
        System.out.println("Ki keresi a legtöbbet?:");
        int index = 0;
        for (int i = 1; i < szemAdat.length; i++) {
            if (szemAdat[i].getFizetes() > szemAdat[index].getFizetes()) {
                index = i;
            }

        }
        System.out.println(szemAdat[index].getNev() + " keresi a legtöbbet");
        String nev = szemAdat[index].getNev();
        assert nev.equals("Xénia") : "1. hibás válasz";

    }

    private void feladat2() {
        System.out.println("2.feladat");
        System.out.println("mennyi az átlagfizetés?");
        int sum = 0, osszSzam = szemAdat.length;
        for (int i = 0; i < szemAdat.length; i++) {
            sum += szemAdat[i].getFizetes();
        }
        double atlag = sum / osszSzam;
        System.out.println(atlag + " Ft");
        assert atlag == 501_800: "2. hibás válasz";
    }

    private void feladat3() {
        System.out.println("3.feladat");
        System.out.println("mindenki budapesti?");
        boolean budapestMind;
        int i = 0, N = szemAdat.length;
        /* for (int j = 0; j < szemAdat.length; j++) {
            if (szemAdat[i].getCim().equals("Budapest")) {
                budapestiek.add(szemAdat[i].getCim());
                
            }
            System.out.println(budapestiek);
        }*/
        while (i < N && szemAdat[i].getCim().equals("Budapest")) {
            i++;

        }
        budapestMind = i > N;
        System.out.println(budapestMind + " index: " + i + " " + szemAdat[i].getCim());
        
        assert budapestMind == false: "3. hibás válasz";
        assert i <= 1 : "3. hibás progTétel"; 

    }

    private void feladat4() {
        System.out.println("4.feladat");
        int i = 0, N = szemAdat.length;
        boolean vane20felettiBP;
        int index = 0;
        System.out.println("van e 20 év feletti budapesti?");

        while (i < N && !(szemAdat[i].getCim().equals("Bp")) && szemAdat[i].getKor() >= 20) {
          
            i++;
        }
        vane20felettiBP = i < N;
        if (vane20felettiBP) {
            System.out.println(vane20felettiBP + " index: " +  i +" " + szemAdat[i].getKor() + "éves");
        } else {
            System.out.println("nincs");
        }
        
        assert vane20felettiBP == true: "4. hibás válasz";
        assert i == 0 : "4. hibás progTétel";
    }

    private void feladat5() {
        System.out.println("5.feladat");
        System.out.println("Milyen címek vannak eltárolva?:");
        HashSet<String> varosok = new HashSet<>();
        for (SzemelyesAdat adat : szemAdat) {
            varosok.add(adat.getCim());
        }
        System.out.println(varosok);
        assert varosok.size() == 1 : "5. hibás méret";
	assert varosok.contains("Bp") : "5. hibás adat";

    }

    private void feladat6() {
        System.out.println("6.feladat");
        System.out.println("melyik címen hányan laknak? ");
        Map<String, Integer> varosokDB = new HashMap<>();
        for (SzemelyesAdat adat : szemAdat) {
            String kulcs = adat.getCim();
            if (varosokDB.containsKey(kulcs)) {
                int ertek = varosokDB.get(kulcs);
                varosokDB.put(kulcs, ++ertek);

            } else {
                varosokDB.put(kulcs, 1);
            }

        }
        for (Map.Entry<String, Integer> entry : varosokDB.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.printf("%-11s: %4d db \n", key, value);
        }
        assert varosokDB.size() == 1 : "6. hibás méret";
	assert varosokDB.containsKey("Bp") : "6. hibás kulcs";
	assert varosokDB.get("Bp") == 5 : "6. hibás érték";
    }

    private void Linebreak() {
        System.out.println("-------------------------");
    }

    private void feladat7() throws IOException {
        System.out.println("7.feladat");
        System.out.println("nem budapestiek minden adata a nemBp.txt");
        List<SzemelyesAdat> nemBPlakosok = new ArrayList<>();
        List<SzemelyesAdat> BPlakosok = new ArrayList<>();
        int i = 0;
        for (SzemelyesAdat adat : szemAdat) {
            if (!(adat.getCim().equals("Bp"))) {
                nemBPlakosok.add(adat);
            }
        }

        String kimenet = "";
        for (SzemelyesAdat adat : nemBPlakosok) {
            kimenet += adat + "\n";

        }
        Files.writeString(Path.of("nemBp.txt"), kimenet);
    }

}
