package br.com.drfacil.android;

import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Appointment;
import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Specialty;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

/* TODO: Permanent TODO until this file gets deleted */
public class Hardcoded {

    public static List<Insurance> INSURANCES = ImmutableList.of(
            new Insurance("1","Sulamerica"),
            new Insurance("2","Unimed"),
            new Insurance("3","Amil"),
            new Insurance("4","Porto Seguro"),
            new Insurance("5","Bradesco"),
            new Insurance("6","Liberty")
    );

    public static List<Specialty> SPECIALTIES = ImmutableList.of(
            new Specialty("1", "Cirurgião"),
            new Specialty("2", "Podólogo"),
            new Specialty("3", "Ajudante"),
            new Specialty("4", "Neurologista"),
            new Specialty("5", "Cardiologista"),
            new Specialty("6", "Pediatra"),
            new Specialty("7", "Otorrinolaringologista"),
            new Specialty("8", "Pediatra"),
            new Specialty("9", "Ginecologista"),
            new Specialty("10", "Androlaringolista"),
            new Specialty("11", "Urologista"),
            new Specialty("12", "Angiologista"),
            new Specialty("13", "Dermatologista"),
            new Specialty("14", "Oftalmologista"),
            new Specialty("15", "Oncologista"),
            new Specialty("16", "Mastologista"),
            new Specialty("17", "Cagurologista")
    );

