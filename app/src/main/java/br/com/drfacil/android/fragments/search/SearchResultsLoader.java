package br.com.drfacil.android.fragments.search;

import android.content.Context;
import br.com.drfacil.android.ext.loader.AsyncDataLoader;
import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Professional;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class SearchResultsLoader extends AsyncDataLoader<List<Professional>> {

    public SearchResultsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Professional> loadInBackground() {
        return ImmutableList.of(
                new Professional(
                        1, "João da Silva",
                        new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                        5),
                new Professional(
                        2, "Bernardo Rufino",
                        new Address(2, "Av. Adilson Seroa da Motta", "22621-290", "103", "", "Rio de Janeiro", "RJ", "Brazil"),
                        3),
                new Professional(
                        3, "Daniela Bayma de Almeida",
                        new Address(3, "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                        4),
                new Professional(
                        4, "Felipe Mendes dos Santos",
                        new Address(4, "Av. Engenheiro Fressayt", "12313-211", "102", "", "São Paulo", "SP", "Brazil"),
                        3),
                new Professional(
                        5, "Fernando Joaquim",
                        new Address(5, "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                        2),
                new Professional(
                        1, "Fogão",
                        new Address(1, "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                        5),
                new Professional(
                        1, "Prefeiro",
                        new Address(1, "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                        4),
                new Professional(
                        3, "Daniela Bayma de Almeida",
                        new Address(3, "Av. João de Abrantes ", "31984-453", "123", "", "São José dos Campos", "SP", "Brazil"),
                        4),
                new Professional(
                        4, "Felipe Mendes dos Santos",
                        new Address(4, "Av. Engenheiro Fressayt", "12313-211", "102", "", "São Paulo", "SP", "Brazil"),
                        3),
                new Professional(
                        5, "Fernando Joaquim",
                        new Address(5, "Rua Joaquim", "22312-203", "1001", "", "Juiz de Fora", "MG", "Brazil"),
                        2),
                new Professional(
                        1, "Fogão",
                        new Address(1, "Rua que saco", "939123-900", "900", "", "Rio de Janeiro", "RJ", "Brazil"),
                        5),
                new Professional(
                        1, "Prefeiro",
                        new Address(1, "Vai se fude, porra de mock", "12039123", "129", "", "Vassouras", "Pás", "País das Maravilhas"),
                        4),
                new Professional(
                        1, "João da Silva",
                        new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                        2),
                new Professional(
                        1, "João da Silva",
                        new Address(1, "Rua H22A", "12228-453", "123", "", "Rio de Janeiro", "RJ", "Brazil"),
                        3));
    }

    @Override
    protected void releaseResources(List<Professional> data) {
        /* No-op */
    }

    @Override
    protected void startObserving() {

    }

    @Override
    protected void stopObserving() {

    }
}
