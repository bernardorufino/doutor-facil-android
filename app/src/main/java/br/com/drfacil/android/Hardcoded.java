package br.com.drfacil.android;

import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Specialty;
import com.google.common.collect.ImmutableList;

import java.util.List;

/* TODO: Permanent TODO until this file gets deleted */
public class Hardcoded {

    public static List<Insurance> INSURANCES = ImmutableList.of(
            new Insurance("Sulamerica"),
            new Insurance("Unimed"),
            new Insurance("Amil"),
            new Insurance("Porto Seguro"),
            new Insurance("Bradesco"),
            new Insurance("Liberty")
    );

    public static List<Specialty> SPECIALTIES = ImmutableList.of(
            new Specialty("Cirurgião"),
            new Specialty("Podólogo"),
            new Specialty("Ajudante"),
            new Specialty("Neurologista"),
            new Specialty("Cardiologista"),
            new Specialty("Pediatra"),
            new Specialty("Otorrinolaringologista"),
            new Specialty("Pediatra"),
            new Specialty("Ginecologista"),
            new Specialty("Androlaringolista"),
            new Specialty("Urologista"),
            new Specialty("Angiologista"),
            new Specialty("Dermatologista"),
            new Specialty("Oftalmologista"),
            new Specialty("Oncologista"),
            new Specialty("Mastologista"),
            new Specialty("Cagurologista")
    );

    public static List<Professional> PROFESSIONALS = ImmutableList.of(
            new Professional(
                    1, "André Saraiva",
                    new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Cirurgião",
                    "https://scontent-a-mia.xx.fbcdn.net/hphotos-ash3/t1.0-9/1558596_10202889535225577_1261402839_n.jpg",
                    5),
            new Professional(
                    2, "Samuel Flávio Pomba",
                    new Address(2, "Av. Adilson Seroa da Motta", "22621-290", "103", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Cirurgião",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c49.49.617.617/s160x160/601429_500061950087868_1709153825_n.jpg",
                    3),
            new Professional(
                    3, "Bruno Almeida Estima",
                    new Address(3, "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                    "Cirurgião",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c1.0.666.666/s160x160/1377243_441574975962329_298782807_n.jpg",
                    4),
            new Professional(
                    5, "Cássio dos Santos",
                    new Address(5, "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                    "Pediatra",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c170.50.621.621/s160x160/307956_4073677414871_1654557525_n.jpg",
                    2),
            new Professional(
                    1, "Harry Luiz Felipe Ramos Potter",
                    new Address(1, "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Cardiologista",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c0.2.385.385/s160x160/531935_468245489905565_2092884356_n.jpg",
                    5),
            new Professional(
                    1, "Vitor Carvalho",
                    new Address(1, "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                    "Otorrinolaringonaosei",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c35.35.441.441/s160x160/263034_127614417320443_4195035_n.jpg",
                    4),
            new Professional(
                    3, "Bernardo Rufino",
                    new Address(3, "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                    "Carteador",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c15.15.185.185/s160x160/264063_10150233494897807_6629474_n.jpg",
                    4),
            new Professional(
                    4, "Felipe Croata Pererira",
                    new Address(4, "Av. Engenheiro Fressayt", "12313-211", "102", "", "São Paulo", "SP", "Brazil"),
                    "Obstetra",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c50.50.621.621/s160x160/382387_10150942488546169_537955621_n.jpg",
                    3),
            new Professional(
                    5, "Adan Vitor Carvalho",
                    new Address(5, "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                    "Psicólogo",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c24.0.160.160/p160x160/1512622_573965186024230_1868819654_n.jpg",
                    2),
            new Professional(
                    1, "Rodrigo Roim",
                    new Address(1, "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Ajudante",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c2.0.160.160/p160x160/64568_10152205905857905_1012952160_n.jpg",
                    5),
            new Professional(
                    1, "Renan Cruzeiro",
                    new Address(1, "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                    "Cirurgião",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c63.9.115.115/599495_386591934733083_1569059707_n.jpg",
                    4),
            new Professional(
                    1, "João da Silva",
                    new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Podólogo",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/t1.0-1/p160x160/1375321_496424843786819_1968729283_n.jpg",
                    2),
            new Professional(
                    1, "João da Silva",
                    new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                    "Neuroalgumacoisa",
                    "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/t1.0-1/p160x160/971516_520668877987014_1987117223_n.jpg",
                    3)
    );

}