    public static List<Professional> PROFESSIONALS = ImmutableList.of(
            new Professional(
                    "1", "André Saraiva",
                    new Address("1", "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(0)),
                    Arrays.asList(INSURANCES.get(0)),
                    "https://scontent-a-mia.xx.fbcdn.net/hphotos-ash3/t1.0-9/1558596_10202889535225577_1261402839_n.jpg",
                    5),
            new Professional(
                    "2", "Samuel Flávio Pomba",
                    new Address("2", "Av. Adilson Seroa da Motta", "22621-290", "103", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(1)),
                    Arrays.asList(INSURANCES.get(1)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c49.49.617.617/s160x160/601429_500061950087868_1709153825_n.jpg",
                    3),
            new Professional(
                    "3", "Bruno Almeida Estima",
                    new Address("3", "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(3)),
                    Arrays.asList(INSURANCES.get(2)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c1.0.666.666/s160x160/1377243_441574975962329_298782807_n.jpg",
                    4),
            new Professional(
                    "4", "Cássio dos Santos",
                    new Address("5", "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(4)),
                    Arrays.asList(INSURANCES.get(3)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c170.50.621.621/s160x160/307956_4073677414871_1654557525_n.jpg",
                    2),
            new Professional(
                    "5", "Harry Luiz Felipe Ramos Potter",
                    new Address("1", "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(7)),
                    Arrays.asList(INSURANCES.get(4)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c0.2.385.385/s160x160/531935_468245489905565_2092884356_n.jpg",
                    5),
            new Professional(
                    "6", "Vitor Carvalho",
                    new Address("1", "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                    Arrays.asList(SPECIALTIES.get(8)),
                    Arrays.asList(INSURANCES.get(5)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c35.35.441.441/s160x160/263034_127614417320443_4195035_n.jpg",
                    4),
            new Professional(
                    "7", "Bernardo Rufino",
                    new Address("3", "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(9)),
                    Arrays.asList(INSURANCES.get(4)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c15.15.185.185/s160x160/264063_10150233494897807_6629474_n.jpg",
                    4),
            new Professional(
                    "8", "Felipe Croata Pererira",
                    new Address("4", "Av. Engenheiro Fressayt", "12313-211", "102", "", "São Paulo", "SP", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(10)),
                    Arrays.asList(INSURANCES.get(3)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c50.50.621.621/s160x160/382387_10150942488546169_537955621_n.jpg",
                    3),
            new Professional(
                    "9", "Adan Vitor Carvalho",
                    new Address("5", "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(11)),
                    Arrays.asList(INSURANCES.get(2)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c24.0.160.160/p160x160/1512622_573965186024230_1868819654_n.jpg",
                    2),
            new Professional(
                    "10", "Rodrigo Roim",
                    new Address("1", "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(12)),
                    Arrays.asList(INSURANCES.get(1)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c2.0.160.160/p160x160/64568_10152205905857905_1012952160_n.jpg",
                    5),
            new Professional(
                    "11", "Renan Cruzeiro",
                    new Address("1", "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                    Arrays.asList(SPECIALTIES.get(15)),
                    Arrays.asList(INSURANCES.get(0)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c63.9.115.115/599495_386591934733083_1569059707_n.jpg",
                    4),
            new Professional(
                    "12", "João da Silva",
                    new Address("1", "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(16)),
                    Arrays.asList(INSURANCES.get(4)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/t1.0-1/p160x160/1375321_496424843786819_1968729283_n.jpg",
                    2),
            new Professional(
                    "13", "João da Silva",
                    new Address("1", "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    Arrays.asList(SPECIALTIES.get(2)),
                    Arrays.asList(INSURANCES.get(5)),
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/t1.0-1/p160x160/971516_520668877987014_1987117223_n.jpg",
                    3)
    );

//    public static void main(String[] args) {
//        System.out.println("INSURANCES = {");
//        for (Insurance insurance : INSURANCES) {
//            System.out.println("  " + insurance.getId() + " => Insurance.create(");
//            System.out.println("    id: " + insurance.getId()+ ",");
//            System.out.print("    name: \"" + insurance.getName() + "\")");
//            if (INSURANCES.get(INSURANCES.size()-1) != insurance) {
//                System.out.println(", ");
//            } else {
//                System.out.println();
//            }
//        }
//        System.out.println("}");
//        System.out.println("SPECIALTIES = {");
//        for (Specialty specialty : SPECIALTIES) {
//            System.out.println("  " + specialty.getId() + " => Specialty.create(");
//            System.out.println("    id: " + specialty.getId() + ",");
//            System.out.print("    name: \"" + specialty.getName() + "\")");
//            if (SPECIALTIES.get(SPECIALTIES.size()-1) != specialty) {
//                System.out.println(", ");
//            } else {
//                System.out.println();
//            }
//        }
//        System.out.println("}");
//        System.out.println("PROFESSIONALS = {");
//        for (Professional professional : PROFESSIONALS) {
//            Address address = professional.getAddress();
//            System.out.println("  " + professional.getId() + " => Professional.create(");
//            System.out.println("    id: " + professional.getId() + ",");
//            System.out.println("    name: \"" + professional.getName() + "\",");
//            System.out.println("    address: Address.create(");
//            System.out.println("      id: " + address.getId() + ",");
//            System.out.println("      street: \"" + address.getStreet() + "\",");
//            System.out.println("      zip: \"" + address.getZip() + "\",");
//            System.out.println("      number: \"" + address.getNumber() + "\",");
//            System.out.println("      complement: \"" + address.getComplement() + "\",");
//            System.out.println("      city: \"" + address.getCity() + "\",");
//            System.out.println("      state: \"" + address.getState() + "\",");
//            System.out.println("      country: \"" + address.getCountry() + "\"),");
//            System.out.println("    specialty: SPECIALTIES[" + professional.getSpecialties().getId() + "],");
//            System.out.println("    insurance: INSURANCES[" + professional.getInsurances().getId() + "],");
//            System.out.println("    image_url: \"" + professional.getImageUrl() + "\",");
//            System.out.print("    rating: " + professional.getRating() + ")");
//            if (PROFESSIONALS.get(PROFESSIONALS.size()-1) != professional) {
//                System.out.println(", ");
//            } else {
//                System.out.println();
//            }
//        }
//        System.out.println("}");
//    }

    public static List<Appointment> APPOINTMENTS = ImmutableList.of(
            new Appointment("1", PROFESSIONALS.get(0), DateTime.now()),
            new Appointment("2", PROFESSIONALS.get(1), DateTime.now().plusDays(1)),
            new Appointment("3", PROFESSIONALS.get(2), DateTime.now().plusDays(2)),
            new Appointment("4", PROFESSIONALS.get(3), DateTime.now().plusDays(3)),
            new Appointment("5", PROFESSIONALS.get(4), DateTime.now().plusDays(4)));

}
