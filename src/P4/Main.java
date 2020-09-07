package P4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Lollol99"
            );

            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);

            testDAO(rdao);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDAO(ReizigerDAOPsql rdao) {

        System.out.println("------------------------------------------------ReizigerDAO------------------------------------------------");
        List<Reiziger> reizigers = rdao.findAll();

        //print alle reizigers met hun bijbehorende adres
        int aantal = 0;
        if (!reizigers.isEmpty()) {
            for (Reiziger a : reizigers) {
                aantal += 1;
                System.out.println(a);
            }
        }
        System.out.println("------------[" + aantal + " reizigers getoond]------------\n");

        //print alle kaarten
        List<OVChipkaart> kaarten = rdao.getOdao().findall();
        aantal = 0;
        if(!kaarten.isEmpty()) {
            for(OVChipkaart o : kaarten) {
                aantal += 1;
                System.out.println(o);
            }
        }
        System.out.println("------------[" + aantal + " OV chipkaarten getoond]------------\n");

        //saved een nieuwe reiziger r1
        Reiziger r1 = new Reiziger(6, "R", "van den", "Brink", Date.valueOf("2002-04-30"));
        if(rdao.save(r1)) {
                System.out.println("[Nieuwe reiziger aangemaakt]");
         } else {
            System.out.println("Gebruiker niet gesaved omdat er al een gebruiker met dit id al bestaat.");
        }

        //gooit nieuwe ovchipkaarten in de lijst ovkaarten van de nieuwe reiziger r1
        OVChipkaart o1 = new OVChipkaart(11111, Date.valueOf("2023-04-30"), 2, 50.50, 6);
        r1.getOvChipkaarten().add(o1);

        OVChipkaart o2 = new OVChipkaart(22222, Date.valueOf("2023-05-30"), 1, 27.50, 6);
        r1.getOvChipkaarten().add(o2);

        OVChipkaart o3 = new OVChipkaart(33333, Date.valueOf("2022-04-30"), 1, 21.50, 6);
        r1.getOvChipkaarten().add(o3);

        //saved de gehele lijst van ovchipkaarten van reiziger r1 in de database
        for (OVChipkaart o : r1.getOvChipkaarten()) {
                if(rdao.getOdao().save(o)) {
                    System.out.println("[OV chipkaart opgeslagen]");
                } else {
                    System.out.println("[OV chipkaart niet opgeslagen, bestaat al!]");
                }
        }
        }
    }